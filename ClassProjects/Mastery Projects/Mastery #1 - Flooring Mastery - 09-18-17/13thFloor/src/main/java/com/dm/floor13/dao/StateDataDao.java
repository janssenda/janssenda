/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.model.State;

/**
 *
 * @author danimaetrix
 */
public interface StateDataDao {

    public void readDataFromFile(String filename) throws FileSkipException;

    public boolean isState(String stateCode);

    public State getState(String stateCode) throws MissingDataException;


}
