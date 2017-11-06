package com.dm.herotoday.controller;

import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.model.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class SearchController {

    private DataCoordinatorDao dao;

    @Inject
    public SearchController(DataCoordinatorDao dao) {
        this.dao = dao;
    }






    @ResponseBody
    @RequestMapping(value = {"/heroes","/heroes/"}, method = RequestMethod.GET)
    public List<Hero> getHero(HttpServletRequest req) {
        String id = null, name = null, type = null, desc = null,
                locID = null;

        try {
            String load = req.getParameter("load");
            if (load.equals("false")) return dao.getHeroList();
        } catch (NullPointerException e) {}

        try {
            locID = req.getParameter("locID");
            if (locID.equals("null") || locID.isEmpty()) locID = null;
        } catch (NullPointerException e) {}

        try {
            id = req.getParameter("id");
            if (id.equals("null")) id = null;
        } catch (NullPointerException e) {}

        try {
            name = req.getParameter("name");
            if (name.equals("null")) name = null;
        } catch (NullPointerException e) {}

        try {
            type = req.getParameter("type");
            if (type.equals("null")) type = null;
        } catch (NullPointerException e) {}

        try {
            desc = req.getParameter("desc");
            if (desc.equals("null")) desc = null;
        } catch (NullPointerException e) {}

        if (locID != null){
            return dao.heroesByLocation(Integer.parseInt(locID));
        }

        return dao.getFromHeroes(id, name, type, desc);
    }

    @ResponseBody
    @RequestMapping(value = {"/powers","/powers/"}, method = RequestMethod.GET)
    public List<Power> getPowers(HttpServletRequest req) {
        String id = null, name = null, desc = null;

        try {
            String load = req.getParameter("load");
            if (load.equals("false")) return dao.getPowerList();
        } catch (NullPointerException e) {}

        try {
            id = req.getParameter("id");
            if (id.equals("null")) id = null;
        } catch (NullPointerException e) {}

        try {
            name = req.getParameter("name");
            if (name.equals("null")) name = null;
        } catch (NullPointerException e) {}

        try {
            desc = req.getParameter("desc");
            if (desc.equals("null")) desc = null;
        } catch (NullPointerException e) {}

        return dao.getFromPowers(id, name, desc);
    }

    @ResponseBody
    @RequestMapping(value = {"/orgs","/orgs/"}, method = RequestMethod.GET)
    public List<Organization> getOrgs(HttpServletRequest req) {
        String id = null, name = null, desc = null;

        try {
            String load = req.getParameter("load");
            if (load.equals("false")) return dao.getOrgList();
        } catch (NullPointerException e) {}

        try {
            id = req.getParameter("id");
            if (id.equals("null")) id = null;
        } catch (NullPointerException e) {}

        try {
            name = req.getParameter("name");
            if (name.equals("null")) name = null;
        } catch (NullPointerException e) {}

        try {
            desc = req.getParameter("desc");
            if (desc.equals("null")) desc = null;
        } catch (NullPointerException e) {}

        return dao.getFromOrgs(id, name, desc);
    }


    @ResponseBody
    @RequestMapping(value = {"/headquarters","/headquarters/"}, method = RequestMethod.GET)
    public List<Headquarters> getHeadquarters(HttpServletRequest req) {
        String id = null, name = null, desc = null;

        try {
            String load = req.getParameter("load");
            if (load.equals("false")) return dao.getHeadquartersList();
        } catch (NullPointerException e) {}

        try {
            id = req.getParameter("id");
            if (id.equals("null")) id = null;
        } catch (NullPointerException e) {}

        try {
            name = req.getParameter("name");
            if (name.equals("null")) name = null;
        } catch (NullPointerException e) {}

        try {
            desc = req.getParameter("desc");
            if (desc.equals("null")) desc = null;
        } catch (NullPointerException e) {}

        return dao.getFromHeadquarters(id, name, desc);
    }


    @ResponseBody
    @RequestMapping(value = {"/locations","/locations/"}, method = RequestMethod.GET)
    public List<Location> getLocs(HttpServletRequest req) {
        String id = null, name = null, country = null, city = null,
        state = null, zip = null, heroID = null;

        try {
            String load = req.getParameter("load");
            if (load.equals("false")) return dao.getLocationList();
        } catch (NullPointerException e) {}

        try {
            heroID = req.getParameter("heroID");
            if (heroID.equals("null") || heroID.isEmpty()) heroID = null;
        } catch (NullPointerException e) {}

        try {
            id = req.getParameter("id");
            if (id.equals("null")) id = null;
        } catch (NullPointerException e) {}

        try {
            name = req.getParameter("name");
            if (name.equals("null")) name = null;
        } catch (NullPointerException e) {}

        try {
            country = req.getParameter("country");
            if (country.equals("null")) country = null;
        } catch (NullPointerException e) {}

        try {
            city = req.getParameter("city");
            if (city.equals("null")) city = null;
        } catch (NullPointerException e) {}

        try {
            state = req.getParameter("state");
            if (state.equals("null")) state = null;
        } catch (NullPointerException e) {}

        try {
            zip = req.getParameter("zip");
            if (zip.equals("null")) zip = null;
        } catch (NullPointerException e) {}

        if (heroID != null){
            return dao.locationsByHero(Integer.parseInt(heroID));
        }

        return dao.getFromLocations(id, name, country, city, state, zip);
    }

    @ResponseBody
    @RequestMapping(value = {"/sightings","/sightings/"}, method = RequestMethod.GET)
    public List<Sighting> getSightings(HttpServletRequest req) {
        String id = null, date = null, locID = null, limit = null;

        try {
            id = req.getParameter("id");
            if (id.equals("null")) id = null;
        } catch (NullPointerException e) {}

        try {
            date = req.getParameter("date");
            if (date.equals("null")) date = null;
        } catch (NullPointerException e) {}

        try {
            locID = req.getParameter("locID");
            if (locID.equals("null")) locID = null;
        } catch (NullPointerException e) {}

        try {
            limit = req.getParameter("limit");
            if (limit.equals("null")) limit = null;
        } catch (NullPointerException e) {}

        return dao.getFromSightings(id, date, locID, limit);
    }


}
