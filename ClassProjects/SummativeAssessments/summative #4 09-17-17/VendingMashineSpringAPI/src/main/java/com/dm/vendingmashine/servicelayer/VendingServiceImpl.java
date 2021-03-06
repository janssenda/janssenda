/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.servicelayer;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.dm.vendingmashine.dao.InventoryDao;
import com.dm.vendingmashine.dao.PricingDao;

/**
 *
 * @author danimaetrix
 */
public class VendingServiceImpl implements VendingService {

    String errmsg;
    PricingDao daoPrices;
    InventoryDao daoInv;
    Map inventory;
    Map<String, String> pricing;

    public VendingServiceImpl(InventoryDao daoInv, PricingDao daoPrices) {

        this.daoPrices = daoPrices;
        this.daoInv = daoInv;

        try {

            this.pricing = daoPrices.loadPricingFromFile("priceData.csv");
            this.inventory = daoInv.readInventoryFromFile("inventoryData.csv");
        } catch (FileIOException e) {
            this.errmsg = e.getMessage();
        }
    }

    @Override
    public Map<String, String> getPricing() {
        return pricing;
    }

    @Override
    public void setPricing(Map<String, String> pricing) {
        this.pricing = pricing;
    }

    @Override
    public String checkForFileIOErrors() {
        return errmsg;
    }

    // Returns the simple pricemap // not currently needed
    @Override
    public Map<String, String> returnPriceMap() {
        return pricing;
    }

    // Builds the arraylist for the view class (i.e, prepares the data needed
    // to give the user choices.  Must be located in service layer because it
    // needs access to BOTH dao implementations (isSoldOut, pricing)
    @Override
    public List<String[]> returnPriceArrayWithStatus() {
        List<String[]> priceList = new ArrayList<>();
        Set<String> brands = pricing.keySet();
        brands.stream().forEach(name -> {
            String[] currentString = new String[3];
            currentString[0] = name;
            currentString[1] = (String) pricing.get(name);
            if (isSoldOut(name)) {
                currentString[2] = "<Out>";
            } else {
                currentString[2] = "";
            }
            priceList.add(currentString);
        });
        return priceList;
    }

    @Override
    public Product getProduct(String productName) {
        return daoInv.getProduct(productName);
    }

    @Override
    public Product vendProduct(Money m, String productName) throws NoItemInventoryException, InsufficientFundsException {
        if (!isSoldOut(productName)) {
            validateMoney(m, productName);
            calculateChange(m, productName);
        }
        return daoInv.vendItem(productName);
    }

    // Simply queries the dao to check on item availability.  Returns true if sold out.
    @Override
    public boolean isSoldOut(String productName) {
        return (daoInv.getProductQuantity(productName) == 0);
    }

    // We compare the big decimal values of the money object and the price
    // and return true if the money is >= the price, and false otherwise
    @Override
    public boolean validateMoney(Money m, String productName) throws InsufficientFundsException {
        BigDecimal cost = new BigDecimal((String) pricing.get(productName));

        boolean afford = (m.getTotalmoney().compareTo(cost) >= 0);

        if (!afford) {
            throw new InsufficientFundsException(productName + ": Insufficient funds attempt... ($"
                    + m.getTotalmoney().toString() + "/"
                    + pricing.get(productName) + ")");
        }

        return afford;
    }

    // Computes the change for user.  The change in available funds is computed via
    // big decimal methods to retain accuracy.  The physical change, however, must be rounded
    // to two decimal places so it can be broken down to US denominations evenly.
    // Since prices and paymnent are in the form of two decmial place doubles anyways,
    // the "rounding" that takes place here actually does nothing but is included
    // for completeness (i.e., we could charge fractional cents on top of items if we 
    // really wanted to, without requiring any code modification.    
    @Override
    public Money calculateChange(Money m, String name) {

        String price = (String) pricing.get(name);
        BigDecimal bigPrice = new BigDecimal(price);

        m.setTotalmoney(m.getTotalmoney().subtract(bigPrice));

        return m;
    }

    @Override
    public void updateInventory() throws FileIOException {
        daoInv.writeInventoryToFile("output.csv");
    }
}
