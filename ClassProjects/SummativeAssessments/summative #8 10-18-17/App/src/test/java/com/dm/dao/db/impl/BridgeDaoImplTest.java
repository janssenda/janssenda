package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.BridgeDao;
import com.dm.dao.db.interfaces.ContactDao;
import com.dm.model.Contact;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.corba.Bridge;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertTrue;

public class BridgeDaoImplTest {
    private static BridgeDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("bridgeDao", BridgeDao.class);
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
    public void orgsheadquarters () throws Exception{

        assertTrue(dao.addOrgsHeadquarters(2,5));
        try {
            dao.addOrgsHeadquarters(2,5);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removeOrgsHeadquarters(2,5));
        // Remove pre-existing
        assertTrue(dao.removeOrgsHeadquarters(2,2));
    }


    @Test
    public void orgsheroes () throws Exception{

        assertTrue(dao.addOrgsHeroes(1,7));
        try {
            dao.addOrgsHeroes(1,7);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removeOrgsHeroes(1,7));
        // Remove pre-existing
        assertTrue(dao.removeOrgsHeroes(1,1));
    }

    @Test
    public void sightingsheroes () throws Exception{
        assertTrue(dao.addSightingsHeroes(1,3));
        try {
            dao.addSightingsHeroes(1,3);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removeSightingsHeroes(1,3));
        // Remove pre-existing
        assertTrue(dao.removeSightingsHeroes(1,14));
    }

    @Test
    public void powersheroes () throws Exception{
        assertTrue(dao.addPowersHeroes(1,5));
        try {
            dao.addPowersHeroes(1,5);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removePowersHeroes(1,5));
        // Remove pre-existing
        assertTrue(dao.removePowersHeroes(1,3));
    }
}
