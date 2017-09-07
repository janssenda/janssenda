/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unit_testing_2;

/**
 *
 * @author Danimaetrix
 */
public class MakePi {

    // Return an int array length n containing the first n digits of pi.
    //
    // makePi(3) -> {3, 1, 4}
    public int[] makePi(int n) {
        int[] tpi = new int[n];
        Double realPi = 0.1 * Math.PI;
        String[] realPiStr = realPi.toString().split("\\.");
        char pichar[] = realPiStr[1].toCharArray();

        for (int i = 0; i < n; i++) {
            tpi[i] = Integer.parseInt(String.valueOf(pichar[i]));            
        }
        return tpi;
    }

}
