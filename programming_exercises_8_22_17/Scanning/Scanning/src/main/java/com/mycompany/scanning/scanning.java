/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scanning;


import java.util.Scanner;



public class scanning {
    public static void main(String[] args) {
      
 
     double number;
     String noun1, adjective1, noun2, adjective2, pln1,pln2,pln3,verb,pverb;
     String passage, testvar3333, testvar555;
        
     Scanner scantron = new Scanner(System.in);
    
     System.out.println("Please enter a noun:");
     noun1 = scantron.next(); 
     System.out.println("Please enter an adjective:");
     adjective1 = scantron.next();
     System.out.println("Please enter another noun:");     
     noun2 = scantron.next();
     System.out.println("Please enter another adjective:");     
     adjective2 = scantron.next();  
     System.out.println("Please enter a plural noun:");     
     pln1 = scantron.next();      
     System.out.println("Please enter a plural noun:");     
     pln2 = scantron.next();      
     System.out.println("Please enter a plural noun:");     
     pln3 = scantron.next();      
     System.out.println("Please enter a verb:");     
     verb = scantron.next();
     System.out.println("Please enter the past form:");     
     pverb = scantron.next();

     passage = noun1 + ": the " + noun2 + " frontier.\n These are "
             + "the voyages of the starship" + noun2 +". Its " + adjective2
             + " year mission: to explore stra nge" + pln1 + pln2;
     
        System.out.println(passage);


        
        
        
        
        
        
        
    }
    
}
