/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.service;

import com.dm.floor13.dao.FileHandler;
import com.dm.floor13.dao.OrderDao;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.dao.ProductDataDao;
import com.dm.floor13.dao.SearchMethod;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.dao.StateDataDao;
import com.dm.floor13.dao.TrainingOrderDaoImpl;
import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author danimaetrix
 */
public class OrderServiceImpl implements OrderService {

    private String orderDir, productList, stateList;
    OrderDao orderDao;
    ProductDataDao productDao;
    StateDataDao stateDao;
    boolean mode = true;

    public OrderServiceImpl(OrderDao orderDao,
            StateDataDao stateDao,
            ProductDataDao productDao) {

        readDataFiles("floor13.cfg");
        if (mode) {
            this.orderDao = new TrainingOrderDaoImpl(orderDir);
        } else {
            this.orderDao = new OrderDaoImpl(orderDir);
        }

        this.stateDao = stateDao;
        this.productDao = productDao;
        
        loadData();
        
    }

    public OrderServiceImpl(StateDataDao stateDao,
            ProductDataDao productDao, 
            FileHandler handler, String dir, String cfgPath) {

        readDataFiles(cfgPath);

        if (mode) {
            this.orderDao = new TrainingOrderDaoImpl(dir, handler);
        } else {
            this.orderDao = new OrderDaoImpl(dir, handler);
        }

        this.stateDao = stateDao;
        this.productDao = productDao;

        loadData();
    }

    private void loadData() {

        try {
            stateDao.readDataFromFile(stateList);
            productDao.readDataFromFile(productList);            
            orderDao.readAllOrdersFromDirectory(orderDir);    
        } catch (FileSkipException e) {

        }

    }

    private void readDataFiles(String cfgPath) {
        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(cfgPath)));
            this.mode = Boolean.parseBoolean(sc.nextLine().split("=")[1].trim());
            this.orderDir = sc.nextLine().split("=")[1].trim();
            this.stateList = sc.nextLine().split("=")[1].trim();
            this.productList = sc.nextLine().split("=")[1].trim();
            
            
        } catch (FileNotFoundException e) {

        }

    }

    @Override
    public List<Order> findOrders(SearchMethod method, Object key) {
        return orderDao.findOrders(method, key);
    }

    @Override
    public Order addUpdateOrder(Order order) throws ChangeOrderException {
        populateOrder(order);
        return orderDao.addUpdateOrder(order);
    }

    @Override
    public boolean removeOrder(Order order) throws OrderNotFoundException,
            ChangeOrderException {
        return orderDao.removeOrder(order);
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

                Product p = order.getProduct();
                State s = order.getState();

                if (!order.getProduct().getProductName().equals(exOrder.getProduct().getProductName())) {
                    if (!productDao.isProduct(p.getProductName())) {
                        return false;
                    }
                }

                if (!order.getState().getStateCode().equals(exOrder.getState().getStateCode())) {
                    if (!stateDao.isState(s.getStateCode())) {
                        return false;
                    }
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

//        order.getState().setStateCode(order.getState().getStateCode().trim());       
//        
//        order.getProduct().setProductName(StringUtil.capitalFirst
//                (order.getProduct().getProductName().trim().toLowerCase()));
        if (!exists) {

            Product p = order.getProduct();
            State s = order.getState();

            if (!productDao.isProduct(p.getProductName())) {
                return false;
            }

            if (!stateDao.isState(s.getStateCode())) {
                return false;
            }
        }

        order.setOrderStatus(false);
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

    @Override
    public boolean getMode() {
        return orderDao.getMode();
    }

}
