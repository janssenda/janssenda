
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
    public class CoordinatorSightingEndpointTest {

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

            Sighting s = new Sighting();
            s.setLocID(43);
            s.setSightingTime(LocalDateTime.now());

            Hero h = new Hero();
            h.setHeroName("Danimaetrix");
            h.setHeroType("Default");
            h.setDescription("The one and only");

            List<Hero> heroList = new ArrayList<>();

            heroList.add(h);
            heroList.add(dao.getFromHeroes("4").get(0));

            s.setSightingHeroes(heroList);

            int slist = dao.getFromSightings().size();
            int hlist = dao.getFromHeroes().size();
            int bslist = bdao.sizeSightingsHeroes();

            dao.changeSighting(s);

            assertTrue(dao.getFromSightings().size() == slist + 1);
            assertTrue(dao.getFromHeroes().size() == hlist + 1);
            assertTrue(bdao.sizeSightingsHeroes() == bslist + 2);

            s = dao.getFromSightings(Integer.toString(s.getSightingID())).get(0);
            s.setLocID(97);
            dao.changeSighting(s);

            assertTrue(s.equals(dao.getFromSightings(Integer.toString(s.getSightingID())).get(0)));

            dao.removeSighting(s.getSightingID());

            assertTrue(dao.getFromSightings().size() == slist);
            assertTrue(dao.getFromHeroes().size() == hlist + 1);
            assertTrue(bdao.sizeSightingsHeroes() == bslist);


        }



    }


