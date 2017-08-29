/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplecalculator;

import java.util.Scanner;

/**
 *
 * @author danimaetrix
 */
public class UserInt {

    private int userchoice;
    double a, b;
    private Scanner sc = new Scanner(System.in);
    private UserInfoGetter getter = new UserInfoGetter();
    
  
    public void calcStream(){
        boolean run = true, answer;
         
        while (run){
            
            showMainMenu();
            System.out.println("The result is: " + fetchResult());
            System.out.print("Would you like to try again? ");
            answer = getter.getUserAnswer();
   
            if (answer == false){
                run = false;
            }
            
            
        }
        System.out.println("");
        
        
        
        
    }
    
    
    public double fetchResult(){
        double result = 0;
        SimpleCalculator myCalc = new SimpleCalculator();
        
        switch (userchoice){
            case 1:{
                result = myCalc.add(a,b);
                break;                
            }
                
            case 2:{
                result = myCalc.subtract(a,b);
                break;                
            }
            case 3:{
                result = myCalc.multiply(a,b);
                break;                
            }
            case 4:{
                result = myCalc.divide(a,b);
                break;                
            }            
        } 
        
        return result;
        
    }
    
    

    public void showMainMenu() {


        
        System.out.println("\n\n");
        System.out.println("What would you like to do?");
        System.out.println("1. Add");
        System.out.println("2. Subtract");
        System.out.println("3. Multiply");
        System.out.println("4. Divide");


        this.userchoice = getter.getUserInt(1,4);
        System.out.println("");
        getUserValues();
        System.out.println("");
        
    }
    
    public void getUserValues(){
        
        System.out.print("Enter value for a ");
        this.a = getter.getUserDouble();
    
        System.out.print("Enter value for b ");
        this.b = getter.getUserDouble(); 
      
}



}
