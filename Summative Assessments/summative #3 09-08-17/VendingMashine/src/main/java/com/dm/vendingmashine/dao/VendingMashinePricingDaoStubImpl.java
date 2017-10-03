/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * @author danimaetrix
 */
public class VendingMashinePricingDaoStubImpl implements VendingMashinePricingDao {


    @Override
    public Map<String,String> loadPricingFromFile(String filename) {
        
        Map<String,String> prices = new LinkedHashMap<>();
        
        prices.put("Coke","1.50");
        prices.put("Diet Coke","1.50");
        prices.put("Sprite","1.50");
        prices.put("Mello Yellow","1.50");
        prices.put("Dasani","1.75");        
        
        return prices;
    }

}
