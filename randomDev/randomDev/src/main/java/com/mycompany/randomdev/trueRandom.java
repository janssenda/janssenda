/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.randomdev;
//import java.util.Random;
import java.security.*;




public class trueRandom {
    
        public static int randInt(int min, int max){
        int x, y;
        


        return y;
        }
        


        public static int fetchRandom(int min, int max, String Type){
            int num;
            SecureRandom secrandomizer = new SecureRandom();
            
            
            
            num = secrandomizer.nextInt(max-min + 1) + min;        
        return num;
        }


}
