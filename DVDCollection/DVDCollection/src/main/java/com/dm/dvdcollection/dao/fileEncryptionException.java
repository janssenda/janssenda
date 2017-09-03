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
public class fileEncryptionException extends Exception {
        public fileEncryptionException(String message) {
        super(message);
    }

    public fileEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
