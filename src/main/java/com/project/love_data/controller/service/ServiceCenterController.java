package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.model.resource.QuestionsImage;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.*;
import com.project.love_data.businessLogic.service.ControllerScriptUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.project.love_data.model.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.project.love_data.util.ConstantValues.Linux_Image_Upload_Path;

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
    @Autowired
    NoticeIMGRepository noticeIMGRepository;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventAttendRepository eventAttendRepository;
    @Autowired
    EventWinRepository eventWinRepository ;
    @Autowired
    BizRegService bizRegService;
    @Autowired
    PointRepository pointRepository;
    @Autowired
    ControllerScriptUtils scriptUtils;

    @GetMapping(value = "/ServiceCenter/Notice/{page}")
    public String Notice(@PathVariable("page") String page, Model model, HttpServletResponse response) {
        List<Notice> notice = serviceCenterService.not_select_all();
        List<Notice> notice_page = null;
        model.addAttribute("search", false);
        long no_size = notice.size();
        long no_page = notice.size()/15;
        long no_page_na = notice.size()%15;
        long no_page_size = no_page/10;
        long no_page_size_na = no_page%10;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("no_time", format1.format(today));

        if(no_page_size_na >= 1){
            no_page_size = no_page_size+1;
            model.addAttribute("no_page_size",no_page_size);
        }
        else {
            model.addAttribute("no_page_size",no_page_size);
        }

        if(no_page_na >= 1){
            no_page = no_page+1;
            model.addAttribute("no_page",no_page);
        }
        else {
            model.addAttribute("no_page",no_page);
        }
        model.addAttribute("no_size",notice.size());

        // 페이지네이션
        long j=0;

        if(notice.size()<15){
            model.addAttribute("noti",notice);
        }else {
            for (int i = 0; i < no_size; i++) {
                notice_page = notice.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("noti",notice_page);
                        break;
                    } else {
                        notice.subList(0,15).clear();

                        if(notice.size()<15){
                            model.addAttribute("noti",notice);
                            break;
                        }
                    }
                }
            }
        }

        return "/user/Service_center_no";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/notice/{page}")
    public String admin_Notice(@PathVariable("page") String page, Model model, HttpServletResponse response) {
        List<Notice> notice = serviceCenterService.not_select_all();
        List<Notice> notice_page = null;
        model.addAttribute("search", false);
        long no_size = notice.size();
        long no_page = notice.size()/15;
        long no_page_na = notice.size()%15;
        long no_page_size = no_page/10;
        long no_page_size_na = no_page%10;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("no_time", format1.format(today));

        if(no_page_size_na >= 1){
            no_page_size = no_page_size+1;
            model.addAttribute("no_page_size",no_page_size);
        }
        else {
            model.addAttribute("no_page_size",no_page_size);
        }

        if(no_page_na >= 1){
            no_page = no_page+1;
            model.addAttribute("no_page",no_page);
        }
        else {
            model.addAttribute("no_page",no_page);
        }
        model.addAttribute("no_size",notice.size());

        // 페이지네이션
        long j=0;

        if(notice.size()<15){
            model.addAttribute("noti",notice);
        }else {
            for (int i = 0; i < no_size; i++) {
                notice_page = notice.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("noti",notice_page);
                        break;
                    } else {
                        notice.subList(0,15).clear();

                        if(notice.size()<15){
                            model.addAttribute("noti",notice);
                            break;
                        }
                    }
                }
            }
        }

        return "admin/admin_notice";
    }

    @GetMapping(value = "/ServiceCenter/Notice/search/{menu}/{text}/{page}")
    public String Nsearch( @PathVariable("page") String page,
                           @PathVariable("text") String text,
                           @PathVariable("menu") String menu,
                           Model model,
                           Principal principal)  {
        List<Notice> notice = serviceCenterService.no_search_all(menu, text);
        model.addAttribute("search", true);
        model.addAttribute("text", text);
        model.addAttribute("menu", menu);

        List<Notice> notice_page = null;
        model.addAttribute("search", false);
        long no_size = notice.size();
        long no_page = notice.size()/15;
        long no_page_na = notice.size()%15;
        long no_page_size = no_page/10;
        long no_page_size_na = no_page%10;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("no_time", format1.format(today));

        if(no_page_size_na >= 1){
            no_page_size = no_page_size+1;
            model.addAttribute("no_page_size",no_page_size);
        }
        else {
            model.addAttribute("no_page_size",no_page_size);
        }

        if(no_page_na >= 1){
            no_page = no_page+1;
            model.addAttribute("no_page",no_page);
        }
        else {
            model.addAttribute("no_page",no_page);
        }
        model.addAttribute("no_size",notice.size());

        // 페이지네이션
        long j=0;

        if(notice.size()<15){
            model.addAttribute("noti",notice);
        }else {
            for (int i = 0; i < no_size; i++) {
                notice_page = notice.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("noti",notice_page);
                        break;
                    } else {
                        notice.subList(0,15).clear();

                        if(notice.size()<15){
                            model.addAttribute("noti",notice);
                            break;
                        }
                    }
                }
            }
        }

        return "/user/Service_center_no";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/notice/search/{menu}/{text}/{page}")
    public String admin_Nsearch( @PathVariable("page") String page,
                           @PathVariable("text") String text,
                           @PathVariable("menu") String menu,
                           Model model,
                           Principal principal)  {
        List<Notice> notice = serviceCenterService.no_search_all(menu, text);
        model.addAttribute("search", true);
        model.addAttribute("text", text);
        model.addAttribute("menu", menu);

        List<Notice> notice_page = null;
        model.addAttribute("search", false);
        long no_size = notice.size();
        long no_page = notice.size()/15;
        long no_page_na = notice.size()%15;
        long no_page_size = no_page/10;
        long no_page_size_na = no_page%10;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("no_time", format1.format(today));

        if(no_page_size_na >= 1){
            no_page_size = no_page_size+1;
            model.addAttribute("no_page_size",no_page_size);
        }
        else {
            model.addAttribute("no_page_size",no_page_size);
        }

        if(no_page_na >= 1){
            no_page = no_page+1;
            model.addAttribute("no_page",no_page);
        }
        else {
            model.addAttribute("no_page",no_page);
        }
        model.addAttribute("no_size",notice.size());

        // 페이지네이션
        long j=0;

        if(notice.size()<15){
            model.addAttribute("noti",notice);
        }else {
            for (int i = 0; i < no_size; i++) {
                notice_page = notice.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("noti",notice_page);
                        break;
                    } else {
                        notice.subList(0,15).clear();

                        if(notice.size()<15){
                            model.addAttribute("noti",notice);
                            break;
                        }
                    }
                }
            }
        }

        return "/admin/admin_notice";
    }

    @GetMapping(value = "/ServiceCenter/Notice_Post/{num}")
    public String Notice_no(@PathVariable("num") String num, Model model, HttpServletResponse response)  {
        Notice notice = serviceCenterService.noti_select_no(num);
        notice.setNoti_viewCount(notice.getNoti_viewCount()+1);
        serviceCenterService.not_update(notice);
        model.addAttribute("noti",notice);
        return "/service/noti_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/notice_post/{num}")
    public String admin_Notice_no(@PathVariable("num") String num, Model model, HttpServletResponse response)  {
        Notice notice = serviceCenterService.noti_select_no(num);
        notice.setNoti_viewCount(notice.getNoti_viewCount()+1);
        serviceCenterService.not_update(notice);
        model.addAttribute("noti",notice);
        return "/admin/admin_notice_detail";
    }

    @GetMapping(value = "/ServiceCenter/Questions/{page}")
    public String Questions(@PathVariable("page") String page, Model model, HttpServletResponse response) {
        List<Questions> questions = serviceCenterService.qu_select_all();
        List<Questions> questions_page = null;
        model.addAttribute("search", false);
        long qu_size = questions.size();
        long qu_page = questions.size()/15;
        long qu_page_na = questions.size()%15;
        long qu_page_size = qu_page/10;
        long qu_page_size_na = qu_page%10;
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("qu_time", format1.format(today));

        if(qu_page_size_na >= 1){
            qu_page_size = qu_page_size+1;
            model.addAttribute("qu_page_size",qu_page_size);
        }
        else {
            model.addAttribute("qu_page_size",qu_page_size);
        }


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
            for (int i = 0; i < qu_size; i++) {
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
    public String Policy(Model model, HttpServletRequest request )  {
        String affiliate =request.getParameter("affiliate");
        if(affiliate != null){
            model.addAttribute("affiliate",1);
        }
        Policy policy = new Policy();
        policy.model_add(model, policy);
        return "/user/Service_center_po";
    }

    @GetMapping(value = "/ServiceCenter/Withdrawal")
    public String Withdrawal(Model model, HttpServletResponse response, Principal principal) throws IOException {
        if (principal == null) {
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
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
        long qu_size = questions.size();
        long qu_page = questions.size()/15;
        long qu_page_na = questions.size()%15;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("qu_time", format1.format(today));

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
            for (int i = 0; i < qu_size; i++) {
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/ServiceCenter/Notice_Delete")
    public String Notice_Delete(Principal principal,HttpServletRequest request) throws  IOException{
        String noti_no= request.getParameter("noti_no");
        Notice notice = serviceCenterService.noti_select_no(noti_no);
        notice.setNoti_activation(false);
        serviceCenterService.not_update(notice);

        List<NoticeIMG> noticeIMGS = serviceCenterService.select_notiimg_num(noti_no);
        log.info("노티 삭제 사진: "+noticeIMGS.size());
        for( int i = 0; i < noticeIMGS.size() ; i++ )
        {
            try {
                noticeIMGS.get(i).setNoti_activation(false);
                serviceCenterService.notiimg_update(noticeIMGS.get(i));
                if("Windows_NT".equals(System.getenv().get("OS"))) {
                    String r = request.getSession().getServletContext().getRealPath("/");
                    int idx = r.indexOf("main");
                    String imgpath = r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMGS.get(i).getNotiimg_name();

                    File file = FileUtils.getFile(imgpath);
                    File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMGS.get(i).getNotiimg_name());
                    FileUtils.moveFile(file, fileToMove);
                }else {
                    String imgpath = Linux_Image_Upload_Path+"notice/" + noticeIMGS.get(i).getNotiimg_name();

                    File file = FileUtils.getFile(imgpath);
                    File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"upload/" + noticeIMGS.get(i).getNotiimg_name());
                    FileUtils.moveFile(file, fileToMove);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "redirect:/ServiceCenter/Notice/1";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/admin/Questions_answer/{num}")
    public String admin_Questions_answer(@PathVariable("num") String num,Principal principal,HttpServletRequest request){
        User user = userService.select(principal.getName());
        Questions questions = serviceCenterService.qu_select_no(num);
        questions.setQu_answer(true);
        questions.setQu_answer_manager(user.getUser_nic());
        questions.setQu_answer_text(request.getParameter("info"));
        serviceCenterService.qu_update(questions);
        return "redirect:/admin/Questions_Post/"+num;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/ServiceCenter/Questions_Post_mana/{num}")
    public String Questions_no_mana(@PathVariable("num") String num, Model model, HttpServletResponse response , Principal principal) throws IOException {
        Questions questions = serviceCenterService.qu_select_no(num);
        List<QuestionsImage> questionsImage = questionsImageService.qu_no_imgselect(num);
        model.addAttribute("qu_img", questionsImage);
        model.addAttribute("qu", questions);
        return "/service/qu_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/Questions_Post_mana/{num}")
    public String ad_Questions_no_mana(@PathVariable("num") String num, Model model, HttpServletResponse response , Principal principal) throws IOException {
        Questions questions = serviceCenterService.qu_select_no(num);
        List<QuestionsImage> questionsImage = questionsImageService.qu_no_imgselect(num);
        model.addAttribute("qu_img", questionsImage);
        model.addAttribute("qu", questions);
        return "admin/admin_qna_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/qna/{page}")
    public String admin_qna(@PathVariable("page") String page, Model model, HttpServletResponse response , Principal principal) throws IOException {
        List<Questions> questions = serviceCenterService.qua_all();
        List<Questions> questions_page = null;
        model.addAttribute("search", false);
        long qu_size = questions.size();
        long qu_page = questions.size()/15;
        long qu_page_na = questions.size()%15;
        long qu_page_size = qu_page/10;
        long qu_page_size_na = qu_page%10;
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("qu_time", format1.format(today));

        if(qu_page_size_na >= 1){
            qu_page_size = qu_page_size+1;
            model.addAttribute("qu_page_size",qu_page_size);
        }
        else {
            model.addAttribute("qu_page_size",qu_page_size);
        }


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
            for (int i = 0; i < qu_size; i++) {
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
        return "admin/admin_qna";
    }

    @GetMapping(value = "/ServiceCenter/Questions_Post/{num}")
    public String Questions_no(@PathVariable("num") String num, Model model, HttpServletResponse response , Principal principal) throws IOException {
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

    @GetMapping(value = "/admin/Questions_Post/{num}")
    public String admin_Questions_no(@PathVariable("num") String num, Model model, HttpServletResponse response , Principal principal) throws IOException {
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
        return "admin/admin_qna_detail";
    }

    @PostMapping(value = "/ServiceCenter/Questions_Post_add/add")
    public String Post_add_add(@RequestParam("files") List<MultipartFile> fileList, HttpServletRequest request, Principal principal){
        List<String> filePath = null;
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        User user = userService.select(principal.getName());

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                0, 3, PathType.QNA, request);

        Boolean secret = false;
        if(request.getParameter("secret").equals("1")){
            secret = true;
        }
        Questions questions = Questions.builder()
                .qu_answer(false)
                .qu_date(format1.format(today))
                .qu_type(request.getParameter("qu_type"))
                .qu_secret(secret)
                .qu_user(user.getUser_nic())
                .qu_user_no(user.getUser_no().toString())
                .qu_text(request.getParameter("info"))
                .qu_title(request.getParameter("title"))
                .build();
        serviceCenterService.qu_update(questions);
        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
        }else {
            for (int i = 1; i<filePath.size();) {
                QuestionsImage questionsImage = QuestionsImage.builder()
                        .qu_img_url(filePath.get(i))
                        .user_no(user.getUser_no())
                        .qu_no(questions.getQu_no())
                        .build();
                questionsImageRepository.save(questionsImage);
                i = i+2;
            }
        }
        return "redirect:/ServiceCenter/Questions/1";
    }

    @PostMapping(value = "/ServiceCenter/Questions_Post_Update/update")
    public String Post_update_update(@RequestParam("files") List<MultipartFile> fileList, HttpServletRequest request, Principal principal)throws IOException{
        List<String> filePath = null;
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 업데이트시 기존 사진 아웃
        questionsImageRepository.qu_no_img_false(request.getParameter("qu_no"));

        Questions questions = serviceCenterService.qu_select_no(request.getParameter("qu_no"));
        User user = userService.select(principal.getName());

        Boolean secret = false;
        if(request.getParameter("secret").equals("1")){
            secret = true;
        }

//        log.info("files list"+ fileList.get(0).getOriginalFilename());
        for(int i = 0; i<fileList.size(); i++){
            List<QuestionsImage> questionsImage = questionsImageRepository.qu_name_imgselect(fileList.get(i).getOriginalFilename());
            if(questionsImage.size() > 0) {
                log.info(i+" :존재함");
                questions.setQu_activation(true);
                questions.setQu_secret(secret);
                questions.setQu_text(request.getParameter("info"));
                questions.setQu_title(request.getParameter("title"));
                serviceCenterService.qu_update(questions);
                QuestionsImage questionsImages = QuestionsImage.builder()
                        .qu_img_url(questionsImage.get(0).getQu_img_url())
                        .user_no(user.getUser_no())
                        .qu_no(questions.getQu_no())
                        .build();
                questionsImageRepository.save(questionsImages);
            }
            else {
                log.info(i+" :존재하지 않음");

                List<MultipartFile> fileList2 = fileList.subList(i,i+1);
                filePath = fileUploadService.execute(fileList2, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                        0, 3, PathType.QNA, request);

                questions.setQu_secret(secret);
                questions.setQu_text(request.getParameter("info"));
                questions.setQu_title(request.getParameter("title"));
                serviceCenterService.qu_update(questions);
                if (filePath == null) {
                    log.warn("파일이 제대로 저장되지 않았습니다.");
                }else {
                    QuestionsImage questionsImages = QuestionsImage.builder()
                            .qu_img_url(filePath.get(1))
                            .user_no(user.getUser_no())
                            .qu_no(questions.getQu_no())
                            .build();
                    questionsImageRepository.save(questionsImages);
                }
            }
        }
        try {
            // 기존 이미지 qna -> upload
            List<QuestionsImage> questionsImage2 = questionsImageRepository.qu_no_img_out(request.getParameter("qu_no"));
            for (int i = 0; i < questionsImage2.size(); i++) {
                String imgpath;
                if ("Windows_NT".equals(System.getenv().get("OS"))) {
                    String r = request.getSession().getServletContext().getRealPath("/");
                    int idx = r.indexOf("main");
                    imgpath = r.substring(0, idx) + "main/resources/static/image/qna/" + questionsImage2.get(i).getQu_img_url();

                    File file = FileUtils.getFile(imgpath);
                    File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/QNA^" + questionsImage2.get(i).getQu_img_url());
                    FileUtils.moveFile(file, fileToMove);
                } else {
                    imgpath = Linux_Image_Upload_Path + "qna/" + questionsImage2.get(i).getQu_img_url();

                    File file = FileUtils.getFile(imgpath);
                    File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path + "upload/QNA^" + questionsImage2.get(i).getQu_img_url());
                    FileUtils.moveFile(file, fileToMove);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return "redirect:/ServiceCenter/Questions_Post/"+request.getParameter("qu_no");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/ServiceCenter/Notice_Post_add")
    public String Post_add_noti(Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
        return "/service/noti_Post_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/notice_add")
    public String admin_Post_add_noti(Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
        return "admin/admin_notice_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/ServiceCenter/Notice_Post_add/add")
    public String Post_add_noti_add(HttpServletRequest request, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            User user = userService.select(principal.getName());
            Date today = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String body = request.getParameter("form_name");
            List<NoticeIMG> noticeIMG = serviceCenterService.select_notiimg(user.getUser_nic());
            log.info("사진 갯수"+noticeIMG.size());
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                log.info("폴문안에 body는 : "+body);
                log.info("사진명은 : "+noticeIMG.get(i).getNotiimg_name());
                if(body.contains(noticeIMG.get(i).getNotiimg_name())){
                    
                    // 포함
                    body = body.replace("/image/upload/"+noticeIMG.get(i).getNotiimg_name(),"/image/notice/"+noticeIMG.get(i).getNotiimg_name());

                    try {

                        String imgpath;
                        if("Windows_NT".equals(System.getenv().get("OS"))){
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx =  r.indexOf("main");
                            imgpath =r.substring(0, idx)+"main/resources/static/image/upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx)+"main/resources/static/image/notice/"+noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            imgpath = Linux_Image_Upload_Path+"upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"notice/"+noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 미포함
                    noticeIMG.get(i).setNoti_activation(false);
                }
            }

            Notice notices = Notice.builder()
                    .noti_activation(true)
                    .noti_manager(user.getUser_nic())
                    .noti_title(request.getParameter("title"))
                    .noti_text(body)
                    .noti_date(format1.format(today))
                    .noti_viewCount(0)
                    .build();
            noticeRepository.save(notices);
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                noticeIMG.get(i).setNotiimg_postno(notices.getNoti_no().toString());
                serviceCenterService.notiimg_update(noticeIMG.get(i));
            }
        }

        return "redirect:/ServiceCenter/Notice/1";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/admin/notice_post_add/add")
    public String admin_Post_add_noti_add(HttpServletRequest request, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            User user = userService.select(principal.getName());
            Date today = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String body = request.getParameter("form_name");
            List<NoticeIMG> noticeIMG = serviceCenterService.select_notiimg(user.getUser_nic());
            log.info("사진 갯수"+noticeIMG.size());
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                log.info("폴문안에 body는 : "+body);
                log.info("사진명은 : "+noticeIMG.get(i).getNotiimg_name());
                if(body.contains(noticeIMG.get(i).getNotiimg_name())){

                    // 포함
                    body = body.replace("/image/upload/"+noticeIMG.get(i).getNotiimg_name(),"/image/notice/"+noticeIMG.get(i).getNotiimg_name());

                    try {

                        String imgpath;
                        if("Windows_NT".equals(System.getenv().get("OS"))){
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx =  r.indexOf("main");
                            imgpath =r.substring(0, idx)+"main/resources/static/image/upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx)+"main/resources/static/image/notice/"+noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            imgpath = Linux_Image_Upload_Path+"upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"notice/"+noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 미포함
                    noticeIMG.get(i).setNoti_activation(false);
                }
            }

            Notice notices = Notice.builder()
                    .noti_activation(true)
                    .noti_manager(user.getUser_nic())
                    .noti_title(request.getParameter("title"))
                    .noti_text(body)
                    .noti_date(format1.format(today))
                    .noti_viewCount(0)
                    .build();
            noticeRepository.save(notices);
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                noticeIMG.get(i).setNotiimg_postno(notices.getNoti_no().toString());
                serviceCenterService.notiimg_update(noticeIMG.get(i));
            }
        }

        return "redirect:/admin/notice/1";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/ServiceCenter/Notice_Post_Update/update")
    public String admin_Post_update_noti_update(HttpServletRequest request, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            User user = userService.select(principal.getName());
            Date today = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String body = request.getParameter("form_name");
            List<NoticeIMG> noticeIMG_ol = serviceCenterService.select_notiimg_num(request.getParameter("num"));
            for( int i = 0; i < noticeIMG_ol.size() ; i++ )
            {
                if(body.contains(noticeIMG_ol.get(i).getNotiimg_name())){}
                else {
                    // 없다면
                    try {
                        noticeIMG_ol.get(i).setNoti_activation(false);
                        serviceCenterService.notiimg_update(noticeIMG_ol.get(i));

                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMG_ol.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMG_ol.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }
                        else {
                            String imgpath = Linux_Image_Upload_Path+"notice/"+noticeIMG_ol.get(i).getNotiimg_name();
                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"upload/" + noticeIMG_ol.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            List<NoticeIMG> noticeIMG = serviceCenterService.select_notiimg(user.getUser_nic());
            log.info("사진 갯수"+noticeIMG.size());
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                log.info("폴문안에 body는 : "+body);
                log.info("사진명은 : "+noticeIMG.get(i).getNotiimg_name());
                if(body.contains(noticeIMG.get(i).getNotiimg_name())){

                    // 포함
                    body = body.replace("/image/upload/"+noticeIMG.get(i).getNotiimg_name(),"/image/notice/"+noticeIMG.get(i).getNotiimg_name());

                    try {
                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            String imgpath = Linux_Image_Upload_Path+"upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"notice/" + noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 미포함
                    noticeIMG.get(i).setNoti_activation(false);
                }
            }

            Notice notices = serviceCenterService.noti_select_no(request.getParameter("num"));
            notices.setNoti_title(request.getParameter("title"));
            notices.setNoti_text(body);
            noticeRepository.save(notices);
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                noticeIMG.get(i).setNotiimg_postno(notices.getNoti_no().toString());
                serviceCenterService.notiimg_update(noticeIMG.get(i));
            }
        }

        return "redirect:/ServiceCenter/Notice/1";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/admin/Notice_Post_Update/update")
    public String Post_update_noti_update(HttpServletRequest request, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            User user = userService.select(principal.getName());
            Date today = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String body = request.getParameter("form_name");
            List<NoticeIMG> noticeIMG_ol = serviceCenterService.select_notiimg_num(request.getParameter("num"));
            for( int i = 0; i < noticeIMG_ol.size() ; i++ )
            {
                if(body.contains(noticeIMG_ol.get(i).getNotiimg_name())){}
                else {
                    // 없다면
                    try {
                        noticeIMG_ol.get(i).setNoti_activation(false);
                        serviceCenterService.notiimg_update(noticeIMG_ol.get(i));

                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMG_ol.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMG_ol.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }
                        else {
                            String imgpath = Linux_Image_Upload_Path+"notice/"+noticeIMG_ol.get(i).getNotiimg_name();
                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"upload/" + noticeIMG_ol.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            List<NoticeIMG> noticeIMG = serviceCenterService.select_notiimg(user.getUser_nic());
            log.info("사진 갯수"+noticeIMG.size());
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                log.info("폴문안에 body는 : "+body);
                log.info("사진명은 : "+noticeIMG.get(i).getNotiimg_name());
                if(body.contains(noticeIMG.get(i).getNotiimg_name())){

                    // 포함
                    body = body.replace("/image/upload/"+noticeIMG.get(i).getNotiimg_name(),"/image/notice/"+noticeIMG.get(i).getNotiimg_name());

                    try {
                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            String imgpath = Linux_Image_Upload_Path+"upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"notice/" + noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 미포함
                    noticeIMG.get(i).setNoti_activation(false);
                }
            }

            Notice notices = serviceCenterService.noti_select_no(request.getParameter("num"));
            notices.setNoti_title(request.getParameter("title"));
            notices.setNoti_text(body);
            noticeRepository.save(notices);
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                noticeIMG.get(i).setNotiimg_postno(notices.getNoti_no().toString());
                serviceCenterService.notiimg_update(noticeIMG.get(i));
            }
        }

        return "redirect:/admin/notice/1";
    }

    @GetMapping(value = "/ServiceCenter/Questions_Post_add")
    public String Post_add(Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
        return "/service/qu_Post_add";
    }

    @GetMapping(value = "/ServiceCenter/Questions_Delete/{num}")
    public String Questions_Delete(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            Questions questions = serviceCenterService.qu_select_no(num);
            User user = userService.select(principal.getName());
            if(user.getUser_nic().equals(questions.getQu_user()) ){
                List<QuestionsImage> questionsImage = questionsImageService.qu_no_imgselect(num);
                for( int i = 0; i < questionsImage.size() ; i++ )
                {
                    try {
                        questionsImage.get(i).setQu_img_Activation(false);
                        questionsImageService.update(questionsImage.get(i));
                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/qna/" + questionsImage.get(i).getQu_img_url();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/QNA^" + questionsImage.get(i).getQu_img_url());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            String imgpath = Linux_Image_Upload_Path+"qna/" + questionsImage.get(i).getQu_img_url();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"upload/QNA^" + questionsImage.get(i).getQu_img_url());
                            FileUtils.moveFile(file, fileToMove);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                questions.setQu_activation(false);
                serviceCenterService.qu_update(questions);
            }else {
                scriptUtils.alertAndBackPage(response,"다른사람의 글을 함부로 삭제할 수 없습니다.");
            }

        }
        return "redirect:/ServiceCenter/Questions/1";
    }

    @GetMapping(value = "/ServiceCenter/Notice_Update/{num}")
    public String Notice_Update(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            Notice notice = serviceCenterService.noti_select_no(num);
            model.addAttribute("noti",notice);
            return "/service/noti_Post_update";
        }
        return "redirect:/ServiceCenter/Notice_Post/"+num;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/notice_update/{num}")
    public String admin_Notice_Update(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            Notice notice = serviceCenterService.noti_select_no(num);
            model.addAttribute("noti",notice);
            return "admin/admin_notice_update";
        }
        return "redirect:/admin/notice_post/"+num;
    }


    @GetMapping(value = "/ServiceCenter/Questions_Update/{num}")
    public String Questions_Update(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response) throws IOException {
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

    @PostMapping(value ="/imgUpload")
    public String imgUpload(HttpServletRequest req, ImgVO imgVO){
        String result="";
        try{
            if(imgVO.getFiledata() != null && imgVO.getFiledata().getOriginalFilename() != null){

                String imgpath;
                if("Windows_NT".equals(System.getenv().get("OS"))){
                    String r = req.getSession().getServletContext().getRealPath("/");
                    int idx =  r.indexOf("main");
                    imgpath =r.substring(0, idx)+"main\\resources\\static\\image\\upload\\";
                }else {
                    imgpath = Linux_Image_Upload_Path+"upload/";
                }

                String path = imgpath;
                File file = new File(path);
                System.out.print(path);
                if(!file.exists()){
                    file.mkdirs();
                }
                String realName ="NTC^" + UUID.randomUUID().toString() + imgVO.getFiledata().getOriginalFilename();
                imgVO.getFiledata().transferTo(new File(path + realName));
                result += "&bNewLine=true&sFileName="+imgVO.getFiledata().getOriginalFilename()+"&sFileURL=/resources/img/"+realName;
            }else{
                result += "&errstr=error";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:" + imgVO.getCallback() +"?callback_func=" + imgVO.getCallback_func()+result;
    }

    @PostMapping(value = "/multiImgUpload")
    public void multiImgUpload(HttpServletRequest req, HttpServletResponse res, Principal principal){
        try{
            String sFileInfo = "";
            String imgpath;
            // os판단 if("Windows_NT".equals(System.getenv().get("OS")))
            if("Windows_NT".equals(System.getenv().get("OS"))){
                String r = req.getSession().getServletContext().getRealPath("/");
                int idx =  r.indexOf("main");
                imgpath =r.substring(0, idx)+"main\\resources\\static\\image\\upload\\";
            }else {
                imgpath = Linux_Image_Upload_Path+"upload/";
            }

            String fileName = req.getHeader("file-name");
            log.info("여기 오긴오니?" + imgpath);
            String prifix = fileName.substring(fileName.lastIndexOf(".")+1);
            prifix = prifix.toLowerCase();
            String path = imgpath;

            File file = new File(path);
            System.out.print(path);
            if(!file.exists()){
                file.mkdirs();
            }

            String realName ="NTC^" +UUID.randomUUID().toString() + "." +prifix;

            InputStream is = req.getInputStream();
            OutputStream os = new FileOutputStream(new File(path + realName));
            int read = 0;
            byte b[] = new byte[1024];
            while( (read = is.read(b)) != -1){
                os.write(b,0,read);
            }

            if(is != null){
                is.close();
            }
            os.flush();
            os.close();

            User user = userService.select(principal.getName());
            NoticeIMG noticeIMG = NoticeIMG.builder()
                    .notiimg_user(user.getUser_nic())
                    .notiimg_name(realName)
                    .noti_activation(true)
                    .notiimg_postno("0")
                    .build();
            noticeIMGRepository.save(noticeIMG);

            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName="+fileName;
            sFileInfo += "&sFileURL="+"/image/upload/"+realName;
            PrintWriter print = res.getWriter();
            print.print("파일 인포"+sFileInfo);
            print.flush();
            print.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 이벤트

    @GetMapping(value = "/ServiceCenter/Event/{page}")
    public String Event(@PathVariable("page") String page, Model model, HttpServletResponse response) {
        List<Event> events = serviceCenterService.ev_all();
        List<Event> notice_page = null;
        model.addAttribute("search", false);
        long no_size = events.size();
        long no_page = events.size()/15;
        long no_page_na = events.size()%15;
        long no_page_size = no_page/10;
        long no_page_size_na = no_page%10;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("no_time", format1.format(today));

        if(no_page_size_na >= 1){
            no_page_size = no_page_size+1;
            model.addAttribute("no_page_size",no_page_size);
        }
        else {
            model.addAttribute("no_page_size",no_page_size);
        }

        if(no_page_na >= 1){
            no_page = no_page+1;
            model.addAttribute("no_page",no_page);
        }
        else {
            model.addAttribute("no_page",no_page);
        }
        model.addAttribute("no_size",events.size());

        // 페이지네이션
        long j=0;

        if(events.size()<15){
            model.addAttribute("eve",events);
        }else {
            for (int i = 0; i < no_size; i++) {
                notice_page = events.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("eve",notice_page);
                        break;
                    } else {
                        events.subList(0,15).clear();

                        if(events.size()<15){
                            model.addAttribute("eve",events);
                            break;
                        }
                    }
                }
            }
        }

        return "/user/Service_center_ev";
    }

    @GetMapping(value = "/ServiceCenter/Event/search/{menu}/{text}/{page}")
    public String Esearch( @PathVariable("page") String page,
                           @PathVariable("text") String text,
                           @PathVariable("menu") String menu,
                           Model model,
                           Principal principal)  {
        List<Event> events = serviceCenterService.ev_search_all(menu, text);
        model.addAttribute("search", true);
        model.addAttribute("text", text);
        model.addAttribute("menu", menu);

        List<Event> notice_page = null;
        model.addAttribute("search", false);
        long no_size = events.size();
        long no_page = events.size()/15;
        long no_page_na = events.size()%15;
        long no_page_size = no_page/10;
        long no_page_size_na = no_page%10;

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("no_time", format1.format(today));

        if(no_page_size_na >= 1){
            no_page_size = no_page_size+1;
            model.addAttribute("no_page_size",no_page_size);
        }
        else {
            model.addAttribute("no_page_size",no_page_size);
        }

        if(no_page_na >= 1){
            no_page = no_page+1;
            model.addAttribute("no_page",no_page);
        }
        else {
            model.addAttribute("no_page",no_page);
        }
        model.addAttribute("no_size",events.size());

        // 페이지네이션
        long j=0;

        if(events.size()<15){
            model.addAttribute("eve",events);
        }else {
            for (int i = 0; i < no_size; i++) {
                notice_page = events.subList(0,15);

                if (i % 15 == 0) {
                    j = j + 1;
                    if (j == Long.parseLong(page)) {
                        model.addAttribute("eve",notice_page);
                        break;
                    } else {
                        events.subList(0,15).clear();

                        if(events.size()<15){
                            model.addAttribute("eve",events);
                            break;
                        }
                    }
                }
            }
        }

        return "/user/Service_center_ev";
    }

    @GetMapping(value = "/ServiceCenter/Event_Post/{num}")
    public String Event_no(@PathVariable("num") String num, Model model, HttpServletResponse response, Principal principal)  {
        Event event = serviceCenterService.ev_select_no(num);
        event.setEv_viewCount(event.getEv_viewCount()+1);
        serviceCenterService.ev_update(event);
        List<EventAttend> eventAttendList = serviceCenterService.evattd_find_EvNo(event.getEv_no().toString());
        model.addAttribute("all_attend",eventAttendList.size());
        if(principal != null){
            User user = userService.select(principal.getName());
            Long Ppoint = bizRegService.findplupoint(user.getUser_no().toString());
            Long Mpoint = bizRegService.findmapoint(user.getUser_no().toString());
            List<EventAttend> eventAttends = serviceCenterService.evattd_find_UserEvNo(user.getUser_no().toString(),event.getEv_no().toString());
            model.addAttribute("my_attend",eventAttends.size());
            if(Ppoint!=null){
                if(Mpoint!=null){
                    if(Ppoint>Mpoint){
                        model.addAttribute("point",Ppoint-Mpoint);
                    }else {
                        model.addAttribute("point",0);
                    }
                }else {
                    model.addAttribute("point",Ppoint);
                }
            }else {
                model.addAttribute("point",0);
            }
        }
        model.addAttribute("eve",event);
        return "/service/ev_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/ServiceCenter/Event_Delete")
    public String Event_Delete(Principal principal,HttpServletRequest request) throws  IOException{
        String noti_no= request.getParameter("noti_no");
        Event event = serviceCenterService.ev_select_no(noti_no);
        event.setEv_activation(false);
        serviceCenterService.ev_update(event);

        List<NoticeIMG> noticeIMGS = serviceCenterService.select_notiimg_num(noti_no);

        for( int i = 0; i < noticeIMGS.size() ; i++ )
        {
            try {
                noticeIMGS.get(i).setNoti_activation(false);
                serviceCenterService.notiimg_update(noticeIMGS.get(i));
                if("Windows_NT".equals(System.getenv().get("OS"))) {
                    String r = request.getSession().getServletContext().getRealPath("/");
                    int idx = r.indexOf("main");
                    String imgpath = r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMGS.get(i).getNotiimg_name();

                    File file = FileUtils.getFile(imgpath);
                    File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMGS.get(i).getNotiimg_name());
                    FileUtils.moveFile(file, fileToMove);
                }else {
                    String imgpath = Linux_Image_Upload_Path+"event/" + noticeIMGS.get(i).getNotiimg_name();

                    File file = FileUtils.getFile(imgpath);
                    File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"upload/" + noticeIMGS.get(i).getNotiimg_name());
                    FileUtils.moveFile(file, fileToMove);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "redirect:/ServiceCenter/Event/1";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/ServiceCenter/Event_Post_add")
    public String Post_add_ev(Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }
        return "/service/ev_Post_add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/ServiceCenter/Event_Post_add/add")
    public String Post_add_ev_add(HttpServletRequest request, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            User user = userService.select(principal.getName());
            Date today = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String body = request.getParameter("form_name");
            List<NoticeIMG> noticeIMG = serviceCenterService.select_notiimg(user.getUser_nic());
            log.info("사진 갯수"+noticeIMG.size());
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                log.info("폴문안에 body는 : "+body);
                log.info("사진명은 : "+noticeIMG.get(i).getNotiimg_name());
                if(body.contains(noticeIMG.get(i).getNotiimg_name())){

                    // 포함
                    body = body.replace("/image/upload/"+noticeIMG.get(i).getNotiimg_name(),"/image/notice/"+noticeIMG.get(i).getNotiimg_name());

                    try {

                        String imgpath;
                        if("Windows_NT".equals(System.getenv().get("OS"))){
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx =  r.indexOf("main");
                            imgpath =r.substring(0, idx)+"main/resources/static/image/upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx)+"main/resources/static/image/notice/"+noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            imgpath = Linux_Image_Upload_Path+"upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"notice/"+noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 미포함
                    noticeIMG.get(i).setNoti_activation(false);
                }
            }

            Event event = Event.builder()
                    .ev_activation(true)
                    .ev_manager(user.getUser_nic())
                    .ev_title(request.getParameter("title"))
                    .ev_text(body)
                    .ev_start(request.getParameter("start"))
                    .ev_stop(request.getParameter("stop"))
                    .ev_end(request.getParameter("end"))
                    .ev_item(Long.parseLong(request.getParameter("item")))
                    .ev_viewCount(0)
                    .build();
            eventRepository.save(event);

            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                noticeIMG.get(i).setNotiimg_postno(event.getEv_no().toString());
                serviceCenterService.notiimg_update(noticeIMG.get(i));
            }
        }

        return "redirect:/ServiceCenter/Event/1";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/ServiceCenter/Event_Post_Update/update")
    public String Post_update_ev_update(HttpServletRequest request, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            User user = userService.select(principal.getName());
            Date today = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String body = request.getParameter("form_name");
            List<NoticeIMG> noticeIMG_ol = serviceCenterService.select_notiimg_num(request.getParameter("num"));
            for( int i = 0; i < noticeIMG_ol.size() ; i++ )
            {
                if(body.contains(noticeIMG_ol.get(i).getNotiimg_name())){}
                else {
                    // 없다면
                    try {
                        noticeIMG_ol.get(i).setNoti_activation(false);
                        serviceCenterService.notiimg_update(noticeIMG_ol.get(i));

                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMG_ol.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMG_ol.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }
                        else {
                            String imgpath = Linux_Image_Upload_Path+"notice/"+noticeIMG_ol.get(i).getNotiimg_name();
                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"upload/" + noticeIMG_ol.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            List<NoticeIMG> noticeIMG = serviceCenterService.select_notiimg(user.getUser_nic());
            log.info("사진 갯수"+noticeIMG.size());
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                log.info("폴문안에 body는 : "+body);
                log.info("사진명은 : "+noticeIMG.get(i).getNotiimg_name());
                if(body.contains(noticeIMG.get(i).getNotiimg_name())){

                    // 포함
                    body = body.replace("/image/upload/"+noticeIMG.get(i).getNotiimg_name(),"/image/notice/"+noticeIMG.get(i).getNotiimg_name());

                    try {
                        if("Windows_NT".equals(System.getenv().get("OS"))) {
                            String r = request.getSession().getServletContext().getRealPath("/");
                            int idx = r.indexOf("main");
                            String imgpath = r.substring(0, idx) + "main/resources/static/image/upload/" + noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/notice/" + noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }else {
                            String imgpath = Linux_Image_Upload_Path+"upload/"+noticeIMG.get(i).getNotiimg_name();

                            File file = FileUtils.getFile(imgpath);
                            File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path+"notice/" + noticeIMG.get(i).getNotiimg_name());
                            FileUtils.moveFile(file, fileToMove);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // 미포함
                    noticeIMG.get(i).setNoti_activation(false);
                }
            }

            Event event = serviceCenterService.ev_select_no(request.getParameter("num"));
            event.setEv_title(request.getParameter("title"));
            event.setEv_text(body);
            event.setEv_start(request.getParameter("start"));
            event.setEv_stop(request.getParameter("stop"));
            event.setEv_end(request.getParameter("end"));
            event.setEv_item(Long.parseLong(request.getParameter("item")));
            eventRepository.save(event);
            for( int i = 0; i < noticeIMG.size() ; i++ )
            {
                noticeIMG.get(i).setNotiimg_postno(event.getEv_no().toString());
                serviceCenterService.notiimg_update(noticeIMG.get(i));
            }
        }

        return "redirect:/ServiceCenter/Event/1";
    }

    @GetMapping(value = "/ServiceCenter/Event_Update/{num}")
    public String Event_Update(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response) throws IOException {
        if(principal == null){
            scriptUtils.alertAndMovePage(response, "로그인 해주세요.", "/login");
        }else {
            Event event = serviceCenterService.ev_select_no(num);
            model.addAttribute("eve",event);
            return "/service/ev_Post_update";
        }
        return "redirect:/ServiceCenter/Event_Post/"+num;
    }

    @GetMapping(value = "/ServiceCenter/Event_attend/{num}")
    public String Event_attend(@PathVariable("num") String num, Model model, Principal principal, HttpServletResponse response){
        // num 이벤트 번호
        User user = userService.select(principal.getName());

        Point point = Point.builder()
                .user_no(user.getUser_no())
                .point(Long.parseLong("100"))
                .point_get_out("eve")
                .get_no_use_no(Long.parseLong(num))
                .get_plus_mi(false)
                .build();
        pointRepository.save(point);

        EventAttend eventAttend = EventAttend.builder()
                .ev_no(Long.parseLong(num))
                .point_no(point.getPoint_no())
                .user_no(user.getUser_no())
                .build();
        eventAttendRepository.save(eventAttend);

        return "redirect:/ServiceCenter/Event_Post/"+num;
    }

    // 이벤트 관리자
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/event")
    public String buisnessman(Model model){

        List<Event> events = serviceCenterService.ev_all();
        model.addAttribute("eve",events);

        return "admin/admin_event";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/event_detail/{num}")
    public String buisnessman_page(Model model,@PathVariable("num") Long num){

        Event event = serviceCenterService.ev_select_no(num.toString());
        model.addAttribute("eve",event);

        List<EventWin> eventWin = serviceCenterService.evwin_find_EvNo(num.toString());
        model.addAttribute("win",eventWin);

        List<User> users = new ArrayList<>();
        for(int i = 0; i<eventWin.size(); i++){
            User user = userService.select(eventWin.get(i).getUser_no());
            users.add(user);
        }
        model.addAttribute("win_user",users);

        return "admin/admin_event_detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/event_win")
    public String event_win(Model model) throws ParseException {

        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        List<Event> events = serviceCenterService.ev_find_item();
        for(int i = 0; i<events.size(); i++){
            Date endDay = format1.parse(events.get(i).getEv_end());
            if(date.after(endDay)) {
                // 추첨
                events.get(i).setEv_item_activation(true);
                List<EventAttend> eventAttend = serviceCenterService.evattd_find_EvNo(events.get(i).getEv_no().toString());
                Collections.shuffle(eventAttend);
                if (events.get(i).getEv_item() < eventAttend.size()) {
                    for (int j = 0; j < events.get(i).getEv_item(); j++) {
                        EventWin eventWIn = EventWin.builder()
                                .user_no(eventAttend.get(j).getUser_no())
                                .ev_no(eventAttend.get(j).getEv_no())
                                .ranking(new Long(j+1))
                                .build();
                        eventWinRepository.save(eventWIn);
                    }
                } else {
                    for (int j = 0; j < eventAttend.size(); j++) {
                        EventWin eventWIn = EventWin.builder()
                                .user_no(eventAttend.get(j).getUser_no())
                                .ev_no(eventAttend.get(j).getEv_no())
                                .ranking(new Long(j+1))
                                .build();
                        eventWinRepository.save(eventWIn);
                    }
                }
                eventRepository.save(events.get(i));
            }
        }

        return "redirect:/admin/event";
    }
}

