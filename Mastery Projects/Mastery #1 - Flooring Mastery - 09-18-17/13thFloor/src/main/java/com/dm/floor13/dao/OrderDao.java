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
 * @author danimaetrixz
 */
public interface OrderDao { 



    // Reads all orders in the current directory, and sorts them according to order number,
    // and then by revision date.  The result is a map of List<Order> that are keyed by order date,
    // and each list contains all the order revisions from latest (0) to oldest (N).  Upon
    // first read of a data set, the order global current order number is updated.
    public Map<String, List<Order>> readAllOrdersFromDirectory();

    // Used to safely fetch an order from the map. It is assumed that if the order does
    // not exist in the map, it should not exist in the file database since the two are
    // linked in real time.  Throws an exception if the order does not exist.
    public List<Order> getOrder(String orderNumber) throws OrderNotFoundException; 

    // Removes an order from all data storage based on order number.
    // If the order exists, its history is pulled from the map.  The file
    // handler method removes the order data from any file containing the order.
    // The list of files to modify is determined by getting the dates in each order
    // revision and constructing the filenames.  If the file removal is successful,
    // the order data is also removed from the map.  All files are backed up in real
    // time to prevent data loss during modification.
    public void removeOrder(String orderNumber) throws OrderNotFoundException, ChangeOrderException; 

    // Adds new orders or updates existing orders.  The map key is used to determine
    // whether an order already exists.  If so, the order is inserted into the 
    // map list at position 0 since it is newest.  If insertion is successful,
    // the file handler is used to safely add data to existing files for the order date,
    // or create a new file if the date does not exist. A new order number is generated and
    // and assigned to the order if it does not contain one
    public Order addUpdateOrder(Order order) throws ChangeOrderException; 
    // Updates the current order number by finding the maximum value of order
    // numbers in the map.  Runs at startup and generally does not need to be 
    // re-run, since currentOrderNumber is auto-updated with new entries
//    public void updateCurrentOrderNumber();  

    public boolean orderExists(String orderNumber); 

//    // Fetches the current order number
//    public int getCurrentOrderNumber(); 

//    // Current working directory
//    public String getcurrentDir(); 
//
//    public void setcurrentDir(String directory);
//
//    public int getOrderNumberLength(); 
//
//    public void setOrderNumberLength(int orderNumberLength);

//    public void writeOrdersToDirectory(String path); 

//    public Map<String, List<Order>> getOrderMap(); 
//
//    public void setOrderMap(Map<String, List<Order>> orderMap); 
//
//    public int getOrderMapSize();

}
