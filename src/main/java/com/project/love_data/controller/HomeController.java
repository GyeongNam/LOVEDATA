package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.BizRegService;
import com.project.love_data.businessLogic.service.CourseService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.businessLogic.service.UserService;
import com.project.love_data.model.service.BizReg;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import com.project.love_data.security.model.UserRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
//@RestController
@Log4j2
public class HomeController {

    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    UserService userService;
    @Autowired
    BizRegService bizRegService;

    @GetMapping("/" )
    public String home(Model model){
        List<Location> tempLocationList = locService.hotLocationList(4, 7, 0);
        List<Course> tempCourseList = corService.hotCourseList(4, 7, 0);
        List<BizReg> bizRegs = bizRegService.findAllByCertifiedTrue();
        List<Location> locations = new ArrayList<>();
        for(int i =0; i<bizRegs.size(); i++){
            List<Location> locationList = locService.findLocByUserNo(bizRegs.get(i).getUserNo());
            for(int j =0; j<locationList.size(); j++){
                locations.add(locService.selectLiveLoc(locationList.get(j).getLoc_no()));
            }
        }

        locations.sort(new Comparator<Location>() {
            @Override
            public int compare(Location loc0, Location loc1) {
                // TODO Auto-generated method stub
                int age0 = Integer.parseInt(String.valueOf(loc0.getLoc_no()));
                int age1 = Integer.parseInt(String.valueOf(loc1.getLoc_no()));

                if(age0 == age1) return 0;
                else if(age0 < age1) return 1;
                else return -1;
            }
        });
        if(locations.size()<4) {
            model.addAttribute("bizloc", locations);
        }else {
            model.addAttribute("bizloc", locations.subList(0, 4));
        }

        model.addAttribute("lochotList", tempLocationList);
        model.addAttribute("corhotList", tempCourseList);

        return "home";
    }
}
