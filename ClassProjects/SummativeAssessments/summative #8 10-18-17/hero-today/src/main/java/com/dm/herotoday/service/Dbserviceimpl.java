package com.dm.herotoday.service;

import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.model.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.List;

@Named
public class Dbserviceimpl implements Dbservice {

    private DataCoordinatorDao dao;

    @Inject
    public Dbserviceimpl(DataCoordinatorDao dao) {
        this.dao = dao;
    }


    @Override public List<Hero> getFromHeroes(String... args) {return dao.getFromHeroes();    }
    @Override public List<Organization> getFromOrgs(String... args) { return dao.getFromOrgs();  }
    @Override public List<Headquarters> getFromHeadquarters(String... args) { return dao.getFromHeadquarters(); }
    @Override public List<Sighting> getFromSightings(String... args) { return dao.getFromSightings(); }
    @Override public List<Location> getFromLocations(String... args) { return dao.getFromLocations(); }
    @Override public List<Hero> heroesByLocation(int locID) { return dao.heroesByLocation(locID);  }
    @Override public List<Location> locationsByHero(int heroID) { return dao.locationsByHero(heroID); }
    @Override public List<Sighting> sightingsByDate(LocalDate d) { return dao.sightingsByDate(d); }
}
