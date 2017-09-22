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
    private String currentDir, currentRoot;


    public ProductDataDaoImpl() {
        this.currentDir = "./data/orders";
        this.currentRoot = "./data";
    }

    public ProductDataDaoImpl(String rootdir, String workingdir) {
        this.currentDir = workingdir;
        this.currentRoot = rootdir;
       }

    public void readDataFromFile() throws FileSkipException {
        FileHandler orderHandler = new FileHandler(currentRoot);
        Map<String, Product> productMap = null;

        try {
            productMap = orderHandler.readProductsFromFile(currentRoot + "/Products.txt");
        } catch (FileIOException e) {
            throw new FileSkipException("Products file was not read - blank, in use, or corrupt...");
        }

        if (productMap.isEmpty()) {
            throw new FileSkipException("Productsfile was not read - blank, in use, or corrupt...");
        }

        this.productMap = productMap;
    }


    public boolean isProduct(String productName) {
        return productMap.containsKey(productName);
    }

    public Product getProduct(String productName) throws MissingDataException {
        if (productMap.containsKey(productName)) {
            return productMap.get(productName);
        } else {
            throw new MissingDataException("That product was not recognized...");
        }
    }

    // Current working directory
    public String getcurrentDir() {
        return currentDir;
    }

    public void setcurrentDir(String directory) {
        this.currentDir = directory;
    }    

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public int getProductMapSize() {
        return productMap.size();
    }
}
