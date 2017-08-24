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
    
        public static int randInt(long seed, int min, int max){
        
        SecureRandom secrandomizer = new SecureRandom();
        Random randomizer = new Random();
        int x, y;
        long seedS;
        
        if (seed>0){
          randomizer.setSeed(seed); 
        }
        
        else{
            byte[] b  = secrandomizer.generateSeed(8);
            ByteBuffer bb = ByteBuffer.wrap(b);    
            seedS = bb.getLong();
            randomizer.setSeed(seed); 
        }
        
        
        x = randomizer.nextInt(max-min + 1) + min;
        y = secrandomizer.nextInt(max-min + 1) + min;
        
        return y;
        }

        
        public static double randDoub(long seed, double min, double max){
        
        Random randomizer = new Random();
        double x;
        
        if (seed>0){
          randomizer.setSeed(seed); 
        }
        
        x = randomizer.nextDouble()*(max-min+1) + min;
        
        return x;
        }        
        
}
