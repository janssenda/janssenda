/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.randomdev;
import java.io.*;
import java.util.Formatter;
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
        double x = 10.09275;
        String testvar;
        /*
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            //bufferedWriter.write("Hello  World");
            
            testvar = "test" + String.valueOf(x);
            bufferedWriter.write(testvar);
            
            
            bufferedWriter.close();
            
            
        
        }   
    
        catch(IOException ex){
                }
        
        */
        try{       
        File file1 = new File("test.txt");
        Formatter fmt = new Formatter(file1);


        for (int i = 0; i < 5; i++) {
            //fmt.format("%5.2f", x);
            fmt.format("%5.2f   %5.0f" + "\n",x, x);
            fmt.flush();
            }
        }    
    
        catch(IOException ex){
        }
         
    
            return;
    }
    
}
