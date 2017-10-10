package com.dm.vendingapi.controller;


import com.dm.vendingapi.dao.NoItemInventoryException;
import com.dm.vendingapi.dto.Money;
import com.dm.vendingapi.servicelayer.InsufficientFundsException;
import com.dm.vendingapi.servicelayer.VendingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({"/jsp"})
public class VendingJSPController {

    private VendingService service;

    @Inject
    public VendingJSPController(VendingService service) {
        this.service = service;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomeMap(Model model) {
        model = mergeSharedData(model);
        model.addAttribute("boxmsg", "Welcome!!!");
        return "index";
    }

    @RequestMapping(value = {"/vendItem"}, method = RequestMethod.POST)
    public String vendItem(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);
        Money m = new Money(getAsBigDecimal(request.getParameter("totalCash")));
        String item = request.getParameter("selectedItem");

        try {
            service.vendProduct(m, item);
            model.addAttribute("plist", service.returnInventoryMap());
        } catch (NoItemInventoryException e) {
            model.addAttribute("boxmsg", "SOLD OUT!!!");
            return "index";
        } catch (InsufficientFundsException e){

            String amtShort = new BigDecimal(service.getPricing().get(item))
                    .subtract(m.getTotalmoney()).setScale(2,RoundingMode.HALF_UP).toString();

            model.addAttribute("boxmsg", "Insufficient Funds!! <br/> Please add $"+amtShort);
            return "index";
        }

        model.addAttribute("change", buildChangeMap(m));
        model.addAttribute("boxmsg", "Thank you!!!");
        model.addAttribute("totalCash", "0.00");

        return "index";

    }


    @RequestMapping(value = {"/getChange"}, method = RequestMethod.POST)
    public String getChange(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);
        Money m = new Money(getAsBigDecimal(request.getParameter("totalCash")));
        model.addAttribute("change", buildChangeMap(m));
        model.addAttribute("boxmsg", "Change: ");
        model.addAttribute("totalCash", "0.00");
        return "index";
    }

    @RequestMapping(value = {"/loadItem"}, method = RequestMethod.POST)
    public String loadItem(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);
        model.addAttribute("boxmsg", "Make a selection...");
        model.addAttribute("selectedItem", request.getParameter("selectedItemButton"));
        return "index";
    }


    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public String addMoney(HttpServletRequest request, Model model) {

        model = mergeSharedData(request, model);
        BigDecimal add;
        Money m = new Money(getAsBigDecimal(request.getParameter("totalCash")));

        switch(request.getParameter("mbutton")){
            case "dollar":
                add = new BigDecimal("1.00");
                break;
            case "quarter":
                add = new BigDecimal("0.25");
                break;
            case "dime":
                add = new BigDecimal("0.10");
                break;
            case "nickel":
                add = new BigDecimal("0.05");
                break;

            default: add = new BigDecimal("0.00");
        }

        String newTotal = add.add(m.getTotalmoney()).setScale(2, RoundingMode.HALF_UP).toString();
        model.addAttribute("totalCash", String.format(newTotal));
        model.addAttribute("boxmsg", "Make a selection...");

        return "index";
    }



    private Model mergeSharedData(HttpServletRequest request, Model model){

        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));

        model.addAttribute("totalCash", request.getParameter("totalCash"));
        model.addAttribute("selectedItem", request.getParameter("selectedItem"));
        model.addAttribute("plist", service.returnInventoryMap());
        model.addAttribute("time", formd);
        return model;
    }

    private Model mergeSharedData(Model model){

        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));

        model.addAttribute("plist", service.returnInventoryMap());
        model.addAttribute("time", formd);
        return model;
    }

    private Map<String, String> buildChangeMap(Money m){

        Map<String, String> changeMap = new HashMap<>();

        changeMap.put("quarters", m.getQuarters()+"x0.25");
        changeMap.put("dimes", m.getDimes()+"x0.10");
        changeMap.put("nickels", m.getNickels()+"x0.10");
        changeMap.put("pennies", m.getPennies()+"x0.01");

        return changeMap;
    }

    private BigDecimal getAsBigDecimal(String s){
        BigDecimal current;
        try {
            current = new BigDecimal(s);
        } catch (NumberFormatException e) {
            current = BigDecimal.ZERO;
        }

        return current;
    }

}
