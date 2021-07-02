package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.model.service.UserRecentLoc;
import com.project.love_data.model.user.User;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.security.service.UserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

import static com.project.love_data.util.ConstantValues.*;

@Log4j2
@Controller
public class LocationController {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    LocationService locService;
    @Autowired
    ImageService imgService;
    @Autowired
    CommentService comService;
    @Autowired
    UserService userService;
    @Autowired
    UserRecentLocService userRecentLocService;
    @Autowired
    UserDetailsService userDetailsService;
    List<String> tagList = new ArrayList<>();

    @RequestMapping("/service/loc_registration")
    public String loc_Reg(Model model) {
        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
        model.addAttribute("tagList", tagList);

        return "/service/loc_registration";
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

        Location entity = locService.register(reqParam, tagList, filePath);
        User user = userService.select(Long.parseLong(reqParam.get("user_no")));
        //Todo 업로드한 장소테이블에 정보 인서트 하기
//        user.addUploadLocation(entity);
        userService.update(user);
        LocationDTO dto = locService.entityToDto(entity);

        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/service/loc_recommend";
    }

    @GetMapping(value = "/service/loc_recommend")
    public String goToLocRecommendList() {
        return "redirect:/service/loc_recommend/list";
    }

    @GetMapping(value = "/service/loc_detail")
    public String locDetail(Long locNo, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Authentication authentication,
                            Model model, HttpServletRequest request) {
        if (locNo != null) {
            locService.incViewCount(locNo);
            LocationDTO dto = locService.selectLocDTO(locNo);
            pageRequestDTO.setSize(MAX_COM_COUNT);
            PageResultDTO<CommentDTO, Comment> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION);

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                if (authentication.isAuthenticated()) {
                    AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
                    UserRecentLoc item = userRecentLocService.register(locNo, authUserModel.getUser_no());
                    if (item == null) {
                        log.warn(authUserModel.getUser_no() + "의 최근 접속한 기록을 추가하는 과정에서 문제가 발생하였습니다.");
                        log.warn("장소 번호 (" + locNo + ")");
                    } else {
                        log.info("기록 추가 성공");
                    }
                    log.info("temp");
                }
            }

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
        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
        pageRequestDTO.setSize(MAX_LOC_LIST_SIZE);
        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);
        model.addAttribute("result", resultDTO);
        model.addAttribute("tagList", tagList);

//        if(authentication != null) {
//            AuthUserModel authUser = (AuthUserModel) authentication.getPrincipal();
////            log.info(authUser.getUser_no());
//        }

        return "/service/loc_recommend";
    }

    @GetMapping("/service/loc_edit")
    public String locEdit(Long locNo, Model model) {
        if (locNo != null) {
            LocationDTO dto = locService.selectLocDTO(locNo);

            List<LocationTag> tagList = Arrays.asList(LocationTag.values());

            model.addAttribute("dto", dto);
            model.addAttribute("tagList", tagList);

            return "/service/loc_edit";
        }

        return "/service/loc_recommend";
    }

    @PostMapping("/service/loc_edit/regData")
    public String locEditData(@RequestParam("files") List<MultipartFile> fileList,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        List<String> filePath = null;
        Map<String, String> reqParam = new HashMap<>();

        reqParam.put("loc_no", request.getParameter("loc_no"));
        reqParam.put("loc_uuid", request.getParameter("loc_uuid"));
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

        Location loc_no_Test = locService.selectLoc(Long.valueOf(reqParam.get("loc_no")));
        Location loc_uuid_Test = locService.selectLoc(reqParam.get("loc_uuid"));

        // 장소 수정 정보에 입력된 장소가 맞는지 확인하는 과정 (Validating)
        if (loc_no_Test == null || loc_uuid_Test == null || !loc_uuid_Test.equals(loc_no_Test)) {
            log.warn("Given Location Edit Information doesn't match with DB");
            log.info("Please Check the Value");
            return "redirect:/service/loc_recommend";
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

        LocationDTO dto = locService.entityToDto(locService.edit(reqParam, tagList, filePath));
//        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/service/loc_recommend";
    }
}