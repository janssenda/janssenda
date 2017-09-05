/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.dao;

import com.dm.dvdcollection.dto.Title;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/* This class controls how the libraries are written to and read from disk
there are two methods - readFromFile and writeToFile.  Encryption is included
as an option for the user, and is used when password != null.  Othwerwise, 
standard comma delinieated output is used.  File extnesion .cplib is created for libraries.
Encrytped libraries also have encrypted filenames.
*/

public class CppFileHandler {

    private static final String DELIMITER = ",";            // Delimiter for reading and writing files
    private static final String EXT = ".cplib";             // Arbitrary extension created for this project
    private String password;
    private String filename;

    // Constructor for standar library
    public CppFileHandler(String filename) {
        this.filename = filename;
        this.password = null;
    }

    // Consructor for encrypted library
    public CppFileHandler(String filename, String password) {
        this.filename = filename;
        this.password = password;
    }

    public List<Title> readFromFile() throws fileIOException {
        int skipcount = 0, lncount = 0;                             // Counts skipped lines (if corrupt).  Not currently used.
        Scanner scanner;
        List<Title> titleList = new ArrayList<>();

        // Determine if user is loading an encrypted file
        if (password != null) {
            try {
                filename = StringEncrypt.encrypt(filename+EXT, password);   // Calculate the filename for encrypted library.             
            } catch (Exception e) {
            }

        } else if (!filename.contains(".")) {
            filename = filename + EXT;             // User our "proprietary" format if user does not specify.  Other formats can be read
        }                                          // as long as they are comma delinieated     

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (FileNotFoundException e) {
            throw new fileIOException("Wrong password or library... ", e);
        }

        String currentline;
        String[] currentTokens;

        try {
            while (scanner.hasNextLine()) {

                // Decrypt line if encryption was used, read a standard line otherwise
                if (password != null) {
                    currentline = StringEncrypt.decrypt(scanner.nextLine(), password);
                } else {
                    currentline = scanner.nextLine();
                }
                if (currentline == null) {
                    skipcount = skipcount + 1;
                
                } else {
                    // if current line is readable, proceed with read
                    currentTokens = currentline.split(DELIMITER);
                    Title currentTitle = new Title(currentTokens[0]);
                    currentTitle.setDuration(currentTokens[1]);
                    currentTitle.setReleaseDate(currentTokens[2]);
                    currentTitle.setMpaaRating(currentTokens[3]);
                    currentTitle.setUserRating(currentTokens[4]);
                    currentTitle.setDirector(currentTokens[5]);
                    currentTitle.setStudio(currentTokens[6]);
                    currentTitle.setUserNotes(currentTokens[7]);

                    titleList.add(currentTitle);
                }
                lncount = lncount + 1;
            }

        } catch (Exception e) {
            throw new fileIOException("Error reading file.  Unencrypted, corrupt or wrong password.", e);
        }

        scanner.close();        
        return titleList;

    }

    public void writeToFile(List<Title> titleList) throws fileIOException {
        PrintWriter out;
        String currentline;

        // Force the .cplib extension on all libraries
        if (filename.contains(".")) {
            String[] tokens = filename.split("\\.");

            filename = tokens[0] + EXT;
        } else {
            filename = filename + EXT;
        }

        // Calculate encrypted flename if encryption is used
        if (password != null) {
            filename = StringEncrypt.encrypt(filename, password);
        }

        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new fileIOException("Error opening file.  Is it open"
                    + "\nin another application? ", e);
        }

        for (Title currentTitle : titleList) {

            currentline = currentTitle.getTitle() + DELIMITER
                    + currentTitle.getDuration() + DELIMITER
                    + currentTitle.getReleaseDate() + DELIMITER
                    + currentTitle.getMpaaRating() + DELIMITER
                    + currentTitle.getUserRating() + DELIMITER
                    + currentTitle.getDirector() + DELIMITER
                    + currentTitle.getStudio() + DELIMITER
                    + currentTitle.getUserNotes();
            if (password != null) {
                out.println(StringEncrypt.encrypt(currentline, password));          // Encrypt line
            } else {
                out.println(currentline);                                           // Do not encrypt
            }
            out.flush();
        }
        out.close();
    }

}
