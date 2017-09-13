/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.bowling;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author danimaetrix
 */
public class CalculatorTest {

    Calculator calc = new Calculator();

    @Test
    public void testCalculateScore() {


        List<Integer[]> frames = new ArrayList<>();
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 0});
        frames.add(new Integer[] {10, 10, 10});  //300


        
        
        System.out.println(calc.calculateScore(frames));

    }

}
