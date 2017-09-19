/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.model;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.productName);
        hash = 43 * hash + Objects.hashCode(this.costpersqft);
        hash = 43 * hash + Objects.hashCode(this.laborpersqft);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.costpersqft, other.costpersqft)) {
            return false;
        }
        if (!Objects.equals(this.laborpersqft, other.laborpersqft)) {
            return false;
        }
        return true;
    }

}
