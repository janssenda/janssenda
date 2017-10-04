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
public class baseUserIO implements UserIO{

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

    public int readInt(int min, int max) {
        return userIntRecieve(min, max);
    }

    @Override
    public boolean readAnswer() {
        Scanner sc = new Scanner(System.in);
        boolean valid = false, answer = true;
        String userinput;

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
                }

            } catch (Exception e) {
                System.out.println("Invalid Input");
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
        choice = sc.nextInt();
        return choice;
    }

    public double userDoubleRecieve(double min, double max) {
        //Safely returns an Integer within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        double choice = 0;
        choice = sc.nextDouble();
        return choice;
    }

}
