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
public class MyC2 implements Callable<String> {
    int myval = 0;
    
    public int getMyval() {
        return myval;
    }

    void setMyval(int myval) {
        this.myval = myval;
    }


    @Override
    public String call() throws Exception {

        return Thread.currentThread().getName();
    }
}
