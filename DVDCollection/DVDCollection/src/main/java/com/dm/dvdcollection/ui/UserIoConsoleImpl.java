/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.ui;

import java.util.Scanner;
import java.io.Console;

/**
 *
 * @author danimaetrix
 */
public class UserIoConsoleImpl implements UserIo {

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readLine(String msg) {
        System.out.print(msg + "");
        String userinput;
        Scanner sc = new Scanner(System.in);
        userinput = sc.nextLine();
        return userinput;
   
    }
    

    @Override
    public String readPasswordLn(String msg) {        
        Scanner sc = new Scanner(System.in);
        Console console = System.console();
        System.out.print(msg + "");

        try {
            String userinput = new String(console.readPassword());
            return userinput;
            
        } catch (Exception e) {
            String userinput = sc.nextLine();
            return userinput;
        }        
        
    }

    @Override
    public String readLine() {
        String userinput;
        Scanner sc = new Scanner(System.in);
        userinput = sc.nextLine();
        return userinput;
    }

    @Override
    public double readDouble() {
        double max = Double.MAX_VALUE;
        double min = -max;
        return userDoubleRecieve(min, max);
    }

    public double readDouble(double min, double max) {
        return userDoubleRecieve(min, max);
    }

    @Override
    public int readInt() {
        int max = Integer.MAX_VALUE;
        int min = -max;
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(int min, int max) {
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(String msg, int min, int max) {
        System.out.print(msg + "");
        return userIntRecieve(min, max);
    }

    @Override
    public boolean readAnswer(String msg) {
        return readAnswerBody(msg);
    }

    @Override
    public boolean readAnswer() {
        return readAnswerBody("");
    }

    public boolean readAnswerBody(String msg) {
        Scanner sc = new Scanner(System.in);
        boolean valid = false, answer = true;
        String userinput;

        System.out.print(msg + "");
        while (valid == false) {
            try {

                userinput = sc.nextLine();

                if (userinput.toLowerCase().equals("y") || userinput.toLowerCase().equals("yes")) {
                    answer = true;
                    valid = true;

                } else if (userinput.toLowerCase().equals("n") || userinput.toLowerCase().equals("no")) {
                    answer = false;
                    valid = true;

                } else {
                    System.out.println("Invalid Input");
                    System.out.print("Try again: ");
                }

            } catch (Exception e) {
                System.out.println("Invalid Input");
                System.out.print("Try again: ");
                sc.next();

            }

        }
        return answer;
    }

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
                    System.out.print("Try again: ");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
                System.out.print("Try again: ");
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
                    System.out.print("Try again: ");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
                System.out.print("Try again: ");
                sc.next();

            }

        }
        return choice;
    }

}
