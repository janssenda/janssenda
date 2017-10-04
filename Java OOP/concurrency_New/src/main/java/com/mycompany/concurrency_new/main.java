/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.concurrency_new;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author Danimaetrix
 */
public class main {

    public static void main(String[] args) throws InterruptedException {
        int N = 35;
        ExecutorService executor = Executors.newFixedThreadPool(N);
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; i++) {            
            Runnable worker = new HelloRunnable(startSignal,doneSignal);
            executor.execute(worker);
        }

        startSignal.countDown();
        long t0 = System.nanoTime();
        
        doneSignal.await();
        long dT = System.nanoTime() - t0;

        System.out.println("Computation time (ms): " + TimeUnit.NANOSECONDS.toMillis(dT));
        System.out.println("Finished all threads");

        executor.shutdown();
    }


}
