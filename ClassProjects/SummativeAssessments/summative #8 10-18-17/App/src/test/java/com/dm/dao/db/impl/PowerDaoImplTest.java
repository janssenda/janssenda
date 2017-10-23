package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.PowerDao;
import com.dm.exceptions.SQLUpdateException;
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

public class PowerDaoImplTest {
    private static PowerDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("powerDao", PowerDao.class);
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
    public void addPower() throws Exception {

        Power p = new Power();
        p.setPowerID(0);
        p.setPowerName("Ultra Guitar");
        p = dao.addPower(p);
        assertTrue(p.getPowerID() != 0);
    }

    @Test
    public void removePower() throws Exception {

        Power p = new Power();
        p.setPowerID(0);
        p.setPowerName("Ultra Guitar");
        p = dao.addPower(p);

        assertTrue(dao.removePower(p.getPowerID()));
        try {
            dao.removePower(3);
            fail("Power is tied to constraints");
        } catch (Exception e) {
            // Pass
        }

        try {
            dao.removePower(0);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }

    }
    //
    @Test
    public void updatePower() throws Exception {

        Power p = dao.getFromPowers("5").get(0);

        assertFalse(p.getPowerName().equals("ZILTOID"));
        p.setPowerName("ZILTOID");
        assertTrue(dao.updatePower(p));

        p.setPowerID(0);

        try {
            dao.updatePower(p);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }
}
