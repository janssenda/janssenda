/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methodrandom;

import java.util.*;

/**
 *
 * @author Danimaetrix
 */
public class methodrandom {

        public static float readValue(String prompt) {
            // declare and initialize a Scanner variable
            Scanner sc = new Scanner(System.in);
            // print prompt to console
            System.out.println(prompt);
            // read value into String data type
            String input = sc.nextLine();
            // convert the String to a float
            float floatVal = Float.parseFloat(input);
            // return the float value
            return floatVal;
        }    
    
        public static double testMethod(int x, int y){
            
            
            return x+y;
        }
        
        
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
         
        double x = testMethod(3,5);
            System.out.println(x);
        
        String xxx = "test";
        float floatVal = Float.parseFloat("152");
        System.out.println(floatVal);
        
    }   
    


            
            
}
