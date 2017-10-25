package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.PowerDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Power;
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
public class PowerDaoImpl implements PowerDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public PowerDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_POWER_QUERY =
            "INSERT INTO superpowers (PowerName, Description) VALUES(?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM superpowers WHERE PowerID = ?";

    private static final String REMOVE_POWER_QUERY =
            "DELETE FROM superpowers WHERE PowerID = ?";

    private static final String GET_POWER_QUERY =
            "SELECT * FROM superpowers";

    private static final String GET_POWER_BYID_QUERY =
            "SELECT * FROM superpowers WHERE PowerID = ?";

    private static final String GET_POWER_QUERY_MULTI =
            "SELECT * FROM superpowers WHERE 1 = 1 " +
                    "AND (@PowerID IS NULL OR PowerID = @PowerID) " +
                    "AND (@PowerName IS NULL OR PowerName = @PowerName) " +
                    "AND (@Description IS NULL OR Description = @Description) ";

    private static final String UPDATE_POWER_QUERY =
            "UPDATE superpowers SET PowerName = ?, Description = ? WHERE PowerID = ?";

    @Override
    @Transactional
    public Power addPower(Power power) throws SQLUpdateException, DuplicateEntryException {

        if(ifExists(power.getPowerID())){
            throw new DuplicateEntryException("Power already exists, update instead");
        }

        try {
            if (jdbcTemplate.update(ADD_POWER_QUERY, power.getPowerName(), power.getDescription()) > 0) {
                power.setPowerID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
                return power;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removePower(int powerID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_POWER_QUERY, Integer.toString(powerID)) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updatePower(Power power) throws SQLUpdateException {

        try {
            if (jdbcTemplate.update(UPDATE_POWER_QUERY,
                    power.getPowerName(),
                    power.getDescription(),
                    power.getPowerID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Power> getAllPowers() {
        return jdbcTemplate.query(GET_POWER_QUERY, new PowerMapper());
    }

    @Override
    public List<Power> getPowerByID(int powerID) {
        return jdbcTemplate.query(GET_POWER_BYID_QUERY, new PowerMapper(), powerID);
    }

    @Override
    public List<Power> getFromPowers(String... args) {
        String[] allArgs = {null, null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);
        return getFromPowers(allArgs[0], allArgs[1], allArgs[2]);
    }

    @Override

    public List<Power> getFromPowers(String powerID, String powerName, String description) {

        if (powerID == null || powerID.isEmpty()) { powerID = null;}
        if (powerName == null || powerName.isEmpty()) { powerName = null; }
        if (description == null || description.isEmpty()) {description = null;}

        String setup = "SET @PowerID = ?, @PowerName = ?, @Description = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, powerID);
            ps.setString(2, powerName);
            ps.setString(3, description);

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();
            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for powers based on the given criteria,
            return jdbcTemplate.query(GET_POWER_QUERY_MULTI, new PowerMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    private static final class PowerMapper implements RowMapper<Power> {

        public Power mapRow(ResultSet rs, int rowNum) throws SQLException {

            Power p = new Power();
            p.setPowerID(rs.getInt("PowerID"));
            p.setPowerName(rs.getString("PowerName"));
            p.setDescription(rs.getString("Description"));
            return p;
        }
    }

    @Override
    public boolean ifExists(int powerID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, Integer.toString(powerID)) > 0);
    }
}
