/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            if (file.isDirectory()){
                File folderSub = new File(dir + "/unModifiedData/" + file.getName());                
            }
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
            orderDao.getOrder("16826").get(0);
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
    public void testRemoveOrder() {

        // Get the order
        try {
            orderDao.getOrder("16826");
        } catch (OrderNotFoundException e) {
            fail("Order should exist");
        }

        // Issue removal command
        try {
            orderDao.removeOrder("16826");
        } catch (BackupFileException | FileIOException
                | MissingFileException | OrderNotFoundException e) {
            fail(e.getMessage());
        }

        // Make sure order is gone from the map
        try {
            orderDao.getOrder("16826");
            fail("Order should not exist");
        } catch (OrderNotFoundException e) {
            // Test passes
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
        String filename = dir + "/testData/" + "Orders_05082011.csv";
        if (new File(filename).exists()) {
            fail("File should have been removed");
        }

        // Make sure the temp data file was removed
        filename = dir + "/testData/tmp/" + "16826_temp.csv";
        if (new File(filename).exists()) {
            fail("File should have been removed");
        }

    }

    @Test
    public void testExceptions() {
        // Issue nonexistent order removal command
        try {
            orderDao.removeOrder("00001");
            fail("This order does not exist");
        } catch (BackupFileException | FileIOException
                | MissingFileException | OrderNotFoundException e) {
            assertEquals(e.getClass(), OrderNotFoundException.class);
        }

        // Issue nonexistent order get command
        try {
            orderDao.getOrder("00001");
            fail("This order does not exist");
        } catch (OrderNotFoundException e) {
            assertEquals(e.getClass(), OrderNotFoundException.class);
        }

        // Issue order removal command but lock the destination file
        // to force a BackupFileException
        File lockedfile = new File(dir + "/testData/tmp/" + "55041_temp.csv");

        try {
            // Lock the destination file
            RandomAccessFile raf = new RandomAccessFile(lockedfile, "rw");
            FileChannel fc = raf.getChannel();
            FileLock fl = fc.tryLock();

            try {
                orderDao.removeOrder("55041");
                fail("Backup should have been locked");
            } catch (BackupFileException | FileIOException
                    | MissingFileException | OrderNotFoundException e) {
                assertEquals(e.getClass(), BackupFileException.class);
            }
            
            fl.release();

        } catch (IOException e) {

        }

    }

//
    @Test
    public void testAddOrder() {
        String newnum = "";
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
                newnum = orderDao.AddUpdateOrder(newOrder);
            } catch (BackupFileException | FileIOException e) {
                fail(e.getMessage());
            }

            newOrder.setOrderNumber(newnum);
            assertEquals(1, orderDao.getOrder(newnum).size());
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
                ordernum = orderDao.AddUpdateOrder(newOrder);
            } catch (BackupFileException | FileIOException e) {
                fail(e.getMessage());
            }

            assertEquals(ordernum, newOrder.getOrderNumber());
            assertEquals(2, orderDao.getOrder(ordertochange).size());

            LocalDateTime rev1, rev2;
            Order order0 = orderDao.getOrder(ordertochange).get(0);
            Order order1 = orderDao.getOrder(ordertochange).get(1);

            rev1 = order1.getRevisionDate();
            rev2 = order0.getRevisionDate();

            assertEquals(-1, rev1.compareTo(rev2));

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
