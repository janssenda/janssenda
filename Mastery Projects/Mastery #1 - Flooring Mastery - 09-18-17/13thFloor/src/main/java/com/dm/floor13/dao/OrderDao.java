/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author danimaetrix
 */
public class OrderDao {

    private Map<String, Order> orderMap;
    private int orderNumberLength;
    private String currentDir;

    public OrderDao() {
        this.currentDir = "./orders";
        this.orderNumberLength = 5;
    }

    public OrderDao(String directory) {
        this.currentDir = directory;
        this.orderNumberLength = 5;
    }

    public Map<String, Order> readAllOrdersFromDirectory() {

        FileHandler orderHandler = new FileHandler();
        List<Order> orders = orderHandler.readAllOrders(currentDir, orderNumberLength);

        this.orderMap = orders.stream()
                .collect(Collectors.toMap(Order::getOrderNumber, o -> o));
        
//        this.orderMap.forEach((k, v) -> {
//            v.setRevisionDate(LocalDateTime.now());
//        });

        return this.orderMap;
    }

    public void writeOrdersToDirectory(String path) {
        FileHandler orderHandler = new FileHandler();
        orderHandler.writeAllOrdersSplitFilesByDate(orderMap, currentDir + path);
    }

    public void removeOrder(String orderNumber) throws OrderNotFoundException {
        if (orderMap.containsKey(orderNumber)) {
            orderMap.remove(orderNumber);
        } else {
            throw new OrderNotFoundException("Error: order does not exist...");
        }

    }

    public Order getOrder(String orderNumber) throws OrderNotFoundException {
        if (orderMap.containsKey(orderNumber)) {
            return orderMap.get(orderNumber);
        } else {
            throw new OrderNotFoundException("Error: order does not exist...");
        }
    }

    public void addOrder(String orderNumber, Order order) {
        orderMap.put(orderNumber, order);
    }

    public String getcurrentDir() {
        return currentDir;
    }

    public void setcurrentDir(String directory) {
        this.currentDir = directory;
    }

    public int getOrderNumberLength() {
        return orderNumberLength;
    }

    public void setOrderNumberLength(int orderNumberLength) {
        this.orderNumberLength = orderNumberLength;
    }

}
