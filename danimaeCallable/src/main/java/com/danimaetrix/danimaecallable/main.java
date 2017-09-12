package com.danimaetrix.danimaecallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
public class main {

    public static void main(String args[]) throws InterruptedException {
        int threads = 10;
        int listsize = 200;
        int piece = listsize / threads;
        int[][] data = new int[listsize][listsize];
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(threads);

        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        //create a list to hold the Future object associated with Callable
        List<Future<Integer[]>> list = new ArrayList<>();

        //Create MyCallable instance
        for (int i = 0; i < listsize; i++) {
            for (int j = 0; j < listsize; j++) {
                data[i][j] = j;
            }
        }

        for (int i = 0; i < listsize; i++) {
            executor.submit(new MyCallable(startSignal, doneSignal, data[i], i));
        }

//            list.add(future);
        //i = i + piece;    
        long t0 = System.nanoTime();
        startSignal.countDown();       
        executor.shutdown();
        //doneSignal.countDown();
        executor.awaitTermination(1, TimeUnit.DAYS);
        long dT = System.nanoTime() - t0;

        int sum = 0;
        for (int i = 0; i < listsize; i++) {
            for (int j = 0; j < listsize; j++) {
                sum = sum + data[i][j];
            }

        }
        //shut down the executor service now
//        for (Future<String> fut : list) {
//            try {
//                //print the return value of Future, notice the output delay in console
//                // because Future.get() waits for task to get completed
//                System.out.println(new Date() + "::" + fut.get());
//
//            } catch (InterruptedException | ExecutionException e) {
//                //e.printStackTrace();
//            }
//        }
        System.out.println("Sum: " + sum);
        System.out.println("Computation time (ms): " + TimeUnit.NANOSECONDS.toMillis(dT));
        System.out.println("Finished all threads");

    }

}
