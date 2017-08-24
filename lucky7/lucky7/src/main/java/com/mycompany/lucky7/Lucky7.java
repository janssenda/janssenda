/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lucky7;

/**
 *
 * @author danimaetrix
 */
public class Lucky7 {
    public static void main(String[] args) {
        double initial_money, cmoney, x;
        
        
        initial_money = 150.00;
        cmoney = initial_money;
        
        
        while (cmoney > 0){
            
            
            
        x = trueRandom.randDoub(0,1,50);
            
            System.out.printf("%5.2f", x);
            System.out.print("\n");
            
            cmoney=cmoney-10;
            
        }
        
        
        
        
        
    }
    
}
