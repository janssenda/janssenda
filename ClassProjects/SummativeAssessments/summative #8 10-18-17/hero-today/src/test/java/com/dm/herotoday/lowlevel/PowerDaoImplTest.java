package com.dm.herotoday.lowlevel;

import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.interfaces.PowerDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Power;
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
public class PowerDaoImplTest {

    @Inject private PowerDao dao;
    @Inject private DBMaintenance mdao;

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

        Power p2 = dao.getFromPowers(Integer.toString(p.getPowerID())).get(0);

        assertTrue(p.equals(p2));

        try{
            dao.addPower(p);
            fail("Duplicate");
        }catch (DuplicateEntryException e){
            // Pass
        }


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
