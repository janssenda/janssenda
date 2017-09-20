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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


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
    private int skippedLineCount;
    private int skippedFileCount;
    private String DIR;

    // Constructor 
    public FileHandler() {
        this.DIR = "./orders";
        this.skippedLineCount = 0;
        this.skippedFileCount = 0;
    }

    public FileHandler(String directory) {
        this.DIR = directory;
        this.skippedLineCount = 0;
        this.skippedFileCount = 0;
    }

    public Map<String, BigDecimal> readTaxesFromFile(String filename) throws FileIOException {
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

    public Map<String, List<BigDecimal>> readPricingFromFile(String filename) throws FileIOException {
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

    public void writeAllOrdersSplitFilesByDate(Map<String, Order> orderMap, String directory) {
        writeOrdersToSplitFiles(orderMap, directory);
    }

    private void writeOrdersToSplitFiles(Map<String, Order> orderMap, String directory) {
        Map<String, List<Order>> dateMap;
        new File(directory).mkdirs();

        File folder = new File(directory);

        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        dateMap = orderMap.values().stream()
                .sorted((o1, o2)-> o1.getLastName().compareTo(o2.getLastName()))
                .collect(Collectors.groupingBy(o -> o.getDate()
                .format(DateTimeFormatter.ofPattern("MMddyyyy"))));

        dateMap.forEach((k, v) -> {
            String name = directory + "Orders_" + k + ".csv";

            try {
                writeAllOrders(v, name);
            } catch (FileIOException e) {
                ////////
            }

        });

    }

    public void writeAllOrders(List<Order> orderList, String filename) throws FileIOException {
        PrintWriter out;
        String header = "Order Number,first_name,last_name,State,Area,Material,Tax,CPSF,LCPSF,MaterialCost,LaborCost,TotalCost";

        //new File(filename).isFile();
        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new FileIOException("Error opening file.  Is it open"
                    + "\nin another application? ", e);
        }

        out.write(header + "\n");
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            Product p = order.getProduct();

            out.write(order.getOrderNumber() + DELIMITER
                    + order.getDate().format(DateTimeFormatter.ofPattern(DATEFORMAT)) + DELIMITER
                    + order.getLastName() + DELIMITER
                    + order.getFirstName() + DELIMITER
                    + order.getState().getStateCode() + DELIMITER
                    + order.getArea().toString() + DELIMITER
                    + p.getProductName() + DELIMITER
                    + order.getState().getTaxrate().toString() + DELIMITER
                    + p.getCostpersqft().toString() + DELIMITER
                    + p.getLaborpersqft().toString() + DELIMITER
                    + order.getMaterialCost().toString() + DELIMITER
                    + order.getLaborCost().toString() + DELIMITER
                    + order.getTotalCost().toString() + "\n");
        }

        out.flush();
        out.close();
    }

    public List<Order> readAllOrders(String userDir, int orderNumberLength) {
        return readAllOrdersFromFile(userDir, orderNumberLength);
    }

    private List<Order> readAllOrdersFromFile(String dir, int orderNumberLength) {
        this.skippedLineCount = 0;
        this.skippedFileCount = 0;
        List<Order> allOrders = new ArrayList<>();
        Set<Order> allOrderSet = new HashSet<>();
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    allOrderSet.addAll(readOrdersFromSingleFile(file, orderNumberLength));
                } catch (FileSkipException | MissingFileException e) {
                    skippedFileCount = skippedFileCount + 1;
                }
            }
        }
        allOrders.addAll(allOrderSet);
        return allOrders;
    }

    private boolean isOrderNumber(String s, int orderNumberLength) {

        try {
            Integer i = Integer.parseInt(s);

            if (s.length() == orderNumberLength) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Order> readOrdersFromSingleFile(String filename, int orderNumberLength)
            throws FileSkipException, MissingFileException {

        File file = new File(filename);
        return readOrdersFromSingleFile(file, orderNumberLength);
    }

    private List<Order> readOrdersFromSingleFile(File file, int orderNumberLength)
            throws FileSkipException, MissingFileException {
        LocalDate d;
        Scanner scanner;
        List<Order> orderList = new ArrayList<>();
        Set<Order> orderSet = new HashSet<>();

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new MissingFileException("Order file could not be found", e);
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

                    if (!isOrderNumber(currentTokens[0], orderNumberLength)) {
                        throw new NumberFormatException("Bad order number");
                    }

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
                    skippedLineCount = skippedLineCount + 1;
                    // Skips the line if there is a problem but continues reading file
                }

            }

            if (orderList.isEmpty()) {
                throw new FileSkipException("Error reading file: empty or corrupt ");
            }

        } catch (Exception e) {
            // Skips the entire file if there are no valid lines
            throw new FileSkipException("Error reading file: empty or corrupt ", e);
        }

        scanner.close();
        return orderList;
    }

    public void AuditLogToFile(String entry, boolean writeMode, String filename) throws FileIOException {
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

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    public void setSkippedLineCount(int skippedLineCount) {
        this.skippedLineCount = skippedLineCount;
    }

    public int getSkippedFileCount() {
        return skippedFileCount;
    }

    public void setSkippedFileCount(int skippedFileCount) {
        this.skippedFileCount = skippedFileCount;
    }

}
