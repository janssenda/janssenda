/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingFileException;
import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.exceptions.FileIOException;
import com.dm.floor13.exceptions.BackupFileException;
import com.dm.floor13.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author danimaetrix
 */
public class OrderDaoImpl implements OrderDao {

 
    private Map<String, List<Order>> orderMap;
    private int orderNumberLength;
    private String currentDir, currentRoot;
    private int currentOrderNumber;

    public OrderDaoImpl() {
        this.currentDir = "./data/orders";
        this.currentRoot = "./data";
        this.orderNumberLength = 5;
    }

    public OrderDaoImpl(String rootdir, String workingdir) {
        this.currentDir = workingdir;
        this.currentRoot = rootdir;
        this.orderNumberLength = 5;
    }


    // Reads all orders in the current directory, and sorts them according to order number,
    // and then by revision date.  The result is a map of List<Order> that are keyed by order date,
    // and each list contains all the order revisions from latest (0) to oldest (N).  Upon
    // first read of a data set, the order global current order number is updated.
    @Override
    public Map<String, List<Order>> readAllOrdersFromDirectory() {

        FileHandler orderHandler = new FileHandler(currentDir);

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
    @Override
    public List<Order> getOrder(String orderNumber) throws OrderNotFoundException {
        List<Order> clonedOrderList = new ArrayList<>();
        List<Order> originalOrderList;
        if (orderMap.containsKey(orderNumber)) {
            originalOrderList = orderMap.get(orderNumber);

            for (int i = 0; i < originalOrderList.size(); i++) {
                Order base = originalOrderList.get(i);
                Order clone = originalOrderList.get(i).clone();

                clone.setState(base.getState().clone());
                clone.setProduct(base.getProduct().clone());

                clonedOrderList.add(clone);
            }
            return clonedOrderList;
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
    @Override
    public void removeOrder(String orderNumber) throws OrderNotFoundException, ChangeOrderException {

        if (orderMap.containsKey(orderNumber)) {
            FileHandler orderHandler = new FileHandler(currentDir);
            List<Order> orderList = orderMap.get(orderNumber);

            orderMap.remove(orderNumber);

            if (orderExists(orderNumber)) {
                throw new ChangeOrderException("Problem removing order... ");
            }

            try {
                orderHandler.removeOrderFromAll(orderList, currentDir);

            } catch (BackupFileException | MissingFileException | FileIOException e) {
                throw new ChangeOrderException("Problem removing order... ");
            }

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
    @Override
    public Order addUpdateOrder(Order order) throws ChangeOrderException {

        if (!orderExists(order.getOrderNumber())) {
            List<Order> newOrder = new ArrayList<>();
            order.setOrderNumber(Integer.toString(currentOrderNumber + 1));
            newOrder.add(order);

            order.recalculateData();
            orderMap.put(order.getOrderNumber(), newOrder);

            if (orderMap.get(order.getOrderNumber()).get(0) != order) {
                currentOrderNumber = currentOrderNumber - 1;
                throw new ChangeOrderException("Problem adding new order... ");
            }

            // Try to write the changes to file.  If we fail, remove the order
            // from the map...
            try {
                writeSingleOrderToDirectory(newOrder);
            } catch (FileIOException | BackupFileException e) {
                orderMap.remove(order.getOrderNumber());
                throw new ChangeOrderException("Problem adding new order... ");

            }
            currentOrderNumber = currentOrderNumber + 1;

        } else {

            // Order already exists, now we append it instead of adding a new order
            List<Order> oldOrder = orderMap.get(order.getOrderNumber());
            order.recalculateData();
            oldOrder.add(0, order);
            orderMap.put(order.getOrderNumber(), oldOrder);

            if (orderMap.get(order.getOrderNumber()).get(0) != order) {
                oldOrder.remove(0);
                orderMap.put(order.getOrderNumber(), oldOrder);
                throw new ChangeOrderException("Problem adding new order... ");
            }

            // Try to write the changes to file.  If we fail, remove the order
            // from the map...
            try {
                writeSingleOrderToDirectory(oldOrder);
            } catch (FileIOException | BackupFileException e) {
                oldOrder.remove(0);
                orderMap.put(order.getOrderNumber(), oldOrder);
                orderMap.remove(order.getOrderNumber());
                throw new ChangeOrderException("Problem adding new order... ");
            }

        }

        return order;
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
    private void writeSingleOrderToDirectory(List<Order> orderList)
            throws FileIOException,
            BackupFileException {
        writeSingleOrderToDirectory(orderList, "");
    }

    private void writeSingleOrderToDirectory(List<Order> orderList, String path)
            throws FileIOException,
            BackupFileException {

        FileHandler orderHandler = new FileHandler(currentDir);
        orderHandler.writeSingleOrder(orderList, currentDir + path + "/");

    }

    
    @Override
    public boolean orderExists(String orderNumber) {
        return orderMap.containsKey(orderNumber);
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
        FileHandler orderHandler = new FileHandler(currentDir);
        orderHandler.writeAllOrdersSplitFilesByDate(orderMap, currentDir + path + "/");
    }


    public Map<String, List<Order>> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, List<Order>> orderMap) {
        this.orderMap = orderMap;
    }


    public int getOrderMapSize() {
        return orderMap.size();
    }


}
