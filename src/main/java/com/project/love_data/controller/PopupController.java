package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.ControllerScriptUtils;
import com.project.love_data.businessLogic.service.ReviewImageService;
import com.project.love_data.businessLogic.service.ReviewService;
import com.project.love_data.dto.CourseDTO;
import com.project.love_data.dto.ReviewDTO;
import com.project.love_data.dto.ReviewImageDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.security.model.AuthUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Log4j2
@Controller
public class PopupController {
    @Autowired
    ReviewService revService;
    @Autowired
    ReviewImageService revImgService;
    @Autowired
    ControllerScriptUtils scriptUtils;

    @RequestMapping("/popup/jusoPopup")
    public String popup_juso() {
        return "/popup/jusoPopup";
    }

    @RequestMapping("/popup/locationSearchPopup")
    public String popupLocationSearch() {
        return "/popup/locationSearchPopup";
    }

    @RequestMapping("/popup/courseSearchPopup")
    public String popupCourseSearch() {
        return "/popup/courseSearchPopup";
    }

    @RequestMapping("/popup/reviewRegisterPopup")
    public String popupReviewRegister() {
        return "/popup/reviewRegisterPopup";
    }

    @RequestMapping("/popup/reviewEditPopup")
    public String popupReviewRegister(@RequestParam String revNo, Authentication authentication, Model model,
                                                                        HttpServletResponse response) throws IOException {

        if (authentication == null) {
            log.info("Anonymous User Can't access to Review Edit");
            model.addAttribute("alertMsg", "로그인 하지 않은 상태로 리뷰를 수정할 수 없습니다.");

            return "/alert/alertAndClose";
        }

        ReviewDTO reviewDTO = revService.selectDTO(Long.valueOf(revNo));

        if (reviewDTO == null) {
            log.info("Can't find matching Review of given RevNo");
            scriptUtils.alertPageout(response, "해당 리뷰를 찾을 수 없습니다.");
        }

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();

        if (!reviewDTO.getUserNo().equals(authUserModel.getUser_no())) {
            log.info("Current authUser are not match with review User");
            scriptUtils.alertPageout(response, "리뷰를 작성하지 않았으므로, 리뷰를 수정할 수 없습니다.");
        }

        List<ReviewImageDTO> revImgList = null;
        List<ReviewImage> tempRevImgList = revImgService.getAllLiveImageByRevNo(reviewDTO.getRevNo());

        if (tempRevImgList != null) {
            revImgList = new ArrayList<>();

            for (ReviewImage image : tempRevImgList) {
                revImgList.add(revImgService.entityToDTO(image));
            }
        }

        model.addAttribute("dto", reviewDTO);
        model.addAttribute("revImgList", revImgList);

        return "/popup/reviewEditPopup";
    }
}
