package com.dm.herotoday.coordinator;

import com.dm.herotoday.dao.impl.BridgeDaoImpl;
import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.impl.DataCoordinatorDaoImpl;
import com.dm.herotoday.model.*;
import org.aspectj.weaver.ast.Or;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static junit.framework.TestCase.assertTrue;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoordinatorHeadQEndpointTest {

    @Inject
    private DataCoordinatorDaoImpl dao;
    @Inject private DBMaintenance mdao;
    @Inject private BridgeDaoImpl bdao;

    @Before
    public void setUp() {
        // Executes a stored procedure call that resets the database
        // to default state.  Useful for ensuring that each test starts
        // with a known good data structure;
        mdao.refresh();
    }

    @Test
    public void testAddUpdateOrg() throws Exception{

        Headquarters hq = new Headquarters();


        hq.setHeadQName("Carag");
        hq.setHeadQAdress("4316 Chicago Ave");


        Organization o = new Organization();
        o.setOrgName("Kloud");
        o.setDescription("No no no");

        List<Organization> orgList = new ArrayList<>();
        orgList.add(o);
        orgList.add(dao.getFromOrgs("7").get(0));
        hq.setOrgList(orgList);

        Contact c = new Contact();
        Contact c2 = new Contact();
        c.setEmail("Faraway@Gmail.com");
        c2.setEmail("Nomore@never.com");

        List<Contact> clist = new ArrayList<>();
        clist.add(c);
        clist.add(c2);
        hq.setContactList(clist);

        int clistsize = dao.getFromContacts().size();
        int orgsize = dao.getFromOrgs().size();
        int borgs = bdao.sizeOrgsHeadquarters();

        Headquarters hqnew = dao.changeHeadquarters(hq);

        assertTrue(dao.getFromContacts().size() == clistsize + 2);
        assertTrue(dao.getFromOrgs().size() == orgsize + 1);
        assertTrue(bdao.sizeOrgsHeadquarters() == borgs + 2);

        hqnew = dao.getFromHeadquarters(Integer.toString(hqnew.getHeadQID())).get(0);

        hqnew.setHeadQName("New name");
        hq = dao.changeHeadquarters(hqnew);

        assertTrue(hq.equals(hqnew));

        hq.setContactList(null);
        hq.setOrgList(null);

        dao.changeHeadquarters(hq);

        assertTrue(dao.getFromContacts().size() == clistsize);
        assertTrue(dao.getFromOrgs().size() == orgsize + 1);
        assertTrue(bdao.sizeOrgsHeadquarters() == borgs);

        dao.removeHeadquarters(hq.getHeadQID());

        assertTrue(dao.getFromHeadquarters(Integer.toString(hq.getHeadQID())).size() == 0);

    }



}
