/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.controller;

import com.dm.floor13.dao.SearchMethod;
import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.model.Order;
import com.dm.floor13.service.OrderService;
import com.dm.floor13.ui.ConsoleTextViewImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class Controller {

    boolean mode;
    OrderService service;
    ConsoleTextViewImpl view;

    public Controller(OrderService service, ConsoleTextViewImpl view) {
        this.service = service;
        this.view = view;
        this.mode = service.getMode();
        view.setMode(mode);
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
                addOrder();
                break;
            case 2:
                updateOrder();
                break;
            case 3:
                removeOrder();
                break;
            case 4:
                searchOrders();
                break;
            case 5:
                viewOrder();
                break;
            case 6:
                return false;
        }

        return true;

    }

    private void addOrder() {

        Order newOrder = view.addNewOrder();

        while (!view.infoVerify()) {
            view.updateOrder(newOrder);
        }

        updateOrder(newOrder);

    }

    private void updateOrder() {

        // Get the order (if it exsits) and show it to the user
        try {
            Order order = service.getOrder(view.getOrderNumber()).get(0);
            updateOrder(order);
            // If the user entered an invalid number, throw order not found exception
        } catch (OrderNotFoundException e) {
            view.orderNotFound();
        }
    }

    private void updateOrder(Order order) {
        boolean valid = false;
        Order safe = order;

//        if (!order.isNewOrder()) {
//            try {
//                safe = service.getOrder(order.getOrderNumber()).get(0);
//                valid = !view.updateOrder(safe);
//            } catch (OrderNotFoundException e) {
//
//            }            
//        }
        safe = order.clone();
        if (!order.isNewOrder()) {

            valid = !view.updateOrder(safe);
        }

        // Loop until the order is valid or the user cancels the request
        while (!valid) {
            // Test to make sure the order is valid
            if (service.validateOrder(safe)) {

                // If the order is valid, we display it for final verification
                view.displayOrder(safe);

                // Ask user for confirmation
                if (view.changeOrderVerify()) {

                    // Try to add the order
                    try {
                        // Confirm successful addition
                        safe.setRevisionDate(LocalDateTime.now());
                        String newNumber = service.addUpdateOrder(safe).getOrderNumber();
                        view.addOrderSuccess(newNumber);
                        valid = true;
                    } catch (ChangeOrderException e) {
                        view.displayExceptionMessage(e.getMessage());
                    }
                } else {
                    valid = true;
                }

                // If the order fails the validation test
            } else {
                view.newOrderBadEntry();
                view.waitOnUser();

                // Let the user change fields to address problems.
                // If the user chooses to quit instead, the loop will exit
                if (!view.updateOrder(safe)) {
                    valid = true;
                }
            }
        }

    }

    private void searchOrders() {

        switch (view.orderSearchMenu()) {
            case 1:
                searchResults(service
                        .findOrders(SearchMethod.DATE, LocalDate.now()));
                break;
            case 2:
                searchResults(service
                        .findOrders(SearchMethod.DATE, view.getOrderDate()));
                break;
            case 3:
                searchResults(service
                        .findOrders(SearchMethod.NAME, view.getName()));
                break;
            case 4:
                searchResults(service
                        .findOrders(SearchMethod.KEYWORD, view.getKeywords()));
                break;
            case 5:
                searchResults(service
                        .findOrders(SearchMethod.STATE, view.getStateName()));
                break;
            case 6:
                searchResults(service
                        .findOrders(SearchMethod.PRODUCT_NAME, view.getProductName()));
                break;
            case 7:
                searchResults(service
                        .findOrders(SearchMethod.AREA_GT, view.getArea()));
                break;
            case 8:
                searchResults(service
                        .findOrders(SearchMethod.AREA_LT, view.getArea()));
                break;

            case 9:
                searchResults(service
                        .findOrders(SearchMethod.ORDER_NUMBER, view.getOrderNumber()));
                break;
            case 10:
                searchResults(service
                        .findOrders(SearchMethod.DATE_BEFORE, view.getOrderDate()));
                break;
            case 11:
                searchResults(service
                        .findOrders(SearchMethod.DATE_AFTER, view.getOrderDate()));
                break;
            case 12:
                searchResults(service
                        .findOrders(SearchMethod.ALL, ""));
                break;

            default:
                break;
        }

    }

    private void searchResults(List<Order> orderList) {
        view.cls();
        view.displaySearchBanner();
        view.displayOrderList(orderList, true);
        int choice = view.getSearchInquiry(orderList.size());
        if (choice != 0) {
            viewOrder(orderList.get(choice - 1));
        }
    }

    private void removeOrder() {

        // Get the order (if it exsits) and show it to the user
        try {
            Order order = service.getOrder(view.getOrderNumber()).get(0);
            removeOrder(order);
            // If the user entered an invalid number, throw order not found exception
        } catch (OrderNotFoundException e) {
            view.orderNotFound();
        }
    }

    private void removeOrder(Order order) {

        view.displayOrder(order);
        // Ask the user to confirm removal of the order
        if (view.removeOrder(order)) {
            try {
                if (service.removeOrder(order)) {
                    view.removeOrderSuccess(order.getOrderNumber());
                }
            } catch (ChangeOrderException e) {
                view.removeOrderError();
            } catch (OrderNotFoundException e) {
                view.orderNotFound();
            }
        }
    }

    private void viewOrder() {

        try {
            Order order = service.getOrder(view.getOrderNumber()).get(0);
            viewOrder(order);
        } catch (OrderNotFoundException e) {
            view.orderNotFound();
        }

    }

    private void viewOrder(Order order) {

        // Shows the user a single order (if it exsists), or throws an exception if it doesn't
        view.displayOrder(order);
        switch (view.displayOrderOptions()) {
            case 1:
                updateOrder(order);
                break;
            case 2:
                removeOrder(order);
                break;
            case 3:
                orderHistory(order);
                viewOrder(order);
                break;
            default:
                break;
        }

    }

    private void orderHistory(Order order) {

        view.displayHistoryBanner();

        try {
            List<Order> orderList = service.getOrder(order.getOrderNumber());
            view.cls();
            view.displayHistoryBanner();
            view.displayOrderList(orderList, true);
            view.waitOnUser();
        } catch (OrderNotFoundException e) {

        }

    }

}
