/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.concurrency_new;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Danimaetrix
 */
public class main {

    public static void main(String[] args) {
        boolean ex = true;
        ExecutorService executor = Executors.newFixedThreadPool(1000);

        
        long t0 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Runnable worker = new HelloRunnable();
            executor.execute(worker);
        }

        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();

        try {
            ex = executor.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {

        }

        long dT = System.nanoTime() - t0;
        System.out.println("Computation time (us): " + TimeUnit.NANOSECONDS.toMicros(dT));
        //System.out.println("Termination on time: " + ex);
        System.out.println("Finished all threads");
    }

}
