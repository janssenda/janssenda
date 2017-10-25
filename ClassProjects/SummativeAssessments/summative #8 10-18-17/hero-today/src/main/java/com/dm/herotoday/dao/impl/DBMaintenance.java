package com.dm.herotoday.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.CallableStatement;
import java.sql.Connection;

@Named
public class DBMaintenance {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public DBMaintenance(JdbcTemplate jdbcTemplate){
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
