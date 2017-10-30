package com.dm.herotoday.controller;

import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.*;
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
    public Hero changeHero(@RequestBody Hero hero) {

        try {
            return dao.changeHero(hero);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            return hero;
        }
    }

    @RequestMapping(value = {"/powers","/powers/"}, method = RequestMethod.POST)
    @ResponseBody
    public Power changePowers(@RequestBody Power power) {

        try {
            return dao.changePower(power);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            return power;
        }
    }

    @RequestMapping(value = {"/orgs","/orgs/"}, method = RequestMethod.POST)
    @ResponseBody
    public Organization changeOrgs(@RequestBody Organization org) {

        try {
            return dao.changeOrg(org);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            return org;
        }
    }

    @RequestMapping(value = {"/headquarters","/headquarters/"}, method = RequestMethod.POST)
    @ResponseBody
    public Headquarters changeHeadquarters(@RequestBody Headquarters headQ) {

        try {
            return dao.changeHeadquarters(headQ);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            return headQ;
        }
    }

    @RequestMapping(value = {"/locations","/locations/"}, method = RequestMethod.POST)
    @ResponseBody
    public Location changeLocations(@RequestBody Location loc) {

        try {
            return dao.changeLocation(loc);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            return loc;
        }
    }

    @RequestMapping(value = {"/sightings","/sightings/"}, method = RequestMethod.POST)
    @ResponseBody
    public Sighting changeSightings(@RequestBody Sighting sighting) {

        try {
            return dao.changeSighting(sighting);
        } catch (DuplicateEntryException | SQLUpdateException e) {
            return sighting;
        }
    }
}
