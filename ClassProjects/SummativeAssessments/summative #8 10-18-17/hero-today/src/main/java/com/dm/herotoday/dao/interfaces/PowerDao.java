package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Power;

import java.util.List;

public interface PowerDao {

    public Power addPower(Power power) throws SQLUpdateException, DuplicateEntryException;
    public boolean removePower(int powerID) throws SQLUpdateException;
    public boolean updatePower(Power power) throws SQLUpdateException;
    public List<Power> getAllPowers();
    public List<Power> getFromPowers(String... args);
    public List<Power> getFromPowers(String powerID, String powerName, String description);
    public boolean ifExists(int powerID);
    public List<Power> getPowerByID(int powerID);
}
