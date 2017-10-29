package com.dm.herotoday.controller;

import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.model.Hero;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class MainRestController {

    DataCoordinatorDao dao;

    @Inject
    public MainRestController(DataCoordinatorDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/heroes/", method = RequestMethod.GET)
    public List<Hero> getHero(HttpServletRequest req) {
        String id = null, name = null, type = null, desc = null;

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

        return dao.getFromHeroes(id, name);
    }
}
