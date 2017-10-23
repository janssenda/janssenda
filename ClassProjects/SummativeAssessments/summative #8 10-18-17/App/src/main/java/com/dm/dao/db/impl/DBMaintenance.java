package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.HeroDao;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Hero;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

public class DBMaintenance {

        private JdbcTemplate jdbcTemplate;

        public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void refresh(){
            Connection c;
            try {
                c = jdbcTemplate.getDataSource().getConnection();
                CallableStatement refresh = c.prepareCall("{CALL refreshdata()}");
                refresh.execute();
            } catch (Exception e){
                // Success
            }

        }

}
