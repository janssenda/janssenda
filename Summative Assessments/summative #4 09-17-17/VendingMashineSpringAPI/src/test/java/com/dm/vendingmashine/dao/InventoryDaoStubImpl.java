/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.InventoryDao;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Product;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author danimaetrix
 */
public class InventoryDaoStubImpl implements InventoryDao {

    private Map<String, List<Product>> inventory = new HashMap<>();

    public Map<String, List<Product>> readInventoryFromFileNormal(String filename) throws FileIOException {

        Product p1 = new Product();
        p1.setProductName("Coke1");
        p1.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p1.setInformation("Information");
        p1.setMessage("My Message");

        Product p2 = new Product();
        p2.setProductName("Coke1");
        p2.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p2.setInformation("Information");
        p2.setMessage("My Message");

        Product p3 = new Product();
        p3.setProductName("Coke1");
        p3.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p3.setInformation("Information");
        p3.setMessage("My Message");

        List<Product> cokelist = new ArrayList<>();

        cokelist.add(p1);
        cokelist.add(p2);
        cokelist.add(p3);

        // Stream the list and sort into our map. NOTE: Our items can now be out of order and will still sort!!! :) :)
        //this.inventory = InventoryList.stream().collect(Collectors.groupingBy(p -> p.getProductname()));      
        this.inventory = cokelist.stream().collect(Collectors.groupingBy(Product::getProductName));

        return this.inventory;
    }

    @Override
    public Map<String, List<Product>> readInventoryFromFile(String filename) {

        Product p1 = new Product();
        p1.setProductName("Coke1");
        p1.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p1.setInformation("Information");
        p1.setMessage("My Message");

        Product p2 = new Product();
        p2.setProductName("Coke2");
        p2.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p2.setInformation("Information");
        p2.setMessage("My Message");

        Product p3 = new Product();
        p3.setProductName("Coke3");
        p3.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p3.setInformation("Information");
        p3.setMessage("My Message");

        List<Product> cokelist = new ArrayList<>();

        cokelist.add(p1);
        cokelist.add(p2);
        cokelist.add(p3);

        this.inventory.put("Coke", cokelist);

        return this.inventory;
    }

    @Override
    public void writeInventoryToFile(String filename) {
        //VendingFileHandler fileHandler = new VendingFileHandler(filename);
        List<Product> ProductList = new ArrayList<>();
        String DELIMITER = ",";
        // We need to reverse the process of bringing the data in.  We obtain 
        // the keyset from the inventory map, and stream the keys.
        // for each key name, we obtain a list containing product objects associated
        // with that key.  We then simply append the list to our master list and move
        // to the next key.  In this way we can have dynamically changing inventory
        // without the need for explicit management.  Our sorting algorithm organizes
        // incoming data, so we do not care how it is laid out here: only that it gets
        // written as a list with the correct formatting!!!

        Set<String> brands = inventory.keySet();
        brands.stream().forEach(name -> {
            List<Product> l = (ArrayList<Product>) inventory.get(name);
            ProductList.addAll(l);
        });

        // Modified from file handler for screen printing;
        for (int i = 0; i < ProductList.size(); i++) {

            Product p = ProductList.get(i);
            System.out.println(p.getProductName() + DELIMITER
                    + p.getBestBy().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + DELIMITER
                    + p.getMessage() + DELIMITER
                    + p.getInformation());
        }

    }

    // Our vend method.  This method gets the next-in-line product of the requested 
    // productname. It returns the object to be "vended" upward to the user, and 
    // removes it from the corresponding list.  Our items will be nicely vended
    // in the order in which they have been loaded into the machine!!
    @Override
    public Product vendItem(String productName) throws NoItemInventoryException {

        if (getProductQuantity(productName) == 0) {
            throw new NoItemInventoryException("Error:  this item is empty...");
        } else {
            Product p = inventory.get(productName).get(0);
            inventory.get(productName).remove(0);

            return p;
        }

    }

    // Simply obtains the inventory of a product name by getting the length of
    // the ArrayList mapped to by that name. If the key does not exist, returns 0
    // to avoid null pointers and allow main menu to display sold out staus properly
    @Override
    public int getProductQuantity(String productName) {
        //System.out.println(productName);
        if (inventory.containsKey(productName)) {
            return inventory.get(productName).size();
        } else {
            return 0;
        }
    }

    @Override
    public Product getProduct(String productName) {
        return inventory.get(productName).get(0);
    }

}
