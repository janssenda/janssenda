/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.app;

import com.dm.floor13.dao.OrderDaoImpl;
import com.dm.floor13.model.Order;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args) throws Exception{

        OrderDaoImpl dao = new OrderDaoImpl();
        dao.readAllOrdersFromDirectory();
        //List<Order> test = dao.getOrder("37282");
//        
//        test.get(0).setLastName("Ericson55");
//        test.get(0).setDate(LocalDate.parse("03/01/2013",DateTimeFormatter.ofPattern("MM/dd/yyyy")));
//        
//        dao.addOrder(test.get(0).getOrderNumber(), test.get(0));
        dao.removeOrder("28633");
        //dao.writeOrdersToDirectory("/output/");
        

    }

}
