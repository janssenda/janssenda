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
    private String name;
    private int population;
    private int sqmi;
    
    capitol(String name, int population, int sqmi){
        this.name = name;
        this.population = population;
        this.sqmi = sqmi;
    }
    
    String getData(){
        
        return "This sis a test";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * @return the sqmi
     */
    public int getSqmi() {
        return sqmi;
    }

    /**
     * @param sqmi the sqmi to set
     */
    public void setSqmi(int sqmi) {
        this.sqmi = sqmi;
    }
    
    
}
