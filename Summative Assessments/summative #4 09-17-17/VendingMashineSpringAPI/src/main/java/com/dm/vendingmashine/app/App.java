/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.app;

import com.dm.vendingmashine.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args)  {
        
//        // IO injection
//        UserIo io = new UserIoConsoleImpl();
//        View view = new AsciiViewImpl(io);
//        
//        // Inject service layer with DAO implementations
//        InventoryDao daoInv = new InventoryDaoImpl();
//        PricingDao daoPrices = new PricingDaoImpl();
//        VendingService service = new VendingServiceImpl(daoInv, daoPrices);        
//        
//        // Inject controller with view and service layer implementations
//        Controller controller = new Controller(view, service);        
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        
        Controller controller = ctx.getBean("controller", Controller.class);
        
   
        controller.run();



    }
}
