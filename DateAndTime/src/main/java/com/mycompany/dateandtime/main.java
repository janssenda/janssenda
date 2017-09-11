/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dateandtime;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 *
 * @author Danimaetrix
 */
public class main {

    public static void main(String[] args) {

        LocalDate ld = LocalDate.now();
        //ZonedDateTime ld = ZonedDateTime.now();
        //ld = ld.minusDays(5);
        
        System.out.println(ld.format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        

        //System.out.println(ld.format(DateTimeFormatter.ofPattern("MM/dd/yy @ h:mm a z")));
        //System.out.println(ld.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
//        for (int i = 0; i < 5; i++) {
//
//            LocalDateTime ld = LocalDateTime.now();
//
//            System.out.println(ld.format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm:ss.SSS")));
//        }
//        long t1 = System.nanoTime();
//
//        try {
//            Thread.sleep(2000);
//        } catch (Exception e) {
//
//        }
//        long dT = System.nanoTime() - t1;
//
//        dT = TimeUnit.NANOSECONDS.toMillis(dT);
//        System.out.println(dT);
//    }
    }
}
