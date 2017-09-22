/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.library.general;

/**
 *
 * @author danimaetrix
 */
public class StringUtil {

    // Short method to shorten and extend string to length l, using "..." to truncate 
    // and whitespace to extend.  Used to format fields for output to user in printAllTitles
    public static String padSter(String string, int stringLength){
        return padStr(string, stringLength, false);
    }
    
    public static String padStr(String string, int stringLength, boolean dots) {
        int l = stringLength;
        char[] newstr = new char[l];

        if (string.length() > l) {
            char[] str = string.toCharArray();
            if (dots) {
                System.arraycopy(str, 0, newstr, 0, l - 3);

                for (int i = 0; i < 3; i++) {
                    newstr[(l - 4) + i] = '.';
                }
            } else {
                System.arraycopy(str, 0, newstr, 0, l);
            }

            String fstring = new String(newstr);
            return fstring;

        } else {
            char[] str = string.toCharArray();
            System.arraycopy(str, 0, newstr, 0, str.length);

            for (int i = str.length + 1; i < l; i++) {
                newstr[i] = ' ';

            }
            String fstring = new String(newstr);
            return fstring;
        }
    }

}
