/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interestcalculator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;





public class main {
    public static void main(String[] args) {
    String period;
        
    BigDecimal r = new BigDecimal("2.5");
    BigDecimal p0 = new BigDecimal("500");

    BigDecimal data[][];
    int years = 4;
    period = "yearly";
    
    
    data = calcInterest(r, p0, years, period);  
    printData(data);
    
    
    //writer(data);     

    }
    
    
    
    
    
    
    public static BigDecimal[][] calcInterest(BigDecimal r, BigDecimal p0, int years, String period){
    BigDecimal [][] data = new BigDecimal [years][4];   //year#, principal @ start, earned interest, principal @ end
    
    
    for (int i=0; i < years; i++){
        
        data[i][0] = BigDecimal.valueOf(i);
        data[i][1] = p0;
        data[i][2] = calcPrincipal(r, p0, period);
        data[i][3] = data[i][2].add(p0); 
        
        p0 = data[i][3];
        
    }
    
    return data;
    }

    
    
    
    public static BigDecimal calcPrincipal(BigDecimal r, BigDecimal principal, String period){
    BigDecimal p_earned = new BigDecimal("0");
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
            p_earned = BigDecimal.valueOf(1).add(
                    r.multiply(BigDecimal.valueOf(0.01)));
            p_earned = (p_earned.multiply(principal)).subtract(principal);
            
            principal = principal.add(p_earned);
            
            //p_earned = (principal*(1+0.01*r) - principal);            
            //principal = principal + p_earned;   
        }
    
    return p_earned;
    }


    public static void printData(BigDecimal[][] data){

    System.out.println("\n\nYear    P0 ($)        Pe ($)        Pf ($)");
    System.out.println("----------------------------------------");    
                    
    for (int i = 0; i < data.length; i++){
        for (int j = 0; j<4;j++){
            
            if(j == 0){
                System.out.printf("%2.0f", data[i][j]);
                System.out.print("      ");            
            }
            else{
                System.out.printf("%2.2f", data[i][j]);
                System.out.print("      "); 
            }
        }
        System.out.println("\n");      
    }
      
        System.out.println("\n\n");    

    
    return;
    }

    
    
    
    
    
        public static void writer(BigDecimal [][] data){
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