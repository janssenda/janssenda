/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.concurrency_new;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Danimaetrix
 */
public class HelloRunnable implements Runnable {

    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    HelloRunnable(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        } // return;

    }

    void doWork() {
        
        for (int i = 0; i< 2; i++){
        try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.println("Done");
        }

    }

}
