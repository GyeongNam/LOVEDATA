package com.project.love_data.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RestController
@Log4j2
public class TestController {
    @GetMapping(value = "/service/locRoc_searchValue")
    public String printTestValue(HttpServletRequest request){
        log.info("locRoc search Value : " + request.getParameter("text"));
        return "redirect:/service/loc_recommend";
    }
}
