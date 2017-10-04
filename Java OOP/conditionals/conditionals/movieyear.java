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
        
       int byear, age;
       String moviename="false";
       Scanner scantron = new Scanner(System.in);
       
       System.out.println("\n\n");
       System.out.print("What year were you born?? ");
       byear = scantron.nextInt();
       
       if (byear <= 1965){       
           moviename = "MASH";           
        }

       else if (byear <= 1975){       
           moviename = "Jurassic Park";           
        }       

       else if (byear <= 1985){       
           moviename = "Space Jam";           
        }    
       
       else if (byear <= 1995){       
           moviename = "Harry Potter";           
        }    

       else if (byear <= 2005){       
           moviename = "Up";           
        }       

 
       if (moviename == "false"){
           System.out.println("Wow, you are a baby!!!");
       }
       
       else {
           
        System.out.println("Did you know that " + moviename + " came out that year? Wow you are old!!");
        
       }
       System.out.println("\n\n");
              

     
    }
}
