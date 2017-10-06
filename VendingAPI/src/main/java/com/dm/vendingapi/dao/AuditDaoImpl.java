/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author danimaetrix
 */
public class AuditDaoImpl implements AuditDao {
    
    @Override
    public void WriteAuditToFile(String entry) throws FileIOException {
        LocalDateTime index = LocalDateTime.now();        

        
        String logString = 
                index.format(DateTimeFormatter
                        .ofPattern("MM/dd/yy @ hh:mm a - "));
        
        FileHandler auditFile = new FileHandler("AuditLog.txt");        
        
        
        auditFile.AuditLogToFile(logString + entry, true);
    
    }
    
    
}
