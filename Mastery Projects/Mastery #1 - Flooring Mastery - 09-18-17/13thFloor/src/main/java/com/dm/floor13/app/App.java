/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.app;

import com.danimaetrix.library.io.UserIo;
import com.danimaetrix.library.io.UserIoConsoleColorImpl;
import com.dm.floor13.controller.Controller;
import com.dm.floor13.dao.OrderDao;
import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.dao.ProductDataDao;
import com.dm.floor13.dao.ProductDataDaoImpl;
import com.dm.floor13.dao.StateDataDao;
import com.dm.floor13.dao.StateDataDaoImpl;
import com.dm.floor13.service.OrderService;
import com.dm.floor13.service.OrderServiceImpl;
import com.dm.floor13.ui.ConsoleTextViewImpl;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args) throws Exception{
        
        UserIo io = new UserIoConsoleColorImpl();
        
        ConsoleTextViewImpl view = new ConsoleTextViewImpl(io);
        OrderDao orderDao = new OrderDaoImpl();
        StateDataDao stateDao = new StateDataDaoImpl();
        ProductDataDao productDao = new ProductDataDaoImpl();
        
        OrderService service = new OrderServiceImpl(orderDao,stateDao,productDao);
        
        Controller controller = new Controller(service, view);
               
        controller.run();

    }

}
