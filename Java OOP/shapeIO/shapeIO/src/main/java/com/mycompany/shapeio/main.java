/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shapeio;

/**
 *
 * @author danimaetrix
 */
public class main {

    public static void main(String[] args) {
        System.out.println("\n\n");

        shape myRect = new rectangle(2.0, 3.7);
        shape myCircle = new circle(0.5);
        shape myTriangle = new triangle(0.3, 1);
        shape mySquare = new square(3);

        System.out.print("Rectangle Area: ");
        System.out.println(myRect.getArea());

        System.out.print("Circle Area: ");
        System.out.println(myCircle.getArea());

        System.out.print("Triangle Area: ");
        System.out.println(myTriangle.getArea());
        
        System.out.print("Square Area: ");
        System.out.println(mySquare.getArea());
        
        System.out.println("\n\n");
    }
}
