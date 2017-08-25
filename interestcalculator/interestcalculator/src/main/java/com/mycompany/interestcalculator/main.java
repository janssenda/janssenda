/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interestcalculator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;





public class main {
    public static void main(String[] args) {
    double r, p0;
    int years;
    String period;
        
    r = 2.5;
    p0 = 500;
    years = 10;
    period = "yearly";
    
    
    double[][] data = calcInterest(r, p0, years, period);  
    printData(data);
    writer(data);
    
 
        
    }
    
    
    
    
    
    
    public static double[][] calcInterest(double r, double p0, int years, String period){
    double [][] data = new double [years][4];   //year#, principal @ start, earned interest, principal @ end
    
    
    for (int i=0; i < years; i++){
        
        data[i][0] = i;
        data[i][1] = p0;
        data[i][2] = calcPrincipal(r, p0, period);
        data[i][3] = data[i][2]+p0; 
        
        p0 = data[i][3];
        
    }
    
    return data;
    }

    
    
    
    public static double calcPrincipal(double r, double principal, String period){
    double p_earned = 0;
    int intp = 4;
    
        switch (period.toLowerCase()) {
            case "quarterly": intp = 4;
                break;
            case "monthly": intp = 12;
                break;
            case "yearly": intp = 1;
                break;
            case "daily": intp = 365;
                break;                
        }
    
        for (int i =0; i<intp; i++){
            p_earned = (principal*(1+0.01*r) - principal);
            principal = principal + p_earned;   
        }
    
    return p_earned;
    }


    public static void printData(double[][] data){

    System.out.println("\n\nYear    P0 ($)        Pe ($)        Pf ($)");
    System.out.println("----------------------------------------");    
                    
    for (int i = 0; i < data.length; i++){
        for (int j = 0; j<4;j++){
            
            if(j == 0){
                System.out.printf("%2.0f", data[i][j]);
                System.out.print("      ");            
            }
            else{
                System.out.printf("%2.2e", data[i][j]);
                System.out.print("      "); 
            }
        }
        System.out.println("\n");      
    }
      
        System.out.println("\n\n");    

    
    return;
    }

    
    
    
    
    
        public static void writer(double [][] data){
        String fileName = "test.txt";
        String line;
        
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            
            line = String.format("%6d", 2);
            System.out.println(line);
            
            
            for (int i = 0; i < data.length; i++){
            for (int j = 0; j<4;j++){
            
            if(j == 0){
                
                bufferedWriter.write("0");
                bufferedWriter.write("      ");
                //System.out.printf("%2.0f", data[i][j]);
                //System.out.print("      ");            
            }
            else{
                bufferedWriter.write(String.valueOf(data[i][j]));
                bufferedWriter.write("      ");    
               // System.out.print("      "); 
                

            }

            }
            
            bufferedWriter.write("\n");
            }
            
          
            
            bufferedWriter.close();       
        }
    
        catch(IOException ex){
                }
        
        
                
    
            return;
        }
        
        

}