/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.ui;

import com.danimaetrix.library.io.UserIo;
import com.danimaetrix.library.io.UserIoConsoleColorImpl;
import com.dm.floor13.dao.OrderDao;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.dao.ProductDataDao;
import com.dm.floor13.dao.ProductDataDaoImpl;
import com.dm.floor13.dao.StateDataDao;
import com.dm.floor13.dao.StateDataDaoImpl;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.model.Order;
import com.dm.floor13.service.OrderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author danimaetrix
 */
public class ConsoleTextViewImplTest {
//
//    String dir = "./data/orders/test_files/daoTestData";
//    String dirSub = dir + "/testData/";
//
//    OrderDao orderDao = new OrderDaoImpl(dir, dirSub);
//    StateDataDao stateDao = new StateDataDaoImpl(dir, dirSub);
//    ProductDataDao productDao = new ProductDataDaoImpl(dir, dirSub);
//    UserIo io = new UserIoConsoleColorImpl();
//
//    ConsoleTextViewImpl view = new ConsoleTextViewImpl(io);
//
//    OrderServiceImpl service;
//
//    public ConsoleTextViewImplTest() {
//    }
//
//    //@Before
//    public void setUp() throws FileSkipException {
//
//        copyDirAndFiles(dir + "/unModifiedData/", dir + "/testData/");
//        this.service = new OrderServiceImpl(orderDao, stateDao, productDao);
//    }
//
//    //@After
//    public void tearDown() {
//
//        File folder = new File(dir + "/testData");
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
//    //@Test
//    public void displayOrderTest() throws OrderNotFoundException {
//
////        Order o = orderDao.getOrder("39568").get(0);
////
////        //view.displayOrder(o);
//////        view.displayMainMenu();
////        view.updateOrder(o);
//
//    }
//
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

}
