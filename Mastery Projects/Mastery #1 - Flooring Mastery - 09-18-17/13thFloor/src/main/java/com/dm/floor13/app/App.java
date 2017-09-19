/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.app;

import com.dm.floor13.dao.FileHandler;
import com.dm.floor13.dao.FileIOException;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args) {

        FileHandler files = new FileHandler();
        try {
            files.readAllOrdersFromFile();
        } catch (FileIOException e) {

        }
    }

}
