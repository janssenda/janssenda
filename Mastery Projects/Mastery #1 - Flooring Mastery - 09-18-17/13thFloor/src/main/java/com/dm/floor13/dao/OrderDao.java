/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author danimaetrix
 */
public class OrderDao {

    private Map<String, List<Order>> orderMap;
    private int orderNumberLength;
    private String currentDir;
    private int currentOrderNumber;

    public OrderDao() {
        this.currentDir = "./orders";
        this.orderNumberLength = 5;
    }

    public OrderDao(String directory) {
        this.currentDir = directory;
        this.orderNumberLength = 5;
    }

    // Reads all orders in the current directory, and sorts them according to order number,
    // and then by revision date.  The result is a map of List<Order> that are keyed by order date,
    // and each list contains all the order revisions from latest (0) to oldest (N).  Upon
    // first read of a data set, the order global current order number is updated.
    public Map<String, List<Order>> readAllOrdersFromDirectory() {

        FileHandler orderHandler = new FileHandler();
        List<Order> orders = orderHandler.readAllOrders(currentDir, orderNumberLength);

        this.orderMap = orders.stream().sorted((o1, o2)
                -> -o1.getRevisionDate().compareTo(o2.getRevisionDate()))
                .collect(Collectors.groupingBy(Order::getOrderNumber));

        updateCurrentOrderNumber();

        return this.orderMap;
    }

    // Used to safely fetch an order from the map. It is assumed that if the order does
    // not exist in the map, it should not exist in the file database since the two are
    // linked in real time.  Throws an exception if the order does not exist.
    public List<Order> getOrder(String orderNumber) throws OrderNotFoundException {
        if (orderMap.containsKey(orderNumber)) {
            return orderMap.get(orderNumber);
        } else {
            throw new OrderNotFoundException("Error: order does not exist...");
        }
    }

    // Removes an order from all data storage based on order number.
    // If the order exists, its history is pulled from the map.  The file
    // handler method removes the order data from any file containing the order.
    // The list of files to modify is determined by getting the dates in each order
    // revision and constructing the filenames.  If the file removal is successful,
    // the order data is also removed from the map.  All files are backed up in real
    // time to prevent data loss during modification.
    public void removeOrder(String orderNumber)
            throws OrderNotFoundException,
            BackupFileException,
            FileIOException,
            MissingFileException {

        if (orderMap.containsKey(orderNumber)) {
            FileHandler orderHandler = new FileHandler();
            List<Order> orderList = orderMap.get(orderNumber);

            orderHandler.removeOrderFromAll(orderList, currentDir);
            orderMap.remove(orderNumber);

        } else {
            throw new OrderNotFoundException("Error: order does not exist...");
        }

    }

    // Adds new orders or updates existing orders.  The map key is used to determine
    // whether an order already exists.  If so, the order is inserted into the 
    // map list at position 0 since it is newest.  If insertion is successful,
    // the file handler is used to safely add data to existing files for the order date,
    // or create a new file if the date does not exist. A new order number is generated and
    // and assigned to the order if it does not contain one
    public String AddUpdateOrder(String orderNumber, Order order) throws
            FileIOException,
            BackupFileException {

        if (order.getOrderNumber() == null || !orderMap.containsKey(order.getOrderNumber())) {
            List<Order> newOrder = new ArrayList<>();
            order.setOrderNumber(Integer.toString(currentOrderNumber + 1));
            newOrder.add(order);
            orderMap.put(orderNumber, newOrder);
            writeSingleOrderToDirectory(newOrder, "");
            currentOrderNumber = currentOrderNumber + 1;

        } else {

            List<Order> oldOrder = orderMap.get(order.getOrderNumber());
            oldOrder.add(0, order);
            orderMap.put(order.getOrderNumber(), oldOrder);
            writeSingleOrderToDirectory(oldOrder, "");

        }

        return order.getOrderNumber();
    }

    // Updates the current order number by finding the maximum value of order
    // numbers in the map.  Runs at startup and generally does not need to be 
    // re-run, since currentOrderNumber is auto-updated with new entries
    public void updateCurrentOrderNumber() {

        this.currentOrderNumber = orderMap.keySet()
                .stream()
                .mapToInt(n
                        -> Integer.parseInt(n)).max().getAsInt();
    }

    // Responsiblel for implementing the calls to file database from the add/edit/remove
    // methods described above. Not to be used outside of those methods.
    private void writeSingleOrderToDirectory(List<Order> orderList, String path)
            throws FileIOException,
            BackupFileException {

        FileHandler orderHandler = new FileHandler();
        orderHandler.writeSingleOrder(orderList, currentDir + path + "/");

    }

    // Fetches the current order number
    public int getCurrentOrderNumber() {
        return this.currentOrderNumber;
    }

    // Current working directory
    public String getcurrentDir() {
        return currentDir;
    }

    public void setcurrentDir(String directory) {
        this.currentDir = directory;
    }

    public int getOrderNumberLength() {
        return orderNumberLength;
    }

    public void setOrderNumberLength(int orderNumberLength) {
        this.orderNumberLength = orderNumberLength;
    }

    public void writeOrdersToDirectory(String path) {
        FileHandler orderHandler = new FileHandler();
        orderHandler.writeAllOrdersSplitFilesByDate(orderMap, currentDir + path + "/");
    }

}
