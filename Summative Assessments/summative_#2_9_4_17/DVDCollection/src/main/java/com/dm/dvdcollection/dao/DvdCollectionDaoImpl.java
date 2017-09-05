/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.dao;

import com.dm.dvdcollection.dto.Title;
import com.dm.dvdcollection.ui.generateFakeDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public class DvdCollectionDaoImpl implements DvdCollectionDao {

    // Hashmap for storing the library in memory
    private Map<String, Title> library = new HashMap<>();

    @Override
    public Title addTitle(String titlename, Title title) {
        Title newTitle = library.put(titlename, title);
        return newTitle;
    }

    @Override
    public Title removeTitle(String titlename) {
        Title removedTitle = library.remove(titlename);
        return removedTitle;
    }

    @Override
    public List<Title> getAllTitles() {
        return new ArrayList<>(library.values());
    }

    @Override
    public void editTitle(Title title, String titlename) {
        library.remove(titlename);
        library.put(title.getTitle(), title);
    }

    @Override
    public Title searchTitle(String titlename) {
        return library.get(titlename);
    }

    // ONLY called to generate fake library data for easy testing.  Not used at all in final project version.
    @Override
    public void createLibrary(int size) {
        generateFakeDB fake = new generateFakeDB();
        ArrayList<Title> a = fake.buildDB(size);
        for (int i = 0; i < size; i++) {
            library.put(a.get(i).getTitle(), a.get(i));
        }

    }

    // Places the title list retrieved by loadEncLibFromFile into the memory map, one title at a time
    @Override
    public void loadLibrary(List<Title> title) {
        for (Title currentTitle : title) {
            library.put(currentTitle.getTitle(), currentTitle);
        }
    }

    // Implements the file handling functionalit.  CppFileHandler controls physical process based on
    // input from user(filename, password).  Returns an ArrayList of titles to loadLibrary when user
    // loads a new library.
    @Override
    public void writeEncLibToFile(String filename, String password) throws fileIOException {
        CppFileHandler fileHandler = new CppFileHandler(filename, password);
        fileHandler.writeToFile(getAllTitles());
    }

    @Override
    public List<Title> loadEncLibFromFile(String filename, String password) throws fileIOException {
        CppFileHandler fileHandler = new CppFileHandler(filename, password);
        return fileHandler.readFromFile();
    }

}
