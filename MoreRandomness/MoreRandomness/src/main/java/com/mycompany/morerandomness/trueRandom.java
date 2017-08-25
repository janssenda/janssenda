/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.morerandomness;
import java.util.*;
import java.security.*;
import java.nio.*;
/**
 *
 * @author danimaetrix
 */
public class trueRandom {
    
        public static int randInt(int min, int max){
        int x, y;
        
        SecureRandom secrandomizer = new SecureRandom();  
        y = secrandomizer.nextInt(max-min + 1) + min;

        return y;
        }
        
     

        /*
        public static double randDoub(long seed, double min, double max){
        
        Random randomizer = new Random();
        double x;
        
        if (seed>0){
          randomizer.setSeed(seed); 
        }
        
        x = randomizer.nextDouble()*(max-min+1) + min;
        
        return x;
        }  
**/
        
}
