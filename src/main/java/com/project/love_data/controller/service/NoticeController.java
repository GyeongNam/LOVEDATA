package com.project.love_data.controller.service;

import com.project.love_data.controller.UserController;
import com.project.love_data.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
public class NoticeController {



    @GetMapping(value = "/Notice")
    public String Notice(Model model, HttpServletResponse response)  {
        Policy policy = new Policy();
        policy.model_add(model, policy);


        return "/user/Notice";
    }
}

