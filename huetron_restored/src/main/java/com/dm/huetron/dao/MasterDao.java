package com.dm.huetron.dao;

import com.dm.huetron.exception.SQLUpdateException;
import com.dm.huetron.model.Light;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class MasterDao {
    private JdbcTemplate jdbcTemplate;
    private static final String ADD_LIGHT_QUERY = "INSERT INTO lights (Title, Description) VALUES(?,?)";
    private static final String GET_LIGHTS_QUERY = "SELECT * FROM lights";

    @Autowired
    public MasterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Light> getAllLights() {
        return this.jdbcTemplate.query(GET_LIGHTS_QUERY, new MasterDao.LightMapper());
    }

    @Transactional
    public Light addLight(Light light)
            throws SQLUpdateException {
        try {
            if (this.jdbcTemplate.update(ADD_LIGHT_QUERY, light.getTitle(), light.getDescription()) > 0) {
                light.setLightID(this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", String.class));
                return light;
            }
            throw new SQLUpdateException("There was a problem adding the data or row does not exist");
        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }


    private static final class LightMapper implements RowMapper<Light> {
        public Light mapRow(ResultSet rs, int rowNum) throws SQLException {
            Light l = new Light();
            l.setLightID(rs.getString("LightID"));
            l.setTitle(rs.getString("Title"));
            l.setDescription(rs.getString("Description"));
            return l;
        }
    }
    
    
}
