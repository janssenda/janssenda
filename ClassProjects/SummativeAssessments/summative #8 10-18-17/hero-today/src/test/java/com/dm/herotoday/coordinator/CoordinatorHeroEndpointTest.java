package com.dm.herotoday.coordinator;

import com.dm.herotoday.dao.impl.BridgeDaoImpl;
import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.dao.impl.DataCoordinatorDaoImpl;
import com.dm.herotoday.model.Hero;
import com.dm.herotoday.model.Organization;
import com.dm.herotoday.model.Power;
import com.dm.herotoday.model.Sighting;
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
public class CoordinatorHeroEndpointTest {

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
    public void testAddUpdateHero() throws Exception{

        Hero h = new Hero();

        h.setHeroName("Danimaetrix");
        h.setHeroType("Default");
        h.setDescription("The one and only");

        Power p = new Power();
        p.setPowerName("Cat Smash");

        int plistsize = dao.getFromPowers().size();
        int bridgeplistsize = bdao.sizePowersHeroes();

        int olistsize = dao.getFromOrgs().size();
        int bridgeolistsize = bdao.sizeOrgsHeroes();

        int slistsize = dao.getFromSightings().size();
        int bridgeslistsize = bdao.sizeSightingsHeroes();

        List<Power> powerList = new ArrayList<>();
        powerList.add(p);
        powerList.add(dao.getFromPowers("1").get(0));

        h.setHeroPowers(powerList);

        Organization o = new Organization();
        o.setOrgName("Carag");
        o.setDescription("The best org ever");
        List<Organization> orgList = new ArrayList<>();

        orgList.add(o);
        orgList.add(dao.getFromOrgs("2").get(0));

        h.setHeroOrgs(orgList);

        List<Hero> slist = new ArrayList<>();
        slist.add(h);

        Sighting s = new Sighting();
        s.setLocID(134);
        s.setSightingTime(LocalDateTime.now());
        s.setSightingHeroes(slist);

        List<Sighting> herosightings = new ArrayList<>();
        herosightings.add(s);

        h.setHeroSightings(herosightings);

        Hero hnew = dao.changeHero(h);

        assertTrue(hnew.getHeroName().equals(h.getHeroName()));

        assertTrue(hnew.getHeroPowers().size() == 2);
        assertTrue(dao.getFromPowers().size() == plistsize + 1);
        assertTrue(bdao.sizePowersHeroes() == bridgeplistsize + 2);

        assertTrue(hnew.getHeroOrgs().size() == 2);
        assertTrue(dao.getFromOrgs().size() == olistsize + 1);
        assertTrue(bdao.sizeOrgsHeroes() == bridgeolistsize + 2);

        assertTrue(hnew.getHeroSightings().size() == 1);
        assertTrue(dao.getFromSightings().size() == slistsize + 1);
        assertTrue(bdao.sizeSightingsHeroes() == bridgeslistsize + 1);

        hnew.getHeroPowers().add(dao.getFromPowers("3").get(0));
        dao.changeHero(hnew);

        assertTrue(hnew.getHeroPowers().size() == 3);
        assertTrue(dao.getFromPowers().size() == plistsize + 1);
        assertTrue(bdao.sizePowersHeroes() == bridgeplistsize + 3);

        hnew.setHeroPowers(null);
        hnew.setHeroOrgs(null);
        hnew.setHeroSightings(null);

        hnew = dao.changeHero(hnew);

        assertTrue(hnew.getHeroPowers().size() == 0);
        assertTrue(dao.getFromPowers().size() == plistsize + 1);
        assertTrue(bdao.sizePowersHeroes() == bridgeplistsize);

        assertTrue(hnew.getHeroOrgs().size() == 0);
        assertTrue(dao.getFromOrgs().size() == olistsize + 1);
        assertTrue(bdao.sizeOrgsHeroes() == bridgeolistsize);

        assertTrue(hnew.getHeroSightings().size() == 0);
        assertTrue(dao.getFromSightings().size() == slistsize + 1);
        assertTrue(bdao.sizeSightingsHeroes() == bridgeslistsize);

        dao.removeHero(hnew.getHeroID());
        assertTrue(dao.getFromHeroes(Integer.toString(hnew.getHeroID())).size()==0);


    }



}
