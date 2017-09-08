/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject.app;

import com.mycompany.serverproject.dao.ServerDao;
import com.mycompany.serverproject.dao.ServerDaoInMemImpl;
import com.mycompany.serverproject.dto.Server;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Danimaetrix
 */
public class App {

    public static void main(String[] args) {

        ServerDao dao = new ServerDaoInMemImpl();

        for (int i = 0; i < 5; i++) {
            Server myServer = new Server();
            myServer.setName("web" + i);
            myServer.setIp("192.168.1." + i);
            myServer.setManufacturer("Dell");
            myServer.setRam(8);
            myServer.setNumProcessors(i + 1);
            myServer.setPurchaseDate(LocalDate.parse("2010-05-01", DateTimeFormatter.ISO_DATE));
            dao.addServer(myServer);
        }

        for (int i = 0; i < 3; i++) {
            Server myServer = new Server();
            myServer.setName("financial" + i);
            myServer.setIp("105.18.1." + i);
            myServer.setManufacturer("HP");
            myServer.setRam(12);
            myServer.setNumProcessors(i + 2);
            myServer.setPurchaseDate(LocalDate.parse("2016-06-05", DateTimeFormatter.ISO_DATE));
            dao.addServer(myServer);
        }

        for (int i = 0; i < 6; i++) {
            Server myServer = new Server();
            myServer.setName("data" + i);
            myServer.setIp("64.108.10." + i);
            myServer.setManufacturer("Xeon");
            myServer.setRam(32);
            myServer.setNumProcessors(i + 3);
            myServer.setPurchaseDate(LocalDate.parse("2006-12-16", DateTimeFormatter.ISO_DATE));
            dao.addServer(myServer);
        }

        List<Server> dells = dao.getServersByManufacturer("Dell");
        System.out.println("\n");
        //dells.stream().forEach(s -> System.out.println(s.getName()));

        Map<String, List<Server>> serverMap = dao.getAllServersGroupByManufacturer();

        Set<String> manufacturers = serverMap.keySet();

        manufacturers.stream()
                .forEach(name -> {

                    System.out.println("=================================");
                    System.out.println("Manufacturer: " + name);

                    serverMap.get(name).stream().forEach(s -> System.out.println(s.getName()));

                });
        System.out.println("\n");
    }

}
