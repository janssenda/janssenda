package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.ContactDao;
import com.dm.dao.db.interfaces.ContactDao;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Contact;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ContactDaoImplTest {
    private static ContactDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("contactDao", ContactDao.class);
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
    public void addContact() throws Exception {
        Contact c = new Contact();
        c.setHeadQID(5);
        c.setEmail("superman@super.com");
        int init = dao.getAllContacts().size();
        dao.addContact(c);
        assertTrue(dao.getAllContacts().size() > init);

        c = dao.getFromContacts("5").get(0);

        try {
            dao.addContact(c);
            fail("Duplicate entry");
        } catch (Exception e){
            // Pass
        }


    }

    @Test
    public void removeContact() throws Exception {

        Contact c = new Contact();
        c.setHeadQID(5);
        c.setEmail("superman@super.com");
        int init = dao.getAllContacts().size();

        dao.addContact(c);
        assertTrue(dao.removeContact(c));

        c.setHeadQID(0);
        try {
            dao.removeContact(c);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }

        // Test remove existing
        c.setHeadQID(1);
        c.setEmail("watchtower@moon.moon");
        assertTrue(dao.removeContact(c));

    }

    @Test
    public void updateContact() throws Exception {

        Contact c = dao.getFromContacts("5").get(0);

        Contact cold = c.clone();

        assertFalse(c.getEmail().equals("ZILTOID@ZILTOID"));
        c.setEmail("ZILTOID@ZILTOID");

        assertTrue(dao.updateContact(cold, c));
        c = dao.getFromContacts("5").get(0);

        assertTrue(c.getEmail().equals("ZILTOID@ZILTOID"));

        c.setHeadQID(0);

        try {
            dao.updateContact(cold, c);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }
}
