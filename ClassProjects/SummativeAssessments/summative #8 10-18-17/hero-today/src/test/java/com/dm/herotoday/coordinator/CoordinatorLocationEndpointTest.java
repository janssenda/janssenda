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
public class CoordinatorLocationEndpointTest {

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

        Location loc = new Location();

        loc.setLocName("Carag");
        loc.setDescription("The one and only");

        Location lnew = dao.changeLocation(loc);
        assertTrue(lnew.getLocSightings().size() == 0);

        loc = dao.getFromLocations(Integer.toString(lnew.getLocID())).get(0);

        loc.setLocName("Carag Sea");
        dao.changeLocation(loc);

        assertTrue(loc.equals(dao.getFromLocations(Integer.toString(lnew.getLocID())).get(0)));
        dao.removeLocation(loc.getLocID());
        assertTrue(dao.getFromLocations(Integer.toString(loc.getLocID())).size() == 0);

    }



}
