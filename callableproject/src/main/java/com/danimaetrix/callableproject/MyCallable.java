/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.callableproject;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
    
    public static void main(String args[]){
        
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(30);
        
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<>();
        
        //Create MyCallable instance
        Callable<String> callable = new MyCallable();
        long t0 = System.nanoTime();        
        for(int i=0; i< 50; i++){
            
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<String> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+fut.get());
                
            } catch (InterruptedException | ExecutionException e) {
                //e.printStackTrace();
            }
        }
        


        

        long dT = System.nanoTime() - t0;

        System.out.println("Computation time (ms): " + TimeUnit.NANOSECONDS.toMillis(dT));
        System.out.println("Finished all threads");
        //shut down the executor service now
        executor.shutdown();
    }

}