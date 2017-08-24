/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.factorizer;

/**
 *
 * @author danimaetrix
 */
public class factorizer {
    public static void main(String[] args) {
    int fsum = 0, counter = 0, factc, factp=0, delta=0, icount=0;    
        
    int num = 104729;    
        System.out.println("\n\n");
        System.out.println("Factors:");
        for (int i = num/2; i > 0; i--){

            if (num % i == 0){
               System.out.print("\n" + i);

               fsum = fsum+i;
               counter = counter+1; 

               if (i < num/2-1){
                   factc = i;
                   delta = (factp - factc)/60;
                   System.out.print("------" + delta);
   
               }
               
               factp = i; 
               i = i-delta;

            }
            
            icount = icount+1;
            

            
            
        }
        System.out.println("\n\n");    
        System.out.println("Iterations: "  + icount);     
        System.out.println("# of Factors: "  + counter); 
        System.out.println("Sum of Factors: "  + fsum);
                
        
        if (counter == 1){
            System.out.println(num + " is a prime number!!");
        }
        if (fsum == num){
            System.out.println(num + " is a perfect number!!");
        }        
        System.out.println("\n\n");
    
    }
    
    
    public static int getFactors(int num){
        
 
        return 0;
    } 
}
