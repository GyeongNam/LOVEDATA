package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.Comment;
import com.project.love_data.security.model.AuthUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Log4j2
@Controller
public class ServiceController {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    LocationService locService;
    @Autowired
    ImageService imgService;
    @Autowired
    CommentService comService;

    final static int MAX_LOC_LIST_SIZE = 4;
    final static int MAX_COM_COUNT = 5;
    final static int MAX_UPLOAD_COUNT = 10;
    final static int MIN_UPLOAD_COUNT = 3;
    List<String> tagList = new ArrayList<>();

    @RequestMapping("/service/loc_registration")
    public String loc_Reg() {
        return "/service/loc_registration";
    }

    @RequestMapping("/popup/jusoPopup")
    public String popup_juso() {
        return "/popup/jusoPopup";
    }

    @PostMapping("/service/loc/tags")
    public void locGetTagsList(@RequestParam("tags[]") String[] tagArray) {
        tagList = Arrays.asList(tagArray);

        if (tagList != null) {
            log.info("태그 등록 성공");
        } else {
            log.info("태그 등록 실패");
        }
    }

    @GetMapping("/service/loc_registration/regData")
    public String locRegistartionDataNoAccess() {
        log.info("Access Invalid(Shouldn't Access with GET Method)");
        return "redirect:/service/loc_recommend";
    }

    @PostMapping("/service/loc_registration/regData")
    public String locRegistrationData(@RequestParam("files") List<MultipartFile> fileList,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        List<String> filePath = null;
        Map<String, String> reqParam = new HashMap<>();

        reqParam.put("name", request.getParameter("name"));
        reqParam.put("tel", request.getParameter("tel"));
        reqParam.put("info", request.getParameter("info"));
        reqParam.put("zipNo", request.getParameter("zipNo"));
        reqParam.put("roadAddr", request.getParameter("roadAddrPart1"));
        reqParam.put("addrDetail", request.getParameter("addrDetail"));
        reqParam.put("siDoName", request.getParameter("siNm"));
        reqParam.put("siGunGuName", request.getParameter("sggNm"));
//        // Todo Debug 목적용 코드 나중에 삭제할 것
        if (request.getParameter("user_no") != null) {
            reqParam.put("user_no", (request.getParameter("user_no")));
        } else {
            reqParam.put("user_no", (request.getParameter("user_no_debug")));
        }

        if (tagList.isEmpty()) {
            log.warn("No Location Tag Found (Must add tag before submit location)");
            return "redirect:/service/loc_recommend";
        }

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/loc_recommend";
        }

        LocationDTO dto = locService.entityToDto(locService.register(reqParam, tagList, filePath));
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/service/loc_recommend";
    }

    @GetMapping(value = "/service/loc_recommend")
    public String goToLocRecommendList() {
        return "redirect:/service/loc_recommend/list";
    }

    @GetMapping(value = "/service/loc_detail")
    public String locDetail(Long locNo, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                            Model model,
                            HttpServletRequest request) {
        if (locNo != null){
            locService.incViewCount(locNo);
            LocationDTO dto = locService.selectLocDTO(locNo);
            pageRequestDTO.setSize(MAX_COM_COUNT);
            PageResultDTO<CommentDTO, Comment> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
            = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION);

            model.addAttribute("dto", dto);
            model.addAttribute("resComDTO", resultCommentDTO);

            return "/service/loc_detail";
        }

        return "/service/loc_recommend";
    }

    @GetMapping(value = "/service/loc_recommend/list")
    public String locRecommendList(HttpServletRequest request,
                                   PageRequestDTO pageRequestDTO,
                                   Authentication authentication,
                                   Model model) {
        // 최대 4개의 장소 표시
        pageRequestDTO.setSize(MAX_LOC_LIST_SIZE);
        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);
        model.addAttribute("result", resultDTO);

        if(authentication != null) {
            AuthUserModel authUser = (AuthUserModel) authentication.getPrincipal();
            log.info(authUser.getUser_no());
        }

        return "/service/loc_recommend";
    }

    @GetMapping("/service/loc_edit")
    public String locEdit(Long locNo, Model model) {
        if (locNo != null){
            LocationDTO dto = locService.selectLocDTO(locNo);

            model.addAttribute("dto", dto);

            return "/service/loc_edit";
        }

        return "/service/loc_recommend";
    }
}