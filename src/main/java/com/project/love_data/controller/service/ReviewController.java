package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Point;
import com.project.love_data.model.service.Review;
import com.project.love_data.repository.PointRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.project.love_data.util.ConstantValues.*;

@Controller
@Log4j2
public class ReviewController {
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    ReviewService revService;
    @Autowired
    ReviewImageService revImgService;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    ControllerScriptUtils scriptUtils;
    @Autowired
    PointRepository pointRepository;

    @PostMapping(value = "/service/rev_registration")
    public String regReview(HttpServletRequest request, Model model, @RequestParam("files") List<MultipartFile> fileList,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        Set<String> keys = request.getParameterMap().keySet();

//        for (String key : keys) {
//            log.info(key + " : " + request.getParameter(key));
//        }
//        log.info(fileList);
//        log.info(fileList.size());

        Long corNo = Long.valueOf(request.getParameter("cor_no"));
        Long userNo = Long.valueOf(request.getParameter("user_no"));
        String revContent = request.getParameter("rev_content_input");
        Float totalRate = Float.valueOf(request.getParameter("rev_total_rate"));
        Integer locRate = Integer.valueOf(request.getParameter("rev_loc_rate"));
        Integer moveRate = Integer.valueOf(request.getParameter("rev_move_rate"));
        Integer timeRate = Integer.valueOf(request.getParameter("rev_time_rate"));
        Integer revisitRate = Integer.valueOf(request.getParameter("rev_revisit_rate"));
        String resultType = request.getParameter("resultType");
        List<String> filePath = null;

        Map<String, Integer> scoreMap = revService.getScoreMap(locRate,
                moveRate, timeRate, revisitRate);

        Review entity = revService.createRevEntity(corNo, userNo, revContent, scoreMap, totalRate);

        entity = revService.update(entity);

        if (entity == null) {
            log.info("Error Occurs during Comment entity creation");
            log.info("Please check Input value");
            return "redirect:/service/cor_detail?locNo=" + corNo;
        }

        Point point = Point.builder()
                .user_no(userNo)
                .point(Long.parseLong("100"))
                .point_get_out("rev")
                .get_no_use_no(entity.getRevNo())
                .get_plus_mi(true)
                .build();
        pointRepository.save(point);

        if (!fileList.isEmpty()) {
            filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                    REV_MIN_UPLOAD_COUNT, REV_MAX_UPLOAD_COUNT, PathType.REV, request);

            if (filePath == null) {
                log.warn("파일이 제대로 저장되지 않았습니다.");
                return "redirect:/service/cor_detail?corNo=" + corNo + "&page=1";
            }

            for (int i = 0; i < filePath.size(); i += 2) {
                ReviewImage revImg = revImgService.getImageEntity(userNo,filePath.get(i),
                        filePath.get(i+1), corNo, entity.getRevNo(), (long) i/2);

                revImgService.update(revImg);
            }
        }

