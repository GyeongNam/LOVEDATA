package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.controller.UserController;
import com.project.love_data.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.project.love_data.util.ConstantValues.MAX_UPLOAD_COUNT;
import static com.project.love_data.util.ConstantValues.MIN_UPLOAD_COUNT;
import static java.awt.SystemColor.control;
import static java.awt.SystemColor.info;

@Log4j2
@Controller
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    UserService userService;
    @Autowired
    FileUploadService fileUploadService;

    @GetMapping(value = "/ServiceCenter")
    public String Notice(Model model, HttpServletResponse response)  {
        Policy policy = new Policy();
        policy.model_add(model, policy);

        List<Notice> notice = noticeService.not_select_all();
        List<Questions> questions = noticeService.qu_select_all();

        model.addAttribute("nots_size",notice.size());
        model.addAttribute("qu_size",questions.size());
        model.addAttribute("nots",notice);
        model.addAttribute("qu",questions);

        return "/user/Service_center";
    }

    @ResponseBody
    @PostMapping(value = "/Questions/search")
    public Map<String, List<Questions>> Qsearch(@RequestBody HashMap<String, String> data, Principal principal)  {
        Map<String, List<Questions>> map = new HashMap<String, List<Questions>>();

        List<Questions> questions = noticeService.qu_search_all(data.get("select"), data.get("text"));
        map.put("questions",questions);

        return map;
    }

    @GetMapping(value = "/ServiceCenter/Notice/{num}")
    public String Notice_no(@PathVariable("num") String num, Model model, HttpServletResponse response)  {
        Notice notice = noticeService.noti_select_no(num);
        notice.setNoti_viewCount(notice.getNoti_viewCount()+1);
        noticeService.not_update(notice);
        model.addAttribute("noti",notice);
        return "/service/noti_detaill";
    }

    @GetMapping(value = "/ServiceCenter/Questions/{num}")
    public String Questions_no(@PathVariable("num") String num, Model model, HttpServletResponse response, Principal principal) throws IOException {
        ScriptUtils scriptUtils = new ScriptUtils();
        Questions questions = noticeService.qu_select_no(num);
        if (questions.isQu_secret()) {
            if (principal == null) {
                scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
            } else {
                String user_no = userService.finduserNo(principal.getName());
                Questions questions2 = noticeService.secret_check(num, user_no);

                if (questions2 == null) {
                    scriptUtils.alertPageout(response, "비밀글입니다.");
                } else {
                    model.addAttribute("qu", questions);
                }
            }
        } else {
            model.addAttribute("qu", questions);
        }
        return "/service/qu_detail";
    }

    @PostMapping(value = "/ServiceCenter/Questions_Post_add/add")
    public String Post_add_add(@RequestParam("files") List<MultipartFile> fileList, HttpServletRequest request, Principal principal){
        List<String> filePath = null;
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        User user = userService.select(principal.getName());

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, 0, 2, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
        }
        log.warn(">??>?"+filePath);

        Boolean secret = false;
        if(request.getParameter("secret").equals("1")){
            secret = true;
        }
        Questions questions = Questions.builder()
                .qu_answer(false)
                .qu_date(format1.format(today))
                .qu_secret(secret)
                .qu_user(user.getUser_nic())
                .qu_user_no(user.getUser_no().toString())
                .qu_text(request.getParameter("info"))
                .qu_title(request.getParameter("title"))
                .build();
        noticeService.qu_update(questions);

        return "redirect:/ServiceCenter";
    }

    @GetMapping(value = "/ServiceCenter/Questions_Post_add")
    public String Post_add(Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            ScriptUtils scriptUtils = new ScriptUtils();
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
        return "/service/qu_Post_add";
    }

    public class ScriptUtils {

        public void init(HttpServletResponse response) {
            response.setContentType("text/html; charset=euc-kr");
            response.setCharacterEncoding("euc-kr");
        }

        public void alert(HttpServletResponse response, String alertText) throws IOException {
            init(response);
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertText + "');</script> ");
            out.flush();
        }

        public void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage)
                throws IOException {
            init(response);
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
            out.flush();
        }

        public void alertAndBackPage(HttpServletResponse response, String alertText) throws IOException {
            init(response);
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
            out.flush();
        }
        public void alertPageout(HttpServletResponse response, String alertText) throws IOException {
            init(response);
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertText + "'); window.close();</script>");
            out.flush();
        }
    }
}

