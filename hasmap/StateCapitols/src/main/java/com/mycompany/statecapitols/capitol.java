/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.statecapitols;

/**
 *
 * @author danimaetrix
 */
public class capitol {
    String name;
    String population;
    int sqmi;
    
    capitol(String name, String population, int sqmi){
        this.name = name;
        this.population = population;
        this.sqmi = sqmi;
    }
    
    String getData(){
        
        return "This sis a test";
    }
    
    
}
