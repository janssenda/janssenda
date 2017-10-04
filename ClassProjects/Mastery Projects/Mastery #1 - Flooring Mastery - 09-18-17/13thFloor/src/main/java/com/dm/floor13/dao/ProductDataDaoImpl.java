/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.exceptions.FileIOException;
import com.dm.floor13.model.Product;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public class ProductDataDaoImpl implements ProductDataDao {

    private Map<String, Product> productMap;
    private String currentRoot;


    public ProductDataDaoImpl() {
        this.currentRoot = "./";
    }

    public ProductDataDaoImpl(String rootdir) {
        this.currentRoot = rootdir;
       }  


    @Override
    public void readDataFromFile(String filename) throws FileSkipException {
        FileHandler orderHandler = new LargeDataHandler();
        Map<String, Product> productMap;

        try {
            productMap = orderHandler.readProductsFromFile(filename);
        } catch (FileIOException e) {
            throw new FileSkipException("Products file was not read - blank, in use, or corrupt...");
        }

        if (productMap.isEmpty()) {
            throw new FileSkipException("Productsfile was not read - blank, in use, or corrupt...");
        }

        this.productMap = productMap;        
        
    }      

    @Override
    public boolean isProduct(String productName) { 
        return productMap.containsKey(productName);
    }

    @Override
    public Product getProduct(String productName) throws MissingDataException {
        if (productMap.containsKey(productName)) {
            return productMap.get(productName);
        } else {
            throw new MissingDataException("That product was not recognized...");
        }
    }

    // Current working directory
    public String getcurrentDir() {
        return currentRoot;
    }

    public void setcurrentDir(String directory) {
        this.currentRoot = directory;
    }    

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public int getProductMapSize() {
        return productMap.size();
    }
}
