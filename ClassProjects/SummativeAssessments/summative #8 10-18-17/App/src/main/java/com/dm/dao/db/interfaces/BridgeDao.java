package com.dm.dao.db.interfaces;

import com.dm.exceptions.SQLUpdateException;
import org.springframework.dao.DataIntegrityViolationException;

public interface BridgeDao {


    public boolean addOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException ;
    public boolean removeOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException;
    public boolean addOrgsHeroes(int orgID, int heroID) throws SQLUpdateException;
    public boolean removeOrgsHeroes(int orgID, int heroID) throws SQLUpdateException;
    public boolean addSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException;
    public boolean removeSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException;
    public boolean addPowersHeroes(int powerID, int heroID) throws SQLUpdateException;
    public boolean removePowersHeroes(int powerID, int heroID) throws SQLUpdateException;

}
