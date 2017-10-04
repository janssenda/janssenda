/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.app;

import com.dm.floor13.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args) throws Exception {

//        UserIo io = new UserIoConsoleColorImpl();        
//        ConsoleTextViewImpl view = new ConsoleTextViewImpl(io);
//        
//        OrderDao orderDao = new OrderDaoImpl();
//        StateDataDao stateDao = new StateDataDaoImpl();
//        ProductDataDao productDao = new ProductDataDaoImpl();
//        
//        OrderService service = new OrderServiceImpl(orderDao,stateDao,productDao);
//        
//        Controller controller = new Controller(service, view);
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");

        Controller controller
                = ctx.getBean("controller", Controller.class);

        controller.run();

    }

}
