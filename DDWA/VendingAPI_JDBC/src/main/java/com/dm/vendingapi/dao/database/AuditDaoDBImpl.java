package com.dm.vendingapi.dao.database;

import com.dm.vendingapi.dao.AuditDao;
import com.dm.vendingapi.dao.FileHandler;
import com.dm.vendingapi.dao.FileIOException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditDaoDBImpl implements AuditDao{

    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void WriteAuditToFile(String entry) throws FileIOException {

        LocalDateTime index = LocalDateTime.now();
        String logString =
                index.format(DateTimeFormatter
                        .ofPattern("MM/dd/yy @ hh:mm a - ")) + entry;


        jdbcTemplate.update("INSERT INTO serverlog (Entry) VALUES (\'"+logString+"\')");

//        FileHandler auditFile = new FileHandler("AuditLog.txt");
//        auditFile.AuditLogToFile(logString, true);
    }
}
