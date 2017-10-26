package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.BridgeDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
public class BridgeDaoImpl implements BridgeDao {

    private JdbcTemplate jdbcTemplate;


    @Inject
    public BridgeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Integer sizeOrgsHeadquarters(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orgsheadquarters", Integer.class);
    }

    @Override
    public Integer sizeOrgsHeroes(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orgsheroes", Integer.class);
    }

    @Override
    public Integer sizeSightingsHeroes(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sightingsheroes", Integer.class);
    }

    @Override
    public Integer sizePowersHeroes(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM superpowersheroes", Integer.class);
    }



    // Modify orgs - headquarters associations
    @Override
    public boolean addOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException {
        String addQuery = "INSERT INTO orgsheadquarters (OrgID, HeadQID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, orgID, headQID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public Integer removeOrgsHeadquarters(String orgID, String headQID) throws SQLUpdateException {
        String DELETE_OHQ_QUERY =
                "DELETE FROM orgsheadquarters WHERE 1 = 1 " +
                        "AND (@OrgID IS NULL OR OrgID = @OrgID) " +
                        "AND (@HeadQID IS NULL OR HeadQID = @HeadQID) ";

        if (orgID == null || orgID.isEmpty()) orgID = null;
        if (headQID == null || headQID.isEmpty()) headQID = null;

        String setup = "SET @OrgID = ?, @HeadQID = ?;";

        try {
            prepareSet(setup, orgID, headQID);
            return jdbcTemplate.update(DELETE_OHQ_QUERY);

        } catch (SQLException e) {
            return null;
        }
    }


    // Modify orgs - heroes associations
    @Override
    public boolean addOrgsHeroes(int orgID, int heroID) throws SQLUpdateException {
        String addQuery = "INSERT INTO orgsheroes (OrgID, HeroID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, orgID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public Integer removeOrgsHeroes(String orgID, String heroID) throws SQLUpdateException {
        String DELETE_OH_QUERY =
                "DELETE FROM orgsheroes WHERE 1 = 1 " +
                        "AND (@OrgID IS NULL OR OrgID = @OrgID) " +
                        "AND (@HeroID IS NULL OR HeroID = @HeroID) ";

        if (orgID == null || orgID.isEmpty()) orgID = null;
        if (heroID == null || heroID.isEmpty()) heroID = null;

        String setup = "SET @OrgID = ?, @HeroID = ?;";

        try {
            prepareSet(setup, orgID, heroID);
            return jdbcTemplate.update(DELETE_OH_QUERY);

        } catch (SQLException e) {
            return null;
        }

    }

    // Modify sightings - heroes associations
    @Override
    public boolean addSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException {
        String addQuery = "INSERT INTO sightingsheroes (SightingID, HeroID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, sightingID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public Integer removeSightingsHeroes(String sightingID, String heroID) throws SQLUpdateException {
        String DELETE_SH_QUERY =
                "DELETE FROM sightingsheroes WHERE 1 = 1 " +
                        "AND (@SightingID IS NULL OR SightingID = @SightingID) " +
                        "AND (@HeroID IS NULL OR HeroID = @HeroID) ";

        if (sightingID == null || sightingID.isEmpty()) sightingID = null;
        if (heroID == null || heroID.isEmpty()) heroID = null;

        String setup = "SET @SightingID = ?, @HeroID = ?;";

        try {
            prepareSet(setup, sightingID, heroID);
            return jdbcTemplate.update(DELETE_SH_QUERY);

        } catch (SQLException e) {
            return null;
        }
    }


    // Modify powers - heroes associations
    @Override
    public boolean addPowersHeroes(int powerID, int heroID) throws SQLUpdateException {
        String addQuery = "INSERT INTO superpowersheroes (PowerID, HeroID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, powerID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public Integer removePowersHeroes(String powerID, String heroID) throws SQLUpdateException {
        String DELETE_PH_QUERY =
                "DELETE FROM superpowersheroes WHERE 1 = 1 " +
                        "AND (@PowerID IS NULL OR PowerID = @PowerID) " +
                        "AND (@HeroID IS NULL OR HeroID = @HeroID) ";

        if (powerID == null || powerID.isEmpty()) powerID = null;
        if (heroID == null || heroID.isEmpty()) heroID = null;

        String setup = "SET @PowerID = ?, @HeroID = ?;";

        try {
            prepareSet(setup, powerID, heroID);
            return jdbcTemplate.update(DELETE_PH_QUERY);

        } catch (SQLException e) {
            return null;
        }
    }






    private void prepareSet(String setup, String param1, String param2) throws SQLException {

        Connection c = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement ps = c.prepareStatement(setup);

        ps.setString(1, param1);
        ps.setString(2, param2);

        String qdata = ps.toString().split(":")[1].trim();
        qdata = qdata.split("]")[0].trim();
        c.close();

        // Execute the prepared statement string to set the variables
        jdbcTemplate.execute(qdata);
    }
}