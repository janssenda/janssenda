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


    ////////////////////
    //  Pass-through  //
    ////////////////////
    public boolean removeHero(int heroID) throws SQLUpdateException;
    public boolean updateHero(Hero hero) throws SQLUpdateException, DuplicateEntryException ;
    public Hero addHero(Hero hero) throws SQLUpdateException, DuplicateEntryException;

    public Contact addContact(Contact contact) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeContact(Contact contact) throws SQLUpdateException;
    public boolean updateContact(Contact oldContact, Contact newContact) throws SQLUpdateException,DuplicateEntryException;

    public Headquarters addHeadquarters(Headquarters headq) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeHeadquarters(int headqID) throws SQLUpdateException;
    public boolean updateHeadquarters(Headquarters headq) throws SQLUpdateException;

    public Location addLocation(Location loc) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeLocation(int locID) throws SQLUpdateException;
    public boolean updateLocation(Location loc) throws SQLUpdateException;

    public Organization addOrg(Organization org) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeOrg(int orgID) throws SQLUpdateException;
    public boolean updateOrg(Organization org) throws SQLUpdateException ;

    public Power addPower(Power power) throws SQLUpdateException,DuplicateEntryException;
    public boolean removePower(int powerID) throws SQLUpdateException;
    public boolean updatePower(Power power) throws SQLUpdateException;

    public Sighting addSighting(Sighting sighting) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeSighting(int sightingID) throws SQLUpdateException;
    public boolean updateSighting(Sighting sighting) throws SQLUpdateException;

    // Bridge maps
    public boolean addOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException;
    public Integer removeOrgsHeadquarters(String orgID, String headQID) throws SQLUpdateException;
    public boolean addOrgsHeroes(int orgID, int heroID) throws SQLUpdateException;
    public Integer  removeOrgsHeroes(String orgID, String heroID) throws SQLUpdateException;
    public boolean addSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException;
    public Integer  removeSightingsHeroes(String sightingID, String heroID) throws SQLUpdateException;
    public boolean addPowersHeroes(int powerID, int heroID) throws SQLUpdateException;
    public Integer  removePowersHeroes(String powerID, String heroID) throws SQLUpdateException;

}
