package com.dm.herotoday;

import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.impl.DataCoordinatorDaoImpl;
import com.dm.herotoday.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoordinatorTest {

    @Inject private DataCoordinatorDaoImpl dao;
    @Inject private DBMaintenance mdao;

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

    @Test public void removeherofull() throws Exception{


        assertTrue(dao.removeHero(2));
        assertTrue(dao.removeHeadquarters(1));
        System.out.println(dao.removeLocation(5));


    }

}
