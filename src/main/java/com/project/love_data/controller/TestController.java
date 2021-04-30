package com.project.love_data.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RestController
@Log4j2
public class TestController {
    @GetMapping(value = "/test/service_sample/testvalue")
    public String printTestValue(HttpServletRequest request){
        log.info("service_sample 테스트 밸류 : " + request.getParameter("text"));
        return "redirect:/test/service_sample";
    }

    @GetMapping(value = "/test/Bootstrap/Collapse_Test/value")
    public String printTestValue2(HttpServletRequest request){
        log.info("Collapse_Test 테스트 밸류 : " + request.getParameter("text"));
        return "redirect:/test/collapse";
    }
}
