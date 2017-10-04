/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.userio;

import ioUtility.*;

/**
 *
 * @author danimaetrix
 */
public class main {

    public static void main(String[] args) {

        //handleUserIO myUI = new handleUserIO();
        //FileOutput writer = new FileOutput("test.txt");

        /*
        double x = 5;
        double y = 8;

        String testString = String.format("%5.2f %5.2f", x,y);

        for (int i = 0; i < 10; i++) {
            writer.writeToFile(testString + i + "\n");
        }
        
        writer.closeFile();
         */
        FileInput reader = new FileInput("test.txt");

        //while (reader.hasNext()) {
        //    System.out.println(reader.getLine());
        //}
        
        reader.readData();
    }
}
