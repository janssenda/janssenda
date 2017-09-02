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

void createDB(int size);

void exitMessage();
    
}
