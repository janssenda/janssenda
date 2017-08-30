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
public class rectangle extends shape {
    double W,L; 

    
    rectangle(double L, double W){
        this.W = W;
        this.L = L;
    }
   
    
    protected double getArea(){
            return (L*W);        
    }
    
    protected double getPerimeter(){        
            return (2.0*L + 2.0*W);
    }
    
}
