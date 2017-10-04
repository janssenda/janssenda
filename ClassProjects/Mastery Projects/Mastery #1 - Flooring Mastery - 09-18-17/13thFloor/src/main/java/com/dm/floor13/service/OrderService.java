/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.service;

import com.dm.floor13.dao.SearchMethod;
import com.dm.floor13.exceptions.ChangeOrderException;
import com.dm.floor13.exceptions.OrderNotFoundException;
import com.dm.floor13.model.Order;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public interface OrderService {

    public boolean getMode();
    
    public List<Order> findOrders(SearchMethod method, Object key);

    public Order addUpdateOrder(Order order) throws ChangeOrderException; 

    public boolean removeOrder(Order order) throws OrderNotFoundException,
            ChangeOrderException; 
            
    public List<Order> getOrder(String orderNumber) throws OrderNotFoundException;  
            
    public boolean validateOrder(Order order); 
    
//    public Order populateOrder(Order order);

}
