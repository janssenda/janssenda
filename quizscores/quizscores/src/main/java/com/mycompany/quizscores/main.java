/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quizscores;

/**
 *
 * @author Danimaetrix
 */
public class main {
    public static void main(String[] args) {
        StudentGrades gradebook = new StudentGrades();
        
        gradebook.viewGrades();
        gradebook.viewGrades("Student# 1");
        
    }
   
}
