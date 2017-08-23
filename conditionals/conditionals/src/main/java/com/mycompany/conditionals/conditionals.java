/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.conditionals;

import java.util.Scanner;
/**
 *
 * @author danimaetrix
 */
public class conditionals {
    public static void main(String[] args) {
        
        String lastname, team = "blank";
        String splitpoints = "BDHPV", splitpoints2 = "arooi";
        char firstletter, sletter, ctest;
        int flet, slet, clet,clet2;
        
        lastname = "XYZ";
        
        firstletter = lastname.charAt(0);
        sletter = lastname.charAt(1);
        flet = Character.getNumericValue(firstletter);
        slet = Character.getNumericValue(sletter);
        
 
      System.out.println("\n");

        
       
        
        for (int i = 0; i<=4; i++){
            
            ctest = splitpoints.charAt(i);
            clet = Character.getNumericValue(ctest);

            
            if (flet == clet){
                
                ctest = splitpoints2.charAt(i);
                clet2 = Character.getNumericValue(ctest);
                
                if (slet <= clet){                  

                   
                switch (i) {
                    case 0: {
                        team = "Red Dragons";  
                        i= 5;
                        break;
                    }
                    case 1: {
                        team = "Dark Wizards";  
                        i= 5;
                        break;
                    }                    
                    case 2: {
                        team = "Moving Castles";  
                        i= 5;
                        break;
                    }                    
                    case 3: {
                        team = "Golden Snitches";  
                        i= 5;
                        break;
                    }
                    case 4: {
                        team = "Night Guards";  
                        i= 5;
                        break;
                    }                    
                   
                }             
                }          
             
            }

            else if (flet < clet){
                
                switch (i) {
                    case 0: {
                        team = "Red Dragons";  
                        i= 5;
                        break;
                    }
                    case 1: {
                        team = "Dark Wizards";  
                        i= 5;
                        break;
                    }                    
                    case 2: {
                        team = "Moving Castles";  
                        i= 5;
                        break;
                    }                    
                    case 3: {
                        team = "Golden Snitches";  
                        i= 5;
                        break;
                    }
                    case 4: {
                        team = "Night Guards";  
                        i= 5;
                        break;
                    }                    
                   
                }             
            }
            

            else {
                team = "Black Holes";
            }   
            
        
   
     
    }
         System.out.println("Your team is: " + team);
 System.out.println("\n");    
    }
    
}