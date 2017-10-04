/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.danimaecallable;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Danimaetrix
 */
public class MyCallable implements Callable<String> {

    private int[] line;
    int ln;
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    MyCallable(CountDownLatch startSignal, CountDownLatch doneSignal, int[] line, int ln) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
        this.line = line;
        this.ln = ln;

    }

    @Override
    public String call() throws Exception {
        startSignal.await();

        for (int i = 0; i < line.length; i++) {
            line[i] = 13 * line[i];
        }
        Thread.sleep(100);
        

        doneSignal.countDown();

        return "xyz";

    }
}
