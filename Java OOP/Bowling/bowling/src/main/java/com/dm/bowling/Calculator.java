/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.bowling;

import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class Calculator {

    Calculator() {

    }

    public int calculateScore(List<Integer[]> scores) {
        int totalScore = 0;
        boolean prevloop = false;

        for (int i = 0; i < scores.size(); i++) {

            Integer[] frame = scores.get(i);

            int frameScore = frame[0] + frame[1];

            if (i < 9 && i < scores.size() - 1) {
                

                if (!prevloop){

                boolean cont = true;
                int j = i;
                while (cont && j < scores.size()-1) {
                    System.out.println(i + " " + j);
                    frame = scores.get(j);
                    Integer[] nextFrame = scores.get(j + 1);
                    
                    if (isStrike(frame)) {
                        
                        frameScore = frameScore + nextFrame[0] + nextFrame[1];
                        //i = i + 1;
                        prevloop = true;
                    }
                    else if (isSpare(frame)) {
                        frameScore = frameScore + nextFrame[0];
                        //i = i + 1;
                        prevloop = true;
                    } else{
                        cont = false;
                        prevloop = false;
                        
                    }
                    j = j + 1;

                }
                
                }
                
                //i = i + 1;
               

            } else {

                if (isStrike(frame) || isSpare(frame) && i == 9) {
                    frameScore = frame[0] + frame[1] + frame[2];
                }
            }

            totalScore = totalScore + frameScore;

        }

        return totalScore;

    }

    public boolean isSpare(Integer[] frame) {
        return (frame[0] != 10 && frame[0] + frame[1] == 10);
    }

    public boolean isStrike(Integer[] frame) {
        return (frame[0] == 10);
    }

}
