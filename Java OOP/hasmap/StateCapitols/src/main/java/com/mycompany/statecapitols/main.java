/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.statecapitols;

import java.util.HashMap;
import java.util.Set;

public class main {

    public static void main(String[] args) {

        HashMap<String, capitol> myData = new HashMap<>();

        // Add raw data to capitols
        capitol[] Capitols = new capitol[50];
        Capitols[0] = new capitol("St. Paul", 50000, 400);

        myData.put("Minnesota", Capitols[0]);


        for (String key : myData.keySet()) {
            capitol c = myData.get(key);
            System.out.printf("%s - %s | Pop: %d | Area: %d sq mi", key, c.getName(), c.getPopulation(), c.getSqmi());
        }

    }

}
