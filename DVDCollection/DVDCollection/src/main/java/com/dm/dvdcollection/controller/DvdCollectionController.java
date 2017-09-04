/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.controller;

import com.dm.dvdcollection.dao.DvdCollectionDao;
import com.dm.dvdcollection.dao.fileIOException;
import com.dm.dvdcollection.dto.Title;
import com.dm.dvdcollection.ui.DvdCollectionView;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class DvdCollectionController {

    // Dependency Injection
    DvdCollectionView view;
    DvdCollectionDao dao;

    public DvdCollectionController(DvdCollectionDao dao, DvdCollectionView view) throws fileIOException {
        this.dao = dao;
        this.view = view;
    }

    /*
    // Main program loop
     */
    public void run() throws fileIOException {
        dao.loadLibrary(dao.loadEncLibFromFile("mytestlib.cplib", null));    // Loads a real data test lib at runtime -- saves test time, not needed.
        //dao.createLibrary(15);                                            // Used to add a fake library for testing.  Not needed.
        boolean repeat = true;

        while (repeat) {
            repeat = evaluateSelection(getSelection());
        }
        exitProgram();
    }

    /*
    // End main program loop
     */
    // Main menu screen
    private boolean evaluateSelection(int choice) throws fileIOException {
        boolean repeat = true;
        switch (choice) {
            case 1:
                addTitle();
                break;
            case 2:
                removeTitle();
                break;
            case 3:
                listTitles();
                view.waitOnUser();
                break;
            case 4:
                editTitle();
                break;
            case 5:
                searchTitles();
                break;
            case 6:
                saveLibrary();
                break;
            case 7:
                loadLibrary();
                break;
            case 8:
                //EXIT
                repeat = false;
                break;
        }
        return repeat;
    }

    private int getSelection() {
        view.showMainMenuBanner();
        return view.printMainGetChoice();
    }

    // Lets user find a title to remove
    private void removeTitle() {
        removeTitleByEntry();
    }

    // Removes a title with no user input (for edit menu)
    private void removeTitle(String key) {
        removeTitleByKey(key);
    }

    // User selects a title to remove
    private void removeTitleByEntry() {
        boolean confirm;
        listTitles();
        do {
            view.showRemoveTitleBanner();
            String titlename = view.getDvdTitle();

            try {
                Title title = dao.searchTitle(titlename);
                view.showTitleInfo(title);
                confirm = view.confirmRemoveTitle();
                if (confirm) {
                    dao.removeTitle(titlename);
                    view.removalSuccess();
                }
            } catch (Exception e) {
                view.noEntryException();
            }
        } while (view.askRemove());
    }

    // Title removed automatically from input menu
    private void removeTitleByKey(String key) {
        boolean confirm;
        confirm = view.confirmRemoveTitle();
        if (confirm) {
            dao.removeTitle(key);
            view.removalSuccess();
        }
    }

    /* The Title object is passed by reference... so when
    we modify it in the editor, we must be careful to ensure
    that the proper information is sent to DAO.  We send it the 
    old key so the DAO knows what to remove, and we give it the 
    updated version of the object to put into the map as a replacement
     */
    private void editTitle() {
        int choice;                                         // choice from edit menu (1 - 8)

        do {
            listTitles();

            view.showEditTitleBanner();                         // edit menu banner

            String titlename = view.getDvdTitle();              // titlename is also the key for the old title object
            try {                                               // careful to catch exception if search returns null
                Title title = dao.searchTitle(titlename);       // we retrieve the object here
                choice = view.showEditMenuGetChoice(title);     // we show the user the object fields to pick from
                switch (choice) {
                    case 0:
                        removeTitle(titlename);                     // perform a quick remove if user picks 0
                        break;
                    case 9:
                        break;
                    default:
                        view.showEditorGetEdits(title, choice);     // the user makes changes to 'title'
                        dao.editTitle(title, titlename);            // the changes are written to the library by DAO
                        view.editTitleSuccess();
                        break;
                }
            } catch (Exception e) {
                view.noEntryException();                        // call our handler for null search result
            }
        } while (view.askEdit());
    }

    private void listTitles() {
        List<Title> titleList = dao.getAllTitles();
        view.printAllTitles(titleList);
    }

    private void addTitle() {
        do {
            Title title = view.addTitle();
            dao.addTitle(title.getTitle(), title);
            view.addTitleSuccess();
        } while (view.askAdd());
    }

    private void exitProgram() {
        view.showExitMessage();
    }

    // Searches the map for a single title requested by user
    private void searchTitles() {
        view.showSearchTitlesBanner();
        String titlename = view.getDvdTitle();
        try {
            Title title = dao.searchTitle(titlename);
            view.showTitleInfo(title);

        } catch (Exception e) {
            view.noEntryException();
        }
    }

    // Save library to disk using filename and password (if encrypted)
    private void saveLibrary() throws fileIOException {

        view.showSaveLibraryBanner();
        switch (view.showSaveMenuGetChoice()) {
            case 1: {
                // Non encrypted library save -- password = null
                try {
                    dao.writeEncLibToFile(view.getFilename(), null);
                    view.librarySaveSuccess();
                    break;
                } catch (fileIOException e) {
                    view.showException(e.getMessage());
                }
            }

            case 2: {
                try {
                    // Encrypted file save.  Filename AND contents are encrypted using AES
                    dao.writeEncLibToFile(view.getFilename(), view.getUserPassword());
                    view.librarySaveSuccess();
                    break;
                } catch (fileIOException e) {
                    view.showException(e.getMessage());
                }

            }
            case 3: {
                break;
            }
        }

    }

    // Load library from disk using filename and password (if encrypted)
    private void loadLibrary() throws fileIOException {
        view.showLoadLibraryBanner();
        switch (view.showLoadMenuGetChoice()) {
            case 1: {
                // No encryption - password = null
                try {
                    dao.loadLibrary(dao.loadEncLibFromFile(view.getFilename(), null));
                    view.libraryLoadSuccess();
                } catch (fileIOException e) {
                    view.showException(e.getMessage());
                }
                break;
            }

            case 2: {
                try {
                    // Load encryped file.  Password used to unmask filename and read contents
                    dao.loadLibrary(dao.loadEncLibFromFile(view.getFilename(), view.getUserPassword()));
                    view.libraryLoadSuccess();
                } catch (fileIOException e) {
                    view.showException(e.getMessage());
                }
            }
            break;
            case 3: {
                break;
            }
        }

    }

}
