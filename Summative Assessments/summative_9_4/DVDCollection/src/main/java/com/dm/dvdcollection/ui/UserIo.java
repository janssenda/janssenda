/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.ui;

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

    public int readInt(int x, int y);

    public int readInt(String msg, int x, int y);

    public int readInt();

    public boolean readAnswer();

    public boolean readAnswer(String msg);
}
