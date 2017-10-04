/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.objectiveobjects;

/**
 *
 * @author danimaetrix
 */
public class mainp {
    public static void main(String[] args) {
        
        guitar test = setupGuitar();
        
        
    }
    

    public static guitar setupGuitar(){
        
        guitar ibanezA = new guitar();
        
        ibanezA.setBrand("Ibanez");
        ibanezA.setColor("Seafoam");
        ibanezA.setStrings(6);  
        
        
        return ibanezA;
    }





}
