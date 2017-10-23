package com.dm.dao.db.interfaces;

import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Location;
import java.util.List;

public interface LocationDao {

    public Location addLocation(Location loc) throws SQLUpdateException;
    public boolean removeLocation(int locID) throws SQLUpdateException;
    public boolean updateLocation(Location loc) throws SQLUpdateException;
    public List<Location> getAllLocations();
    public List<Location> getFromLocations(String... args);
    public List<Location> getFromLocations(String locID, String locName,
                                           String country, String city, String state, String address,
                                           String zip, String lat, String lon, String description);

    public boolean ifExists(int locID);
}
