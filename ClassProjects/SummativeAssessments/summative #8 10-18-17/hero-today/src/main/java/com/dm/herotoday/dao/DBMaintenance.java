package com.dm.herotoday.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.CallableStatement;
import java.sql.Connection;

@Named
public class DBMaintenance {

        @Inject
        private JdbcTemplate jdbcTemplate;
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
