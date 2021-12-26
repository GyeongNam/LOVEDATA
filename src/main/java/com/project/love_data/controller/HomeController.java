package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.BizRegService;
import com.project.love_data.businessLogic.service.CourseService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.businessLogic.service.UserService;
import com.project.love_data.model.service.BizReg;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Location;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
    public String home(Model model) throws JSONException, IOException {
        List<Location> tempLocationList = locService.hotLocationList(4, 7, 0);
        List<Course> tempCourseList = corService.hotCourseList(4, 7, 0);
        List<BizReg> bizRegs = bizRegService.findAllLiveByCertifiedTrue();
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

//        String url = "https://graph.facebook.com/v12.0/105161258693771/feed?fields=attachments,message&limit=1&access_token=";
//        JSONObject json = readJsonFromUrl(url);
//
//        model.addAttribute("instart",json);
        model.addAttribute("lochotList", tempLocationList);
        model.addAttribute("corhotList", tempCourseList);

        return "home";
    }
    private static String jsonReadAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = reader.read()) != -1){
            sb.append((char) cp);
        }

        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = jsonReadAll(br);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
