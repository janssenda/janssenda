package com.dm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class DefaultController {

    @RequestMapping(value="/returnRequest",method= RequestMethod.POST)
    public String returnRequest(HttpServletRequest request, Map<String, Object> model) {

        String userInput = request.getParameter("userInput");
        model.put("userInput", userInput);

        return "index";


    }

}