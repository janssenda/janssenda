package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.HeroDao;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Hero;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class HeroDaoImplTest {

    private static HeroDao dao;
    private static DBMaintenance mdao;

    @BeforeClass
    public static void preWork(){
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("heroDao", HeroDao.class);
        mdao = ctx.getBean("maintenanceDao", DBMaintenance.class);
    }

    @AfterClass
    public static void postWork(){
        mdao.refresh();
    }

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

        assertTrue(h.getHeroID() != 0);
        dao.removeHero(h.getHeroID());
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
            dao.removeHero(5);
            fail("Hero is tied to constraints");
        } catch (Exception e) {
            // Pass
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
        h.setHeroID(0);
        try {
            dao.updateHero(h);
            fail("ID does not exist");
        } catch (SQLUpdateException e) {
            // Pass
        }
    }

}