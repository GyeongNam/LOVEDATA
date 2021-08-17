package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.model.resource.QuestionsImage;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.QuestionsImageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import com.project.love_data.model.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Controller
public class ServiceCenterController {
    @Autowired
    ServiceCenterService serviceCenterService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionsImageService questionsImageService;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    QuestionsImageRepository questionsImageRepository;

    @GetMapping(value = "/ServiceCenter/Notice/{page}")
    public String Notice(@PathVariable("page") String page, Model model, HttpServletResponse response) {
        List<Notice> notice = serviceCenterService.not_select_all();
        long notice_page = notice.size()/15;
        long notice_page_na = notice.size()%15;
        if(notice_page_na <= 1){
            notice_page = notice_page+1;
            model.addAttribute("notice_page",notice_page);
        }
        else {
            model.addAttribute("notice_page",notice_page);
        }
        model.addAttribute("nots_size",notice.size());

        model.addAttribute("nots",notice);
        return "/user/Service_center_no";
    }

    @GetMapping(value = "/ServiceCenter/Notice_Post/{num}")
    public String Notice_no(@PathVariable("num") String num, Model model, HttpServletResponse response)  {
        Notice notice = serviceCenterService.noti_select_no(num);
        notice.setNoti_viewCount(notice.getNoti_viewCount()+1);
        serviceCenterService.not_update(notice);
        model.addAttribute("noti",notice);
        return "/service/noti_detaill";
    }

