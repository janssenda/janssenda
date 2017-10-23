package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.HeadquartersDao;
import com.dm.dao.db.interfaces.PowerDao;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Headquarters;
import com.dm.model.Power;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeadquartersDaoImplTest {
    private static HeadquartersDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("headquartersDao", HeadquartersDao.class);
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
    public void addHeadquarters() throws Exception {

        Headquarters h = new Headquarters();
        h.setHeadQID(0);
        h.setHeadQName("The Dungeon");
        h = dao.addHeadquarters(h);
        assertTrue(h.getHeadQID() != 0);

        Headquarters h2 = dao.getFromHeadquarters(Integer.toString(h.getHeadQID())).get(0);

        assertTrue(h2.equals(h));
    }

    @Test
    public void removeHeadquarters() throws Exception {

        Headquarters h = new Headquarters();
        h.setHeadQID(0);
        h.setHeadQName("The Dungeon");
        h = dao.addHeadquarters(h);
        assertTrue(dao.removeHeadquarters(h.getHeadQID()));


        try {
            dao.removeHeadquarters(3);
            fail("Power is tied to constraints");
        } catch (Exception e) {
            // Pass
        }

        try {
            dao.removeHeadquarters(0);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }
//
    }
//    //
    @Test
    public void updateHeadquarters() throws Exception {

        Headquarters h = dao.getFromHeadquarters("5").get(0);

        assertFalse(h.getHeadQName().equals("ZILTOID"));
        h.setHeadQName("ZILTOID");
        assertTrue(dao.updateHeadquarters(h));

        h.setHeadQID(0);

        try {
            dao.updateHeadquarters(h);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }
}
