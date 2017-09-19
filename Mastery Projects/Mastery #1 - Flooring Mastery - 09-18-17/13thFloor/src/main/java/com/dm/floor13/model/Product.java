/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.model;

import java.math.BigDecimal;

/**
 *
 * @author Danimaetrix
 */
public class Product {

    private String productName;
    private BigDecimal costpersqft;
    private BigDecimal laborpersqft;

    public Product(String productName) {
        this.productName = productName;
        this.costpersqft = BigDecimal.ZERO;
        this.laborpersqft = BigDecimal.ZERO;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getCostpersqft() {
        return costpersqft;
    }

    public void setCostpersqft(BigDecimal costpersqft) {
        this.costpersqft = costpersqft;
    }

    public BigDecimal getLaborpersqft() {
        return laborpersqft;
    }

    public void setLaborpersqft(BigDecimal laborpersqft) {
        this.laborpersqft = laborpersqft;
    }

}
