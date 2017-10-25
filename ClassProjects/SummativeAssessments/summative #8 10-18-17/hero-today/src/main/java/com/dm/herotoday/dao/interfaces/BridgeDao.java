package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.SQLUpdateException;

public interface BridgeDao {


    public boolean addOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException ;
    public Integer removeOrgsHeadquarters(String orgID, String headQID) throws SQLUpdateException;
    public boolean addOrgsHeroes(int orgID, int heroID) throws SQLUpdateException;
    public Integer  removeOrgsHeroes(String orgID, String heroID) throws SQLUpdateException;
    public boolean addSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException;
    public Integer  removeSightingsHeroes(String sightingID, String heroID) throws SQLUpdateException;
    public boolean addPowersHeroes(int powerID, int heroID) throws SQLUpdateException;
    public Integer  removePowersHeroes(String powerID, String heroID) throws SQLUpdateException;
    public Integer removeFromContacts(String headQID);
    public Integer removeFromSightings(String locID);

}
