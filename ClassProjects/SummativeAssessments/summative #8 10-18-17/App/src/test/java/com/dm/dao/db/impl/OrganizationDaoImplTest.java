package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.OrganizationDao;

import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Organization;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrganizationDaoImplTest {
    private static OrganizationDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("organizationDao", OrganizationDao.class);
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
    public void addOrg() throws Exception {

        Organization p = new Organization();
        p.setOrgID(0);
        p.setOrgName("Kloud");
        p = dao.addOrg(p);
        assertTrue(p.getOrgID() != 0);
    }

    @Test
    public void removeOrg() throws Exception {

        Organization p = new Organization();
        p.setOrgID(0);
        p.setOrgName("Kloud");
        p = dao.addOrg(p);

        assertTrue(dao.removeOrg(p.getOrgID()));
        try {
            dao.removeOrg(3);
            fail("Org is tied to constraints");
        } catch (Exception e) {
            // Pass
        }

        try {
            dao.removeOrg(0);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }

    }
    //
    @Test
    public void updateOrg() throws Exception {

        Organization p = dao.getFromOrgs("5").get(0);

        assertFalse(p.getOrgName().equals("ZILTOID"));
        p.setOrgName("ZILTOID");
        assertTrue(dao.updateOrg(p));

        p.setOrgID(0);

        try {
            dao.updateOrg(p);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }
}
