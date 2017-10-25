package com.dm.herotoday;

import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.interfaces.OrganizationDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Organization;
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
public class OrganizationDaoImplTest {

    @Inject private OrganizationDao dao;
    @Inject private DBMaintenance mdao;

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
        Organization p2 = dao.getFromOrgs(Integer.toString(p.getOrgID())).get(0);

        assertTrue(p.equals(p2));

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
