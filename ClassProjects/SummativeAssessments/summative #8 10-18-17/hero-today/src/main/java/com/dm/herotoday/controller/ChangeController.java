package com.dm.herotoday.controller;

import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;

@CrossOrigin
@RestController

public class ChangeController {

    private DataCoordinatorDao dao;

    @Inject
    public ChangeController(DataCoordinatorDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/heroes","/heroes/"}, method = RequestMethod.POST)
    @ResponseBody
    @ExceptionHandler
    public Hero changeHero(@RequestBody Hero hero) throws SQLUpdateException{

        try {

            return dao.changeHero(hero);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            throw new SQLUpdateException("Server error " +
                    "or hero already exists, please edit instead.");
        }
    }

    @RequestMapping(value = {"/powers","/powers/"}, method = RequestMethod.POST)
    @ResponseBody
    @ExceptionHandler
    public Power changePowers(@RequestBody Power power) throws SQLUpdateException {

        try {
            return dao.changePower(power);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            throw new SQLUpdateException("Server error " +
                    "or power already exists, please edit instead.");
        }
    }

    @RequestMapping(value = {"/orgs","/orgs/"}, method = RequestMethod.POST)
    @ResponseBody
    @ExceptionHandler
    public Organization changeOrgs(@RequestBody Organization org) throws SQLUpdateException{

        try {
            return dao.changeOrg(org);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            throw new SQLUpdateException("Server error " +
                    "or organization already exists, please edit instead.");
        }
    }

    @RequestMapping(value = {"/headquarters","/headquarters/"}, method = RequestMethod.POST)
    @ResponseBody
    @ExceptionHandler
    public Headquarters changeHeadquarters(@RequestBody Headquarters headQ) throws SQLUpdateException{

        try {
            return dao.changeHeadquarters(headQ);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            throw new SQLUpdateException("Server error " +
                    "or headquarters already exists, please edit instead.");
        }
    }

    @RequestMapping(value = {"/locations","/locations/"}, method = RequestMethod.POST)
    @ResponseBody
    @ExceptionHandler
    public Location changeLocations(@RequestBody Location loc) throws SQLUpdateException {

        try {
            return dao.changeLocation(loc);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            throw new SQLUpdateException("Server error " +
                    "or location already exists, please edit instead.");
        }
    }

    @RequestMapping(value = {"/sightings","/sightings/"}, method = RequestMethod.POST)
    @ResponseBody
    public Sighting changeSightings(@RequestBody Sighting sighting) throws SQLUpdateException {

        try {
            return dao.changeSighting(sighting);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            throw new SQLUpdateException("Server error " +
                    "or sighting already exists, please edit instead.");
        }
    }
}
