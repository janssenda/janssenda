/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.controller;

import com.dm.dvdcollection.dao.DvdCollectionDao;
import com.dm.dvdcollection.ui.DvdCollectionView;

/**
 *
 * @author danimaetrix
 */
public class DvdCollectionController {

    DvdCollectionView view;
    DvdCollectionDao dao;
    
    public DvdCollectionController(DvdCollectionDao dao, DvdCollectionView view){
     this.dao = dao;
     this.view = view;
    }

    public void run() {
        
        view.showMainMenuBanner();
        view.printMainGetChoice();

    }

}
