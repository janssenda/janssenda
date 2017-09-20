/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Danimaetrix
 */
public class OrderDaoImplTest {

    String dir = "./orders/test_files/daoTestData";
    OrderDaoImpl orderDao = new OrderDaoImpl(dir + "/testData/");
    Map<String, List<Order>> orderMap;

    public OrderDaoImplTest() {
    }

    @Before
    public void setUp() {

        File folder = new File(dir + "/unModifiedData");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            try {
                Files.copy(file.toPath(), new File(dir + "/testData/" + file.getName())
                        .toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                fail("Setup failed");
            }
        }

        this.orderMap = orderDao.readAllOrdersFromDirectory();

    }

    @After
    public void tearDown() {

        File folder = new File(dir + "/testData");

        // Cleans directory of all files
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                //file.delete();
//                System.out.println(file.getName());
                file.delete();
////                file.
////                System.out.println(file.exists());
//                System.out.println("-----");
            }
        }

    }

//    @Test
//    public void testReadAllOrdersFromDirectory() {
//        assertEquals(15, orderMap.size());
//        orderMap.forEach((k, v) -> {
//            assertTrue(validateOrderList(v));
//        });
//    }
//
//    @Test
//    public void testGetOrder() {
//
//        try {
//            orderDao.getOrder("16826");
//        } catch (OrderNotFoundException e) {
//            fail("Order should exist");
//        }
//
//        try {
//            orderDao.getOrder("00001");
//            fail("Order should not exist");
//        } catch (OrderNotFoundException e) {
//
//        }
//
//    }
    @Test
    public void testRemoveOrder() throws Exception {
//        FileHandler file = new FileHandler(dir + "/testData/");
//        file.removeOrderFromAll(orderDao.getOrder("16826"));
        try {
            orderDao.getOrder("16826");
        } catch (OrderNotFoundException e) {
            fail("Order should exist");
        }

        try {
            orderDao.removeOrder("16826");
        } catch (BackupFileException | FileIOException
                | MissingFileException | OrderNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            orderDao.getOrder("16826");
            fail("Order should not exist");
        } catch (OrderNotFoundException e) {
            // Test passes
        }

        orderMap.clear();
        orderMap = orderDao.readAllOrdersFromDirectory();

        try {
            orderDao.getOrder("16826");
            fail("Order should not exist");
        } catch (OrderNotFoundException e) {
            // Test passes
        }

    }
//
//    @Test
//    public void testAddUpdateOrder() throws Exception {
//    }
//
//    @Test
//    public void testUpdateCurrentOrderNumber() {
//    }
//
//    @Test
//    public void testGetCurrentOrderNumber() {
//    }
//
//    @Test
//    public void testGetcurrentDir() {
//    }
//
//    @Test
//    public void testSetcurrentDir() {
//    }
//
//    @Test
//    public void testGetOrderNumberLength() {
//    }
//
//    @Test
//    public void testSetOrderNumberLength() {
//    }
//
//    @Test
//    public void testWriteOrdersToDirectory() {
//    }

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
