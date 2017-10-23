package com.dm.dao.db.impl;

import com.dm.model.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static junit.framework.TestCase.assertTrue;

public class CoordinatorTest {
    private static DataCoordinatorDaoImpl dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("coordinatorDao", DataCoordinatorDaoImpl.class);
        mdao = ctx.getBean("maintenanceDao", DBMaintenance.class);
    }

    @AfterClass
    public static void postWork() {
        //mdao.refresh();
    }

    @Before
    public void setUp() {
        // Executes a stored procedure call that resets the database
        // to default state.  Useful for ensuring that each test starts
        // with a known good data structure;
        mdao.refresh();
    }

    @Test public void getHeroes() {
        Hero h = dao.getFromHeroes("1").get(0);
        assertTrue(h.getHeroPowers().size() == 6);
        assertTrue(h.getHeroOrgs().size() == 3);
        assertTrue(h.getHeroSightings().size() == 316);
    }

    @Test public void getOrgs() {
        Organization o = dao.getFromOrgs("4").get(0);
        assertTrue(o.getMembers().size() == 1);
        assertTrue(o.getOrgHeadQ().size() == 3);
    }

    @Test public void getHeadquarters() {
        Headquarters h = dao.getFromHeadquarters("1").get(0);
        assertTrue(h.getContactList().size() == 1);
    }

    @Test public void getSightings() {
        Sighting s = dao.getFromSightings("1").get(0);
        assertTrue(s.getSightingHeroes().size() == 2);
    }

    @Test public void getLocations() {
        Location l = dao.getFromLocations("1").get(0);
        assertTrue(l.getLocSightings().get(0).getSightingID() == 371);
    }

    @Test public void otherMethods() {
        assertTrue(dao.heroesByLocation(1).size() == 5);
        assertTrue(dao.locationsByHero(1).size() == 298);

        LocalDate date = LocalDate.parse("11/19/2016", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        assertTrue(dao.sightingsByDate(date).size() == 4);
    }


}
