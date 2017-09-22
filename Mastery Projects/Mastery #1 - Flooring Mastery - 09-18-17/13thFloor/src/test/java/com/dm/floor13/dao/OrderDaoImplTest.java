/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Danimaetrix
 */
public class OrderDaoImplTest {

    String dir = "./data/orders/test_files/daoTestData";
    String dirSub = dir + "/testData/";
    
    OrderDaoImpl orderDao = new OrderDaoImpl(dir, dirSub);
    StateDataDaoImpl stateDao = new StateDataDaoImpl(dir, dirSub);
    ProductDataDaoImpl productDao = new ProductDataDaoImpl(dir, dirSub); 
    
    
    Map<String, List<Order>> orderMap;

    public OrderDaoImplTest() {
    }

    @Before
    public void setUp() {
        copyDirAndFiles(dir + "/unModifiedData/", dir + "/testData/");
        this.orderMap = orderDao.readAllOrdersFromDirectory();
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
    public void testReadAllOrdersFromDirectory() {

        assertEquals(15, orderMap.size());
        orderMap.forEach((k, v) -> {
            assertTrue(validateOrderList(v));
        });
    }

    @Test
    public void testGetOrder() {

        try {
            Order order = orderDao.getOrder("16826").get(0);

            // Verify that we have recieved a clone of the original order
            assertFalse(order.compareThisTo(orderDao.getOrder("16826").get(0)));

            // Verify that the clone contains the same data as the original
            assertTrue(order.equals(orderDao.getOrder("16826").get(0)));

        } catch (OrderNotFoundException e) {
            fail("Order should exist");
        }

        try {
            orderDao.getOrder("00001");
            fail("Order should not exist");
        } catch (OrderNotFoundException e) {

        }

    }

    @Test
    public void testReadData() {
        try {
            stateDao.readDataFromFile();
            productDao.readDataFromFile();

        } catch (FileSkipException e) {
            fail("File was not read");
        }

        Map<String, State> stateMap = stateDao.getStateMap();
        Map<String, Product> productMap = productDao.getProductMap();

        assertEquals(4, stateMap.size());
        assertEquals(4, productMap.size());

        assertTrue(productMap.containsKey("Carpet"));
        assertTrue(productMap.containsKey("Laminate"));
        assertTrue(productMap.containsKey("Tile"));
        assertTrue(productMap.containsKey("Wood"));
        try {
            State s = stateDao.getState("IN");
            assertTrue(6.00 == s.getTaxrate().doubleValue());

            Product p = productDao.getProduct("Laminate");

            assertTrue(1.75 == p.getCostpersqft().doubleValue());
            assertTrue(2.10 == p.getLaborpersqft().doubleValue());

        } catch (MissingDataException e) {

        }

    }

//
    @Test
    public void testAddOrder() {
        String newnum = "";
        String newnum2 = "";
        Order newOrder;
        Order freshOrder;

        // Get the order
        try {
            newOrder = orderDao.getOrder("16826").get(0);
            newOrder.setFirstName("Danimae");
            newOrder.setLastName("Janssen");
            newOrder.setOrderNumber(null);
            newOrder.setDate(LocalDate.now());
            newOrder.setRevisionDate(LocalDateTime.now());
            newOrder.recalculateData();

            try {
                newnum = orderDao.addUpdateOrder(newOrder).getOrderNumber();

                // Add a second identical to the first but set null to force a new entry
                Order new2 = orderDao.getOrder(newnum).get(0);
                new2.setOrderNumber(null);
                newnum2 = orderDao.addUpdateOrder(new2).getOrderNumber();
                assertEquals(1, Integer.parseInt(newnum2) - Integer.parseInt(newnum));

                Order new3 = orderDao.getOrder(newnum).get(0);

                orderDao.addUpdateOrder(new3);
                assertEquals(2, orderDao.getOrder(newnum).size());

            } catch (ChangeOrderException e) {
                fail(e.getMessage());
            }

            newOrder.setOrderNumber(newnum);
            assertEquals(2, orderDao.getOrder(newnum).size());
            freshOrder = orderDao.getOrder(newnum).get(0);

            assertEquals(freshOrder, newOrder);

            // Make sure order is present in files
            orderMap.clear();
            orderMap = orderDao.readAllOrdersFromDirectory();

            try {
                orderDao.getOrder(newnum);
            } catch (OrderNotFoundException e) {
                fail("Was not present in files");
            }

        } catch (OrderNotFoundException e) {
            fail("Order should exist");
        }

    }

    @Test
    public void testUpdateOrder() {
        String ordernum = "";
        String ordertochange = "16826";
        String oldName;
        Order newOrder;

        // Get the order
        try {
            newOrder = orderDao.getOrder(ordertochange).get(0);
            oldName = newOrder.getFirstName();
            newOrder.setFirstName("Danimae");
            newOrder.setLastName("Janssen");
            newOrder.setRevisionDate(LocalDateTime.now());
            newOrder.recalculateData();

            assertEquals(1, orderDao.getOrder(ordertochange).size());

            try {
                ordernum = orderDao.addUpdateOrder(newOrder).getOrderNumber();
            } catch (ChangeOrderException e) {
                fail(e.getMessage());
            }

            assertEquals(ordernum, newOrder.getOrderNumber());
            assertEquals(2, orderDao.getOrder(ordertochange).size());

            LocalDateTime rev1, rev2;
            Order order0 = orderDao.getOrder(ordertochange).get(0);
            Order order1 = orderDao.getOrder(ordertochange).get(1);

            rev1 = order1.getRevisionDate();
            rev2 = order0.getRevisionDate();            

            assertTrue(rev1.isBefore(rev2));

            assertEquals(oldName, order1.getFirstName());
            assertEquals("Danimae", order0.getFirstName());

            // Make sure order is present in files
            orderMap.clear();
            orderMap = orderDao.readAllOrdersFromDirectory();

            try {
                List<Order> fromfile = orderDao.getOrder(ordernum);

                assertEquals(fromfile.get(0), order0);
                assertEquals(fromfile.get(1), order1);

            } catch (OrderNotFoundException e) {
                fail("Was not present in files");
            }
        } catch (OrderNotFoundException e) {
            fail("Order should exist");
        }

    }

    @Test
    public void testRemoveOrder() {

//        // Get the order
        try {
            orderDao.getOrder("16826");
        } catch (OrderNotFoundException e) {
            fail("Order should exist");
        }
//
        // Issue removal command
        try {
            orderDao.removeOrder("16826");
        } catch (ChangeOrderException | OrderNotFoundException e) {
            fail(e.getMessage());
        }
//
        // Make sure order is gone from the map
        try {
            orderDao.getOrder("16826");
            fail("Order should not exist");
        } catch (OrderNotFoundException e) {
            // Test passes
        }

        String filename = dir + "/testData/" + "Orders_05082011.csv";
        if (new File(filename).exists()) {
            //fail("File should have been removed");
        }
        // Make sure order no longer exists in any files        
        orderMap.clear();
        orderMap = orderDao.readAllOrdersFromDirectory();

        try {
            orderDao.getOrder("16826");
            fail("Order should not exist");
        } catch (OrderNotFoundException e) {
            // Test passes
        }

        // Make sure the empty file was removed
        String filename2 = dir + "/testData/" + "Orders_05082011.csv";
        if (new File(filename).exists()) {
            //fail("File should have been removed");
        }
//
        // Make sure the temp data file was removed
        filename = dir + "/testData/tmp/" + "16826_temp.csv";
        if (new File(filename).exists()) {
            fail("File should have been removed");
        }

    }
//

    @Test
    public void testExceptions() {
        // Issue nonexistent order removal command
        try {
            orderDao.removeOrder("00001");
            fail("This order does not exist");
        } catch (ChangeOrderException | OrderNotFoundException e) {
            assertEquals(e.getClass(), OrderNotFoundException.class);
        }

        // Issue nonexistent order get command
        try {
            orderDao.getOrder("00001");
            fail("This order does not exist");
        } catch (OrderNotFoundException e) {
            assertEquals(e.getClass(), OrderNotFoundException.class);
        }

        // Issue nonexistent order removal command
        try {
            orderDao.removeOrder("16826");
            orderDao.removeOrder("16826");
            fail("This order does not exist");
        } catch (ChangeOrderException | OrderNotFoundException e) {
            assertEquals(e.getClass(), OrderNotFoundException.class);
        }
    }
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

    public void copyDirAndFiles(String base, String target) {

        new File(target).mkdirs();
        File folder = new File(base);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                //copyDirAndFiles(file.toString() + "/", target + file.getName() + "/");
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
