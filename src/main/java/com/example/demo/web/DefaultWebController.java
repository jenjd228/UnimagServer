package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultWebController {

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String index() {
        return "admin";
    }

    @GetMapping("/mainStatistic")
    public String mainStatistic(){
        return "frameGetMainStatistic";
    }

    @GetMapping("/productStatistic")
    public String productStatistic(){
        return "frameGetProductStatistic";
    }

    @GetMapping("/partnerProgramStatistic")
    public String partnerProgramStatistic(){
        return "frameGetPartnerProgramStatistic";
    }

}