        return "redirect:/service/cor_detail?corNo=" + corNo + "&page=1";
    }

    @PostMapping("/service/rev_edit")
    public String editReview(HttpServletRequest request, HttpServletResponse response, Model model,  @RequestParam("files") List<MultipartFile> fileList,
                             Authentication authentication, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) throws IOException {
        Long revNo = Long.valueOf(request.getParameter("rev_no"));
        String revId = request.getParameter("rev_id");
        Long corNo = Long.valueOf(request.getParameter("cor_no"));
        Long userNo = Long.valueOf(request.getParameter("user_no"));
        String revContent = request.getParameter("rev_content_input");
        Float totalRate = Float.valueOf(request.getParameter("rev_total_rate"));
        Integer locRate = Integer.valueOf(request.getParameter("rev_loc_rate"));
        Integer moveRate = Integer.valueOf(request.getParameter("rev_move_rate"));
        Integer timeRate = Integer.valueOf(request.getParameter("rev_time_rate"));
        Integer revisitRate = Integer.valueOf(request.getParameter("rev_revisit_rate"));
        String resultType = request.getParameter("resultType");
        List<String> filePath = null;
        boolean returnFlag = false;

        Course course_temp = corService.selectCor(corNo);
        Review rev_temp = revService.select(revNo);
        Review entity = null;

        if (course_temp == null || rev_temp == null) {
            log.warn("No Matching Result of Given Course Or Review");
            scriptUtils.alertAndBackPage(response, "[오류 발생] 해당하는 코스나 리뷰에 관한 결과가 없습니다.");
            returnFlag = true;
        }

        if (!rev_temp.getUser_no().equals(userNo)) {
            log.warn("Not Match User of given userNo");
            scriptUtils.alertAndBackPage(response, "[오류 발생] 리뷰를 등록한 유저와 같지 않습니다.");
            returnFlag = true;
        }

        if (!rev_temp.getCorNo().equals(course_temp.getCor_no())){
            log.warn("Review isn't belong to Course");
            scriptUtils.alertAndBackPage(response, "[오류 발생] 리뷰가 해당 코스에 작성되어 있지 않습니다.");
            returnFlag = true;
        }

        if (!returnFlag) {
            rev_temp.setRevContent(revContent);
            rev_temp.setSc_loc(locRate);
            rev_temp.setSc_move(moveRate);
            rev_temp.setSc_time(timeRate);
            rev_temp.setSc_revisit(revisitRate);
            rev_temp.setSc_total(totalRate);
            rev_temp.set_modified(true);
            entity = revService.update(rev_temp);
            filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                    REV_MIN_UPLOAD_COUNT, REV_MAX_UPLOAD_COUNT, PathType.REV, request);
            revImgService.updateOldImage(revNo, filePath);
        }
        return "redirect:/service/cor_detail?corNo=" + corNo + "&page=1";
    }

    @PostMapping("/service/rev_del")
    public String delReview(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Long revNo = Long.valueOf(request.getParameter("rev_no"));
        String revId = request.getParameter("rev_id");
        Long corNo = Long.valueOf(request.getParameter("cor_no"));
        Long userNo = Long.valueOf(request.getParameter("user_no"));
        String returnURL = request.getParameter("returnURL");

        Course course_temp = corService.selectCor(corNo);
        Review rev_temp = revService.select(revNo);

        if (course_temp == null || rev_temp == null) {
            log.warn("No Matching Result of Given Course Or Review");
            model.addAttribute("alertMsg", "[오류 발생] 해당하는 코스나 리뷰에 관한 결과가 없습니다.");
            if (returnURL != null) {
                model.addAttribute("redirectURL", returnURL);
            } else {
                model.addAttribute("redirectURL", "/service/cor_detail?corNo=" + corNo + "&page=1");
            }
            return "/alert/alert";
        }

        if (!rev_temp.getUser_no().equals(userNo)) {
            log.warn("Not Match User of given userNo");
            model.addAttribute("alertMsg", "[오류 발생] 리뷰를 등록한 유저와 같지 않습니다.");
            if (returnURL != null) {
                model.addAttribute("redirectURL", returnURL);
            } else {
                model.addAttribute("redirectURL", "/service/cor_detail?corNo=" + corNo + "&page=1");
            }
            return "/alert/alert";
        }

        if (!rev_temp.getCorNo().equals(course_temp.getCor_no())){
            log.warn("Review isn't belong to Course");
            model.addAttribute("alertMsg", "[오류 발생] 리뷰가 해당 코스에 작성되어 있지 않습니다.");
            if (returnURL != null) {
                model.addAttribute("redirectURL", returnURL);
            } else {
                model.addAttribute("redirectURL", "/service/cor_detail?corNo=" + corNo + "&page=1");
            }
            return "/alert/alert";
        }

        revService.delete(revNo);

        if (returnURL != null) {
            return "redirect:" + returnURL;
        }

        return "redirect:/service/cor_detail?corNo=" + corNo + "&page=1";
    }

    @PostMapping("/service/rev_rollback")
    public String rollbackReview(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Long revNo = Long.valueOf(request.getParameter("rev_no"));
        String revId = request.getParameter("rev_id");
        Long corNo = Long.valueOf(request.getParameter("cor_no"));
        Long userNo = Long.valueOf(request.getParameter("user_no"));
        String returnURL = request.getParameter("returnURL");

        Course course_temp = corService.selectCor(corNo);
        Review rev_temp = revService.select(revNo);

        if (course_temp == null || rev_temp == null) {
            log.warn("No Matching Result of Given Course Or Review");
            model.addAttribute("alertMsg", "[오류 발생] 해당하는 코스나 리뷰에 관한 결과가 없습니다.");
            if (returnURL != null) {
                model.addAttribute("redirectURL", returnURL);
            } else {
                model.addAttribute("redirectURL", "/service/cor_detail?corNo=" + corNo + "&page=1");
            }
            return "/alert/alert";
        }

        if (!rev_temp.getUser_no().equals(userNo)) {
            log.warn("Not Match User of given userNo");
            model.addAttribute("alertMsg", "[오류 발생] 리뷰를 등록한 유저와 같지 않습니다.");
            if (returnURL != null) {
                model.addAttribute("redirectURL", returnURL);
            } else {
                model.addAttribute("redirectURL", "/service/cor_detail?corNo=" + corNo + "&page=1");
            }
            return "/alert/alert";
        }

        if (!rev_temp.getCorNo().equals(course_temp.getCor_no())){
            log.warn("Review isn't belong to Course");
            model.addAttribute("alertMsg", "[오류 발생] 리뷰가 해당 코스에 작성되어 있지 않습니다.");
            if (returnURL != null) {
                model.addAttribute("redirectURL", returnURL);
            } else {
                model.addAttribute("redirectURL", "/service/cor_detail?corNo=" + corNo + "&page=1");
            }
            return "/alert/alert";
        }

        revService.rollback(revNo);

        if (returnURL != null) {
            return "redirect:" + returnURL;
        }

        return "redirect:/service/cor_detail?corNo=" + corNo + "&page=1";
    }
}
