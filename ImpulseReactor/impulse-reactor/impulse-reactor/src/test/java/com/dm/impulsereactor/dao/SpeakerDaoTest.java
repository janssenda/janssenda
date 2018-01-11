package com.dm.impulsereactor.dao;

import com.dm.impulsereactor.Exceptions.DuplicateEntryException;
import com.dm.impulsereactor.Exceptions.SQLUpdateException;
import com.dm.impulsereactor.model.Speaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpeakerDaoTest {

    @Autowired
    private SpeakerDao dao;
    @Autowired
    private QueryRunner sc;


    @Before
    public void setUp() {
        // Executes a stored procedure call that resets the database
        // to default state.  Useful for ensuring that each test starts
        // with a known good data structure;
        sc.resetDatabase();
    }

    @Test
    public void getSpeakers() throws Exception {

        
        List<Speaker> speakerList = dao.searchSpeakers(null);
        assertTrue(speakerList.size() == 39);

        Speaker s = dao.searchSpeakers(null,"1").get(0);
        assertTrue(s.getSpeakerModel().equals("V30"));
        assertTrue(s.getSpeakerBrand().equals("Celestion"));
        assertTrue(s.getSpeakerName().equals("Vintage 30"));

        speakerList = dao.searchSpeakers(null,null,null,null,"Greenback");
        assertTrue(speakerList.size() == 3);

        speakerList = dao.searchSpeakers("fttf",null,"g12","stion",null);
        assertTrue(speakerList.size() == 14);

        assertTrue(dao.searchSpeakers(null,"16").size() == 1);
        assertTrue(dao.searchSpeakers(null,"150").size() == 0);

    }

    @Test
    public void updateSpeakers() {

        try {
            Speaker c = dao.searchSpeakers(null, "1").get(0);
            c.setSpeakerName("Alchemist");
            dao.updateSpeaker(c);
            Speaker cnew = dao.searchSpeakers(null, "1").get(0);
            assertTrue(cnew.equals(c));
        } catch (SQLUpdateException e) {

        }

        try {
            Speaker c = dao.searchSpeakers(null, "1").get(0);
            c.setSpeakerModel(null);
            dao.updateSpeaker(c);
            fail("Null entry");
        } catch (SQLUpdateException e) {

        }

        try {
            Speaker c = dao.searchSpeakers(null, "2").get(0);
            Speaker c2 = new Speaker();

            c2.setSpeakerName(c.getSpeakerName());
            c2.setSpeakerModel(c.getSpeakerModel());
            c2.setSpeakerBrand(c.getSpeakerBrand());
            c2.setSpeakerID("1");

            assertTrue(dao.updateSpeaker(c));

            dao.updateSpeaker(c2);
            fail("Duplicate");
        } catch (SQLUpdateException e) {

        }

        try {
            Speaker c = dao.searchSpeakers(null, "1").get(0);
            c.setSpeakerID(null);
            c.setSpeakerName("Forever");

            dao.updateSpeaker(c);
            fail("Doesn't exist");
        } catch (SQLUpdateException e) {

        }

        try {
            Speaker c = dao.searchSpeakers(null, "1").get(0);
            c.setSpeakerID("100");
            c.setSpeakerName("Forever");

            dao.updateSpeaker(c);
            fail("Doesn't exist");
        } catch (SQLUpdateException e) {

        }

    }

    @Test
    public void removeSpeakers() {
        Speaker c = new Speaker();
        c.setSpeakerModel("G13-25");
        c.setSpeakerBrand("Marshall");
        c.setSpeakerName("Monster");

        try {
            c = dao.addSpeaker(c);
            dao.removeSpeaker(c.getSpeakerID());
            assertTrue(dao.searchSpeakers(null,c.getSpeakerID()).size() == 0);
        } catch (Exception e){}

        try {
            dao.removeSpeaker("1");
            fail("Constraint Violation");
        } catch (SQLUpdateException e){

        }

    }

    @Test
    public void addSpeakers() {

        Speaker c = new Speaker();
        c.setSpeakerModel("G13-25");
        c.setSpeakerBrand("Marshall");
        c.setSpeakerName("Monster");

        try {
            c = dao.addSpeaker(c);
        } catch (SQLUpdateException | DuplicateEntryException e){
            fail("New cab");
        }

        try {
            c = dao.addSpeaker(c);
            fail("Not new cab");
        } catch (SQLUpdateException | DuplicateEntryException e){

        }

        Speaker cnew = dao.searchSpeakers(null,c.getSpeakerID()).get(0);
        assertTrue(cnew.equals(c));

        cnew = dao.searchSpeakers(null,"1").get(0);
        try {
            cnew = dao.addSpeaker(cnew);
            fail("Exists");
        } catch (SQLUpdateException | DuplicateEntryException e){

        }

        cnew.setSpeakerID(null);
        try {
            cnew = dao.addSpeaker(cnew);
            fail("Exists");
        } catch (SQLUpdateException | DuplicateEntryException e){

        }

        cnew.setSpeakerID(null);
        cnew.setSpeakerModel(null);
        cnew.setSpeakerBrand("Marshall");
        cnew.setSpeakerName("Monster");
        try {
            cnew = dao.addSpeaker(cnew);
            fail("Null entry in non null field");
        } catch (SQLUpdateException | DuplicateEntryException e){
            System.out.println(e.getMessage());
        }



    }



}
