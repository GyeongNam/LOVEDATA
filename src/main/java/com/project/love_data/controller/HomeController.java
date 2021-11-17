package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.CourseService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Location;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
//@RestController
@Log4j2
public class HomeController {

    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;

    @GetMapping("/" )
    public String home(Model model){
        List<Location> tempLocationList = locService.hotLocationList(4, 7, 0);
        List<Course> tempCourseList = corService.hotCourseList(4, 7, 0);

        model.addAttribute("lochotList", tempLocationList);
        model.addAttribute("corhotList", tempCourseList);

        return "home";
    }
}
