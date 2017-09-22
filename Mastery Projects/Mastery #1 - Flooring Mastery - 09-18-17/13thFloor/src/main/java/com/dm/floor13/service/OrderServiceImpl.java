/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.service;

import com.dm.floor13.dao.OrderDao;
import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.dao.ProductDataDao;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.dao.ProductDataDaoImpl;
import com.dm.floor13.dao.StateDataDao;
import com.dm.floor13.dao.StateDataDaoImpl;
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
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;
    ProductDataDao productDao;
    StateDataDao stateDao;

    public OrderServiceImpl(OrderDao orderDao,
            StateDataDao stateDao,
            ProductDataDao productDao) {

        this.orderDao = orderDao;
        this.stateDao = stateDao;
        this.productDao = productDao;

        loadData();
    }

    private void loadData() {

        try {
            orderDao.readAllOrdersFromDirectory();
            stateDao.readDataFromFile();
            productDao.readDataFromFile();
        } catch (FileSkipException e) {

        }
    }

    @Override
    public void fetchOrders() {

    }

    @Override
    public Order addUpdateOrder(Order order) throws ChangeOrderException {
        populateOrder(order);
        return orderDao.addUpdateOrder(order);
    }

    @Override
    public boolean removeOrder(String orderNumber) throws OrderNotFoundException,
            ChangeOrderException {

        orderDao.removeOrder(orderNumber);
        if (orderDao.orderExists(orderNumber)) {
            return false;
        }
        
        return true;

    }

    @Override
    public List<Order> getOrder(String orderNumber) throws OrderNotFoundException {
        return orderDao.getOrder(orderNumber);
    }

    @Override
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

        if (!productDao.isProduct(p.getProductName())) {
            return false;
        }

        if (!stateDao.isState(s.getStateCode())) {
            return false;
        }
        
        populateOrder(order);

        return true;

    }

    public Order populateOrder(Order order) {
        boolean exists = orderDao.orderExists(order.getOrderNumber());

        if (!exists) {
            try {
                String stateCode = order.getState().getStateCode();
                order.setState(stateDao.getState(stateCode));

                String productName = order.getProduct().getProductName();
                order.setProduct(productDao.getProduct(productName));
            } catch (MissingDataException e) {
                // data integrity is verified in the validation step, therefore
                // this catch should never be reached
            }
        } else {

            // We check to see if the state or product were changed. If so, we need to update
            // the values.  Otherwise, we do not want to change these when we update the order
            try {
                Order oldOrder = orderDao.getOrder(order.getOrderNumber()).get(0);

                if (!order.getState().getStateCode().equals(oldOrder.getState().getStateCode())) {

                    String stateCode = order.getState().getStateCode();
                    order.setState(stateDao.getState(stateCode));
                }

                if (!order.getProduct().getProductName().equals(oldOrder.getProduct().getProductName())) {
                    String productName = order.getProduct().getProductName();
                    order.setProduct(productDao.getProduct(productName));
                }

            } catch (OrderNotFoundException | MissingDataException e) {
                // data integrity is verified in the validation step, therefore
                // this catch should never be reached
            }

        }

        order.setRevisionDate(LocalDateTime.now());
        order.recalculateData();
        return order;
    }

}
