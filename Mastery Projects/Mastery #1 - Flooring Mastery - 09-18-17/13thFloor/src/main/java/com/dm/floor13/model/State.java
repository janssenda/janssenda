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
public class State {
    private String stateCode;
    private BigDecimal taxRate;

    public State(String stateCode, BigDecimal taxRate){
        this.stateCode = stateCode;
        this.taxRate = taxRate;
    }
    
    public State(){
        this.stateCode = "";
        this.taxRate = null;
    }
    
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public BigDecimal getTaxrate() {
        return taxRate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxRate = taxrate;
    }
    
}
