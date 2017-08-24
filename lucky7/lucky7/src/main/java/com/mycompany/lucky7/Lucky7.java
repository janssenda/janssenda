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
        double initial_money, cmoney, i=0;
        int d1, d2;
        
        
        initial_money = 1000.00;
        cmoney = initial_money;
        
        System.out.println("\n\n");
        while (cmoney > 0){
            i=i+1;
            
            cmoney = rollDice(cmoney);       

            System.out.printf("Cash $%5.2f", cmoney);
            System.out.print("\n\n");
            System.out.println("Time: "+ System.nanoTime());
           
        }
        
        
        
        System.out.println("Total Rolls: "+ i);
        System.out.println("\n\n");
    }
    
    
    
    public static double rollDice(double cmoney){
        int d1, d2, sum;
        
        
            d1 = trueRandom.randInt(0,1,7);
            d2 = trueRandom.randInt(0,1,7);

            sum = (d1+d2);
            
            if (sum == 7){
                cmoney = cmoney + 4.00;
                
            }
            
            else {
                cmoney = cmoney - 1.00;
            }
        
        System.out.println("D1: " + d1 + "\nD2: " + d2);
        System.out.println("Sum:" + sum);       
        return cmoney;
    }
    
    
}
