/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.controller;

import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.model.Order;
import com.dm.floor13.service.OrderService;
import com.dm.floor13.ui.ConsoleTextViewImpl;

/**
 *
 * @author danimaetrix
 */
public class Controller {

    OrderService service;
    ConsoleTextViewImpl view;

    public Controller(OrderService service, ConsoleTextViewImpl view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean loop = true;
        int choice = 0;

        while (loop) {

            choice = view.displayMainMenu();
            loop = evaluateMainMenuChoices(choice);

        }

        view.showExitMessage();

    }

    public boolean evaluateMainMenuChoices(int choice) {

        switch (choice) {

            case 1:
                Order newOrder = view.addNewOrder();
                boolean valid = true;

                while (valid) {
                    if (service.validateOrder(newOrder)) {
                        view.displayOrder(newOrder);
                        if (view.changeOrderVerify()) {
                            try {
                                String newNumber = service.addUpdateOrder(newOrder).getOrderNumber();
                                view.addOrderSuccess(newNumber);
                                valid = false;
                            } catch (ChangeOrderException e) {
                                view.displayExceptionMessage(e.getMessage());
                            }
                        };
                    } else {
                        view.newOrderBadEntry();
                        view.waitOnUser();
                        view.displayUpdateOrder(newOrder);
                    }
                }

                break;
            case 2:
                // update
                break;
            case 3:
                try {
                    Order order = service.getOrder(view.getOrderNumber()).get(0);
                    view.displayOrder(order);
                    if (view.removeOrder(order)) {
                        if (service.removeOrder(order.getOrderNumber())) {
                            view.removeOrderSuccess(order.getOrderNumber());
                        }
                    }

                } catch (OrderNotFoundException | ChangeOrderException e) {
                    view.orderNotFound();
                }
                break;
            case 4:
                // find
                break;
            case 5:
                try {
                    Order order = service.getOrder(view.getOrderNumber()).get(0);
                    view.displayOrder(order);
                    view.waitOnUser();

                } catch (OrderNotFoundException e) {
                    view.orderNotFound();
                }
                break;
            case 6:
                return false;
        }

        return true;

    }

}
