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
public class CoordinatorOrganizationEndpointTest {

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

        Organization o = new Organization();

        o.setOrgName("Carag");
        o.setDescription("The one and only");

        Hero h = new Hero();

        h.setHeroName("Danimaetrix");
        h.setHeroType("Default");
        h.setDescription("The one and only");

        Headquarters hq = new Headquarters();

        hq.setHeadQName("Kloudland");
        hq.setHeadQAdress("Nowhere");

        List<Hero> heroList = new ArrayList<>();
        List<Headquarters> headqList = new ArrayList<>();

        heroList.add(h);
        heroList.add(dao.getFromHeroes("5").get(0));

        headqList.add(hq);

        o.setMembers(heroList);
        o.setOrgHeadQ(headqList);

        int hlist = dao.getFromHeroes().size();
        int hqlist = dao.getFromHeadquarters().size();

        int bridgeh = bdao.sizeOrgsHeroes();
        int bridgeq = bdao.sizeOrgsHeadquarters();

        Organization onew = dao.changeOrg(o);

        assertTrue(onew.getMembers().size() == 2);
        assertTrue(dao.getFromHeroes().size() == hlist + 1);
        assertTrue(bdao.sizeOrgsHeroes() == bridgeh + 2);

        assertTrue(onew.getOrgHeadQ().size() == 1);
        assertTrue(dao.getFromHeadquarters().size() == hqlist + 1);
        assertTrue(bdao.sizeOrgsHeadquarters() == bridgeq + 1);

        onew.getOrgHeadQ().add(dao.getFromHeadquarters("3").get(0));
        dao.changeOrg(onew);

        assertTrue(onew.getOrgHeadQ().size() == 2);
        assertTrue(dao.getFromHeadquarters().size() == hqlist + 1);
        assertTrue(bdao.sizeOrgsHeadquarters() == bridgeq + 2);

        onew.setOrgHeadQ(null);
        onew.setMembers(null);

        onew = dao.changeOrg(onew);

        assertTrue(onew.getMembers().size() == 0);
        assertTrue(dao.getFromHeroes().size() == hlist + 1);
        assertTrue(bdao.sizeOrgsHeroes() == bridgeh);

        assertTrue(onew.getOrgHeadQ().size() == 0);
        assertTrue(dao.getFromHeadquarters().size() == hqlist + 1);
        assertTrue(bdao.sizeOrgsHeadquarters() == bridgeq);

        dao.removeOrg(onew.getOrgID());
        assertTrue(dao.getFromOrgs(Integer.toString(onew.getOrgID())).size() == 0);

    }



}
