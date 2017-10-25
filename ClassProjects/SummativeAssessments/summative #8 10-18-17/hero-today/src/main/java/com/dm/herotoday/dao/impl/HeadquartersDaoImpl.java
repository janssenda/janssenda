package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.HeadquartersDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Headquarters;
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
public class HeadquartersDaoImpl  implements HeadquartersDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public HeadquartersDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_HEADQ_QUERY =
            "INSERT INTO headquarters (HeadQName, Address, Description) VALUES(?,?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM headquarters WHERE HeadQID = ?";

    private static final String REMOVE_HEADQ_QUERY =
            "DELETE FROM headquarters WHERE HeadQID = ?";

    private static final String GET_HEADQ_QUERY =
            "SELECT * FROM headquarters";

    private static final String GET_HEADQ_QUERY_MULTI =
            "SELECT * FROM headquarters WHERE 1 = 1 " +
                    "AND (@HeadQID IS NULL OR HeadQID = @HeadQID) " +
                    "AND (@HeadQName IS NULL OR HeadQName = @HeadQName) " +
                    "AND (@Address IS NULL OR Address = @Address) " +
                    "AND (@Description IS NULL OR Description = @Description) ";

    private static final String UPDATE_HEADQ_QUERY =
            "UPDATE headquarters SET HeadQName = ?, Address = ?, Description = ? WHERE HeadQID = ?";

    @Override
    @Transactional
    public Headquarters addHeadquarters(Headquarters headq) throws SQLUpdateException {

        try {
            if (jdbcTemplate.update(ADD_HEADQ_QUERY, headq.getHeadQName(), headq.getHeadQAdress(), headq.getDescription()) > 0) {
                headq.setHeadQID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
                return headq;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removeHeadquarters(int headqID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_HEADQ_QUERY, Integer.toString(headqID)) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updateHeadquarters(Headquarters headq) throws SQLUpdateException {

        try {
            if (jdbcTemplate.update(UPDATE_HEADQ_QUERY,
                    headq.getHeadQName(),
                    headq.getHeadQAdress(),
                    headq.getDescription(),
                    headq.getHeadQID()) <= 0){
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Headquarters> getAllHeadquarterss() {
        return jdbcTemplate.query(GET_HEADQ_QUERY, new HeadquartersMapper());
    }

    @Override
    public List<Headquarters> getFromHeadquarters(String... args) {
        String[] allArgs = {null, null, null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);
        return getFromHeadquarters(allArgs[0], allArgs[1], allArgs[2], allArgs[3]);
    }

    @Override
    @Transactional
    public List<Headquarters> getFromHeadquarters(String headqID, String headqName, String headqAddress, String description) {

        if (headqID == null || headqID.isEmpty()) { headqID = null;}
        if (headqName == null || headqName.isEmpty()) { headqName = null; }
        if (headqAddress == null || headqAddress.isEmpty()) { headqAddress = null; }
        if (description == null || description.isEmpty()) {description = null;}

        String setup = "SET @HeadQID = ?, @HeadQName = ?, @Address = ?, @Description = ?;";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, headqID);
            ps.setString(2, headqName);
            ps.setString(3, headqAddress);
            ps.setString(4, description);

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();
            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for headqs based on the given criteria,
            return jdbcTemplate.query(GET_HEADQ_QUERY_MULTI, new HeadquartersMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    private static final class HeadquartersMapper implements RowMapper<Headquarters> {

        public Headquarters mapRow(ResultSet rs, int rowNum) throws SQLException {

            Headquarters h = new Headquarters();
            h.setHeadQID(rs.getInt("HeadQID"));
            h.setHeadQName(rs.getString("HeadQName"));
            h.setHeadQAdress(rs.getString("Address"));
            h.setDescription(rs.getString("Description"));
            return h;
        }
    }

    @Override
    public boolean ifExists(int headqID) {
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, Integer.toString(headqID)) > 0);
    }

}
