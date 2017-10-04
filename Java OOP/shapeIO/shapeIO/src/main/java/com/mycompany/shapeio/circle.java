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
public class circle extends shape {

    double R;

    circle(double R) {
        this.R = R;
     }

    protected double getArea() {
        return (Math.PI*R*R);
    }

    protected double getPerimeter() {
        return (2.0*Math.PI*R);
    }

}
