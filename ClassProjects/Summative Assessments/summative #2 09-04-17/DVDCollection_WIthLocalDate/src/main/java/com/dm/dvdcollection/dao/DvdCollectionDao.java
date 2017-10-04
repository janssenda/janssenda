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
 * @author danimaetrix
 */
public interface DvdCollectionDao {
    

Title addTitle(String titlename, Title title);

Title removeTitle(String titlename);

List<Title> getAllTitles();

void editTitle(Title title, String titlename);
        
Title searchTitle(String titlename);

// create and load library perform the explicit operation of writing the library into
// the hashmap.  
void createLibrary(int size);
void loadLibrary(List<Title> title);

// Perform the actual process of reading and writing to file.  Passes libraries on
// to loadlibrary, which places them in memory.
void writeEncLibToFile(String filename, String password) throws FileIOException;
List<Title> loadEncLibFromFile(String filename, String password) throws FileIOException;


}