    @GetMapping(value = "/ServiceCenter/Questions/{page}")
    public String Questions(@PathVariable("page") String page, Model model, HttpServletResponse response) {
        List<Questions> questions = serviceCenterService.qu_select_all();
        List<Questions> questions_page = null;
        model.addAttribute("search", false);

        long qu_page = questions.size()/15;
        long qu_page_na = questions.size()%15;
        if(qu_page_na >= 1){
            qu_page = qu_page+1;
            model.addAttribute("qu_page",qu_page);
        }
        else {
            model.addAttribute("qu_page",qu_page);
        }
        model.addAttribute("qu_size",questions.size());

        // 페이지네이션
        long j=0;

        if(questions.size()<15){
            model.addAttribute("qu",questions);
        }else {
            for (int i = 0; i < questions.size(); i++) {
                questions_page = questions.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("qu",questions_page);
                        break;
                    } else {
                       questions.subList(0,15).clear();

                       if(questions.size()<15){
                           model.addAttribute("qu",questions);
                           break;
                       }
                    }
                }
            }
        }

        return "/user/Service_center_qu";
    }

    @GetMapping(value = "/ServiceCenter/Policy")
    public String Policy(Model model, HttpServletResponse response)  {
        Policy policy = new Policy();
        policy.model_add(model, policy);
        return "/user/Service_center_po";
    }

    @GetMapping(value = "/ServiceCenter/Withdrawal")
    public String Withdrawal(Model model, HttpServletResponse response)  {

        return "/user/Service_center_de";
    }


    @GetMapping(value = "/ServiceCenter/Questions/search/{menu}/{text}/{page}")
    public String Qsearch( @PathVariable("page") String page,
                           @PathVariable("text") String text,
                           @PathVariable("menu") String menu,
                           Model model,
                           Principal principal)  {
        List<Questions> questions = serviceCenterService.qu_search_all(menu, text);
        List<Questions> questions_page = null;
        long qu_page = questions.size()/15;
        long qu_page_na = questions.size()%15;

        model.addAttribute("search", true);
        model.addAttribute("text", text);
        model.addAttribute("menu", menu);

        if(qu_page_na >= 1){
            qu_page = qu_page+1;
            model.addAttribute("qu_page",qu_page);
        }
        else {
            model.addAttribute("qu_page",qu_page);
        }
        model.addAttribute("qu_size",questions.size());

        // 페이지네이션
        long j=0;

        if(questions.size()<15){
            model.addAttribute("qu",questions);
        }else {
            for (int i = 0; i < questions.size(); i++) {
                questions_page = questions.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("qu",questions_page);
                        break;
                    } else {
                        questions.subList(0,15).clear();

                        if(questions.size()<15){
                            model.addAttribute("qu",questions);
                            break;
                        }
                    }
                }
            }
        }

        return "user/Service_center_qu";
    }


    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping(value = "/ServiceCenter/Questions_answer/{num}")
    public String Questions_answer(@PathVariable("num") String num,Principal principal,HttpServletRequest request){
        User user = userService.select(principal.getName());
        Questions questions = serviceCenterService.qu_select_no(num);
        questions.setQu_answer(true);
        questions.setQu_answer_manager(user.getUser_nic());
        questions.setQu_answer_text(request.getParameter("info"));
        serviceCenterService.qu_update(questions);
        return "redirect:/ServiceCenter/Questions_Post_mana/"+num;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping(value = "/ServiceCenter/Questions_Post_mana/{num}")
    public String Questions_no_mana(@PathVariable("num") String num, Model model, HttpServletResponse response , Principal principal) throws IOException {
        Questions questions = serviceCenterService.qu_select_no(num);
        List<QuestionsImage> questionsImage = questionsImageService.qu_no_imgselect(num);
        model.addAttribute("qu_img", questionsImage);
        model.addAttribute("qu", questions);
        return "/service/qu_detail";
    }
    @GetMapping(value = "/ServiceCenter/Questions_Post/{num}")
    public String Questions_no(@PathVariable("num") String num, Model model, HttpServletResponse response , Principal principal) throws IOException {
        ScriptUtils scriptUtils = new ScriptUtils();
        Questions questions = serviceCenterService.qu_select_no(num);
        List<QuestionsImage> questionsImage = questionsImageService.qu_no_imgselect(num);
        if (questions.isQu_secret()) {
            if (principal == null) {
                scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
            } else {
                String user_no = userService.finduserNo(principal.getName());
                Questions questions2 = serviceCenterService.secret_check(num, user_no);

                if (questions2 == null) {
                    scriptUtils.alertAndBackPage(response, "비밀글입니다.");
                } else {
                    model.addAttribute("qu_img", questionsImage);
                    model.addAttribute("qu", questions);
                }
            }
        } else {
            model.addAttribute("qu_img", questionsImage);
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
                UploadFileCount.MULTIPLE, 0, 3, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
        }

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
        serviceCenterService.qu_update(questions);

        for (int i = 1; i<filePath.size(); i++) {
            QuestionsImage questionsImage = QuestionsImage.builder()
                    .qu_img_url(filePath.get(i))
                    .user_no(user.getUser_no())
                    .qu_no(questions.getQu_no())
                    .build();
            questionsImageRepository.save(questionsImage);
        }

        return "redirect:/ServiceCenter/Questions/1";
    }

    @PostMapping(value = "/ServiceCenter/Questions_Post_Update/update")
    public String Post_update_update(@RequestParam("files") List<MultipartFile> fileList, HttpServletRequest request, Principal principal){
        List<String> filePath = null;
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Questions questions = serviceCenterService.qu_select_no(request.getParameter("qu_no"));
        User user = userService.select(principal.getName());
        if(!fileList.get(0).getOriginalFilename().equals("")){
            questionsImageRepository.qu_no_img_false(request.getParameter("qu_no"));
        }

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, 0, 3, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
        }

        Boolean secret = false;
        if(request.getParameter("secret").equals("1")){
            secret = true;
        }

        questions.setQu_secret(secret);
        questions.setQu_text(request.getParameter("info"));
        questions.setQu_title(request.getParameter("title"));
        serviceCenterService.qu_update(questions);

        for (int i = 1; i<filePath.size(); i++) {
            QuestionsImage questionsImage = QuestionsImage.builder()
                    .qu_img_url(filePath.get(i))
                    .user_no(user.getUser_no())
                    .qu_no(questions.getQu_no())
                    .build();
            questionsImageRepository.save(questionsImage);
        }

        return "redirect:/ServiceCenter/Questions_Post/"+request.getParameter("qu_no");
    }

    @GetMapping(value = "/ServiceCenter/Questions_Post_add")
    public String Post_add(Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            ScriptUtils scriptUtils = new ScriptUtils();
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
        return "/service/qu_Post_add";
    }



    @GetMapping(value = "/ServiceCenter/Questions_Update/{num}")
    public String Questions_Update(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response) throws IOException {
        ScriptUtils scriptUtils = new ScriptUtils();
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            Questions questions = serviceCenterService.qu_select_no(num);
            User user = userService.select(principal.getName());
            List<QuestionsImage> questionsImage = questionsImageService.qu_no_imgselect(num);
            if(questions.isQu_answer() == true){
                scriptUtils.alertAndMovePage(response, "답변된 글은 수정 불가능합니다.","/ServiceCenter/Questions_Post/"+num);
            }

            if(questions.getQu_user().equals(user.getUser_nic())){
                model.addAttribute("qu",questions);
                model.addAttribute("qu_img", questionsImage);
                model.addAttribute("qu_img_size", questionsImage.size());
                return "/service/qu_Post_update";
            }
        }
        return "redirect:/ServiceCenter/Questions_Post/"+num;
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
        public void Nextpage(HttpServletResponse response, String Nextpage) throws IOException {
            init(response);
            PrintWriter out = response.getWriter();
            out.println("<script>location.href='" + Nextpage + "';</script>");
            out.flush();
        }
    }
}

