/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.danimaecallable;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Danimaetrix
 */
public class MyCallable implements Callable<Integer[]> {
    private Integer[] data;
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    
    MyCallable(CountDownLatch startSignal, CountDownLatch doneSignal, Integer[] data) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal; 
        this.data = data;
    }



    @Override
    public Integer[] call() throws Exception {
        startSignal.await();
        
        
        
        System.out.println(data.length + "-" + data[0]);

//        for (int i = 0; i < data.length; i++){
//            System.out.print(data[i] + "-");
//        }
       
//        System.out.println("");
        

        doneSignal.countDown();
        return data;
    }
}
