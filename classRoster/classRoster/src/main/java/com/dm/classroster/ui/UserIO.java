/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.classroster.ui;

/**
 *
 * @author Danimaetrix
 */
public interface UserIO {

    void print(String msg);

    public String readLine();

    public double readDouble();

    public int readInt(int x, int y);
    public int readInt(String msg, int x, int y);
    public int readInt();

    public boolean readAnswer();

}
