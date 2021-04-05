package com.project.love_data.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
@RequestMapping(value = "sample/", method = RequestMethod.POST)
public class SampleController {
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll");
    }

    @GetMapping("/member")
    public void exMember() {
        log.info("exMember.....");
    }
}
