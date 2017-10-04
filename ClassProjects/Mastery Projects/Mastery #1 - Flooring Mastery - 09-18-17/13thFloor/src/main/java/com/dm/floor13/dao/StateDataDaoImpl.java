/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.dao;

import com.dm.floor13.exceptions.MissingDataException;
import com.dm.floor13.exceptions.FileSkipException;
import com.dm.floor13.exceptions.FileIOException;
import com.dm.floor13.model.State;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public class StateDataDaoImpl implements StateDataDao{

    private Map<String, State> stateMap;
    private String currentRoot;

    public StateDataDaoImpl() {
        this.currentRoot = "./";
    }

    public StateDataDaoImpl(String rootdir) {
        this.currentRoot = rootdir;
    }
//    @Override
//    public void readDataFromFile() throws FileSkipException {
//        readDataFromFile(currentRoot + "/Taxes.txt");
//    }

    @Override
    public void readDataFromFile(String filename) throws FileSkipException {
        FileHandler orderHandler = new LargeDataHandler();
        Map<String, State> stateMap;

        try {            
            stateMap = orderHandler.readStatesFromFile(filename);
        } catch (FileIOException e) {
            throw new FileSkipException("Tax file was not read - blank, in use, or corrupt...");
        }

        if (stateMap.isEmpty()) {
            throw new FileSkipException("Tax file was not read - blank, in use, or corrupt...");
        }        
        
        this.stateMap = stateMap;
    }

    @Override
    public boolean isState(String stateCode) {
        return stateMap.containsKey(stateCode);
    }

    @Override
    public State getState(String stateCode) throws MissingDataException {
        if (stateMap.containsKey(stateCode)) {
            return stateMap.get(stateCode);
        } else {
            throw new MissingDataException("That state was not recognized...");
        }
    }

    // Current working directory
    public String getcurrentDir() {
        return currentRoot;
    }

    public void setcurrentDir(String directory) {
        this.currentRoot = directory;
    }

    public Map<String, State> getStateMap() {
        return stateMap;
    }

    public int getStateMapSize() {
        return stateMap.size();
    }

}
