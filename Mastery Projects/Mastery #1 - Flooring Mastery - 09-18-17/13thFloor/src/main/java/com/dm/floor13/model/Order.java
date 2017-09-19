/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author danimaetrix
 */
public class Order {

    private String orderNumber;
    private String firstName;
    private String lastName;
    private State state;
    private LocalDate date;
    private BigDecimal area;
    private Product product;

    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal totalCost;

    public Order() {
        this.orderNumber = null;
        this.state = new State();
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
        this.state = state;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void recalculateData() {
        materialCost = product.getCostpersqft().multiply(area);
        laborCost = product.getLaborpersqft().multiply(area);
        totalCost = (laborCost.add(materialCost))
                .multiply((state.getTaxrate().scaleByPowerOfTen(-2)).add(BigDecimal.ONE));
    }

}
