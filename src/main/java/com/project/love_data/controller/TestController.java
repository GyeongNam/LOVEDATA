package com.project.love_data.controller;

import com.project.love_data.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
//@RestController
@Log4j2
public class TestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/sample/model_addattributes")
    public String model_addattribute_test(HttpServletRequest request, Model model) {
        Iterator<String> queryIterator = null;
        try {
            queryIterator = Arrays.stream(request.getQueryString().split("&")).iterator();
            List<String> list = new ArrayList<>();
            Map<String, String> map = new HashMap<>();

            while (queryIterator.hasNext()) {
                String tmp = queryIterator.next();
                list.add(tmp.substring(tmp.indexOf('=')+1, tmp.length()));
            }

            log.info(list.get(0));
            log.info(list.get(1));

            map.put("user_email", list.get(0));
            map.put("user_pw", list.get(1));

            model.addAttribute("map", map);
        }
            catch (NullPointerException e) {
            return "/sample/model_addattributes";
        }

        return "/sample/model_addattributes";
    }

    @GetMapping(value = "/sample/response_body")
    public String response_body_test(HttpServletRequest request) {

        return "/sample/response_body";
    }

    @PostMapping(value = "/sample/response_body_value")
    @ResponseBody
    public String response_body_test_value(HttpServletRequest request, HttpServletResponse response) {
        return "test";
    }
}
