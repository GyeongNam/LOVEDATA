package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Review;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/service/rev_registration")
    public String regComment(HttpServletRequest request, Model model, @RequestParam("files") List<MultipartFile> fileList,
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
        Integer totalRate = Integer.valueOf(request.getParameter("rev_total_rate"));
        Integer locRate = Integer.valueOf(request.getParameter("rev_loc_rate"));
        Integer moveRate = Integer.valueOf(request.getParameter("rev_move_rate"));
        Integer timeRate = Integer.valueOf(request.getParameter("rev_time_rate"));
        Integer revisitRate = Integer.valueOf(request.getParameter("rev_revisit_rate"));
        String resultType = request.getParameter("resultType");
        List<String> filePath = null;

        Map<String, Integer> scoreMap = revService.getScoreMap(totalRate, locRate,
                moveRate, timeRate, revisitRate);

        Review entity = revService.createRevEntity(corNo, userNo, revContent, scoreMap);

        entity = revService.update(entity);

        if (!fileList.isEmpty()) {
            filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                    UploadFileCount.MULTIPLE, REV_MIN_UPLOAD_COUNT,
                    REV_MAX_UPLOAD_COUNT, request);

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

        if (entity == null) {
            log.info("Error Occurs during Comment entity creation");
            log.info("Please check Input value");
            return "redirect:/service/cor_detail?locNo=" + corNo;
        }

        return "redirect:/service/cor_detail?corNo=" + corNo + "&page=1";
    }

//    @PostMapping("/service/com_edit")
//    public String editComment(HttpServletRequest request, Model model, Principal principal,
//                              @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
//        Long locNo = Long.valueOf(request.getParameter("locNo"));
//        String userEmail = request.getParameter("userEmail");
//        String cmt_uuid = request.getParameter("cmt_uuid");
//        String cmtContent = request.getParameter("cmtContent");
//        boolean returnFlag = false;
//
//        Location loc_temp = locService.selectLoc(locNo);
//        Comment cmt_temp = cmtService.select(cmt_uuid);
//
//        if (!userEmail.equals(principal.getName())){
//            log.warn(principal.getName());
//            log.warn("Not Match with Comment Posted User and Current Logined User");
//            log.warn("Please Check User");
//            returnFlag = true;
//        }
//
//        if (cmt_temp == null) {
//            log.warn(cmt_uuid);
//            log.warn("Can't not find Comment");
//            log.warn("Please Check Comment uuid is match with current Comment");
//            returnFlag = true;
//        }
//
//        if (returnFlag || !cmt_temp.getLocation().getLoc_no().equals(loc_temp.getLoc_no())){
//            log.warn("Current Comment is not match with Location");
//            log.warn("Please Check Location");
//            returnFlag = true;
//        }
//
//        if (!returnFlag) {
//            cmt_temp.setCmtContent(cmtContent);
//            cmtService.update(cmt_temp);
//        }
//
//        LocationDTO dto = locService.selectLocDTO(locNo);
//        pageRequestDTO.setSize(MAX_COM_COUNT);
//        PageResultDTO<CommentDTO, Comment> resultCommentDTO
////                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
//                = cmtService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION);
//
//        model.addAttribute("dto", dto);
//        model.addAttribute("resComDTO", resultCommentDTO);
//
//        return "redirect:/service/loc_detail?locNo=" + locNo;
//    }
}
