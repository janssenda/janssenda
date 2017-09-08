/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author danimaetrix
 */
public class Money {

    int quarters, dimes, nickels, pennies;
    BigDecimal totalmoney;

    public Money(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
        breakMoney(totalmoney);

    }

    // Break the total amount into real USD.  Note
    // rounding up takes place in this process to
    //facilitate conversion to real values.
    private void breakMoney(BigDecimal m) {
        int total = m.setScale(2, RoundingMode.HALF_UP)
                .movePointRight(2).intValue();

        this.quarters = total / 25;
        total = total - 25 * quarters;
        this.dimes = total / 10;
        total = total - 10 * dimes;
        this.nickels = total / 5;
        this.pennies = total - 5 * nickels;

    }

    public int getQuarters() {
        return quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public int getPennies() {
        return pennies;
    }

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

}
