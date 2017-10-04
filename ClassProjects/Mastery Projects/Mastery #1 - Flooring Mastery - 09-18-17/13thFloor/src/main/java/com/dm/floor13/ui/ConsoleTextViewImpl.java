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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class ConsoleTextViewImpl {

    boolean mode;

    String BANNER_COLOR = ColorIO.YELLOW;
    String dateFormat = "MM/dd/yy";
    String rdateFormat = "MM/dd/yy @ hh:mm a";

    ColorIO c = new ColorIO();

    UserIo io;

    public ConsoleTextViewImpl(UserIo io) {
        this.io = io;
        this.mode = true; // default to training mode
    }

    public Order addNewOrder() {
        Order order = new Order();

        cls();
        displayNewOrderBanner();

        io.print("Please enter the information for the new order...");
        separator();

        LocalDate d = io.readDate("Order date (MM/dd/yyyy): ");

        order.setDate(d);

        order.setFirstName(io.readLine("First name: ").trim());
        order.setLastName(io.readLine("Last name: ").trim());

        State st = new State();
        Product p = new Product();

        st.setStateCode(io.readLine("State: ").trim());
        order.setState(st); 

        p.setProductName(io.readLine("Material: ").trim());
        order.setProduct(p);

        order.setArea(io.readBigDecimal("Area (ft^2): "));
        separator();

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
        io.print("6. Exit (changes are saved)");

        separator();

        return io.readInt(">", 1, 6);

    }

    public void orderNotFound() {
        io.print("\nError! That order does not exist... ");
        waitOnUser();
    }

    public void changeOrderError() {
        io.print("\nError modifying order... ");
        waitOnUser();
    }

    public void removeOrderError() {
        io.print("\nError removing order... ");
        waitOnUser();
    }

    public String getOrderNumber() {
        io.line();
        return String.valueOf(io.readInt("Please enter the order number: ", 0, Integer.MAX_VALUE));
    }

    public void displayOrder(Order order) {
        displayOrder(order, false);
    }

    public void displayOrder(Order order, boolean fieldNumbering) {
        cls();
        int pad = 17;
        int money_space = 11;
        String[] prefix = {"1. ", "2. ", "3. ", "4. ", "5. "};

        if (!fieldNumbering) {
            displayOrderBanner();
            for (int i = 0; i < 5; i++) {
                prefix[i] = "";
            }
        }
        if (!order.isNewOrder()) {
            io.print("Order number: " + order.getOrderNumber(), ColorIO.GREEN);
        }

        String com = ", ";
        if (order.getLastName().equals("")) {
            com = "";
        }

        String firstLine;
        String nextLine = "";
        boolean secondMatLine = false;
        String[] orderToke = order.getProduct().getProductName().split(" ");
        if (orderToke.length > 2) {
            secondMatLine = true;
            firstLine = orderToke[0] + " " + orderToke[1];
            for (int i = 2; i < orderToke.length; i++) {
                nextLine = nextLine + orderToke[i];
            }
        } else {
            firstLine = order.getProduct().getProductName();
        }

        separator();
        io.print(prefix[0] + "Order date: " + order.getDate().format(DateTimeFormatter.ofPattern(dateFormat)), ColorIO.WHITE);
        io.print(prefix[1] + "Name: " + order.getLastName() + com + order.getFirstName(), ColorIO.WHITE);
        io.print(prefix[2] + "State: " + order.getState().getStateCode());

        io.print(prefix[4] + StringUtil.padStr("Area (ft^2): ", pad) + order.getArea().setScale(2, RoundingMode.HALF_UP).toString());
        io.print(prefix[3] + StringUtil.padStr("Material: ", pad) + firstLine);

        if (secondMatLine) {
            io.print(StringUtil.padStr("", pad) + nextLine);
        }

        separator();
        if (!order.isNewOrder()) {
            io.print(StringUtil.padStr("Material Cost:", pad) + "$" + StringUtil.rightAlign(order.getMaterialCost().setScale(2, RoundingMode.HALF_UP).toString(), money_space));
            io.print(StringUtil.padStr("Labor cost:", pad) + "$" + StringUtil.rightAlign(order.getLaborCost().setScale(2, RoundingMode.HALF_UP).toString(), money_space));
            io.print(StringUtil.padStr("Total cost", pad) + "$" + StringUtil.rightAlign(order.getTotalCost().setScale(2, RoundingMode.HALF_UP).toString(), money_space));
            io.print(StringUtil.padStr("Date revised: ", pad)
                    + StringUtil.rightAlign(order.getRevisionDate().format(DateTimeFormatter.ofPattern(dateFormat)), 12));
            io.print(StringUtil.padStr("Time revised: ", pad)
                    + StringUtil.rightAlign(order.getRevisionDate().format(DateTimeFormatter.ofPattern("hh:mm a")), 12));
            separator();
        }

    }

    public int orderSearchMenu() {
        cls();
        displaySearchBanner();
        io.print("How would you like to search?");
        separator();
        io.print("1.  Today");
        io.print("2.  Date");
        io.print("3.  Name");
        io.print("4.  Keyword");
        io.print("5.  State");
        io.print("6.  Product");
        io.print("7.  Min area");
        io.print("8.  Max area");
        io.print("9.  Order Number");
        io.print("10. Dates before");
        io.print("11. Dates after");
        io.print("12. All");
        io.print("0.  Back");
        separator();

        return io.readInt("> ", 0, 12);

    }

    public LocalDate getOrderDate() {
        LocalDate d = io.readDate("Order date (MM/dd/yyyy): ");
        return d;
    }

    public String getProductName() {
        return io.readLine("Enter the product name: ");
    }

    public String getKeywords() {
        return io.readLine("Enter keywords: ");
    }

    public String getName() {
        return io.readLine("Enter name keywords: ");
    }

    public String getStateName() {
        return io.readLine("Enter state name: ");
    }

    public BigDecimal getArea() {
        return io.readBigDecimal("Enter the area (ft^2): ");
    }

    public boolean updateOrder(Order order) {

        while (true) {

            cls();
            displayUpdateBanner();
            io.print("Please select the field you wish to modify: ");
            displayOrder(order, true);
            io.print("6. Done");
            io.print("7. Cancel");

            separator();

            switch (io.readInt(">", 1, 7)) {
                case 1:
                    order.setDate(io.readDate("Order date (MM/dd/yyyy): "));
                    break;
                case 2:
                    order.setFirstName(io.readLine("First name: ").trim());
                    order.setLastName(io.readLine("Last name: ").trim());
                    break;
                case 3:
                    order.getState().setStateCode(io.readLine("State: ").trim());
                    break;
                case 4:
                    order.getProduct().setProductName(io.readLine("Material: ").trim());
                    break;
                case 5:
                    order.setArea(io.readBigDecimal("Area (ft^2): "));
                    break;
                case 6:
                    return true;
                case 7:
                    return false;
            }
        }
    }

    public boolean removeOrder(Order order) {
        displayOrder(order);
        return removeOrderVerify();
    }

    public int displayOrderOptions() {

        io.print("Options:", ColorIO.GREEN);
        io.print("1. Update order");
        io.print("2. Remove order");
        io.print("3. View order history");
        io.print("0. Main menu");
        separator();
        return io.readInt(">", 0, 3);

    }

    public void displayOrderList(List<Order> orderList) {
        displayOrderList(orderList, false);
    }

    public void displayOrderList(List<Order> orderList, boolean fieldNumbering) {

        //             NU  DA  NA  ST  PR  A TC  REV
        int[] width = {16, 12, 20, 30, 20, 8, 13, 40};

        longSeparator();
        
        io.print(StringUtil.padStr("Order Number", width[0])
                + StringUtil.padStr("Date", width[1])
                + StringUtil.padStr("Name", width[2])
                + StringUtil.padStr("State", width[3]+2)
                + StringUtil.padStr("Type", width[4]+2)
                + StringUtil.padStr("Area", width[5])
                + StringUtil.padStr("Total ($)", width[6])
                + StringUtil.padStr("Revised", width[7]), ColorIO.WHITE);

        longSeparator();

        for (int i = 0; i < orderList.size(); i++) {

            if (fieldNumbering) {
                io.printx(StringUtil.padStr(i + 1 + ". ", 8), ColorIO.WHITE);
            }

            Order o = orderList.get(i);

            String com = ", ";
            if (o.getLastName().equals("")) {
                com = "";
            }

            io.printx(StringUtil.padStr(o.getOrderNumber(), width[0] - 8), ColorIO.GREEN);
            io.print(StringUtil.padStr(o.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), width[1])
                    + StringUtil.padStr(o.getLastName() + com + o.getFirstName(), width[2], true)
                    + "  " + StringUtil.padStr(o.getState().getStateCode(), width[3], true)
                    + "  " + StringUtil.padStr(o.getProduct().getProductName(), width[4], true)
                    + "  " + StringUtil.padStr(StringUtil.rightAlign(o.getArea().setScale(2, RoundingMode.HALF_UP).toString(), 5), width[5])
                    + StringUtil.padStr("$" + StringUtil.rightAlign(o.getTotalCost()
                            .setScale(2, RoundingMode.HALF_UP).toString(), 9), width[6])
                    + StringUtil.padStr(o.getRevisionDate()
                            .format(DateTimeFormatter.ofPattern(rdateFormat)), width[7]), ColorIO.RESET);

        }
        longSeparator();
    }

    public int getSearchInquiry(int size) {
        io.print("Which order would you like to see?");
        io.print("Enter '0' to return to the main menu.");
        io.line();
        return io.readInt(">", 0, size);
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

    public boolean infoVerify() {
        return io.readAnswer("Are you sure this information is correct (y/n)? ", ColorIO.PURPLE);
    }

    public void removeOrderSuccess(String orderNum) {
        io.line();
        io.print("<----- Order " + orderNum + " removed successfully! ----->");
        waitOnUser();
    }

    public void addOrderSuccess(String orderNum) {
        io.line();
        io.print("<----- Order " + orderNum + " entered successfully! ----->");
        waitOnUser();
    }

    private void trainingMsg() {
        if (this.mode) {
            io.print(" ** Training Mode **\n", ColorIO.PURPLE);
        } else {
            io.line();
        }
    }

    public void displayResultsBanner() {
        io.print("***** Search Results *****", BANNER_COLOR);
        trainingMsg();
    }

    public void displayHistoryBanner() {
        io.print("***** Order History *****", BANNER_COLOR);
        trainingMsg();
    }

    public void displayMainMenuBanner() {
        io.print("***** Main Menu *****", BANNER_COLOR);
        trainingMsg();
    }

    public void displayUpdateBanner() {
        io.print("***** Update Order *****", BANNER_COLOR);
        trainingMsg();
    }

    public void displayNewOrderBanner() {
        io.print("***** New Order *****", BANNER_COLOR);
        trainingMsg();
    }

    public void displaySearchBanner() {
        io.print("***** Find Orders *****", BANNER_COLOR);
        trainingMsg();
    }

    public void displayOrderBanner() {
        io.print("***** Display Order *****", BANNER_COLOR);
        trainingMsg();

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
        io.print("------------------------------------", ColorIO.CYAN);
    }

    public void longSeparator() {
        io.print("----------------------------------------------------------------------------------------------------------------------------------------------------", ColorIO.CYAN);
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }
}
