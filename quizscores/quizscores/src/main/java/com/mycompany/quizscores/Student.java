/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quizscores;
import java.util.ArrayList;
/**
 *
 * @author Danimaetrix
 */
public class Student {
 
    ArrayList<Double> quizGrades = new ArrayList<>();
    
    public void addGrades(double grade){
        quizGrades.add(grade);        
    }
    
    public ArrayList<Double> getGrades(){        
        return quizGrades;        
    }
    
}
