/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.service;

import com.dm.floor13.dao.ChangeOrderException;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.dao.OrderNotFoundException;
import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
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

    public boolean validateOrder(Order order) {

        order.recalculateData();

        if (order.getArea() == null
                || order.getState() == null
                || order.getProduct() == null
                || order.getDate() == null
                || order.getRevisionDate() == null
                || order.getFirstName() == null
                || order.getLastName() == null) {
            return false;
        }
        
        Product p = order.getProduct();
        State s = order.getState();
        
        if (!orderDao.isProduct(p.getProductName())){
            return false;
        }
        
        if (!orderDao.isState(s.getStateCode())){
            return false;
        }
        
        
        
        
        

        return true;

    }

}
