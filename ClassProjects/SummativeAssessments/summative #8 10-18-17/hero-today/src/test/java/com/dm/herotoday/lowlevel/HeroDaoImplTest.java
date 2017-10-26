package com.dm.herotoday.lowlevel;

import com.dm.herotoday.dao.interfaces.HeroDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.dao.impl.DBMaintenance;
import com.dm.herotoday.model.Hero;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroDaoImplTest {

    @Inject private HeroDao dao;
    @Inject private DBMaintenance mdao;

    @Before
    public void setUp() {
        // Executes a stored procedure call that resets the database
        // to default state.  Useful for ensuring that each test starts
        // with a known good data structure;
        mdao.refresh();
    }

    @Test
    public void addHero() throws Exception {

        Hero h = new Hero();
        h.setHeroID(0);
        h.setHeroName("Batman Returns");
        h.setHeroType("Default");
        h.setDescription(null);

        h = dao.addHero(h);

        assertTrue(dao.ifExists(h));

        assertTrue(h.getHeroID() != 0);

        Hero h2 = dao.getFromHeroes(Integer.toString(h.getHeroID())).get(0);
        assertTrue(h.equals(h2));
    }

    @Test
    public void removeHero() throws Exception {

        Hero h = new Hero();
        h.setHeroID(0);
        h.setHeroName("Batman Returns");
        h.setHeroType("Default");
        h.setDescription(null);

        h = dao.addHero(h);
        assertTrue(dao.removeHero(h.getHeroID()));

        try {
            dao.addHero(h);
            fail("Duplicate");
        } catch (DuplicateEntryException e){
            // Pass
        }

        try {
            dao.removeHero(5);
            fail("Hero is tied to constraints");
        } catch (Exception e) {
            System.out.println(e.getMessage());// Pass
        }

        try {
            dao.removeHero(0);
            fail("Hero ID does not exist");
        } catch (Exception e) {
            // Pass
        }

    }

    @Test
    public void updateHero() throws Exception {
        List<Hero> heroList = dao.getFromHeroes("2");
        Hero h = heroList.get(0);
        String tempname = h.getHeroName();

        assertFalse(tempname.equals("Batman Begins"));
        h.setHeroName("Batman Begins");
        assertTrue(dao.updateHero(h));
        h.setDescription("It's a bat");
        h.setHeroID(0);

        try {
            dao.updateHero(h);
            fail("ID does not exist");
        } catch (Exception e) {
            // Pass
        }
    }

}