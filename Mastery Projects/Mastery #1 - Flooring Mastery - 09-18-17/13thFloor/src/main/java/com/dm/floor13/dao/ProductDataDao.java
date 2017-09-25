/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.model.Product;

/**
 *
 * @author danimaetrix
 */
public interface ProductDataDao {
    
    public void readDataFromFile(String filename) throws FileSkipException;

    public boolean isProduct(String productName);

    public Product getProduct(String productName) throws MissingDataException; 

}
