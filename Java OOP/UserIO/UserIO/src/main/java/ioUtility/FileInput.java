/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInput {

    Scanner sc;

    public FileInput(String filename) {
        try {
            this.sc = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (Exception e) {
            System.out.println("Error opening file");
        }
    }

    public String getLine() {
        return sc.nextLine();
    }

    public int getInt() {
        return sc.nextInt();
    }

    public double getDouble() {
        return sc.nextDouble();
    }

    public boolean hasNext() {
        return sc.hasNext();
    }

    public ArrayList readData() {
        sc.reset();
        ArrayList<double[]> data = new ArrayList<>();
        
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            //String delims = "[ .,!\")_*||(\':?;-=]+&^%$#@~`";
            String delims = "[   ]+";
            String tokens[] = {""};
            
            tokens = currentLine.split(delims);
            double[] temp = new double[tokens.length];

            for (int i = 0; i < tokens.length; i++) {
                temp[i] = Double.parseDouble(tokens[i]);
            }
            //System.out.println(temp[1]);
            data.add(temp);
            
            //System.out.println("");
        }
        
        double d[] = data.get(0);
        System.out.println(d[1]);
        return data;

    }

    public int countOccurences(String string) {
        String delims = "[ .,!\")_*||(\':?;-=]+&^%$#@~`";
        String tokens[] = {""};
        int count = 0;

        sc.reset();
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            tokens = currentLine.split(delims);

            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equalsIgnoreCase(string)) {
                    count = count + 1;
                }
            }

        }
        return count;
    }

    public void closeFile() {
        sc.close();
    }

}
