package com.dm.herotoday;

import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.interfaces.ContactDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactDaoImplTest {

    @Inject private ContactDao dao;
    @Inject private DBMaintenance mdao;

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

        Contact c2 = dao.getFromContacts("5",c.getEmail()).get(0);

        assertTrue(c2.equals(c));
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
