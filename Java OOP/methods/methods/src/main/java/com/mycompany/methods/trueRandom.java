/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methods;
import java.util.*;
/**
 *
 * @author danimaetrix
 */
public class trueRandom {
    
        public static int randInt(int seed, int min, int max){
        
        Random randomizer = new Random();
        int x;
        
        if (seed>0){
          randomizer.setSeed(seed); 
        }
        
        x = randomizer.nextInt(max-min + 1) + min;
        
        return x;
        }

        
        public static double randDoub(int seed, double min, double max){
        
        Random randomizer = new Random();
        double x;
        
        if (seed>0){
          randomizer.setSeed(seed); 
        }
        
        x = randomizer.nextDouble()*(max-min+1) + min;
        
        return x;
        }        
        
}
