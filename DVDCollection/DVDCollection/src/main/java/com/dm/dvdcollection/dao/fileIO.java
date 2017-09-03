/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.dao;

import com.dm.dvdcollection.dto.Title;
import java.util.List;

/**
 *
 * @author Danimaetrix
 */
public interface fileIO {

    void writeToFile(String filename, List<Title> titleList) throws fileIOException;

    List<Title> readFromFile(String filename) throws fileIOException;

    void writeToFile(String filename, List<Title> titleList, String password) throws fileIOException;

    List<Title> readFromFile(String filename, String password) throws fileIOException, fileEncryptionException;

}
