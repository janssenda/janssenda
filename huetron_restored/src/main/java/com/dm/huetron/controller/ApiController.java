package com.dm.huetron.controller;

import com.dm.huetron.dao.MasterDao;
import com.dm.huetron.exception.SQLUpdateException;
import com.dm.huetron.model.Light;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    private MasterDao dao;

    @Autowired
    public ApiController(MasterDao dao) {
        this.dao = dao;
    }

    @ResponseBody
    @RequestMapping(value = {"/lights", "/lights/"}, method = RequestMethod.GET)
    public List<Light> getLights(HttpServletRequest req) {
        return this.dao.getAllLights();
    }

    @RequestMapping(value = {"/lights", "/lights/"},method = RequestMethod.POST)
    @ResponseBody
    @ExceptionHandler
    public Light addLight(@RequestBody Light light)
            throws SQLUpdateException {
        return this.dao.addLight(light);
    }
}
