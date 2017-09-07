/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unit_testing_2;

import static java.lang.Math.abs;
/**
 *
 * @author Danimaetrix
 */
public class Diff21 {

    // Given an int n, return the absolute value of the difference 
    // between n and 21, except return double the absolute value 
    // of the difference if n is over 21. 
    //
    // diff21(23) -> 4
    // diff21(10) -> 11
    // diff21(21) -> 0
    public int diff21(int n) {
        int val = abs(n-21);
        
        if (val > 21){
            return 2*val;
        }else{
            return val;
        }    
    
    }

}
