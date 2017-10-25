package com.dm.herotoday.service;

import com.dm.herotoday.model.*;

import java.time.LocalDate;
import java.util.List;

public interface Dbservice {

    public List<Hero> getFromHeroes(String... args);
    public List<Organization> getFromOrgs(String... args);
    public List<Headquarters> getFromHeadquarters(String... args);
    public List<Sighting> getFromSightings(String... args);
    public List<Location> getFromLocations(String... args);
    public List<Hero> heroesByLocation(int locID);
    public List<Location> locationsByHero(int heroID);
    public List<Sighting> sightingsByDate(LocalDate d);



}
