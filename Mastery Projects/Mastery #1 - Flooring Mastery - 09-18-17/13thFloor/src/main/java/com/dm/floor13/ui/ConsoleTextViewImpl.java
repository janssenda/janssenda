/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.ui;

import com.danimaetrix.library.general.StringUtil;
import com.danimaetrix.library.io.ColorIO;
import com.danimaetrix.library.io.UserIo;
import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author danimaetrix
 */
public class ConsoleTextViewImpl {

    String dateFormat = "MM/dd/yy";

    ColorIO c = new ColorIO();

    UserIo io;

    public ConsoleTextViewImpl(UserIo io) {
        this.io = io;
    }

    public Order addNewOrder() {
        Order order = new Order();
        order.setRevisionDate(LocalDateTime.now());

        cls();
        displayNewOrderBanner();

        io.print("Please enter the information for the new order...");
        separator();

        order.setDate(io.readDate("Order date (MM/dd/yy): "));

        order.setFirstName(io.readLine("First name: ").trim());
        order.setLastName(io.readLine("Last name: ").trim());

        State st = new State();
        Product p = new Product();

        st.setStateCode(io.readLine("State (MN): ").toUpperCase().trim());
        order.setState(st);

        p.setProductName(io.readLine("Material: ").toLowerCase().trim());
        order.setProduct(p);

        order.setArea(io.readBigDecimal("Area (ft^2): "));

        return order;

    }

    public int displayMainMenu() {
        cls();
        displayMainMenuBanner();

        io.print("Floor 13 order management rev 1.0", ColorIO.GREEN);
        io.print("Please make a selection to proceed");
        io.line();
        separator();

        io.print("1. Add a new order");
        io.print("2. Update an order");
        io.print("3. Remove orders");
        io.print("4. Find orders");
        io.print("5. View an order");
        io.print("6. Exit (changes are saved");

        separator();

        return io.readInt(">", 1, 6);

    }

    public void orderNotFound() {
        io.print("\nError! That order does not exist... ");
        waitOnUser();
    }

    public String getOrderNumber() {
        io.line();
        return String.valueOf(io.readInt("Please enter the order number: ", 9999, 99999));
    }

    public void displayOrder(Order order) {
        cls();
        displayOrderBanner();

        int pad = 17;
        io.print("Order number: " + order.getOrderNumber(), ColorIO.GREEN);
        io.print("Order date: " + order.getDate().format(DateTimeFormatter.ofPattern(dateFormat)));
        io.print("Name: " + order.getLastName() + ", " + order.getFirstName());
        io.print("State: " + order.getState().getStateCode());
        separator();
        io.print(StringUtil.padSter("Material: ", pad) + order.getProduct().getProductName());
        io.print(StringUtil.padSter("Area (ft^2): ", pad) + order.getArea().setScale(2, RoundingMode.HALF_UP).toString());
        io.print(StringUtil.padSter("Material Cost:", pad) + "$" + order.getMaterialCost().setScale(2, RoundingMode.HALF_UP).toString());
        io.print(StringUtil.padSter("Labor cost:", pad) + "$" + order.getLaborCost().setScale(2, RoundingMode.HALF_UP).toString());
        io.print(StringUtil.padSter("Total cost", pad) + "$" + order.getTotalCost().setScale(2, RoundingMode.HALF_UP).toString());
        io.print(StringUtil.padSter("Last revised: ", pad) + order.getRevisionDate().format(DateTimeFormatter.ofPattern(dateFormat)));
        separator();

    }

    public Order displayUpdateOrder(Order order) {
        boolean done = false;

        int pad = 17;

        while (!done) {

            cls();
            displayUpdateBanner();
            io.print("Please select the field you wish to modify: ");
            io.print("Order number: " + order.getOrderNumber(), ColorIO.GREEN);
            separator();
            io.print("1. Order date: " + order.getDate().format(DateTimeFormatter.ofPattern(dateFormat)));
            io.print("2. Name: " + order.getLastName() + ", " + order.getFirstName());
            io.print("3. State: " + order.getState().getStateCode());
            io.print(StringUtil.padSter("4. Material: ", pad) + order.getProduct().getProductName());
            io.print(StringUtil.padSter("5. Area (ft^2): ", pad) + order.getArea().setScale(2, RoundingMode.HALF_UP).toString());
            io.print("6. Done updating");

            separator();

            switch (io.readInt(">", 1, 7)) {
                case 1:
                    order.setDate(io.readDate("Order date (MM/dd/yy): "));
                    break;
                case 2:
                    order.setFirstName(io.readLine("First name: ").trim());
                    order.setLastName(io.readLine("Last name: ").trim());
                    break;
                case 3:
                    State st = new State();
                    st.setStateCode(io.readLine("State (MN): ").toUpperCase().trim());
                    order.setState(st);
                    break;
                case 4:
                    Product p = new Product();
                    p.setProductName(io.readLine("Material: ").toLowerCase().trim());
                    order.setProduct(p);
                    break;
                case 5:
                    order.setArea(io.readBigDecimal("Area (ft^2): "));
                    break;
                case 6:
                    done = true;
                    break;
            }
        }
        return order;

    }

    public boolean removeOrder(Order order) {

        displayOrder(order);
        return removeOrderVerify();
    }

    public int displayOrderOptions() {

        io.print("Options:");
        io.line();
        io.print("1. Update order");
        io.print("2. Remove order");
        io.print("3. Main menu");
        separator();
        return io.readInt(">", 1, 3);

    }

    public void newOrderBadEntry() {
        io.line();
        io.print("There was a problem processing your request... "
                + "\nplease make sure all fields are correct.");
    }

    public boolean removeOrderVerify() {
        return io.readAnswer("Are you sure you want to remove this order (y/n)? ", ColorIO.PURPLE);
    }

    public boolean changeOrderVerify() {
        return io.readAnswer("Are you sure you want to add/change this order (y/n)? ", ColorIO.PURPLE);
    }

    public void removeOrderSuccess(String orderNum) {
        io.line();
        io.print("<----- Order " + orderNum + " removed successfully! ----->");
        waitOnUser();
    }

    public void addOrderSuccess(String orderNum) {
        io.line();
        io.print("<----- Order " + orderNum + " added successfully! ----->");
        waitOnUser();
    }

    public void displayMainMenuBanner() {
        io.print("***** Main Menu *****\n");
    }

    public void displayUpdateBanner() {

        io.print("***** Update Order *****\n");
    }

    public void displayNewOrderBanner() {

        io.print("***** New Order *****\n");
    }

    public void displayOrderBanner() {

        io.print("***** Display Order *****\n");
    }

    // Additional user messages  
    public void showExitMessage() {
        io.print("\n");
        io.print("GOODBYE!!");
        io.print("\n");
    }

    // Pause for user    
    public void waitOnUser() {
        io.readLine("\nPress enter to continue...");
    }

    public void cls() {
        for (int i = 0; i < 2000; i++) {
            io.print("\n");
        }
    }

    public void displayExceptionMessage(String msg) {
        io.print("");
        io.print(msg);
    }

    public void separator() {
        io.print("-----------------------------", ColorIO.CYAN);
    }

}
