package com.dm.herotoday.lowlevel;

import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.interfaces.BridgeDao;
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
public class BridgeDaoImplTest {

    @Inject private BridgeDao dao;
    @Inject private DBMaintenance mdao;

    @Before
    public void setUp() {
        // Executes a stored procedure call that resets the database
        // to default state.  Useful for ensuring that each test starts
        // with a known good data structure;
        mdao.refresh();
    }

    @Test
    public void orgsheadquarters () throws Exception{

        assertTrue(dao.addOrgsHeadquarters(2,5));
        try {
            dao.addOrgsHeadquarters(2,5);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removeOrgsHeadquarters("2","5")>0);
        // Remove pre-existing
        assertTrue(dao.removeOrgsHeadquarters("2","2")>0);
        assertTrue(dao.removeOrgsHeadquarters("1",null) == 1);
    }


    @Test
    public void orgsheroes () throws Exception{

        assertTrue(dao.addOrgsHeroes(1,7));
        try {
            dao.addOrgsHeroes(1,7);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removeOrgsHeroes("1","7")>0);
        // Remove pre-existing
        assertTrue(dao.removeOrgsHeroes("1","1")>0);
        assertTrue(dao.removeOrgsHeroes("1",null) == 5);


    }

    @Test
    public void sightingsheroes () throws Exception{
        assertTrue(dao.addSightingsHeroes(1,3));
        try {
            dao.addSightingsHeroes(1,3);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removeSightingsHeroes("1","3")>0);
        // Remove pre-existing
        assertTrue(dao.removeSightingsHeroes("1","14")>0);

        assertTrue(dao.removeSightingsHeroes(null,"5") == 315);
    }

    @Test
    public void powersheroes () throws Exception {
        assertTrue(dao.addPowersHeroes(1,5));
        try {
            dao.addPowersHeroes(1,5);
            fail("Duplicate entry");
        } catch (Exception e) {
            // Pass
        }
        assertTrue(dao.removePowersHeroes("1","5")>0);
        // Remove pre-existing
        assertTrue(dao.removePowersHeroes("1","3")>0);

        assertTrue(dao.removePowersHeroes("2",null) == 8);
    }


}
