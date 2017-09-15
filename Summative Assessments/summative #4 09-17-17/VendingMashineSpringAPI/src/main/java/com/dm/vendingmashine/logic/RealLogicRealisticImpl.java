/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.logic;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import com.dm.vendingmashine.servicelayer.InsufficientFundsException;
import com.dm.vendingmashine.servicelayer.VendingService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author danimaetrix
 */
public class RealLogicRealisticImpl implements RealLogic {

    public static final String IMPLEMENTATION = "realistic";
    VendingService service;
    Map<String, List<Product>> itemMap;

    RealLogicRealisticImpl(VendingService service) {
        this.service = service;
        this.itemMap = new LinkedHashMap<>();
    }

    @Override
    public List<Product> vendProduct(Money m, String productName)
            throws NoItemInventoryException,
            InsufficientFundsException,
            MachineJamException {
        List<Product> products = new ArrayList<>();

        Product p = service.vendProduct(m, productName);

        try {
            machineJamStatus();
            if (itemMap.containsKey(productName)) {
                products = itemMap.get(productName);
                itemMap.remove(productName);
            }
            products.add(p);
        } catch (MachineJamException e) {
            List<Product> productbuffer = new ArrayList<>();
            if (itemMap.containsKey(productName)) {
                productbuffer = itemMap.get(productName);
            }
            productbuffer.add(p);
            itemMap.put(productName, productbuffer);

            throw e;
        }

        return products;
    }

    @Override
    public Map<String, List<Product>> getStuckItems() {
        return itemMap;
    }

    private void machineJamStatus() throws MachineJamException {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            throw new MachineJamException("Oh no!! JAM!!! ");
        }
    }

    @Override
    public List<String[]> returnPriceArrayWithStatus() {
        return service.returnPriceArrayWithStatus();
    }

    @Override
    public void updateInventory() throws FileIOException {
        service.updateInventory();
    }

    @Override
    public String checkForFileIOErrors() {
        return service.checkForFileIOErrors();
    }

}
