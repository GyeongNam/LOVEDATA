package com.project.love_data.controller;


import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.businessLogic.service.UserLikeLocService;
import com.project.love_data.businessLogic.service.UserService;
import com.project.love_data.model.service.UserLikeLoc;
import com.project.love_data.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@Controller
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {
    @Autowired
    LocationService locService;
    @Autowired
    UserLikeLocService likeLocService;
    @Autowired
    UserService userService;

    @PostMapping("/authenticationCheck")
    public boolean onAuthenticationCheck(Authentication authentication,
                                         @RequestBody HashMap<String, Boolean> data) {
//        boolean debug = data.get("debug");
//
//        log.info(data);
//        log.info(debug);
//
//        if(debug) {
//            return true;
//        }

        if (authentication == null ) {
            return false;
        }

        return authentication.isAuthenticated();
    }

    @PostMapping("/onClickLike")
    public boolean onLikeClicked(@RequestBody HashMap<String, String> data) {
//        log.info("data : " + data);

        Long loc_no = Long.valueOf(data.get("loc_no"));
        Long user_no = Long.valueOf(data.get("user_no"));

        if (user_no == -1) {
            log.info("Not Registered!");
            return false;
        }

        User user = userService.select(user_no);

        if (user == null) {
            log.warn("Not Registerd User!");
            log.warn("Please Check");
            return false;
        }

        UserLikeLoc item = likeLocService.selectByLocNoAndUserNo(loc_no, user_no);

        if (item != null){
            log.warn(loc_no + "(loc_no) has been liked before!");
            log.warn("Please Check");
            return false;
        }

        if (loc_no == null) {
            log.warn("loc_no null value");
            return false;
        }

        if ( locService.onClickLike(loc_no)) {
            if (likeLocService.register(loc_no, user_no) == null) {
                locService.onClickLikeUndo(loc_no);
                log.warn("UserLikeLocation register not Completed");
                return false;
            }
            log.info("Liked Successful");
            // Todo 유저가 좋아요한 테이블에 정보 인서트
            return true;
        } else {
            log.info("loc_no is not correct");
            return false;
        }
    }

    @PostMapping("/onClickLikeUndo")
    public boolean onLikeClickUndo(@RequestBody HashMap<String, String> data) {
        log.info("data : " + data);

        Long loc_no = Long.valueOf(data.get("loc_no"));
        Long user_no = Long.valueOf(data.get("user_no"));

        UserLikeLoc item = likeLocService.selectByLocNoAndUserNo(loc_no, user_no);

        if (item == null){
            log.warn(loc_no + " has not been like before!");
            log.warn("Please Check");
            return false;
        }

        if (loc_no == null) {
            log.info("loc_no null value");
            return false;
        }

        if ( locService.onClickLikeUndo(loc_no)) {
            if (!likeLocService.delete(loc_no, user_no)) {
                locService.onClickLike(loc_no);
                log.warn("UserLikeLocation delete not Completed");
                return false;
            }
            log.info("Like Undo Successful");
            return true;
        } else {
            log.info("Like Undo failed");
            return false;
        }
    }
}
