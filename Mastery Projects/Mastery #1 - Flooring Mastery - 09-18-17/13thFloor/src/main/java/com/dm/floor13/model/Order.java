/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author danimaetrix
 */
public class Order implements Cloneable {

    private boolean isNew;
    private String orderNumber;
    private String firstName;
    private String lastName;
    private State state;
    private LocalDate date;
    private LocalDateTime revisionDate;

    private BigDecimal area;
    private Product product;

    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal totalCost;

    public Order() {
        this.isNew = true;
        this.area = BigDecimal.ZERO;
        this.date = LocalDate.MIN;
        this.orderNumber = "< Not Assigned >";
        this.state = new State().clone();
        this.product = new Product().clone();
        this.totalCost = null;
        this.laborCost = null;
        this.materialCost = null;
        this.revisionDate = null;
    }

    public boolean compareThisTo(Order order) {
        return this == order;
    }

    @Override
    public Order clone() {
        Order newOrder;
        try {
            newOrder = (Order) super.clone();
        } catch (CloneNotSupportedException e) {
            newOrder = null;
        }

        newOrder.state = this.state.clone();
        newOrder.product = this.product.clone();

        return newOrder;
    }

    @Override
    public String toString() {

        return ("Order#: " + orderNumber
                + " Date: " + date.format(DateTimeFormatter.ofPattern("MM/dd/yy"))
                + " Name: " + lastName + ", " + firstName);
    }

    public boolean containsKeyWord(String key) {

        String cdate = this.date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toLowerCase();
        String crevdate = this.revisionDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toLowerCase();
        
        return this.orderNumber.toLowerCase().contains(key.toLowerCase())
                || this.firstName.toLowerCase().contains(key.toLowerCase())
                || this.firstName.toLowerCase().contains(key.toLowerCase())
                || this.lastName.toLowerCase().contains(key.toLowerCase())
                || this.product.getProductName().toLowerCase().contains(key.toLowerCase())
                || this.state.getStateCode().toLowerCase().contains(key.toLowerCase())
                || cdate.contains(key.toLowerCase())
                || crevdate.contains(key.toLowerCase())
                || this.area.toString().toLowerCase().contains(key.toLowerCase())
                || this.totalCost.toString().toLowerCase().contains(key.toLowerCase());
    }

    public void setOrderStatus(boolean status) {
        this.isNew = status;
    }

    public boolean isNewOrder() {
        return this.isNew;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Product getProduct() {
        return product;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state.clone();
    }

    public void setProduct(Product product) {
        this.product = product.clone();
    }

    public void recalculateData() {
        materialCost = product.getCostpersqft().multiply(area);
        laborCost = product.getLaborpersqft().multiply(area);
        totalCost = (laborCost.add(materialCost))
                .multiply((state.getTaxrate().scaleByPowerOfTen(-2)).add(BigDecimal.ONE));
    }

    public LocalDateTime getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(LocalDateTime revisionDate) {
        this.revisionDate = revisionDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.orderNumber);
        hash = 11 * hash + Objects.hashCode(this.firstName);
        hash = 11 * hash + Objects.hashCode(this.lastName);
        hash = 11 * hash + Objects.hashCode(this.state);
        hash = 11 * hash + Objects.hashCode(this.date);
        hash = 11 * hash + Objects.hashCode(this.area);
        hash = 11 * hash + Objects.hashCode(this.product);
        hash = 11 * hash + Objects.hashCode(this.materialCost);
        hash = 11 * hash + Objects.hashCode(this.laborCost);
        hash = 11 * hash + Objects.hashCode(this.totalCost);
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.orderNumber, other.orderNumber)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.totalCost, other.totalCost)) {
            return false;
        }
        return true;
    }

}
