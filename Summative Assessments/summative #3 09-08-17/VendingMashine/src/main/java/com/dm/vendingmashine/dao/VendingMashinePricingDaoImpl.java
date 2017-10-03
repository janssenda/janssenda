/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

import java.util.Map;


/**
 *
 * @author danimaetrix
 */
public class VendingMashinePricingDaoImpl implements VendingMashinePricingDao {


    @Override
    public Map<String,String> loadPricingFromFile(String filename) throws FileIOException {

        VendingFileHandler fileHandler = new VendingFileHandler(filename);
        return fileHandler.readPricingFromFile();

    }

}
