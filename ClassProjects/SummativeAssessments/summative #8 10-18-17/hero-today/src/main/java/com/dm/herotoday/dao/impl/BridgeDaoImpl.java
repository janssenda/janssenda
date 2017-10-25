package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.BridgeDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BridgeDaoImpl implements BridgeDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public BridgeDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // Modify orgs - headquarters associations
    @Override
    public boolean addOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException{
        String addQuery = "INSERT INTO orgsheadquarters (OrgID, HeadQID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, orgID, headQID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }
    @Override
    public boolean removeOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException{
        String removeQuery = "DELETE FROM orgsheadquarters WHERE OrgID = ? AND HeadQID = ?";
        try {
            return (jdbcTemplate.update(removeQuery, orgID, headQID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }



    // Modify orgs - heroes associations
    @Override
    public boolean addOrgsHeroes(int orgID, int heroID) throws SQLUpdateException{
        String addQuery = "INSERT INTO orgsheroes (OrgID, HeroID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, orgID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }
    @Override
    public boolean removeOrgsHeroes(int orgID, int heroID) throws SQLUpdateException{
        String removeQuery = "DELETE FROM orgsheroes WHERE OrgID = ? AND HeroID = ?";
        try {
            return (jdbcTemplate.update(removeQuery, orgID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    // Modify sightings - heroes associations
    @Override
    public boolean addSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException{
        String addQuery = "INSERT INTO sightingsheroes (SightingID, HeroID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, sightingID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }
    @Override
    public boolean removeSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException{
        String removeQuery = "DELETE FROM sightingsheroes WHERE SightingID = ? AND HeroID = ?";
        try {
            return (jdbcTemplate.update(removeQuery, sightingID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }    }




    // Modify powers - heroes associations
    @Override
    public boolean addPowersHeroes(int powerID, int heroID) throws SQLUpdateException{
        String addQuery = "INSERT INTO superpowersheroes (PowerID, HeroID) VALUES(?,?)";
        try {
            return (jdbcTemplate.update(addQuery, powerID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }
    @Override
    public boolean removePowersHeroes(int powerID, int heroID) throws SQLUpdateException{
        String removeQuery = "DELETE FROM superpowersheroes WHERE PowerID = ? AND HeroID = ?";
        try {
            return (jdbcTemplate.update(removeQuery, powerID, heroID) > 0);
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }


}
