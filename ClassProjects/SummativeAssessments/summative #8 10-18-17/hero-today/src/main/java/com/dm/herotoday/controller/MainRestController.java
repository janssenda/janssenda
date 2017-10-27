package com.dm.herotoday.controller;

import com.dm.herotoday.dao.interfaces.DataCoordinatorDao;
import com.dm.herotoday.model.Hero;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@CrossOrigin
@RestController
public class MainRestController {

    DataCoordinatorDao dao;

    @Inject
    public MainRestController(DataCoordinatorDao dao){
        this.dao = dao;
    }

    @RequestMapping(value = "/heroes/{id}", method = RequestMethod.GET)
    public List<Hero> getHero(@PathVariable("id") String id) {

        return dao.getFromHeroes(id);
    }
}
