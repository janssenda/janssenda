/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.classroster;

import com.dm.classroster.controller.ClassRosterController;
import com.dm.classroster.dao.ClassRosterDao;
import com.dm.classroster.dao.ClassRosterDaoFileImpl;
import com.dm.classroster.ui.ClassRosterView;
import com.dm.classroster.ui.UserIO;
import com.dm.classroster.ui.UserIOConsoleImpl;

/**
 *
 * @author Danimaetrix
 */
public class App {
    public static void main(String[] args) {
        
        UserIO myIo = new UserIOConsoleImpl();
        ClassRosterView myView = new ClassRosterView(myIo);
        ClassRosterDao myDao = new ClassRosterDaoFileImpl();           
        
        ClassRosterController controller = 
                new ClassRosterController(myDao, myView);
        
        controller.run();
        
        
    }
    
    
}
