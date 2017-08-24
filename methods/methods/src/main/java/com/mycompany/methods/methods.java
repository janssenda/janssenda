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
 * 
 * 
 * 
 */
public class methods {
    
    public static void main(String[] args) {
        int seed = 0;
        
        String color = chooseRandColor(seed,1,5); // call color method here 
        String animal = chooseRandAnimal(seed,1,5); // call animal method again here 
        String colorAgain = chooseRandColor(seed,1,5); // call color method again here 
        int weight = trueRandom.randInt(seed,5,200); // call number method, 
            // with a range between 5 - 200 
        int distance = trueRandom.randInt(seed,10,20); // call number method, 
            // with a range between 10 - 20 
        int number = trueRandom.randInt(seed,10000,20000); // call number method, 
            // with a range between 10000 - 20000 
        int time = trueRandom.randInt(seed,2,6); // call number method, 
            // with a range between 2 - 6         
            
        System.out.println("Once, when I was very small...");

        System.out.println("I was chased by a " + color + ", "
            + weight + "lb " + " miniature " + animal 
            + " for over " + distance + " miles!!");

        System.out.println("I had to hide in a field of over " 
            + number + " " + colorAgain + " poppies for nearly " 
            + time + " hours until it left me alone!");

        System.out.println("\nIt was QUITE the experience, " 
            + "let me tell you!");

    } 
    
    public static String chooseRandColor(int seed, int min, int max){
        String color = "default";                  
        switch (trueRandom.randInt(seed,min,max)) {    
            case 1: color="black"; break;
            case 2: color="red"; break;
            case 3: color="pink"; break;
            case 4: color="green"; break;
            case 5: color="blue";  break;           
        }
        
        return color;
    }

    
    public static String chooseRandAnimal(int seed, int min, int max){
        String color = "default";                  
        switch (trueRandom.randInt(seed,min,max)) {    
            case 1: color="cat"; break;
            case 2: color="dog"; break;
            case 3: color="mouse"; break;
            case 4: color="bird"; break;
            case 5: color="elephant";  break;           
        }
        
        return color;
    }    
    
 /*      
    public static int trueRandom (int seed, int min, int max){
        
        Random randomizer = new Random();
        int x;
        
        if (seed>0){
          randomizer.setSeed(seed); 
        }
        
        x = randomizer.nextInt(max-min + 1) + min;
        
        return x;
        
    }
    
   **/
    
    }
    

    
    
    

