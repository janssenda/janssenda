package com.dm.herotoday;

import com.dm.herotoday.dao.interfaces.SightingDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.model.Sighting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoImplTest {

    @Inject private SightingDao dao;
    @Inject private DBMaintenance mdao;

    @Before
    public void setUp() {
        // Executes a stored procedure call that resets the database
        // to default state.  Useful for ensuring that each test starts
        // with a known good data structure;
        mdao.refresh();
    }

    @Test
    public void addSighting() throws Exception {

        LocalDateTime d = LocalDateTime.now();
        Sighting s = new Sighting();
        s.setSightingID(0);
        s.setSightingTime(d);
        s.setLocID(5);
        s = dao.addSighting(s);
        assertTrue(s.getSightingID() != 0);

        Sighting s2 = dao.getFromSightings(Integer.toString(s.getSightingID())).get(0);

        assertTrue(s2.equals(s));

    }

    @Test
    public void removeSighting() throws Exception {

        LocalDateTime d = LocalDateTime.now();
        Sighting s = new Sighting();
        s.setSightingID(0);
        s.setSightingTime(d);
        s.setLocID(5);
        s = dao.addSighting(s);
        assertTrue(dao.removeSighting(s.getSightingID()));

        try {
            dao.removeSighting(3);
            fail("Sighting is tied to constraints");
        } catch (Exception e) {
            // Pass
        }

        try {
            dao.removeSighting(0);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }

    }

    @Test
    public void updateSighting() throws Exception {

        Sighting s = dao.getFromSightings("5").get(0);
        assertFalse(s.getLocID() == 25);
        s.setLocID(25);

        assertTrue(dao.updateSighting(s));
        s.setSightingID(0);

        try {
            dao.updateSighting(s);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }

    @Test
    public void getByDate() throws Exception {
        assertTrue(dao.getFromSightings(null,"2017-12-01").size() == 4);
    }
}
