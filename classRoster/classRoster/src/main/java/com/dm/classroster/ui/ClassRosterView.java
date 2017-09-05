/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.classroster.ui;

import com.dm.classroster.dto.Student;
import java.util.List;

/**
 *
 * @author Danimaetrix
 */
public class ClassRosterView {

    private UserIO io;

    public ClassRosterView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {

        io.print("\nMain Menu\n");
        io.print("1. List Student IDs");
        io.print("2. Create New Student");
        io.print("3. View a Student");
        io.print("4. Remove a Student");
        io.print("5. Exit");

        return io.readInt("\nPlease select " + "from the above choices: ", 1, 5);

    }

    public Student getNewStudentInfo() {
        String studentId = io.readLine("Please enter Student ID: ");
        String firstName = io.readLine("Please enter First Name: ");
        String lastName = io.readLine("Please enter Last Name: ");
        String cohort = io.readLine("Please enter Cohort: ");

        Student currentStudent = new Student(studentId);

        currentStudent.setStudentId(studentId);
        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        currentStudent.setCohort(cohort);

        return currentStudent;
    }

    public void displayStudentList(List<Student> studentList) {

        //System.out.println("\n");
        for (Student currentStudent : studentList) {
            io.print("["+currentStudent.getStudentId() + ": "
                    + currentStudent.getFirstName() + " "
                    + currentStudent.getLastName() + " "
                    + currentStudent.getCohort()+"]");
        }
        io.readLine("\nPlease hit enter to continue...");
        System.out.println("\n");

    }

    public void displayStudent(Student student) {
        if (student != null) {
            io.print(student.getStudentId());
            io.print(student.getFirstName() + " " + student.getLastName());
            io.print(student.getCohort());
            io.print("");
        } else {
            io.print("No such student.");
        }
        io.readLine("\nPlease hit enter to continue.");
    }

    public String getStudentIdChoice() {
        return io.readLine("\nPlease enter the Student ID: ");

    }

    public void displayDisplayStudentBanner() {
        io.print("\n=== Display Student ===\n");
    }

    public void displayCreateStudentBanner() {
        io.print("\n=== Create Student ===\n");
    }

    public void displayDisplayAllBanner() {
        io.print("\n=== Display All Students ===\n");
    }

    public void displayRemoveStudentBanner() {
        io.print("\n=== Remove Students ===\n");
    }

    public void displayRemoveStudentSuccessBanner() {
        io.print("\nStudent was sucessfully removed.  Please hit enter to continue... ");
    }

    public void displayCreateSuccessBanner() {
        io.readLine(
                "\nStudent successfully created.  Please hit enter to continue...");
    }

    public void displayExitBanner() {
        io.print("\nGood Bye!!!\n");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
