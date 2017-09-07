/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.ui;

import com.dm.dvdcollection.dto.Title;
import java.util.ArrayList;


// Only used to generate a fake library for testing purposes


public class GenerateFakeDB {

    ArrayList<Title> data = new ArrayList<>();

    public void generateFakeDB() {

    }

    public ArrayList<Title> buildDB(int size) {
        String titlename;

        for (int i = 0; i < size; i++) {
            titlename = "DVD # " + i;        
            Title title = new Title(titlename);
            
            title.setDirector("Director");
            title.setMpaaRating(Integer.toString(2*i));
            title.setReleaseDate("2005");
            title.setStudio("Studio");
            title.setUserRating(Integer.toString(i));
            title.setUserNotes("These are user notes...");
            title.setUserRating("5" + i);
            title.setDuration("2:45");
            title.setTitle(titlename);            
            
            data.add(title);          
            
        }
        
        return data;

    }

}
