package com.dm.herotoday;

import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.interfaces.LocationDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoImplTest {

    @Inject private LocationDao dao;
    @Inject private DBMaintenance mdao;

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

        Location l2 = dao.getFromLocations(Integer.toString(loc.getLocID())).get(0);

        assertTrue(l2.equals(loc));

        try{
            dao.addLocation(loc);
            fail("Duplicate");
        } catch (DuplicateEntryException e){
            // Pass
        }

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
