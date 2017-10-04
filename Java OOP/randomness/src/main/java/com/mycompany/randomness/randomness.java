/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.randomness;


import java.util.*;


/**
 *
 * @author danimaetrix
 */
public class randomness {    
    public static void main(String[] args) {
        System.out.println("\n\n");
         
        Random randomizer = new Random();

        randomizer.setSeed(6782);
         
        
        for (int j = 0; j < 1; j++){
        for (int i = 0; i < 10; i++){
            System.out.print(Math.PI*randomizer.nextInt(10000)*((randomizer.nextBoolean()) ? 1 :-1) + "\n");
        }
        }
        
        
        
         System.out.println("\n\n");
         
        
        
    }   
}
