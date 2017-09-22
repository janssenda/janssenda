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
    private String currentDir, currentRoot;

    public StateDataDaoImpl() {
        this.currentDir = "./data/orders";
        this.currentRoot = "./data";
    }

    public StateDataDaoImpl(String rootdir, String workingdir) {
        this.currentDir = workingdir;
        this.currentRoot = rootdir;
    }

    public void readDataFromFile() throws FileSkipException {
        FileHandler orderHandler = new FileHandler(currentRoot);
        Map<String, State> stateMap = null;

        try {
            stateMap = orderHandler.readTaxesFromFile(currentRoot + "/Taxes.txt");
        } catch (FileIOException e) {
            throw new FileSkipException("Tax file was not read - blank, in use, or corrupt...");
        }

        if (stateMap.isEmpty()) {
            throw new FileSkipException("Tax file was not read - blank, in use, or corrupt...");
        }

        this.stateMap = stateMap;
    }

    public boolean isState(String stateCode) {
        return stateMap.containsKey(stateCode);
    }

    public State getState(String stateCode) throws MissingDataException {
        if (stateMap.containsKey(stateCode)) {
            return stateMap.get(stateCode);
        } else {
            throw new MissingDataException("That state was not recognized...");
        }
    }

    // Current working directory
    public String getcurrentDir() {
        return currentDir;
    }

    public void setcurrentDir(String directory) {
        this.currentDir = directory;
    }

    public Map<String, State> getStateMap() {
        return stateMap;
    }

    public int getStateMapSize() {
        return stateMap.size();
    }

}
