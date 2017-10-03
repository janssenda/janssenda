/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.ui;

import java.math.BigDecimal;

/**
 *
 * @author danimaetrix
 */
public interface UserIo {

    void print(String msg);

    public String readLine();

    public String readPasswordLn(String msg);

    public String readLine(String msg);

    public double readDouble();
    public double readDouble(double min, double max); 
    public double readDouble(String msg, double min, double max);
    
    public int readInt(int x, int y);

    public int readInt(String msg, int x, int y);

    public int readInt();

    public boolean readAnswer();

    public boolean readAnswer(String msg);
    
    public BigDecimal readBigDecimal(String msg);
    
}
