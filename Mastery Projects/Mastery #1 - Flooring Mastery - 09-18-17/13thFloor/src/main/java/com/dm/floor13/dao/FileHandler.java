/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import com.dm.floor13.model.Product;
import com.dm.floor13.model.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/* This class allocates reading and writing responsibilities according
to the calling method.  readPricing reads the price map into the system for use
in the service layer.  Inventory IO methods manage importing and exporting of
product list.  The product list is a CSV file containing one line for each single
product (i.e., inventory of 5 coke objects takes 5 lines). Because of this, we can 
allow each individual product object to retain individually owned values. For example,
each can may have a different best - by date, and a different message (as is the norm
in the coca-cola corp currently.  E.g, the names on cans that is part of a marketing campaign.
Now we can surprise the user with these things, and the may be unique per object!!
 */
public class FileHandler {

    private static final String DELIMITER = ",";            // Delimiter for reading and writing files
    private static final String DATEFORMAT = "MM/dd/yyyy";
    private static final String DIR = "./orders";
    private String filename;

    // Constructor 
    public FileHandler(String filename) {
        this.filename = filename;
    }

    public FileHandler() {
        this.filename = "";
    }

    public Map<String, BigDecimal> readTaxesFromFile() throws FileIOException {
        Scanner scanner;
        Map<String, BigDecimal> taxRates = new LinkedHashMap<>();

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (FileNotFoundException e) {
            throw new FileIOException("Could not open pricing file. Filename"
                    + " should be 'priceData.csv'", e);
        }

        String currentline;
        String[] currentTokens;

        try {
            while (scanner.hasNextLine()) {

                currentline = scanner.nextLine();
                try {
                    currentTokens = currentline.split(DELIMITER);

                    for (int i = 0; i < currentTokens.length; i++) {
                        currentTokens[i] = currentTokens[i].trim();
                    }

                    BigDecimal tax = new BigDecimal(currentTokens[1]);

                    if (!currentTokens[0].startsWith("//")) {
                        taxRates.put(currentTokens[0], tax);
                    }
                } catch (Exception e) {
                    // Skips the line if there is a problem but continues reading file
                }

            }
        } catch (Exception e) {
            throw new FileIOException("Error reading file: empty or corrupt ", e);
        }

        if (taxRates.isEmpty()) {
            throw new FileIOException("Error: no pricing data found.  Please"
                    + " add items to 'priceData.csv'");
        }

        scanner.close();
        return taxRates;

    }

    public Map<String, List<BigDecimal>> readPricingFromFile() throws FileIOException {
        Scanner scanner;
        Map<String, List<BigDecimal>> products = new LinkedHashMap<>();
        List<BigDecimal> prices = new ArrayList<>();

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (FileNotFoundException e) {
            throw new FileIOException("Could not open pricing file. Filename"
                    + " should be 'taxes.csv'", e);
        }

        String currentline;
        String[] currentTokens;

        try {
            while (scanner.hasNextLine()) {

                currentline = scanner.nextLine();
                try {
                    currentTokens = currentline.split(DELIMITER);

                    for (int i = 0; i < currentTokens.length; i++) {
                        currentTokens[i] = currentTokens[i].trim();
                    }

                    prices.clear();
                    BigDecimal perSquareFt = new BigDecimal(currentTokens[1]);
                    BigDecimal laborCost = new BigDecimal(currentTokens[2]);

                    prices.add(perSquareFt);
                    prices.add(laborCost);

                    if (!currentTokens[0].startsWith("//")) {
                        products.put(currentTokens[0], prices);
                    }
                } catch (Exception e) {
                    // Skips the line if there is a problem but continues reading file
                }

            }
        } catch (Exception e) {
            throw new FileIOException("Error reading file: empty or corrupt ", e);
        }

        if (products.isEmpty()) {
            throw new FileIOException("Error: no pricing data found.  Please"
                    + " add items to 'products.csv'");
        }

        scanner.close();
        return products;

    }

    public void writeOrdersToFile(List<Order> OrderList) throws FileIOException {
        PrintWriter out;
        String foutName = filename;

        try {
            out = new PrintWriter(new FileWriter(foutName));
        } catch (IOException e) {
            throw new FileIOException("Error opening file.  Is it open"
                    + "\nin another application? ", e);
        }

        for (int i = 0; i < OrderList.size(); i++) {

        }

        out.flush();
        out.close();
    }

    public List<Order> readAllOrdersFromFile() throws FileIOException {
        return readAllOrders(DIR);
    }

    public List<Order> readAllOrdersFromFile(String userDir) throws FileIOException {
        return readAllOrders(userDir);
    }

    public List<Order> readAllOrders(String dir) throws FileIOException {
        List<Order> allOrders = new ArrayList<>();
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    allOrders.addAll(readOrdersFromFile(file));
                } catch (FileIOException e){
                    //
                }
            }
        }
        System.out.println("Size total " + allOrders.size());
        return allOrders;
    }

    public List<Order> readOrdersFromFile(File file) throws FileIOException {
        LocalDate d;
        Scanner scanner;
        List<Order> orderList = new ArrayList<>();

        try {
            //scanner = new Scanner(new BufferedReader(new FileReader(filename)));
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileIOException("Could not open inventory file. Filename"
                    + " should be 'inventoryData.csv'", e);
        }

        String currentline;
        String[] currentTokens;

        try {
            scanner.nextLine();
            while (scanner.hasNextLine()) {

                currentline = scanner.nextLine();
                try {
                    currentTokens = currentline.split(DELIMITER);

                    for (int i = 0; i < currentTokens.length; i++) {
                        currentTokens[i] = currentTokens[i].trim();
                    }

                    Order currentOrder = new Order();
                    d = LocalDate.parse(currentTokens[1], DateTimeFormatter.ofPattern(DATEFORMAT));
                    State st = new State(currentTokens[4], new BigDecimal(currentTokens[7]));
                    Product currentProduct = new Product(currentTokens[6]);

                    currentProduct.setCostpersqft(new BigDecimal(currentTokens[8]));
                    currentProduct.setLaborpersqft(new BigDecimal(currentTokens[9]));

                    currentOrder.setState(st);
                    currentOrder.setOrderNumber(currentTokens[0]);
                    currentOrder.setDate(d);
                    currentOrder.setLastName(currentTokens[2]);
                    currentOrder.setFirstName(currentTokens[3]);
                    currentOrder.setArea(new BigDecimal(currentTokens[5]));
                    currentOrder.setProduct(currentProduct);
                    currentOrder.recalculateData();

                    orderList.add(currentOrder);

                } catch (Exception e) {
                    // Skips the line if there is a problem but continues reading file
                }

            }

            System.out.println("Size " + orderList.size());

        } catch (Exception e) {

            throw new FileIOException("Error reading file: empty or corrupt ", e);
        }

        scanner.close();
        return orderList;
    }

    public void AuditLogToFile(String entry, boolean writeMode) throws FileIOException {
        PrintWriter out;
        String foutName = filename;

        try {
            out = new PrintWriter(new FileWriter(foutName, writeMode));
        } catch (IOException e) {
            throw new FileIOException("Error opening audit file.  Is it open"
                    + "\nin another application? ", e);
        }

        out.write(entry);
        out.write("\r\n");
        out.flush();
        out.close();

    }

}
