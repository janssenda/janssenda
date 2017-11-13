///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dm.floor13.service;
//
//import com.dm.floor13.dao.FileHandler;
//import com.dm.floor13.dao.OrderDao;
//import com.dm.floor13.exceptions.ChangeOrderException;
//import com.dm.floor13.exceptions.FileSkipException;
//import com.dm.floor13.exceptions.MissingDataException;
//import com.dm.floor13.dao.OrderDaoImpl;
//import com.dm.floor13.dao.ProductDataDao;
//import com.dm.floor13.exceptions.OrderNotFoundException;
//import com.dm.floor13.dao.ProductDataDaoImpl;
//import com.dm.floor13.dao.StateDataDao;
//import com.dm.floor13.dao.StateDataDaoImpl;
//import com.dm.floor13.model.Order;
//import com.dm.floor13.model.Product;
//import com.dm.floor13.model.State;
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDate;
//import java.util.List;
//import org.junit.After;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// *
// * @author danimaetrix
// */
//public class OrderServiceTest {
//
//    String cfgPath = "./data/orders/test_files/daoTestData/floor13_test.cfg";
//    String dataDirectory = "./data/orders/test_files/daoTestData";
//    String orderDirectory = dataDirectory + "/testData/";
//    String unModDirectory = dataDirectory + "/unModifiedData/";
//    String productsFile = "./data/orders/test_files/daoTestData/Products.txt";
//    String statesFile = "./data/orders/test_files/daoTestData/Taxes.txt";
//
//    FileHandler fileHandler = new FileHandler(orderDirectory);
//
//    OrderDaoImpl orderDao = new OrderDaoImpl(orderDirectory, fileHandler);
//    StateDataDaoImpl stateDao = new StateDataDaoImpl();
//    ProductDataDaoImpl productDao = new ProductDataDaoImpl();
//
//    OrderServiceImpl service;
//
//    public OrderServiceTest() throws FileSkipException {
//    }
//
//    @Before
//    public void setUp() throws FileSkipException {
//
//        copyDirAndFiles(unModDirectory, orderDirectory);
//        this.service = new OrderServiceImpl(stateDao, productDao,
//                fileHandler, orderDirectory, cfgPath);
//    }
//
//    @After
//    public void tearDown() {
//
//        File folder = new File(orderDirectory);
//
//        // Cleans directory of all files
//        for (File file : folder.listFiles()) {
//            if (!file.isDirectory()) {
//                file.delete();
//            }
//            if (file.isDirectory()) {
//                file.delete();
//            }
//        }
//
//    }
//
//    @Test
//    public void testPopulate() {
//
//        Order o = new Order();
//
//        o.setFirstName("Danimae");
//        o.setLastName("Vostrikov");
//        o.setDate(LocalDate.now());
//        o.setArea(new BigDecimal("1000"));
//
//        State st = new State("IN");
//        Product p = new Product("Tile");
//
//        o.setState(st);
//        o.setProduct(p);
//
////        assertTrue(service.validateOrder(o));
////        assertNull(o.getState().getTaxrate());
////        assertNull(o.getProduct().getCostpersqft());
////        assertNull(o.getTotalCost());
////        assertNull(o.getRevisionDate());
//        service.populateOrder(o);
//
//        assertTrue(service.validateOrder(o));
//        assertNotNull(o.getState().getTaxrate());
//        assertNotNull(o.getProduct().getCostpersqft());
//        assertNotNull(o.getTotalCost());
//        assertNotNull(o.getRevisionDate());
//    }
//
//    @Test
//    public void testPopulateExisting() throws MissingDataException {
//
//        // Test state validation
//        Order old = new Order();
//        Order o = new Order();
//
//        try {
//            o = service.getOrder("39568").get(0);
//            old = service.getOrder("39568").get(0);
//        } catch (OrderNotFoundException e) {
//
//        }
//
//        //assertTrue(service.validateOrder(o));
//        o.setFirstName("Danimae");
//        o.setLastName("Vostrikov");
//
//        assertNotEquals("Danimae", old.getFirstName());
//        assertEquals("Danimae", o.getFirstName());
//        assertEquals(o.getState(), old.getState());
//
//        service.populateOrder(o);
//        assertEquals(o.getState(), old.getState());
//
//        o.setState(stateDao.getState("IN"));
//        service.populateOrder(o);
//        assertEquals(o.getState(), old.getState());
//
//        BigDecimal rate = new BigDecimal("4.50");
//        stateDao.getState("IN").setTaxrate(rate);
//
//        State state = stateDao.getState("IN");
//        assertEquals(0, state.getTaxrate().compareTo(rate));
//
//        service.populateOrder(o);
//        assertTrue(o.getState().getTaxrate().compareTo(rate) != 0);
//        o.getState().setStateCode("IN");
//        service.populateOrder(o);
//
//        assertFalse(o.getState().getTaxrate().compareTo(rate) == 0);
//
//        o.setState(stateDao.getState("MI"));
//        service.populateOrder(o);
//
//        rate = new BigDecimal("5.75");
//        assertTrue(o.getState().getTaxrate().compareTo(rate) == 0);
//    }
//
//    @Test
//    public void testPopulateExistingProduct() throws MissingDataException {
//
//        // Test state validation
//        Order old = new Order();
//        Order o = new Order();
//
//        try {
//            o = service.getOrder("39568").get(0);
//            old = service.getOrder("39568").get(0);
//        } catch (OrderNotFoundException e) {
//
//        }
//
//        service.populateOrder(o);
//        assertEquals(o.getProduct(), old.getProduct());
//
//        o.setProduct(productDao.getProduct("Wood"));
//        service.populateOrder(o);
//        assertEquals(o.getProduct(), old.getProduct());
//
//        BigDecimal rate = new BigDecimal("2.50");
//        productDao.getProduct("Wood").setCostpersqft(rate);
//
//        Product p = productDao.getProduct("Wood");
//        assertEquals(0, p.getCostpersqft().compareTo(rate));
//
//        service.populateOrder(o);
//        assertTrue(o.getProduct().getCostpersqft().compareTo(rate) != 0);
//        o.getProduct().setProductName("Wood");
//        service.populateOrder(o);
//
//        assertFalse(o.getProduct().getCostpersqft().compareTo(rate) == 0);
//
//        o.setProduct(productDao.getProduct("Wood"));
//        service.populateOrder(o);
//        assertTrue(o.getProduct().getCostpersqft().compareTo(rate) == 0);
//
//    }
//
//    @Test
//    public void TestValidate() {
//
//        try {
//            // Test state validation
//            Order o;
//
//            o = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//
//            o.getState().setStateCode("ZZ");
//            assertFalse(service.validateOrder(o));
//
//            o = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//            o.getProduct().setProductName("ajksdhajks");
//            assertFalse(service.validateOrder(o));
//
//        } catch (OrderNotFoundException e) {
//            fail("Order should exist");
//        }
//
//    }
//
//    @Test
//    public void TestValidateExisting() {
//
//        try {
//            // Test state validation
//            Order o, o2;
//
//            o = service.getOrder("62052").get(0);
//            o2 = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//
//            // Validate that we recover the original name if
//            // only whitespace characters are entered
//            o = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//            o.setFirstName("");
//            assertEquals(o.getFirstName(), "");
//
//            service.validateOrder(o);
//            assertEquals(o.getFirstName(), o2.getFirstName());
//
//            // Validate that we recover the original name if
//            // only whitespace characters are entered
//            o = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//            o.setFirstName("\t \n");
//            assertEquals(o.getFirstName(), "\t \n");
//
//            service.validateOrder(o);
//            assertEquals(o.getFirstName(), o2.getFirstName());
//
//            // Validate that we return the original date
//            // if a date before 01/01/1975 is entered
//            o = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//            LocalDate d = LocalDate.of(1974, 12, 31);
//            o.setDate(d);
//            assertEquals(o.getDate(), d);
//
//            service.validateOrder(o);
//            assertEquals(o.getDate(), o2.getDate());
//
//            // Validate that we return the original value
//            // for negative area
//            o = service.getOrder("62052").get(0);
//            assertTrue(service.validateOrder(o));
//            BigDecimal area = new BigDecimal("-10");
//            o.setArea(area);
//            assertEquals(o.getArea(), area);
//
//            service.validateOrder(o);
//            assertEquals(o.getArea(), o2.getArea());
//
//        } catch (OrderNotFoundException e) {
//            fail("Order should exist");
//        }
//
//    }
//
//    @Test
//    public void testAddChange() {
//
//        try {
//            Order o = service.getOrder("28633").get(0);
//            o.setOrderNumber(null);
//
//            service.addUpdateOrder(o);
//            assertNotNull(o.getOrderNumber());
//            assertEquals(1, service.getOrder(o.getOrderNumber()).size());
//
//            service.addUpdateOrder(o);
//            assertNotNull(o.getOrderNumber());
//            assertEquals(2, service.getOrder(o.getOrderNumber()).size());
//
//        } catch (OrderNotFoundException | ChangeOrderException e) {
//            fail("Order should exist");
//        }
//
//    }
//
//    @Test
//    public void testRemove() {
//
//        try {
//
//            assertTrue(service.removeOrder(service.getOrder("28633").get(0)));
//            assertFalse(service.removeOrder(service.getOrder("28633").get(0)));
//            fail("Removed twice");
//        } catch (OrderNotFoundException | ChangeOrderException e) {
//            assertEquals(e.getClass(), OrderNotFoundException.class);
//        }
//
//        try {
//            stateDao.getState("KK");
//            fail("This state does not exist... ");
//        } catch (MissingDataException e) {
//
//        }
//
//        try {
//            stateDao.getState("IN");
//
//        } catch (MissingDataException e) {
//            fail("This state does exist... ");
//        }
//
//    }
//
//    @Test
//    public void testValidSpecific() {
//
//        try {
//            Order o = service.getOrder("28633").get(0);
//
//            o.getState().setStateCode("IN");
//
//            assertTrue(service.validateOrder(o));
//
//        } catch (OrderNotFoundException e) {
//            fail("Order should exist");
//        }
//
//    }
//
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
//    public void copyDirAndFiles(String base, String target) {
//
//        new File(target).mkdirs();
//        File folder = new File(base);
//        File[] listOfFiles = folder.listFiles();
//
//        for (File file : listOfFiles) {
//            if (file.isDirectory()) {
//                copyDirAndFiles(file.toString() + "/", target + file.getName() + "/");
//            } else {
//
//                try {
//
//                    Files.copy(file.toPath(), new File(target + file.getName())
//                            .toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                } catch (IOException e) {
//                    fail("Setup failed");
//                }
//            }
//
//        }
//
//    }
//
//    public boolean validateOrderList(List<Order> orderList) {
//
//        for (int i = 0; i < orderList.size(); i++) {
//
//            Order order = orderList.get(i);
//            order.recalculateData();
//
//            if (order.getArea() == null || order.getArea().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//            if (order.getDate() == null || order.getDate().toString().equals("")) {
//                return false;
//            }
//            if (order.getRevisionDate() == null || order.getRevisionDate().toString().equals("")) {
//                return false;
//            }
//            if (order.getFirstName() == null || order.getFirstName().equals("")) {
//                return false;
//            }
//            if (order.getLastName() == null || order.getLastName().equals("")) {
//                return false;
//            }
//            if (order.getOrderNumber() == null || order.getOrderNumber().length() < 5) {
//                return false;
//            }
//            if (order.getState().getStateCode() == null || order.getState().getStateCode().equals("")) {
//                return false;
//            }
//            if (order.getState().getTaxrate() == null || order.getState().getTaxrate().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//            if (order.getMaterialCost() == null || order.getMaterialCost().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//            if (order.getLaborCost() == null || order.getLaborCost().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//            if (order.getTotalCost() == null || order.getTotalCost().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//            if (order.getProduct().getProductName() == null || order.getProduct().getProductName().equals("")) {
//                return false;
//            }
//            if (order.getProduct().getCostpersqft() == null || order.getProduct().getCostpersqft().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//            if (order.getProduct().getLaborpersqft() == null || order.getProduct().getLaborpersqft().equals(BigDecimal.ZERO)) {
//                return false;
//            }
//        }
//        return true;
//
//    }
//
//}
