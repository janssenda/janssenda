/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.floor13.advice;

import com.dm.floor13.dao.AuditDao;
import com.dm.floor13.exceptions.FileIOException;
import com.dm.floor13.model.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * @author danimaetrix
 */
@Aspect
public class LoggingAdvice {

    AuditDao auditDao;
    String currentOrder;

    public LoggingAdvice(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

//    @AfterThrowing(
//            pointcut = "execution(* com.dm.vendingmashine.logic.RealLogic.vendProduct(..))"
//            + "|| execution(* com.dm.vendingmashine.logic.RealLogic.shakeTheMachine(..))",
//            throwing = "ex")
//    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
//        //Object[] args = joinPoint.getArgs();
//        try {
//            auditDao.WriteAuditToFile(ex.getMessage());
//        } catch (FileIOException e) {
//            System.err.println(
//                    "ERROR: Could not create audit entry in LoggingAdvice.");
//        }
//    }
//
    @After("execution(* com.dm.floor13.dao.OrderDaoImpl.addUpdateOrder(..))")
    public void afterChange(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        try {
            auditDao.WriteAuditToFile("Order changed: " + args[0].toString());
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

    // We need to capture the current order before it is removed,
    // but we only want to log the information if the the order 
    // is succesfully removed.
    @After("execution(* com.dm.floor13.dao.OrderDaoImpl.removeOrder(..))")
    public void afterRemove(JoinPoint joinPoint) {
        try {
            auditDao.WriteAuditToFile("Order removed: " + joinPoint.getArgs()[0].toString());
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

}
