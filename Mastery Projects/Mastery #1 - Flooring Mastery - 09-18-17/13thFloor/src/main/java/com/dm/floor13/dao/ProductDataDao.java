/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.model.Product;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public interface ProductDataDao {

    public void readDataFromFile() throws FileSkipException; 

    public boolean isProduct(String productName);

    public Product getProduct(String productName) throws MissingDataException; 
    
    
    // Current working directory
//    public String getcurrentDir(); 
//
//    public void setcurrentDir(String directory); 
//
//    public Map<String, Product> getProductMap();
//
//    public int getProductMapSize(); 
}
