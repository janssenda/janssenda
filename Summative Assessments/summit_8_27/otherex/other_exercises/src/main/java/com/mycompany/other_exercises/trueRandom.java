/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.other_exercises;
//import java.util.Random;
import java.security.*;




public class trueRandom {
    
        public static int randInt(int min, int max){
        int num;
        
            SecureRandom secrandomizer = new SecureRandom();            
            num = secrandomizer.nextInt(max-min + 1) + min;    

        return num;
        }        


        public static double randDouble(int min, int max){
        double num;
        
            SecureRandom secrandomizer = new SecureRandom(); 
            
            if (min >= 0){
                num = secrandomizer.nextDouble()*((max-min + 1) + min);    
            }
            
            else {
                num = secrandomizer.nextDouble()*(secrandomizer.nextBoolean()? 1:-1)*((max-min + 1) + min);    
        }
            
        return num;
        }
        
        public static boolean randBoolean(){
        boolean num;
        
            SecureRandom secrandomizer = new SecureRandom();            
            num = secrandomizer.nextBoolean();    

        return num;
        }       

        public static double randGauss(int min, int max){
        double num;
        
            SecureRandom secrandomizer = new SecureRandom();            
            num = secrandomizer.nextGaussian()*(max-min);    

        return num;
        }  
        

}
