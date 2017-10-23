package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.LocationDao;
import com.dm.dao.db.interfaces.PowerDao;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Location;
import com.dm.model.Power;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LocationDaoImplTest {
    private static LocationDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("locationDao", LocationDao.class);
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

    @Test
    public void addLocation() throws Exception {

        Location loc = new Location();
        loc.setLocID(0);
        loc.setLocName("Far away");
        loc = dao.addLocation(loc);
        assertTrue(loc.getLocID() != 0);
    }

    @Test
    public void removeLocation() throws Exception {

        Location loc = new Location();
        loc.setLocID(0);
        loc.setLocName("Far away");
        loc = dao.addLocation(loc);

        assertTrue(dao.removeLocation(loc.getLocID()));

        try {
            dao.removeLocation(306);
            fail("ID is tied to constraints");
        } catch (Exception e) {
            // Pass
        }

        try {
            dao.removeLocation(0);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }

    }

    @Test
    public void updateLocation() throws Exception {

        Location loc = dao.getFromLocations("306").get(0);
        assertTrue(loc.getLocName() == null);
        loc.setLocName("ZILTOID");
        assertTrue(dao.updateLocation(loc));
        loc.setLocID(0);
        try {
            dao.updateLocation(loc);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }
}
