package com.project.love_data.controller;


import com.project.love_data.businessLogic.service.LocationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Log4j2
@Controller
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {
    @Autowired
    LocationService locService;

    @PostMapping("/authenticationCheck")
    public boolean onAuthenticationCheck(Authentication authentication,
                                         @RequestBody HashMap<String, Boolean> data) {
        boolean debug = data.get("debug");

        log.info(data);
        log.info(debug);

        if(debug) {
            return true;
        }

        if (authentication == null ) {
            return false;
        }

        return authentication.isAuthenticated();
    }

    @PostMapping("/onClickLike")
    public boolean onLikeClicked(@RequestBody HashMap<String, String> data) {
        log.info("data : " + data);

        String loc_no = data.get("loc_no");

        if (loc_no == null) {
            log.info("loc_no null value");
            return false;
        }

        if ( locService.onClickLike(Long.valueOf(loc_no))) {
            log.info("Liked Successful");
            return true;
        } else {
            log.info("loc_no failed");
            return false;
        }
    }

    @PostMapping("/onClickLikeUndo")
    public boolean onLikeClickUndo(@RequestBody HashMap<String, String> data) {
        log.info("data : " + data);

        String loc_no = data.get("loc_no");

        if (loc_no == null) {
            log.info("loc_no null value");
            return false;
        }

        if ( locService.onClickLikeUndo(Long.valueOf(loc_no))) {
            log.info("Like Undo Successful");
            return true;
        } else {
            log.info("Like Undo failed");
            return false;
        }
    }
}
