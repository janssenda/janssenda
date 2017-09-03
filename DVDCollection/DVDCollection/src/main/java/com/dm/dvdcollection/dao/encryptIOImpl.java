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

/**
 *
 * @author Danimaetrix
 */
public class encryptIOImpl implements fileIO {

    public static final String DELIMITER = "\t";
    public static final String EXT = ".cplib";

    @Override
    public List<Title> readFromFile(String filename, String password) throws fileIOException, fileEncryptionException {
        Scanner scanner;
        List<Title> titleList = new ArrayList<>();

        if (!filename.contains(".")) {
            filename = filename + EXT;             // User our "proprietary" format if user does not specify
        }

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (FileNotFoundException e) {
            throw new fileIOException("Error opening file...", e);
        }

        String currentLine;
        String[] currentTokens;

        try {

            while (scanner.hasNextLine()) {

                currentLine = testEncrypt.decrypt(scanner.nextLine(), password);

                currentTokens = currentLine.split(DELIMITER);

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
            scanner.close();
            return titleList;
        } catch (Exception e) {
            throw new fileEncryptionException("This file is not encrypted... ");
      }
    }

    @Override
    public void writeToFile(String filename, List<Title> titleList, String password) throws fileIOException {
        PrintWriter out;
        String currentline;

        if (filename.contains(".")) {
            String[] tokens = filename.split("\\.");
            System.out.println(tokens[0]);
            filename = tokens[0] + EXT;
        } else {
            filename = filename + EXT;
        }

        try {
            out = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new fileIOException("Error opening file...", e);
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

            out.println(testEncrypt.encrypt(currentline, password));

            out.flush();
        }
        out.close();
    }

    @Override
    public void writeToFile(String filename, List<Title> titleList) throws fileIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Title> readFromFile(String filename) throws fileIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
