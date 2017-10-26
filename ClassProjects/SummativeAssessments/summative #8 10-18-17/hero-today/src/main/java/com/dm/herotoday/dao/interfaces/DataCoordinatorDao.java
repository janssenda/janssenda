package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.*;

import java.time.LocalDate;
import java.util.List;

public interface DataCoordinatorDao {

    public List<Hero> getFromHeroes(String... args);
    public List<Organization> getFromOrgs(String... args);
    public List<Headquarters> getFromHeadquarters(String... args);
    public List<Sighting> getFromSightings(String... args);
    public List<Location> getFromLocations(String... args);
    public List<Hero> heroesByLocation(int locID);
    public List<Location> locationsByHero(int heroID);
    public List<Sighting> sightingsByDate(LocalDate d);
    public List<Power> getFromPowers(String... args);


    ////////////////////
    //  Pass-through  //
    ////////////////////
    public boolean removeHero(int heroID) throws SQLUpdateException;
    public Hero changeHero(Hero hero) throws SQLUpdateException, DuplicateEntryException;

    public Contact changeContact(Contact... args) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeContact(Contact contact) throws SQLUpdateException;

    public Headquarters changeHeadquarters(Headquarters headq) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeHeadquarters(int headqID) throws SQLUpdateException;

    public Location changeLocation(Location loc) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeLocation(int locID) throws SQLUpdateException;

    public Organization changeOrg(Organization org) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeOrg(int orgID) throws SQLUpdateException;

    public Power changePower(Power power) throws SQLUpdateException,DuplicateEntryException;
    public boolean removePower(int powerID) throws SQLUpdateException;

    public Sighting changeSighting(Sighting sighting) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeSighting(int sightingID) throws SQLUpdateException;


}
