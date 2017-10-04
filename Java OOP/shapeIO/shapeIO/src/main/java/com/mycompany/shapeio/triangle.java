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
public class triangle extends shape {
    double B, H;

    triangle(double B, double H) {
        this.B = B;
        this.H = H;
    }

    protected double getArea() {
        return (0.5*B*H);
    }

    protected double getPerimeter() {
        return (B + 2*(Math.sqrt(B*B + H*H)));
    }

}
