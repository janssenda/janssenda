package com.dm.dao.db.interfaces;

import com.dm.dao.db.impl.PowerDaoImpl;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Power;

import java.util.List;

public interface PowerDao {

    public Power addPower (Power power) throws SQLUpdateException;
    public boolean removePower(int powerID) throws SQLUpdateException;
    public boolean updatePower(Power power) throws SQLUpdateException;
    public List<Power> getAllPowers();
    public List<Power> getFromPowers(String... args);
    public List<Power> getFromPowers(String powerID, String powerName, String description);
    public boolean ifExists(int powerID);
    public List<Power> getPowerByID(int powerID);
}
