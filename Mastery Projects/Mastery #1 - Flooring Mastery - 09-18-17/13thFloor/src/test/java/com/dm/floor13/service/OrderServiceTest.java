/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.service;

import com.dm.floor13.dao.ChangeOrderException;
import com.dm.floor13.dao.FileSkipException;
import com.dm.floor13.dao.MissingDataException;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.dao.OrderNotFoundException;
import com.dm.floor13.model.Order;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author danimaetrix
 */
public class OrderServiceTest {

    String dir = "./data/orders/test_files/daoTestData";
    OrderDaoImpl orderDao = new OrderDaoImpl(dir, dir + "/testData/");
    OrderServiceImpl service = new OrderServiceImpl(orderDao);

    public OrderServiceTest() {
    }

    @Before
    public void setUp() throws FileSkipException {

        copyDirAndFiles(dir + "/unModifiedData/", dir + "/testData/");
        orderDao.readAllOrdersFromDirectory();
        orderDao.readDataFromFile();

    }

    @After
    public void tearDown() {

        File folder = new File(dir + "/testData");

        // Cleans directory of all files
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
            if (file.isDirectory()) {
                file.delete();
            }
        }

    }

    @Test
    public void testAddChange() {

        try {
            Order o = service.getOrder("28633").get(0);
            o.setOrderNumber(null);

            service.addUpdateOrder(o);
            assertNotNull(o.getOrderNumber());
            assertEquals(1, service.getOrder(o.getOrderNumber()).size());

            service.addUpdateOrder(o);
            assertNotNull(o.getOrderNumber());
            assertEquals(2, service.getOrder(o.getOrderNumber()).size());

        } catch (OrderNotFoundException | ChangeOrderException e) {
            fail("Order should exist");
        }

    }

    @Test
    public void testRemove() {

        try {

            assertTrue(service.removeOrder("28633"));
            assertFalse(service.removeOrder("28633"));
            fail("Removed twice");
        } catch (OrderNotFoundException | ChangeOrderException e) {
            assertEquals(e.getClass(), OrderNotFoundException.class);
        }

        try {
            orderDao.getState("KK");
            fail("This state does not exist... ");
        } catch (MissingDataException e) {

        }

        try {
            orderDao.getState("IN");

        } catch (MissingDataException e) {
            fail("This state does exist... ");
        }

    }

//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
    public void copyDirAndFiles(String base, String target) {

        new File(target).mkdirs();
        File folder = new File(base);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                copyDirAndFiles(file.toString() + "/", target + file.getName() + "/");
            } else {

                try {

                    Files.copy(file.toPath(), new File(target + file.getName())
                            .toPath(), StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    fail("Setup failed");
                }
            }

        }

    }

    public boolean validateOrderList(List<Order> orderList) {

        for (int i = 0; i < orderList.size(); i++) {

            Order order = orderList.get(i);
            order.recalculateData();

            if (order.getArea() == null || order.getArea().equals(BigDecimal.ZERO)) {
                return false;
            }
            if (order.getDate() == null || order.getDate().toString().equals("")) {
                return false;
            }
            if (order.getRevisionDate() == null || order.getRevisionDate().toString().equals("")) {
                return false;
            }
            if (order.getFirstName() == null || order.getFirstName().equals("")) {
                return false;
            }
            if (order.getLastName() == null || order.getLastName().equals("")) {
                return false;
            }
            if (order.getOrderNumber() == null || order.getOrderNumber().length() < 5) {
                return false;
            }
            if (order.getState().getStateCode() == null || order.getState().getStateCode().equals("")) {
                return false;
            }
            if (order.getState().getTaxrate() == null || order.getState().getTaxrate().equals(BigDecimal.ZERO)) {
                return false;
            }
            if (order.getMaterialCost() == null || order.getMaterialCost().equals(BigDecimal.ZERO)) {
                return false;
            }
            if (order.getLaborCost() == null || order.getLaborCost().equals(BigDecimal.ZERO)) {
                return false;
            }
            if (order.getTotalCost() == null || order.getTotalCost().equals(BigDecimal.ZERO)) {
                return false;
            }
            if (order.getProduct().getProductName() == null || order.getProduct().getProductName().equals("")) {
                return false;
            }
            if (order.getProduct().getCostpersqft() == null || order.getProduct().getCostpersqft().equals(BigDecimal.ZERO)) {
                return false;
            }
            if (order.getProduct().getLaborpersqft() == null || order.getProduct().getLaborpersqft().equals(BigDecimal.ZERO)) {
                return false;
            }
        }
        return true;

    }

}
