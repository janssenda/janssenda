package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.HeroDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Hero;
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
public class HeroDaoImpl implements HeroDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public HeroDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_HERO_QUERY =
            "INSERT INTO heroes (HeroName, HeroType, Description) VALUES(?,?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM heroes WHERE HeroID = ?";

    private static final String REMOVE_HERO_QUERY =
            "DELETE FROM heroes WHERE HeroID = ?";

    private static final String GET_HERO_QUERY =
            "SELECT * FROM heroes";

    private static final String GET_HERO_QUERY_MULTI =
            "SELECT * FROM heroes WHERE 1 = 1 " +
                    "AND (@HeroID IS NULL OR HeroID = @HeroID) " +
                    "AND (@HeroName IS NULL OR HeroName = @HeroName) " +
                    "AND (@HeroType IS NULL OR HeroType = @HeroType) " +
                    "AND (@Description IS NULL OR Description = @Description) ";

    private static final String UPDATE_HERO_QUERY =
            "UPDATE heroes SET HeroName = ?, HeroType = ?, Description = ? WHERE HeroID = ?";


    @Override
    @Transactional
    public Hero addHero(Hero hero) throws SQLUpdateException, DuplicateEntryException {

        if (ifExists(hero)){
            throw new DuplicateEntryException("This hero already exists");
        }

        try {
            if (jdbcTemplate.update(ADD_HERO_QUERY, hero.getHeroName(), hero.getHeroType(), hero.getDescription()) > 0) {

                hero.setHeroID(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
                return hero;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removeHero(int heroID) throws SQLUpdateException {
        try {
            if (jdbcTemplate.update(REMOVE_HERO_QUERY, Integer.toString(heroID)) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            }
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updateHero(Hero hero) throws SQLUpdateException{

//        List<Hero> hlist = getFromHeroes(null,hero.getHeroName(),hero.getHeroType());

//        for (Hero h : hlist){
//            if (h.getHeroID() != hero.getHeroID()){
//                throw new DuplicateEntryException("Update failed: hero exists");
//            }
//
//        }

        try {
            if (jdbcTemplate.update(UPDATE_HERO_QUERY,
                    hero.getHeroName(),
                    hero.getHeroType(),
                    hero.getDescription(),
                    hero.getHeroID()) <= 0) {
                throw new SQLUpdateException("Data could not be changed or row does not exist");
            } else return true;

        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        return jdbcTemplate.query(GET_HERO_QUERY, new HeroMapper());
    }

    @Override
    public List<Hero> getFromHeroes(String... args) {
        String[] allArgs = {null, null, null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);

        return getFromHeroes(allArgs[0], allArgs[1], allArgs[2], allArgs[3]);
    }

    @Override
    @Transactional
    public List<Hero> getFromHeroes(String heroID, String heroName, String heroType, String description) {

        if (heroID == null || heroID.isEmpty()) {
            heroID = null;
        }
        if (heroName == null || heroName.isEmpty()) {
            heroName = null;
        }
        if (heroType == null || heroType.isEmpty()) {
            heroType = null;
        }
        if (description == null || description.isEmpty()) {
            description = null;
        }

        String setup = "SET @HeroID = ?, @HeroName = ?, @HeroType = ?, @Description = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, heroID);
            ps.setString(2, heroName);
            ps.setString(3, heroType);
            ps.setString(4, description);

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();
            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for heroes based on the given criteria,
            return jdbcTemplate.query(GET_HERO_QUERY_MULTI, new HeroMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    private static final class HeroMapper implements RowMapper<Hero> {

        public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hero h = new Hero();

            h.setHeroID(rs.getInt("HeroID"));
            h.setHeroName(rs.getString("HeroName"));
            h.setHeroType(rs.getString("HeroType"));
            h.setDescription(rs.getString("Description"));

            return h;
        }
    }

    @Override
    public boolean ifExists(Hero hero) {
        String hID = Integer.toString(hero.getHeroID());
        return (getFromHeroes(hID).size() > 0 ||
                getFromHeroes(null, hero.getHeroName(), hero.getHeroType()).size() > 0);
    }

}