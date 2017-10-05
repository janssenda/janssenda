package com.dm.contactlist.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactController {
        
    public ContactController() {
    }
    
    @RequestMapping(value="/displayContactsPage", method=RequestMethod.GET)
    public String displayContactsPage() {
        return "contacts";
    }
}
