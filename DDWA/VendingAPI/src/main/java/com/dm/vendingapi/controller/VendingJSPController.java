package com.dm.vendingapi.controller;


import com.dm.vendingapi.dto.Money;
import com.dm.vendingapi.servicelayer.VendingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({"/jsp"})
public class VendingJSPController {

    VendingService service;

    @Inject
    public VendingJSPController(VendingService service) {
        this.service = service;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomeMap(Model model) {

        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));
        model.addAttribute("time", formd);
        model.addAttribute("boxmsg", "Success");

        return "index";
    }


    @RequestMapping(value = {"/getChange"}, method = RequestMethod.POST)
    public String getChange(HttpServletRequest request, Model model) {
        Double current;
        try {
            current = Double.parseDouble(request.getParameter("current"));
        } catch (NumberFormatException e) {
            current = 0.0;
        }

        Money m = new Money(BigDecimal.valueOf(current));

        Map<String, String> changeMap = new HashMap<>();

        changeMap.put("quarters", m.getQuarters()+"x0.25");
        changeMap.put("dimes", m.getDimes()+"x0.10");
        changeMap.put("nickels", m.getNickels()+"x0.10");
        changeMap.put("pennies", m.getPennies()+"x0.01");

        model.addAttribute("change", changeMap);

        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));
        model.addAttribute("time", formd);
        model.addAttribute("boxmsg", "Change: ");
        model.addAttribute("totalCash", "0.00");
        return "index";
    }


    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public String addMoney(HttpServletRequest request, Model model) {
        Double current, add;

        try {
            current = Double.parseDouble(request.getParameter("current"));
        } catch (NumberFormatException e) {
            current = 0.0;
        }

        String amntToAdd = request.getParameter("mbutton");

        switch(amntToAdd){
            case "dollar":
                add = 1.00;
                break;
            case "quarter":
                add = 0.25;
                break;
            case "dime":
                add = 0.10;
                break;
            case "nickel":
                add = 0.05;
                break;

            default: add = 0.0;
        }

        //String newTotal = String.valueOf(add+current);
        double newTotal = add+current;
        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));

        model.addAttribute("totalCash", String.format("%4.2f",newTotal));
        model.addAttribute("time", formd);
        model.addAttribute("boxmsg", "Make a selection...");

        return "index";
    }

}
