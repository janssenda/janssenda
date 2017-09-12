/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.morefunctionaltesting;

/**
 *
 * @author danimaetrix
 */
public class main {

    public static void main(String[] args) {

        
    }

    // Given a String, return a version without the first and 
    // last char, so "Hello" yields "ell". The String length will be at least 2. 
    //
    // trimOne("Hello") -> "ell"
    // trimOne("java") -> "av"
    // trimOne("coding") -> "odin"
    public static String trimOne(String str) {
        
        str = str.substring(1, str.length()-1);
        
        
        return str;
    }

}
