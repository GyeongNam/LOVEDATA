package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.NoticeService;
import com.project.love_data.controller.UserController;
import com.project.love_data.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

import static java.awt.SystemColor.info;

@Log4j2
@Controller
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping(value = "/Notice")
    public String Notice(Model model, HttpServletResponse response)  {
        Policy policy = new Policy();
        policy.model_add(model, policy);

        List<Notice> notice = noticeService.not_select_all();
        List<Questions> questions = noticeService.qu_select_all();

        model.addAttribute("nots_size",notice.size());
        model.addAttribute("qu_size",questions.size());
        model.addAttribute("nots",notice);
        model.addAttribute("qu",questions);

        return "/user/Notice";
    }

    @GetMapping(value = "/Notice/Notice/{num}")
    public String Notice_no(@PathVariable("num") String num, Model model, HttpServletResponse response)  {
        Notice notice = noticeService.noti_select_no(num);
        model.addAttribute("noti",notice);
        return "/service/noti_detaill";
    }

    @GetMapping(value = "/Notice/Questions/{num}")
    public String Questions_no(@PathVariable("num") String num, Model model, HttpServletResponse response)  {
        Questions questions = noticeService.qu_select_no(num);
        model.addAttribute("qu",questions);
        return "/service/qu_detaill";
    }
}

