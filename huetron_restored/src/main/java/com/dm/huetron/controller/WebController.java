package com.dm.huetron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping({"/index", "/"})
    public String home1(Model model) {
        return "index";
    }
}