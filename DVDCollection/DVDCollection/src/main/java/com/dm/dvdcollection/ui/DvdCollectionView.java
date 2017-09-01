/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.ui;

/**
 *
 * @author danimaetrix
 */
public class DvdCollectionView {

    private UserIo io;

    public DvdCollectionView(UserIo io) {
        this.io = io;
    }

    public int printMainGetChoice() {
        io.print("Welcome to your DVD collection library!");
        io.print("Please choose from the following options: \n");
        io.print("1. Add a new title");
        io.print("2. Remove a title");
        io.print("3. See all titles");
        io.print("4. Update a title");
        io.print("5. Search titles");
        io.print("6. Save library");
        io.print("7. Load library");
        io.print("8. Exit (saves changes)");
        return io.readInt("\nEnter your choice: ",1,8);
        
    }

    public void showMainMenuBanner() {
        io.print("\n");
        io.print("*******************");
        io.print("*    MAIN MENU    *");
        io.print("*******************");
    }

}
