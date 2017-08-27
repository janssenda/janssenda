/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.other_exercises;
import java.util.Scanner;

/**
 *
 * @author Danimaetrix
 */
public class main {
    public static void main(String[] args) {
        //dogGenetics();
        //ArrayMath();
        //HealthyHearts();
    }
    
    
    
    public static void HealthyHearts(){
        int age;
        int [] target = new int [2];
        Scanner userInput = new Scanner(System.in);
        
        System.out.print("What is your age? ");        
        age = userInput.nextInt();
        
        
        
        target[0] = (int)Math.round((220-age)*0.5);
        target[1] = (int)Math.round((220-age)*0.85);
        
        System.out.println("Your max heart rate should be: " + (220 - age));
        System.out.println("Your target HR zone is "+target[0]+" - "+target[1] + " beats per minute");
        
        
        
    }
    
    
    public static void ArrayMath(){
        int[][] numstoadd = {{ 1, 90, -33, -55, 67, -16, 28, -55, 15 },
        { 999, -60, -77, 14, 160, 301 },
        { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 
        140, 150, 160, 170, 180, 190, 200, -99 }} ; 
        
        for (int i = 0; i < numstoadd.length; i++){
            System.out.println("#"+(i+1)+" Array Sum: " + addArray(numstoadd[i]));
        }
        
        
    }
    
    
    public static int addArray(int[] arr){
        int sum = 0;
        
        for (int i = 0; i < arr.length; i++){
            sum = sum + arr[i];
        }
        return sum;
    }
    
    public static void dogGenetics(){
        String dogname;
        String[] breedlist = {"St. Bernard", "Chihuahua", 
            "Dramatic RedNosed Asian Pug", "Common Cure", "King Doberman"};
        int [] results;   
        Scanner userInput = new Scanner(System.in);

        System.out.print("\n\nWhat is your dog's name? ");
        dogname = userInput.nextLine();
        System.out.println("Fantastic!  I have just recieved "
                + "\nthe results from our lab for " + dogname + ".  "
                + "\nI think you will be very happy!");
        System.out.println("Here you are: \n");
        
        // Get a set of 5 random ints summing to 100
        results = getRandRange(100,5);
        
        // Show results
        for (int i=0; i<5; i++){            
            System.out.println(results[i]+"% " + breedlist[i]);            
        }
        
        System.out.println("\nWow! What a wonderful little pupper!!");
        System.out.println("Have a nice day!!\n\n");
    }
    
    public static int[] getRandRange(int max, int size){
        /* Returns an integer array of length 'size' containing
        randomly calculated integers that sum to equal 'max'  */
        
        int [] range = new int[size];
        int randrange = max, randval = 0, psum = 0;
        
        for (int i = 0; i<size; i++){

            if (randrange <= 0){
                randval = 0;
            }
            else if (i == 4) {
                randval = randrange;
                
            }
            else {
                randval = trueRandom.randInt(1,randrange);
            }
     
            range[i] = randval;          
            
            randrange = randrange - randval;
            psum = psum + randval;

        } 
        
        return range;
    }


















}

