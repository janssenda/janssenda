package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.LocationDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Location;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Named
public class LocationDaoImpl implements LocationDao {


    private JdbcTemplate jdbcTemplate;

    @Inject
    public LocationDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_LOC_QUERY =
            "INSERT INTO locations (LocName, Country, City, State, StAddress, Zipcode, " +
                    "Latitude, Longitude, Description) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM locations WHERE LocID = ?";

    private static final String REMOVE_LOC_QUERY =
            "DELETE FROM locations WHERE LocID = ?";

    private static final String GET_LOC_QUERY =
            "SELECT * FROM locations";

    private static final String GET_LOC_QUERY_MULTI =
            "SELECT * FROM locations WHERE 1 = 1 " +
                    "AND (@LocID IS NULL OR LocID = @LocID) " +
                    "AND (@LocName IS NULL OR LocName = @LocName) " +
                    "AND (@Country IS NULL OR Country = @Country) " +
                    "AND (@City IS NULL OR City = @City) " +
                    "AND (@State IS NULL OR State = @State) " +
                    "AND (@StAddress IS NULL OR StAddress = @StAddress) " +
                    "AND (@Zipcode IS NULL OR Zipcode = @Zipcode) " +
                    "AND (@Latitude IS NULL OR Latitude = @Latitude) " +
                    "AND (@Longitude IS NULL OR Longitude = @Longitude) " +
                    "AND (@Description IS NULL OR Description = @Description) ";

    private static final String UPDATE_LOC_QUERY =
            "UPDATE locations SET " +
                    "LocName = ?, Country = ?, City = ?, State = ?, StAddress = ?, " +
                    "Zipcode = ?, Latitude = ?, Longitude = ?, Description = ? " +
                    "WHERE LocID = ?";

    @Override
    @Transactional
    public Location addLocation(Location loc) throws SQLUpdateException, DuplicateEntryException {

        if (ifExists(loc.getLocID())){
            throw new DuplicateEntryException("Location exists, please update.");
        }

        try {
            if (jdbcTemplate.update(ADD_LOC_QUERY,

                    loc.getLocName(),
                    loc.getCountry(),
                    loc.getCity(),
                    loc.getState(),
                    loc.getAddress(),
                    loc.getZip(),
                    loc.getLatitude(),
                    loc.getLongitude(),
                    loc.getDescription()) > 0) {

                loc.setLocID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
                return loc;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removeLocation(int locID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_LOC_QUERY, Integer.toString(locID)) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updateLocation(Location loc) throws SQLUpdateException {

        try {
            if (jdbcTemplate.update(UPDATE_LOC_QUERY,
                    loc.getLocName(),
                    loc.getCountry(),
                    loc.getCity(),
                    loc.getState(),
                    loc.getAddress(),
                    loc.getZip(),
                    loc.getLatitude(),
                    loc.getLongitude(),
                    loc.getDescription(),
                    loc.getLocID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(GET_LOC_QUERY, new LocMapper());
    }

    @Override
    public List<Location> getFromLocations(String... args) {
        String[] allArgs = {null, null, null, null, null, null, null, null, null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);
        return getFromLocations(allArgs[0], allArgs[1], allArgs[2], allArgs[3],
                allArgs[4], allArgs[5], allArgs[6], allArgs[7], allArgs[8], allArgs[9]);
    }

    @Override
    @Transactional
    public List<Location> getFromLocations(String locID, String locName,
               String country, String city, String state, String address,
               String zip, String lat, String lon, String description) {

        if (locID == null || locID.isEmpty()) { locID = null;}
        if (locName == null || locName.isEmpty()) { locName = null; }
        if (country == null || country.isEmpty()) { country = null; }
        if (city == null || city.isEmpty()) { city = null; }
        if (state == null || state.isEmpty()) { state = null; }
        if (address == null || address.isEmpty()) { address = null; }
        if (zip == null || zip.isEmpty()) { zip = null; }
        if (lat == null || lat.isEmpty()) { lat = null; }
        if (lon == null || lon.isEmpty()) { lon = null; }
        if (description == null || description.isEmpty()) {description = null;}

        String setup = "SET @LocID = ?, @LocName = ?, @Country = ?, @City = ?, @State = ?, " +
                "@StAddress = ?, @Zipcode = ?, @Latitude = ?, @Longitude = ?, @Description = ? ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, locID);
            ps.setString(2, locName);
            ps.setString(3, country);
            ps.setString(4, city);
            ps.setString(5, state);
            ps.setString(6, address);
            ps.setString(7, zip);
            ps.setString(8, lat);
            ps.setString(9, lon);
            ps.setString(10, description);

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();
            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for locs based on the given criteria,
            return jdbcTemplate.query(GET_LOC_QUERY_MULTI, new LocMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    private static final class LocMapper implements RowMapper<Location> {

        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {

            Location loc = new Location();

            loc.setLocID(rs.getInt("LocID"));
            loc.setLocName(rs.getString("LocName"));
            loc.setCountry(rs.getString("Country"));
            loc.setCity(rs.getString("City"));
            loc.setState(rs.getString("State"));
            loc.setAddress(rs.getString("StAddress"));
            loc.setZip(rs.getString("Zipcode"));
            loc.setLatitude(rs.getDouble("Latitude"));
            loc.setLongitude(rs.getDouble("Longitude"));
            loc.setDescription(rs.getString("Description"));

            return loc;
        }
    }

    @Override
    public boolean ifExists(int locID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, Integer.toString(locID)) > 0);
    }
}
