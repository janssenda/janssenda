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
public class State implements Cloneable {

    private String stateCode;
    private BigDecimal taxRate;

    public State(String stateCode, BigDecimal taxRate) {
        this.stateCode = stateCode;
        this.taxRate = taxRate;
    }

    public State(String stateCode) {
        this.stateCode = stateCode;
        this.taxRate = null;
    }

    public State() {
        this.stateCode = "";
        this.taxRate = null;
    }

    public boolean compareThisTo(State state) {
        return this == state;
    }

    @Override
    public State clone() {
        State newState;
        try {
            newState = (State) super.clone();
        } catch (CloneNotSupportedException e) {
            newState = null;
        }
        return newState;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.stateCode);
        hash = 79 * hash + Objects.hashCode(this.taxRate);
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
        final State other = (State) obj;
        if (!Objects.equals(this.stateCode, other.stateCode)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        return true;
    }

}
