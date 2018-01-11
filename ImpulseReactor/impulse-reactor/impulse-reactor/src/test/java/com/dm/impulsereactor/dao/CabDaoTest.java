package com.dm.impulsereactor.dao;

import com.dm.impulsereactor.Exceptions.DuplicateEntryException;
import com.dm.impulsereactor.Exceptions.SQLUpdateException;
import com.dm.impulsereactor.model.Cab;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabDaoTest {

    @Autowired
    private CabDao dao;
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
    public void getCabs() throws Exception {

        // "CabID","CabName","Brand","Size"
        List<Cab> cabList = dao.searchCabs(null);
        assertTrue(cabList.size() == 41);

        Cab c = dao.searchCabs(null,"4").get(0);
        assertTrue(c.getBrand().equals("Carvin"));
        assertTrue(c.getCabName().equals("Legacy"));
        assertTrue(c.getSize().equals("4x12"));

        cabList = dao.searchCabs(null,null,null,null,"4x12");
        assertTrue(cabList.size() == 19);

        cabList = dao.searchCabs(null,null,null,"Marshall","4x12");
        assertTrue(cabList.size() == 6);

        cabList = dao.searchCabs("fftf",null,null,"end");
        assertTrue(cabList.size() == 6);

        cabList = dao.searchCabs("tttt",null,null,null, "12");
        assertTrue(cabList.size() == 34);

        assertTrue(dao.searchCabs(null,"16").size() == 1);
        assertTrue(dao.searchCabs(null,"150").size() == 0);

    }

    @Test
    public void updateCabs() {

        try {
            Cab c = dao.searchCabs(null, "1").get(0);
            c.setCabName("Alchemist");
            dao.updateCab(c);
            Cab cnew = dao.searchCabs(null, "1").get(0);
            assertTrue(cnew.equals(c));
        } catch (SQLUpdateException e) {

        }

        try {
            Cab c = dao.searchCabs(null, "1").get(0);
            c.setCabName(null);
            dao.updateCab(c);
            fail("Null entry");
        } catch (SQLUpdateException e) {

        }

        try {
            Cab c = dao.searchCabs(null, "2").get(0);
            Cab c2 = new Cab();

            c2.setCabName(c.getCabName());
            c2.setBrand(c.getBrand());
            c2.setSize(c.getSize());
            c2.setCabID("1");

            assertTrue(dao.updateCab(c));

            dao.updateCab(c2);
            fail("Duplicate");
        } catch (SQLUpdateException e) {

        }

        try {
            Cab c = dao.searchCabs(null, "1").get(0);
            c.setCabID(null);
            c.setCabName("Forever");

            dao.updateCab(c);
            fail("Doesn't exist");
        } catch (SQLUpdateException e) {

        }

        try {
            Cab c = dao.searchCabs(null, "1").get(0);
            c.setCabID("100");
            c.setCabName("Forever");

            dao.updateCab(c);
            fail("Doesn't exist");
        } catch (SQLUpdateException e) {

        }

    }

    @Test
    public void removeCabs() {
        Cab c = new Cab();
        c.setSize("4x12");
        c.setBrand("Marshall");
        c.setCabName("Monster");

        try {
            c = dao.addCab(c);
            dao.removeCab(c.getCabID());
            assertTrue(dao.searchCabs(null,c.getCabID()).size() == 0);
        } catch (Exception e){}

        try {
            dao.removeCab("1");
            fail("Constraint Violation");
        } catch (SQLUpdateException e){

        }

    }

    @Test
    public void addCabs() {

        Cab c = new Cab();
        c.setSize("4x12");
        c.setBrand("Marshall");
        c.setCabName("Monster");

        try {
            c = dao.addCab(c);
        } catch (SQLUpdateException | DuplicateEntryException e){
            fail("New cab");
        }

        try {
            c = dao.addCab(c);
            fail("Not new cab");
        } catch (SQLUpdateException | DuplicateEntryException e){

        }

        Cab cnew = dao.searchCabs(null,c.getCabID()).get(0);
        assertTrue(cnew.equals(c));

        cnew = dao.searchCabs(null,"1").get(0);
        try {
            cnew = dao.addCab(cnew);
            fail("Exists");
        } catch (SQLUpdateException | DuplicateEntryException e){

        }

        cnew.setCabID(null);
        try {
            cnew = dao.addCab(cnew);
            fail("Exists");
        } catch (SQLUpdateException | DuplicateEntryException e){

        }

        cnew.setCabID(null);
        cnew.setSize("4x12");
        cnew.setBrand(null);
        cnew.setCabName("Monsterfied");
        try {
            cnew = dao.addCab(cnew);
            fail("Null entry in non null field");
        } catch (SQLUpdateException | DuplicateEntryException e){
            System.out.println(e.getMessage());

        }



    }



}
