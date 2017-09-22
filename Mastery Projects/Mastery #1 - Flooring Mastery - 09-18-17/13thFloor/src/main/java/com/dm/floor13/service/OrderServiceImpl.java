/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.service;

import com.dm.floor13.dao.ChangeOrderException;
import com.dm.floor13.dao.MissingDataException;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.dao.OrderNotFoundException;
import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class OrderServiceImpl {

    OrderDaoImpl orderDao;

    public OrderServiceImpl(OrderDaoImpl dao) {
        this.orderDao = dao;
    }

    public Order addUpdateOrder(Order order) throws ChangeOrderException {
        return orderDao.addUpdateOrder(order);
    }

    public boolean removeOrder(String orderNumber) throws OrderNotFoundException,
            ChangeOrderException {

        orderDao.removeOrder(orderNumber);
        return true;

    }

    public List<Order> getOrder(String orderNumber) throws OrderNotFoundException {
        return orderDao.getOrder(orderNumber);
    }

    public Order populateOrder(Order order) {
        boolean exists = orderDao.orderExists(order.getOrderNumber());
        boolean updateState = !exists;
        boolean updateProduct = updateState;

//        try {
//            if (exists && orderDao.getOrder(order.getOrderNumber()).size() > 1) {
//
//                if (order.getState().equals(order)) {
//
//                }
//
//            }
//        } catch (OrderNotFoundException e) {
//
//        }

        if (!exists) {
            try {
                String stateCode = order.getState().getStateCode();
                order.setState(orderDao.getState(stateCode));

                String productName = order.getProduct().getProductName();
                order.setProduct(orderDao.getProduct(productName));

                order.setRevisionDate(LocalDateTime.now());

            } catch (MissingDataException e) {
                // data integrity is verified in the validation step, therefore
                // this catch should never be reached
            }
        }
        order.recalculateData();
        return order;
    }

    public boolean validateOrder(Order order) {
        boolean exists = orderDao.orderExists(order.getOrderNumber());

        if (order.getArea() == null
                || order.getDate() == null
                || order.getProduct() == null
                || order.getState() == null
                || order.getFirstName() == null
                || order.getLastName() == null) {
            return false;
        }

        if (exists) {
            try {
                Order exOrder = orderDao.getOrder(order.getOrderNumber()).get(0);
                if (order.getFirstName().trim().isEmpty()) {
                    order.setFirstName(exOrder.getFirstName());
                }
                if (order.getLastName().trim().isEmpty()) {
                    order.setLastName(exOrder.getLastName());
                }
                if (order.getProduct().getProductName().trim().isEmpty()) {
                    order.setProduct(exOrder.getProduct());
                }
                if (order.getState().getStateCode().trim().isEmpty()) {
                    order.setState(exOrder.getState());
                }
                if (order.getArea().compareTo(BigDecimal.ZERO) < 0) {
                    order.setArea(exOrder.getArea());
                }
                if (order.getDate().compareTo(LocalDate.of(1975, 01, 01)) <= 0) {
                    order.setDate(exOrder.getDate());
                }
            } catch (OrderNotFoundException e) {

            }
        } else {
            if (order.getFirstName().trim().isEmpty()
                    || order.getLastName().trim().isEmpty()
                    || order.getState().getStateCode().trim().isEmpty()
                    || order.getProduct().getProductName().isEmpty()) {
                return false;
            }
            if (order.getArea().compareTo(BigDecimal.ZERO) < 0) {
                return false;
            }
            if (order.getDate().compareTo(LocalDate.of(1975, 01, 01)) <= 0) {
                return false;
            }
        }

        Product p = order.getProduct();
        State s = order.getState();

        if (!orderDao.isProduct(p.getProductName())) {
            return false;
        }

        if (!orderDao.isState(s.getStateCode())) {
            return false;
        }

        return true;

    }

}
