package com.dm.impulsereactor.dao;

import com.dm.impulsereactor.model.Cab;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Named
public class QueryRunner {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public QueryRunner(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    private String[] getSearchParams(String table, String fields [], String flags, String... args) {
        int nparams = fields.length;
        char [] inflags = null;
        char [] flagArr = new char[nparams];
        String[] allArgs = new String[nparams];
        String SETUP = "SET ";
        String QUERY = "SELECT * FROM " + table + " WHERE 1 = 1 ";
        if (flags != null){inflags = flags.toCharArray();}

        for (int i = 0; i < nparams; i++) {

            if (inflags != null){
                if (i < inflags.length){
                    if ((inflags != null & inflags[i] == 'f') || (inflags != null & inflags[i] == 't')){
                        flagArr[i] = inflags[i];
                    } else flagArr[i] = 'f';
                } else flagArr[i] = 'f';
            } else flagArr[i] = 'f';

            if ( i >= args.length || args[i] == null || args[i].isEmpty()) {
                allArgs[i] = null;
            } else {
                allArgs[i] = args[i];
            }

            SETUP += "@" + fields[i] + " = ";

            if (allArgs[i] != null) {
                SETUP += "'";
            }

            if (flagArr[i] == 't'){
                SETUP += searchify(allArgs[i]);
            } else SETUP += allArgs[i];

            if (allArgs[i] != null) {
                SETUP += "'";
            }

            if (i == nparams - 1) { SETUP += ";";
            } else { SETUP += ", "; }

            QUERY += "AND (@"+fields[i]+" IS NULL OR "+fields[i];

            if (flagArr[i] == 't') {
                QUERY += " LIKE ";
            } else {
                QUERY += " = ";
            }
            QUERY += "@" + fields[i] + ") ";
        }
        return new String[]{SETUP, QUERY};
    }


    @Transactional
    public List<Object> searchDatabase(RowMapper rowMapper, String table, String fields [], String flags, String... args) {
        String[] params = getSearchParams(table, fields, flags, args);

        try {
            jdbcTemplate.execute(params[0]);
            return jdbcTemplate.query(params[1], rowMapper);
        } catch (Exception e) { return null; }

    }

    public void resetDatabase(){
        Connection c;
        try {
            c = jdbcTemplate.getDataSource().getConnection();
            CallableStatement refresh = c.prepareCall("{CALL resetdata()}");
            refresh.execute();
        } catch (Exception e){
            // Success
        }
    }

    private static String searchify(String s) {
        if (s == null || s.isEmpty()) return null;
        else return "%" + s + "%";
    }



}
