/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioUtility;

import java.io.FileWriter;
import java.io.PrintWriter;


public class FileOutput {//implements FileIO {
    PrintWriter out;

    public FileOutput() {
        try {
        PrintWriter out = new PrintWriter(new FileWriter("Default.txt"));
        this.out = out;        
        } catch (Exception e) {
            System.out.println("Error Loading File");
        }
    }
    
    public FileOutput(String filename) {
        try {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        this.out = out;        
        } catch (Exception e) {
            System.out.println("Error Loading File");
        }
    }
    
    
   
    public void writeToFile(String string) {
        out.print(string);
        out.flush();

    }
    
 
    public void closeFile(){
        out.flush();
        out.close();
    }

}
