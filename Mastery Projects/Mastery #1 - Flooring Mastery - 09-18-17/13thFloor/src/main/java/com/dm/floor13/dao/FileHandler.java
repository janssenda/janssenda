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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static final String REVDATEFORMAT = "MM/dd/yyyy @ hh:mm:ss a";
    private int skippedLineCount;
    private int skippedFileCount;
    private int orderNumberLength;
    private String DIR;

    // Constructor 
    public FileHandler() {
        this.DIR = "./orders";
        this.skippedLineCount = 0;
        this.skippedFileCount = 0;
        this.orderNumberLength = 5;
    }

    public FileHandler(String directory) {
        this.DIR = directory;
        this.skippedLineCount = 0;
        this.skippedFileCount = 0;
        this.orderNumberLength = 5;
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

    // This set of methods writes the entire map to file.  The method can be run in the currrent 
    // directory with no directory argument, or in a specific directory.  This method is useful for
    // verifying data integrity, but should not be used for data management.  All files in the specified
    // directory are erased prior to implementation
    public void writeAllOrdersSplitFilesByDate(Map<String, List<Order>> orderMap, String directory) {
        writeOrdersToSplitFiles(orderMap, directory);
    }

    public void writeAllOrdersSplitFilesByDate(Map<String, List<Order>> orderMap) {
        writeOrdersToSplitFiles(orderMap, DIR);
    }

    private void writeOrdersToSplitFiles(Map<String, List<Order>> orderMap, String directory) {
        skippedFileCount = 0;
        Map<String, List<Order>> dateMap;

        // Generates non-existend dirs
        new File(directory).mkdirs();

        File folder = new File(directory);

        // Cleans directory of all files
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        // Builds map for output such that each file will contain all
        // orders for that file date
        dateMap = orderMap.values().stream().flatMap((o) -> o.stream())
                .collect(Collectors.groupingBy(o -> o.getDate()
                .format(DateTimeFormatter.ofPattern("MMddyyyy"))));

        // Write all the order date for each date in the map
        dateMap.forEach((k, v) -> {
            String name = directory + "Orders_" + k + ".csv";

            try {
                writeAllOrders(v, name);
            } catch (FileIOException e) {
                // Do nothing - skip this file
                skippedFileCount = skippedFileCount + 1;
            }

        });

    }

    public void removeOrderFromAll(List<Order> orderList)
            throws BackupFileException,
            FileIOException,
            MissingFileException {   
        

        removeOrderFromAll(orderList, DIR);

    }

    public void removeOrderFromAll(List<Order> orderList, String directory)
            throws BackupFileException,
            FileIOException,
            MissingFileException {
        
        Set<String> orderNames = new HashSet<>();
        String orderNumber = orderList.get(0).getOrderNumber();

        // Collect all the filenames that should contain the order number
        for (int i = 0; i < orderList.size(); i++) {
            orderNames.add(directory + "/Orders_"
                    + orderList.get(i).getDate()
                            .format(DateTimeFormatter.ofPattern("MMddyyyy"))
                    + ".csv");
        }

        List<String> nameList = new ArrayList<>();
        nameList.addAll(orderNames);

        for (int i = 0; i < nameList.size(); i++) {
            removeOrderFromOne(nameList.get(i), orderNumber, directory);
        }

        // Update each file
//        orderNames.forEach((date) -> {
//            try {
//                removeOrderFromOne(date, orderNumber, directory);
//            } catch (BackupFileException | FileIOException | MissingFileException e) {
//
//            }
//        });
    }

    // This method removes an order from a file by making a copy of the file, and then re-creating
    // the original without the order number.  If successful, the method deletes the backup.
    // If unsuccessful, the backup is restored.
    private void removeOrderFromOne(String filename, String orderNumber, String directory)
            throws BackupFileException,
            FileIOException,
            MissingFileException {

        
        int linecounter = 0;
        Scanner sc;
        PrintWriter out;
        directory = directory + "/tmp/";
        new File(directory).mkdirs();

        String tempfilename = directory + orderNumber + "_temp.csv";

        File file = new File(filename);
        File oldFile = new File(tempfilename);

        if (file.exists()) {
            try {
                Files.copy(file.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new BackupFileException("Error backing up original file,"
                        + "modification not permitted... ", e);
            }

            // Original file has been succesfully backed up, now we can
            // proceed with attemp to persist our changes
            try {
                file.delete();
                out = new PrintWriter(new FileWriter(filename, false));
                sc = new Scanner(oldFile);

                while (sc.hasNextLine()) {

                    String currentLine = sc.nextLine();
                    String[] tokens = currentLine.trim().split(DELIMITER);

                    if (!tokens[0].equals(orderNumber)) {
                        out.write(currentLine+"\n");
                        linecounter = linecounter + 1;
                    }

                }

                out.flush();
                
                // If the file is now empty, delete it
                if (linecounter <= 1) {
                    new File(filename).delete();
                }

            // If our changes fail, we recover the original file
            } catch (IOException e) {
                oldFile.renameTo(new File(filename));
                throw new FileIOException("Error writing the new file... ");
            }
            

            out.close();            
            // Delete the temporary file if all is successful    

            oldFile.delete();
        } else {
            throw new MissingFileException("No files for that date exist... ");
        }

    }

    public void writeSingleOrder(List<Order> orderList)
            throws FileIOException,
            BackupFileException {

        writeSingleOrder(orderList, DIR);
    }

    public void writeSingleOrder(List<Order> orderList, String directory)
            throws FileIOException,
            BackupFileException {

        PrintWriter out;
        new File(directory).mkdirs();
        new File(directory + "/tmp/").mkdirs();
        boolean header = true;

        String headerst = "Order Number,first_name,last_name,State,Area,Material,Tax,CPSF,"
                + "LCPSF,MaterialCost,LaborCost,TotalCost, Last Revised\n";

        String tmpDate = orderList.get(0).getDate()
                .format(DateTimeFormatter.ofPattern("MMddyyyy"));

        String filename = directory + "Orders_" + tmpDate + ".csv";
        String tempfilename = directory + "/tmp/" + tmpDate + "_temp.csv";

        File file = new File(filename);
        File oldFile = new File(tempfilename);

        // If the file already exists, we make a copy, and then modify the original.  In
        // case of a backup copy exception, modification of the orignial file will not be 
        // permitted.  Backups are stored in /tmp/.
        if (file.exists()) {
            header = false;
            try {
                Files.copy(file.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new BackupFileException("Error backing up original file,"
                        + "modification not permitted... ", e);
            }
        }

        // Original file has been succesfully backed up, now we can
        // proceed with attemp to persist our changes
        try {

            out = new PrintWriter(new FileWriter(filename, true));

            if (header) {
                out.write(headerst);
            }

            out.write(outputFormat(orderList.get(0)));
            out.flush();

        // If our changes fail, we recover the original file
        } catch (IOException e) {
            oldFile.renameTo(file);
            throw new FileIOException("Error opening the order file... ", e);
        }

        // Delete the temporary file if all is successful
        out.close();
        oldFile.delete();

    }

    public void writeAllOrders(List<Order> orderList, String filename) throws FileIOException {
        PrintWriter out;
        String header = "Order Number,first_name,last_name,State,Area,Material,Tax,CPSF,"
                + "LCPSF,MaterialCost,LaborCost,TotalCost, Last Revised";

        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new FileIOException("Error opening file.  Is it open"
                    + "\nin another application? ", e);
        }

        out.write(header + "\n");
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            out.write(outputFormat(order));
        }

        out.flush();
        out.close();
    }

    private String outputFormat(Order order) {
        Product p = order.getProduct();

        return (order.getOrderNumber() + DELIMITER
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
                + order.getTotalCost().toString() + DELIMITER
                + order.getRevisionDate().format(DateTimeFormatter.ofPattern(REVDATEFORMAT)) + "\n");
    }

    public List<Order> readAllOrders(String userDir) {
        
        return readAllOrdersFromFile(userDir, orderNumberLength);
    }

    public List<Order> readAllOrders(String userDir, int orderNumberLength) {
        return readAllOrdersFromFile(userDir, orderNumberLength);
    }

    private List<Order> readAllOrdersFromFile(String dir, int orderNumberLength) {
        this.skippedLineCount = 0;
        this.skippedFileCount = 0;
        List<Order> allOrders = new ArrayList<>();
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    allOrders.addAll(readOrdersFromSingleFile(file, orderNumberLength));
                } catch (FileSkipException | MissingFileException e) {
                    skippedFileCount = skippedFileCount + 1;
                }
            }
        }

        return allOrders;
    }

    private boolean isOrderNumber(String s, int orderNumberLength) {

        try {
            Integer i = Integer.parseInt(s);
            return s.length() == orderNumberLength;
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
        LocalDateTime drev;
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
                    drev = LocalDateTime.parse(currentTokens[13], DateTimeFormatter.ofPattern(REVDATEFORMAT));
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
                    currentOrder.setRevisionDate(drev);
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

    public int getSkippedFileCount() {
        return skippedFileCount;
    }

}
