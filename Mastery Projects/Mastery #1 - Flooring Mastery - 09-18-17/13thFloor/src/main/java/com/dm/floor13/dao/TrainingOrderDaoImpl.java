/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.model.Order;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danimaetrix
 */
public class TrainingOrderDaoImpl extends OrderDaoImpl {

    public static final String MODE = "TRAINING";

    public TrainingOrderDaoImpl(String dir) {
        super.orderDir = dir;
    }

    public TrainingOrderDaoImpl(String dir, FileHandler handler) {
        super.orderHandler = handler;
        super.orderDir = dir;
    }

    @Override
    public boolean getMode() {
        return true;
    }

    @Override
    public boolean removeOrder(Order order) throws OrderNotFoundException, ChangeOrderException {
        String orderNumber = order.getOrderNumber();

        if (orderMap.containsKey(orderNumber)) {
            orderMap.remove(orderNumber);

            if (orderExists(orderNumber)) {
                throw new ChangeOrderException("Problem removing order... ");
            }
            return true;
        } else {
            throw new OrderNotFoundException("Error: order does not exist...");
        }

    }

    @Override
    public Order addUpdateOrder(Order order) throws ChangeOrderException {

        if (!orderExists(order.getOrderNumber())) {
            List<Order> newOrder = new ArrayList<>();
            order.setOrderNumber(Integer.toString(super.currentOrderNumber + 1));
            order.setOrderStatus(false);
            newOrder.add(order);

            order.recalculateData();
            orderMap.put(order.getOrderNumber(), newOrder);

            if (!orderMap.get(order.getOrderNumber()).get(0).equals(order)) {
                currentOrderNumber = currentOrderNumber - 1;
                order.setOrderStatus(false);
                order.setOrderNumber(null);
                throw new ChangeOrderException("Problem adding new order... ");
            }

            currentOrderNumber = currentOrderNumber + 1;

        } else {

            // Order already exists, now we append it instead of adding a new order
            List<Order> oldOrder = orderMap.get(order.getOrderNumber());
            order.recalculateData();
            oldOrder.add(0, order);
            orderMap.put(order.getOrderNumber(), oldOrder);

            if (orderMap.get(order.getOrderNumber()).get(0) != order) {
                oldOrder.remove(0);
                orderMap.put(order.getOrderNumber(), oldOrder);
                throw new ChangeOrderException("Problem adding new order... ");
            }
        }
        return order;
    }

}
