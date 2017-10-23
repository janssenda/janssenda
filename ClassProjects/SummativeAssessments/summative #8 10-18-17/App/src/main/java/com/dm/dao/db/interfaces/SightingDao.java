package com.dm.dao.db.interfaces;

import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Sighting;
import java.util.List;

public interface SightingDao {

    public Sighting addSighting(Sighting sighting) throws SQLUpdateException;
    public boolean removeSighting(int sightingID) throws SQLUpdateException;
    public boolean updateSighting(Sighting sighting) throws SQLUpdateException;
    public List<Sighting> getAllSightings();
    public List<Sighting> getFromSightings(String... args);
    public List<Sighting> getFromSightings(String sightingID, String sightTime, String locID);
    public boolean ifExists(int sightingID);
}
