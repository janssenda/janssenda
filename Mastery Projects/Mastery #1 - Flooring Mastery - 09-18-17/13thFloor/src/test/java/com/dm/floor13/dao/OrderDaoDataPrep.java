/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.danimaetrix.library.io.UserIoConsoleColorImpl;
import com.dm.floor13.model.Order;
import com.dm.floor13.ui.ConsoleTextViewImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 *
 * @author Danimaetrix
 */
public class OrderDaoDataPrep {

    String dir = "./new_Data/0817_globalUnique_hard";
    String dirSub = dir + "/Orders/output/";

    ConsoleTextViewImpl view = new ConsoleTextViewImpl(new UserIoConsoleColorImpl());

    FileHandler fileHandler = new dataPrepHandler(dirSub);

    OrderDaoImpl orderDao = new OrderDaoImpl(dirSub, fileHandler);
//    StateDataDaoImpl stateDao = new StateDataDaoImpl(dir, dirSub);
//    ProductDataDaoImpl productDao = new ProductDataDaoImpl(dir, dirSub);

    Map<String, List<Order>> orderMap;

    public OrderDaoDataPrep() {
    }

    //@Test
    public void readData() {

//        this.orderMap = orderDao.readAllOrdersFromDirectory();
        List<Order> orders = fileHandler.readAllOrders(dirSub, 8);

        this.orderMap = orders.stream().sorted((o1, o2)
                -> -o1.getRevisionDate().compareTo(o2.getRevisionDate()))
                .collect(Collectors.groupingBy(Order::getOrderNumber));

        
        orderMap.forEach((k, v) -> {
            v.get(0).setRevisionDate(v.get(0).getRevisionDate().minusYears(10));
            System.out.println(v.get(0).getRevisionDate().toString());
        });
        
        fileHandler.writeAllOrdersSplitFilesByDate(orderMap, dirSub + "");

    }

}
