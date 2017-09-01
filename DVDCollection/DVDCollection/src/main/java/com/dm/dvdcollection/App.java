/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection;

import com.dm.dvdcollection.controller.DvdCollectionController;
import com.dm.dvdcollection.dao.DvdCollectionDao;
import com.dm.dvdcollection.dao.DvdCollectionDaoImpl;
import com.dm.dvdcollection.ui.DvdCollectionView;
import com.dm.dvdcollection.ui.UserIo;
import com.dm.dvdcollection.ui.UserIoConsoleImpl;

/**
 *
 * @author danimaetrix
 */
public class App {
    public static void main(String[] args) {
        
        UserIo myIo = new UserIoConsoleImpl();
        DvdCollectionView myView = new DvdCollectionView(myIo);
        DvdCollectionDao myDao = new DvdCollectionDaoImpl();
        
        DvdCollectionController controller = new DvdCollectionController(myDao, myView);
        
        controller.run();
        
        
    }
    
}
