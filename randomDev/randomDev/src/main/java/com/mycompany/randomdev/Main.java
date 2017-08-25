/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.randomdev;
import java.io.*;
/**
 *
 * @author danimaetrix
 */
public class Main {
    public static void main(String[] args) {
        int min, max, iter;
        double avg=0, num; 
        
        min = 0;
        max = 50;
        iter = 1000;
        
        
        for (int i=0; i<iter; i++){
            
            num = trueRandom.randGauss(min,max);
            //System.out.println(num);            
            avg = avg + num;
            
                    
        }
        
        System.out.println("\n\nAverage:   " + avg/iter + "\n\n");
        writer();

    }
    
    
    public static void writer(){
        String fileName = "test.txt";
        
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write("Hello  World");
            
            
            bufferedWriter.close();
            
            
        
        }
    
        catch(IOException ex){
                }
        
        
                
    
            return;
    }
    
}
