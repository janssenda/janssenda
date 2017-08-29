/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iotesting;

/**
 *
 * @author danimaetrix
 */
public class iotest {
    public static void main(String[] args) {
        String teststring = "5*1+(15*8)*6";
        String delims = "[+*()]+";
        String ops = "[0123456789]+";
        
        String[] tokens = teststring.split(delims);
        String[] operators = teststring.split(ops);
        
        for (int i = 0; i<tokens.length; i++){
            System.out.println(tokens[i]);        
        }
        
        for (int i = 0; i<operators.length; i++){
            System.out.println(operators[i]);        
        }        
        
        
        
    }   
    
}
