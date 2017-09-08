/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dto;

import java.time.LocalDate;

/**
 *
 * @author danimaetrix
 */
public class Product {
    String productname;
    String information;
    LocalDate manufactured;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public LocalDate getManufactured() {
        return manufactured;
    }

    public void setManufactured(LocalDate manufactured) {
        this.manufactured = manufactured;
    }
    
    
    
}
