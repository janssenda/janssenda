/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioUtility;

import java.util.Scanner;

/**
 *
 * @author danimaetrix
 */
public class handleUserIO extends baseUserIO {

    public int userIntRecieve(int min, int max) {
        //Safely returns an Integer within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        int choice = 0;
        while (valid == false) {
            try {
                choice = sc.nextInt();

                if (choice < min || choice > max) {
                    System.out.println("Invalid Input");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
                sc.next();

            }

        }
        return choice;
    }

    public double userDoubleRecieve(double min, double max) {
        //Safely returns a double within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        double choice = 0;
        while (valid == false) {
            try {
                choice = sc.nextDouble();

                if (choice < min || choice > max) {
                    System.out.println("Invalid Input");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
                sc.next();

            }

        }
        return choice;
    }

    
}
