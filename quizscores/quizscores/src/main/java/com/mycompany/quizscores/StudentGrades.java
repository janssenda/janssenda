/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.quizscores;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author Danimaetrix
 */
public class StudentGrades {

    HashMap<String, ArrayList<Double>> grades;

    StudentGrades() {
        HashMap<String, ArrayList<Double>> grades = new HashMap<>();

        // Add some students
        for (int i = 0; i < 10; i++) {
            ArrayList<Double> currGrades = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                currGrades.add(trueRandom.randDouble(0, 100));
            }
            grades.put("Student# " + i, currGrades);
        }

        this.grades = grades;
    }

    public void addStudent(String name) {
        //
        grades.put(name, null);
    }

    public void removeStudent(String name) {
        grades.remove(name);
    }

    public void viewGrades(String name) {
        System.out.println("");
        System.out.print(name + ":\t");

        for (int j = 0; j < grades.get(name).size(); j++) {
            System.out.printf("%6.2f\t", grades.get(name).get(j));

        }
    }

    public void viewGrades() {

        for (String key : grades.keySet()) {
            //System.out.printf(key + " " + grades.get(key));
            System.out.println("");

            System.out.print(key + ":\t");

            for (int j = 0; j < grades.get(key).size(); j++) {
                System.out.printf("%6.2f\t", grades.get(key).get(j));

            }

        }

    }

}
