package com.project.love_data.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class ServiceController {
    @GetMapping(value = "/service/loc_registration")
    @PostMapping(value = "/service/loc_registration")
    public String loc_Reg() {
        return "/service/loc_registration";
    }
}
