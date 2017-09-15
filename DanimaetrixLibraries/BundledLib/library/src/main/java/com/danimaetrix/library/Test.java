/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.library;

import com.danimaetrix.library.userIO.ColorIO;
import com.danimaetrix.library.userIO.UserIo;
import com.danimaetrix.library.userIO.UserIoConsoleColorImpl;

/**
 *
 * @author danimaetrix
 */
public class Test {

    public static void main(String[] args) {

        UserIo io = new UserIoConsoleColorImpl();

//           io.readBigDecimal("Give me a number!!!", ColorIO.YELLOW_BOLD);
        ColorIO gen = new ColorIO();

        //System.out.println(gen.getRandomColor() + "Test");
        for (int i = 0; i < 10; i++) {

            io.print("My message test!!", gen.getRandomColor());
        }

    }

    public static void testPrint(String s, String c) {
        System.out.println(c + s);

    }

}
