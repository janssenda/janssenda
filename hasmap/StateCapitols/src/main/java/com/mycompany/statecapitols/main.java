/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.statecapitols;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author danimaetrix
 */
public class main {

    public static void main(String[] args) {

        capitol[] Capitols = new capitol[50];
        
        Capitols[0] = new capitol("St. Paul", "xyx",4);
        
        
        
        HashMap<String, capitol> myData = new HashMap<>();

        myData.put("Minnesota", Capitols[0]);

        for (String key : myData.keySet()) {
            System.out.println(key);
            System.out.println(myData.get(key).getData());
        }

    }

}
