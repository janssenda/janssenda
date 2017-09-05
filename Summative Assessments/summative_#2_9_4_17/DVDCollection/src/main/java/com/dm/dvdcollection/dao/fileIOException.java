/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.dao;

/**
 *
 * @author Danimaetrix
 */
public class fileIOException extends Exception {

    public fileIOException(String message) {
        super(message);
    }

    public fileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
