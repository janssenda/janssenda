/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.controller;

import com.dm.dvdcollection.dao.DvdCollectionDao;
import com.dm.dvdcollection.dao.fileEncryptionException;
import com.dm.dvdcollection.dao.fileIOException;
import com.dm.dvdcollection.dto.Title;

import com.dm.dvdcollection.ui.DvdCollectionView;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class DvdCollectionController {

    DvdCollectionView view;
    DvdCollectionDao dao;

    public DvdCollectionController(DvdCollectionDao dao, DvdCollectionView view)throws fileIOException {
        this.dao = dao;
        this.view = view;
    }

    public void run() throws fileIOException {
        //dao.createDB(15);
        boolean repeat = true;

        while (repeat) {
            repeat = evaluateSelection(getSelection());
        }
        exitProgram();

    }

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

            default:
                unknownCommand();
        }
        return repeat;
    }

    private int getSelection() {
        view.showMainMenuBanner();
        return view.printMainGetChoice();
    }

    private void removeTitle() {
        removeTitleByEntry();
    }

    private void removeTitle(String key) {
        removeTitleByKey(key);
    }

    private void removeTitleByEntry() {
        boolean confirm;

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
        } while (view.askAdd());
    }

    private void exitProgram() {
        view.showExitMessage();
    }

    private void unknownCommand() {

    }

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

    private void saveLibrary() throws fileIOException{

        view.showSaveLibraryBanner();
        switch (view.showSaveMenuGetChoice()) {
            case 1: {
                try {
                    dao.writeLibToFile(view.getFilename());
                    view.librarySaveSuccess();
                    break;
                } catch (fileIOException e) {
                    view.fileIOException();
                }
            }

            case 2: {
                try {
                    dao.writeEncLibToFile(view.getFilename(), view.getUserPassword());
                    view.librarySaveSuccess();
                    break;
                } catch (fileIOException e) {
                    view.fileIOException();
                }

            }
            case 3: {
                break;
            }
        }

    }

    private void loadLibrary() throws fileIOException {
        view.showLoadLibraryBanner();
        switch (view.showLoadMenuGetChoice()) {
            case 1: {
                try {
                    dao.loadDB(dao.loadLibFromFile(view.getFilename()));
                    view.libraryLoadSuccess();
                } catch (fileIOException e) {
                    view.fileIOException();
                }
                break;
            }

            case 2: {
                try {
                    dao.loadDB(dao.loadEncLibFromFile(view.getFilename(), view.getUserPassword()));
                    view.libraryLoadSuccess();
                } catch (fileIOException e) {
                    view.fileIOException();
                } catch (fileEncryptionException e){
                    view.fileEncryptionException();
                }
            }
            break;
            case 3: {
                break;
            }
        }

    }

}
