package com.dm.herotoday.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.exceptions.SQLUpdateException;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class RemoveController {

    private DataCoordinatorDao dao;

    @Inject
    public RemoveController(DataCoordinatorDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/heroes","/heroes/"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ExceptionHandler
    public boolean removeHero(HttpServletRequest req) throws SQLUpdateException{

        try {
            return dao.removeHero(Integer.parseInt(req.getParameter("id")));
        } catch (SQLUpdateException e){
            return false;
        }
    }

    @RequestMapping(value = {"/powers","/powers/"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ExceptionHandler
    public boolean removePower(HttpServletRequest req)throws SQLUpdateException {

        try {
            return dao.removePower(Integer.parseInt(req.getParameter("id")));
        } catch (SQLUpdateException e){
            return false;
        }
    }

    @RequestMapping(value = {"/orgs","/orgs/"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ExceptionHandler
    public boolean removeOrg(HttpServletRequest req) throws SQLUpdateException{

        try {
            return dao.removeOrg(Integer.parseInt(req.getParameter("id")));
        } catch (SQLUpdateException e){
            return false;
        }
    }

    @RequestMapping(value = {"/headquarters","/headquarters/"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ExceptionHandler
    public boolean removeHeadquarters(HttpServletRequest req)throws SQLUpdateException {

        try {
            return dao.removeHeadquarters(Integer.parseInt(req.getParameter("id")));
        } catch (SQLUpdateException e){
            return false;
        }
    }

    @RequestMapping(value = {"/locations","/locations/"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ExceptionHandler
    public boolean removeLocation(HttpServletRequest req)throws SQLUpdateException {

        try {
            return dao.removeLocation(Integer.parseInt(req.getParameter("id")));
        } catch (SQLUpdateException e){
            return false;
        }
    }

    @RequestMapping(value = {"/sightings","/sightings/"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ExceptionHandler
    public boolean removeSighting(HttpServletRequest req) throws SQLUpdateException{

        try {
            return dao.removeSighting(Integer.parseInt(req.getParameter("id")));
        } catch (SQLUpdateException e){
            return false;
        }
    }

}
