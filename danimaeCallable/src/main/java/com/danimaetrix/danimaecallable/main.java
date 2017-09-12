package com.danimaetrix.danimaecallable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String args[]) throws InterruptedException {
        int threads = 20;
        int listsize = 100;
        int piece = listsize/threads;
        Integer[] data = new Integer[listsize];
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(threads);

        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        //create a list to hold the Future object associated with Callable
        List<Future<Integer[]>> list = new ArrayList<>();

        //Create MyCallable instance
        for (int i = 0; i < listsize; i++) {
            data[i] = i;
        }

        for (int i = 0; i < piece; i++) {
            Integer[] thispiece = new Integer[piece];
            
            for (int j = 0; j< piece; j++){
                thispiece[j] = data[i+j];
            }
            
            
            Callable<Integer[]> callable = new MyCallable(startSignal, doneSignal, thispiece);
            Future<Integer[]> future = executor.submit(callable);
//            list.add(future);
            //i = i + piece;
        }


        startSignal.countDown();
        long t0 = System.nanoTime();

        doneSignal.await();
        executor.shutdown();
        long dT = System.nanoTime() - t0;

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
        System.out.println("Computation time (ms): " + TimeUnit.NANOSECONDS.toMillis(dT));
        System.out.println("Finished all threads");

    }

}
