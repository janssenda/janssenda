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
     String noun1, adjective2, noun3, number4, adjective5,pln6,pln7, pln8, verb9, pverb10;
     String passage;
        
     Scanner scantron = new Scanner(System.in);
    
     System.out.println("Please enter a noun:");
     noun1 = scantron.nextLine(); 
     System.out.println("Please enter an adjective:");
     adjective2 = scantron.next();
     System.out.println("Please enter a noun:");     
     noun3 = scantron.next();
     System.out.println("Please enter a number:");     
     number4 = scantron.next();  
     System.out.println("Please enter an adjective:");     
     adjective5 = scantron.next();      
     System.out.println("Please enter a plural noun:");     
     pln6 = scantron.next();      
     System.out.println("Please enter a plural noun:");     
     pln7 = scantron.next();
     System.out.println("Please enter a plural noun:");     
     pln8 = scantron.next();     
     System.out.println("Please enter a verb:");     
     verb9 = scantron.next();
     System.out.println("Please enter the past form:");     
     pverb10 = scantron.next();

     passage = noun1 + ": the " + adjective2 + " frontier.\nThese are "
             + "the voyages of the starship " + noun3 +".\nIts " + number4
             + "-year mission: to explore strange\n" + adjective5 + " " + pln6 + ","
             + " to seek out " + adjective5 + " " + pln7 + " and \n" +
             adjective5 + " " + pln8 + ", to boldly " + verb9 + " where "
                     + "no one has " + pverb10 + " before.";
     
        System.out.println(passage);


        
        
        
        
        
        
        
    }
    
}
