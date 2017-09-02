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
        //library.remove(titleOld.getTitle());
        library.remove(titlename);
        library.put(title.getTitle(), title);     
    }

    @Override
    public Title searchTitle(String titlename) {
        return library.get(titlename);
    }

    @Override
    public void exitMessage() {
        //TO BE ADDED
    }

    public void saveLibrary(String filename) {
        //TO BE ADDED

    }

    public void loadLibrary(String filename) {
        //TO BE ADDED

    }

    public void unknownCommand() {

    }
    
    @Override
    public void createDB(int size){
    
        generateFakeDB fake = new generateFakeDB();
        ArrayList<Title> a = fake.buildDB(size);               
        
        for (int i = 0; i < size; i ++){            
            library.put(a.get(i).getTitle(), a.get(i));            
        }
    
    }

}
