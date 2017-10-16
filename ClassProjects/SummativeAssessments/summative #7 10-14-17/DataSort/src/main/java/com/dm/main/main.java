package com.dm.main;

import com.sun.org.apache.regexp.internal.RE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class main {


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;
        List<Reservation> Reservations = new ArrayList<>();
        Set<Reservation> Reduced = new HashSet<>();
        scanner = new Scanner(new BufferedReader(new FileReader("Reservations.csv")));

        String currentline;
        String[] currentTokens;

        scanner.nextLine();
        while (scanner.hasNextLine()) {

            currentline = scanner.nextLine();
            currentTokens = currentline.split(",");

            for (int i = 0; i < currentTokens.length; i++) {
                currentTokens[i] = currentTokens[i].trim();
            }

            if (!currentTokens[0].startsWith("//")) {
                Reservation r = new Reservation();

                r.setResID(Integer.parseInt(currentTokens[0]));
                r.setRoomNumber(Integer.parseInt(currentTokens[1]));
                r.setStartDate(LocalDate.parse(
                        currentTokens[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                r.setEndDate(LocalDate.parse(
                        currentTokens[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                Reservations.add(r);
            }

        }

        scanner.close();

        int overlaps = 0;

        for (int i = 0; i < Reservations.size(); i++) {
            boolean valid = true;
            Reservation currentRes = Reservations.get(i);

            for (int j = 0; j < Reservations.size(); j++) {
                Reservation testRes = Reservations.get(j);
                if (j != i) {
                    if (currentRes.getRoomNumber() == testRes.getRoomNumber()) {
                        if (currentRes.getStartDate().compareTo(testRes.getStartDate()) >= 0
                                && currentRes.getStartDate().compareTo(testRes.getEndDate()) <= 0) {
                            overlaps += 1;
                            if (Reduced.contains(testRes)) {
                                valid = false;
                            }
                        }
                    }
                }
            }

            if (valid) {
                Reduced.add(currentRes);
            }

        }

        System.out.println(overlaps);
        System.out.println(Reduced.size());
        List<Reservation> rlist = new ArrayList<>();

        PrintWriter out = new PrintWriter("output.csv");
        rlist= Reduced.stream().sorted(Comparator.comparing((r) -> r.getResID())).collect(Collectors.toList());

        for (int i = 0; i < rlist.size(); i++){

            Reservation val = rlist.get(i);

            out.write(val.getResID() + ",");
            out.write(val.getRoomNumber() + ",");
            out.write(val.getStartDate().toString() + ",");
            out.write(val.getEndDate().toString());
            out.write("\n");

        }

        out.flush();
        out.close();
    }

}
