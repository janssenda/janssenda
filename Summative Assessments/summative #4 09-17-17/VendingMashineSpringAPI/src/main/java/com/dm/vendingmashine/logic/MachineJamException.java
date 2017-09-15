/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.logic;

/**
 *
 * @author danimaetrix
 */
public class MachineJamException extends Exception {

    public MachineJamException(String message) {
        super(message);
    }

    public MachineJamException(String message, Throwable cause) {
        super(message, cause);
    }

}
