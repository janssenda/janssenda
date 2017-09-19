/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.app;

import com.dm.floor13.dao.OrderDao;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args) {

        OrderDao dao = new OrderDao();
        dao.readAllOrdersFromDirectory();
        dao.writeOrdersToDirectory("/output/");

    }

}
