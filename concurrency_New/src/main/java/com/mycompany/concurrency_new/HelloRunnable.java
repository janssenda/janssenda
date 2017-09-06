/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.concurrency_new;

/**
 *
 * @author Danimaetrix
 */
public class HelloRunnable implements Runnable {



    @Override
    public void run() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        System.out.println("Done");

        // long sum = 0;
        // for (long i = 1; i < countUntil; i++) {
        //     sum += i;
        // }
        //System.out.println(sum);
    }

}
