package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.SightingDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Sighting;
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
public class SightingDaoImpl implements SightingDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public SightingDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_SIGHTING_QUERY =
            "INSERT INTO sightings (SightTime, LocID) VALUES(?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM sightings WHERE SightingID = ?";

    private static final String REMOVE_SIGHTING_QUERY =
            "DELETE FROM sightings WHERE SightingID = ?";

    private static final String GET_SIGHTING_QUERY =
            "SELECT * FROM sightings";

    private static final String GET_SIGHTING_QUERY_MULTI =
            "SELECT * FROM sightings WHERE 1 = 1 " +
                    "AND (@SightingID IS NULL OR SightingID = @SightingID) " +
                    "AND (@SightTime IS NULL OR SightTime LIKE @SightTime) " +
                    "AND (@LocID IS NULL OR LocID = @LocID) ";

    private static final String UPDATE_SIGHTING_QUERY =
            "UPDATE sightings SET SightTime = ?, LocID = ? WHERE SightingID = ?";

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) throws SQLUpdateException {

        try {
            if (jdbcTemplate.update(ADD_SIGHTING_QUERY, sighting.getSightingTime(), sighting.getLocID()) > 0) {
                sighting.setSightingID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
                return sighting;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removeSighting(int sightingID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_SIGHTING_QUERY, Integer.toString(sightingID)) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updateSighting(Sighting sighting) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(UPDATE_SIGHTING_QUERY,
                    sighting.getSightingTime(),
                    sighting.getLocID(),
                    sighting.getSightingID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        return jdbcTemplate.query(GET_SIGHTING_QUERY, new SightMapper());
    }

    @Override
    public List<Sighting> getFromSightings(String... args) {
        String[] allArgs = {null, null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);
        return getFromSightings(allArgs[0], allArgs[1], allArgs[2]);
    }

    @Override
    @Transactional
    public List<Sighting> getFromSightings(String sightingID, String sightTime, String locID) {

        if (sightingID == null || sightingID.isEmpty()) { sightingID = null;}
        if (sightTime == null || sightTime.isEmpty()) {
            sightTime = null;
        } else sightTime = "%"+sightTime+"%";
        if (locID == null || locID.isEmpty()) {locID = null;}

        String setup = "SET @SightingID = ?, @SightTime = ?, @LocID = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, sightingID);
            ps.setString(2, sightTime);
            ps.setString(3, locID);

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();
            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for sightings based on the given criteria,
            return jdbcTemplate.query(GET_SIGHTING_QUERY_MULTI, new SightMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean ifExists(int sightingID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, Integer.toString(sightingID)) > 0);
    }

    private static final class SightMapper implements RowMapper<Sighting> {

        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting s = new Sighting();
            s.setSightingID(rs.getInt("SightingID"));
            s.setSightingTime(rs.getTimestamp("SightTime").toLocalDateTime());
            s.setLocID(rs.getInt("LocID"));
            return s;
        }
    }


}
