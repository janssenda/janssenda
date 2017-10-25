package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Headquarters;

import java.util.List;

public interface HeadquartersDao {


    public Headquarters addHeadquarters(Headquarters headq) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeHeadquarters(int headqID) throws SQLUpdateException;
    public boolean updateHeadquarters(Headquarters headq) throws SQLUpdateException;
    public List<Headquarters> getAllHeadquarterss();
    public List<Headquarters> getFromHeadquarters(String... args);
    public List<Headquarters> getFromHeadquarters(String headqID, String headqName, String headqAddress, String description);
    public boolean ifExists(int headqID);

}
