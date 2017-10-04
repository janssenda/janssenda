/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplecalculator;

import java.util.Scanner;

public class UserInfoGetter {


    public int getUserInt() {
        int max = Integer.MAX_VALUE;
        int min = -max;
        return userIntRecieve(min, max);        
    }

    public int getUserInt(int min, int max) {
        return userIntRecieve(min, max);        
    }    

    public int userIntRecieve(int min, int max) {
        //Safely returns an Integer within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        int choice = 0;
        while (valid == false) {
            System.out.print(">> ");
            try {
                choice = sc.nextInt();

                if (choice < min || choice > max) {
                    System.out.println("Invalid Selection");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Selection");
                sc.next();

            }

        }
        return choice;
    }    
    
    
    
    
    
    
    public double getUserDouble() {
        double max = Double.MAX_VALUE;
        double min = -max;
        return userDoubleRecieve(min, max);        
    }

    public double getUserDouble(int min, int max) {
        return userDoubleRecieve(min, max);        
    }    

    public double userDoubleRecieve(double min, double max) {
        //Safely returns a double within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        double choice = 0;
        while (valid == false) {
            System.out.print(">> ");
            try {
                choice = sc.nextDouble();

                if (choice < min || choice > max) {
                    System.out.println("Invalid Selection");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Selection");
                sc.next();

            }

        }
        return choice;
    }

    public boolean getUserAnswer() {
        //Safely returns a double within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false, answer = true;
        String userinput;

        
        while (valid == false) {
            //System.out.print(">> ");
            try {

                userinput = sc.nextLine();
                
                if (userinput.toLowerCase().equals("y") || userinput.toLowerCase().equals("yes")){
                    answer = true;
                    valid = true;       

                }
                else if (userinput.toLowerCase().equals("n") || userinput.toLowerCase().equals("no")){
                    answer = false;
                    valid = true;           

                }
                else{                    
                    System.out.println("Invalid Input");
                }

 
            } catch (Exception e) {
                System.out.println("Invalid Input");
                sc.next();

            }

        }
        return answer;
    }
        
    

}
