package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.PointRepository;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.security.model.UserRole;
import com.project.love_data.security.service.UserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    LocationImageService imgService;
    @Autowired
    CommentService comService;
    @Autowired
    UserService userService;
    @Autowired
    UserRecentLocService userRecentLocService;
    @Autowired
    UserLikeLocService userLikeLocService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserLikeCmtService userLikeCmtService;
    @Autowired
    UserDislikeCmtService userDislikeCmtService;
    @Autowired
    ControllerScriptUtils scriptUtils;
    @Autowired
    PointRepository pointRepository;

    List<String> tagList = new ArrayList<>();

    @RequestMapping("/service/loc_registration")
    public String loc_Reg(Model model, HttpServletRequest request) {
        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
        model.addAttribute("tagList", tagList);

        log.info(request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
        log.info(SecurityContextHolder.getContext().getAuthentication());

        return "/service/loc_registration";
    }

    @PostMapping("/service/loc/tags")
    @ResponseBody
    public String locGetTagsList(@RequestParam("tags[]") String[] tagArray, Authentication authentication, HttpServletRequest request) {
//        if (authentication == null) {
//            return "Authentication Failed";
//        }
//
//        if (!authentication.isAuthenticated()) {
//            return "Authentication Failed";
//        }

        tagList = Arrays.asList(tagArray);
        log.info(request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
        log.info(SecurityContextHolder.getContext().getAuthentication());

        if (tagList != null) {
//            log.info("태그 등록 성공");
            return "Tag register Success";
        } else {
//            log.info("태그 등록 실패");
            return "Tag register Fail";
        }
    }

    @GetMapping("/service/loc_registration/regData")
    public String locRegistrationDataNoAccess() {
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
        reqParam.put("fullAddr", request.getParameter("fullRoadAddr"));
        reqParam.put("roadAddr", request.getParameter("roadAddr"));
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

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, PathType.LOC, request);

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

        log.info(request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
        log.info(SecurityContextHolder.getContext().getAuthentication());


        Point point = Point.builder()
                .user_no(Long.parseLong(request.getParameter("user_no")))
                .point(Long.parseLong("500"))
                .point_get_out("loc")
                .get_no_use_no(dto.getLoc_no())
                .get_plus_mi(true)
                .build();
        pointRepository.save(point);
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
            if (locService.selectLoc(locNo) == null) {
                model.addAttribute("isNullLocation", true);
                return "/service/loc_detail";
            } else {
                model.addAttribute("isNullLocation", false);
            }

            boolean isAdmin = false;
            if (authentication != null) {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        isAdmin = true;
                        break;
                    }
                }
            }

            locService.incViewCount(locNo);
            LocationDTO dto = locService.selectLocDTO(locNo);
            pageRequestDTO.setSize(MAX_COM_COUNT);
            PageResultDTO<CommentDTO, Comment> resultCommentDTO = null;
//            PageResultDTO<CommentDTO, Comment> bestCommentDTO = null;
            List<Boolean> cmtLikeList = new ArrayList<>();
            List<Boolean> cmtDislikeList = new ArrayList<>();
            List<CommentDTO> bestCmtList = new ArrayList<>();
            List<Boolean> bestCmtLikeList = new ArrayList<>();
            List<Boolean> bestCmtDislikeList = new ArrayList<>();
            List<Integer> bestCmtIndexList = new ArrayList<>();
            List<Integer> cmtIndexList = new ArrayList<>();
            boolean bestCmtMatchFlag = false;

            if (isAdmin) {
                resultCommentDTO = comService.getCmtPage(pageRequestDTO, CommentSortType.IDX_DES,
                        CommentSearchType.All);
            } else {
                resultCommentDTO = comService.getCmtPage(pageRequestDTO);
            }
            pageRequestDTO.setSize(3);

            for (int i = 0; i < resultCommentDTO.getDtoList().size(); i++) {
                User user = userService.selectLive(resultCommentDTO.getDtoList().get(i).getUser().getUser_no());

                if (user == null) {
                    resultCommentDTO.getDtoList().get(i).getUser().setUser_nic("삭제된 유저");
                    resultCommentDTO.getDtoList().get(i).getUser().setProfile_pic("/image/icon/user/user.png");
                }
            }

            List<Comment> bestCmtHolder = comService.getBestComment(locNo);
            if (bestCmtHolder != null ) {
                if (!bestCmtHolder.isEmpty()) {
                    for (Comment cmt : bestCmtHolder) {
                        CommentDTO cmtDTO = comService.entityToDto(cmt);
                        User user = userService.selectLive(cmt.getUser().getUser_no());
                        if (user == null) {
                            // 삭제된 유저의 경우
                            cmtDTO.getUser().setUser_nic("삭제된 유저");
                            cmtDTO.getUser().setProfile_pic("/image/icon/user/user.png");
                        }
                        bestCmtList.add(comService.entityToDto(cmt));
                    }
                }
            }

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
                if (authentication.isAuthenticated()) {
                    UserRecentLoc item = userRecentLocService.register(locNo, authUserModel.getUser_no());
//                    if (item == null) {
//                        log.warn(authUserModel.getUser_no() + "의 최근 접속한 기록을 추가하는 과정에서 문제가 발생하였습니다.");
//                        log.warn("장소 번호 (" + locNo + ")");
//                    } else {
//                        log.info("기록 추가 성공");
//                    }
                    if (userLikeLocService.selectByLocNoAndUserNo(locNo, authUserModel.getUser_no()) != null) {
                        model.addAttribute("isLiked", true);
                    } else {
                        model.addAttribute("isLiked", false);
                    }

                    for (int i = 0; i < resultCommentDTO.getDtoList().size(); i++) {
                        UserLikeCmt userLikeCmt = userLikeCmtService.selectBycmtNoAndUserNo(resultCommentDTO.getDtoList().get(i).getCmtNo(), authUserModel.getUser_no());
                        UserDislikeCmt userDislikeCmt = userDislikeCmtService.selectBycmtNoAndUserNo(resultCommentDTO.getDtoList().get(i).getCmtNo(), authUserModel.getUser_no());

                        if (userLikeCmt != null && userDislikeCmt == null) {
                            cmtLikeList.add(true);
                            cmtDislikeList.add(false);
                        } else if (userLikeCmt == null && userDislikeCmt != null) {
                            cmtLikeList.add(false);
                            cmtDislikeList.add(true);
                        } else {
                            cmtLikeList.add(null);
                            cmtDislikeList.add(null);
                        }

                        boolean cmtIndexMatchFlag = false;
                        for (int j = 0; j < bestCmtList.size(); j++) {
                            if (resultCommentDTO.getDtoList().get(i).equals(bestCmtList.get(j))) {
                                cmtIndexList.add(j);
                                cmtIndexMatchFlag = true;
                                break;
                            }
                        }

                        if (!cmtIndexMatchFlag) {
                            cmtIndexList.add(-1);
                        }
                    }

                    for (int i = 0; i < bestCmtList.size(); i++) {
                        UserLikeCmt userLikeBestCmt = userLikeCmtService.selectBycmtNoAndUserNo(bestCmtList.get(i).getCmtNo(), authUserModel.getUser_no());
                        UserDislikeCmt userDislikeBestCmt = userDislikeCmtService.selectBycmtNoAndUserNo(bestCmtList.get(i).getCmtNo(), authUserModel.getUser_no());

                        if (userLikeBestCmt != null && userDislikeBestCmt == null) {
                            bestCmtLikeList.add(true);
                            bestCmtDislikeList.add(false);
                        } else if (userLikeBestCmt == null && userDislikeBestCmt != null) {
                            bestCmtLikeList.add(false);
                            bestCmtDislikeList.add(true);
                        } else {
                            bestCmtLikeList.add(null);
                            bestCmtDislikeList.add(null);
                        }

                        bestCmtMatchFlag = false;
                        Integer index = null;
                        for (int j = 0; j < resultCommentDTO.getDtoList().size(); j++) {
                            if (resultCommentDTO.getDtoList().get(j).equals(bestCmtList.get(i))) {
                                index = j;
                                bestCmtMatchFlag = true;
                                break;
                            }
                        }

                        if (bestCmtMatchFlag) {
                            bestCmtIndexList.add(index);
                        } else {
                            bestCmtIndexList.add(-1);
                        }
                    }
                }
            } else {
                model.addAttribute("isLiked", false);
            }

            User userEntity = userService.select(dto.getUser_no());
            UserDTO userDTO = null;
            if (userEntity != null) {
                userDTO = userService.entityToDto(userEntity);
            }

            if (userDTO.getRoleSet().contains(UserRole.BIZ.toString())) {
                model.addAttribute("isBizUploader", true);
            } else {
                model.addAttribute("isBizUploader", false);
            }

            model.addAttribute("dto", dto);
            model.addAttribute("resComDTO", resultCommentDTO);
            model.addAttribute("cmtLikeList", cmtLikeList);
            model.addAttribute("cmtDislikeList", cmtDislikeList);
            model.addAttribute("bestCmtList", bestCmtList);
            model.addAttribute("bestCmtLikeList", bestCmtLikeList);
            model.addAttribute("bestCmtDislikeList", bestCmtDislikeList);
            model.addAttribute("bestCmtIndexList", bestCmtIndexList);
            model.addAttribute("cmtIndexList", cmtIndexList);
            model.addAttribute("userDTO", userDTO);

            return "/service/loc_detail";
        }

        return "/service/loc_recommend";
    }

    @GetMapping(value = "/service/loc_recommend/list")
    public String locRecommendList(HttpServletRequest request,
//                                   PageRequestDTO pageRequestDTO,
                                   Authentication authentication,
                                   Model model) {
        String pageNumber = request.getParameter("page");
        if (pageNumber == null) {
            pageNumber = "1";
        }
        int pageNum = Integer.parseInt(pageNumber);

        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .size(MAX_LOC_LIST_SIZE)
                .sortCriterion(SortCriterion.VIEW)
                .page(pageNum)
                .build();
        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);
        List<Boolean> isLikedList = new ArrayList<>();

        if (authentication == null) {
            for (int i = 0; i < resultDTO.getSize(); i++) {
                isLikedList.add(false);
            }
        } else {
            AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
            Long user_no = authUserModel.getUser_no();
            for (int i = 0; i < resultDTO.getDtoList().size(); i++) {
                Long loc_no = resultDTO.getDtoList().get(i).getLoc_no();
                UserLikeLoc item = userLikeLocService.selectByLocNoAndUserNo(loc_no, user_no);
                if (item != null) {
                    isLikedList.add(true);
                } else {
                    isLikedList.add(false);
                }
            }
        }

        List<KoreanDistrict> korDistricts = Arrays.asList(KoreanDistrict.values());

        model.addAttribute("result", resultDTO);
        model.addAttribute("tagList", tagList);
        model.addAttribute("isLikedList", isLikedList);
        model.addAttribute("korDistrict", korDistricts);

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
        reqParam.put("fullAddr", request.getParameter("fullRoadAddr"));
        reqParam.put("roadAddr", request.getParameter("roadAddr"));
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

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, PathType.LOC, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/loc_recommend";
        }

        LocationDTO dto = locService.entityToDto(locService.edit(reqParam, tagList, filePath));
//        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/service/loc_recommend";
    }

    @GetMapping(value = "/service/loc_recommend/search")
    public String getSearchValue(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("keyword");
        String order = request.getParameter("sortOrder");
        String tagString = request.getParameter("tags");
        String type = request.getParameter("searchType");
        String pageNumber = request.getParameter("page");
        String district = request.getParameter("district");
        if (pageNumber == null) {
            pageNumber = "1";
        }
        if (district == null) {
            district = KoreanDistrict.전국.name();
        }
        int pageNum = Integer.parseInt(pageNumber);

        SortingOrder sortingOrder = null;
        SortCriterion sortCriterion = null;
        SearchType searchType = SearchType.valueOf(type);
        List<String> tempList = Arrays.asList(tagString.split(","));
        List<String> tagListStr = new ArrayList<>();
        for (String s : tempList) {
            if ("".equals(s)) {
                continue;
            } else {
                tagListStr.add(s);
            }
        }

        // 일반 유저는 삭제된 장소 항목을 볼 수 없음
        switch (searchType) {
            case DISABLED:
                searchType = SearchType.NONE;
                break;
            case DISABLED_TAG:
                searchType = SearchType.TAG;
                break;
            case DISABLED_TITLE:
                searchType = SearchType.TITLE;
                break;
            case DISABLED_TITLE_TAG:
                searchType = SearchType.TITLE_TAG;
                break;
        }

        switch (order) {
            case "LIKE_DES":
                // 좋아요 순
                sortCriterion = SortCriterion.LIKE;
                sortingOrder = SortingOrder.DES;
                break;
            case "DATE_DES":
                // 최신 등록순
                sortCriterion = SortCriterion.DATE;
                sortingOrder = SortingOrder.DES;
                break;
            case "DATE_ASC":
                // 오래된 등록순
                sortCriterion = SortCriterion.DATE;
                sortingOrder = SortingOrder.ASC;
                break;
            case "VIEW_DES":
                // 조회순
            default:
                sortCriterion = SortCriterion.VIEW;
                sortingOrder = SortingOrder.DES;
                break;
        }

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .size(MAX_LOC_LIST_SIZE)
                .searchType(searchType)
                .keyword(keyword)
                .tagList(tagListStr)
                .sortCriterion(sortCriterion)
                .sortingOrder(sortingOrder)
                .districtType(KoreanDistrict.valueOf(district))
                .page(pageNum)
                .build();

        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);

        if (resultDTO.getTotalPage() < pageNum) {
            if (resultDTO.getTotalPage() == 0) {
                model.addAttribute("isEmptyResult", true);
            } else {
                model.addAttribute("isRequestPageNumberExceed", true);
            }
        } else {
            model.addAttribute("isRequestPageNumberExceed", false);
        }

        List<LocationTag> tags = Arrays.asList(LocationTag.values());
        List<String> activeTags = tagListStr;

        List<KoreanDistrict> korDistricts = Arrays.asList(KoreanDistrict.values());

        model.addAttribute("result", resultDTO);
        model.addAttribute("tagList", tags);
        model.addAttribute("activeTags", activeTags);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortOrder", order);
        model.addAttribute("searchType", searchType);
        model.addAttribute("korDistrict", korDistricts);
        model.addAttribute("activeDistrict", district);

//        log.info("active tags : " + activeTags);

        return "/service/loc_recommend_search";
    }

    @PostMapping("/service/loc_delete")
    public String locDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            Authentication authentication, Long locNo) {
        if (authentication == null) {
            return "redirect:/service/loc_recommend";
        }

        Location entity = locService.selectLoc(locNo);

        if (entity == null) {
            return "redirect:/service/loc_recommend";
        }

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
        Long user_no = authUserModel.getUser_no();
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) authUserModel.getAuthorities();

        if (!entity.getUser_no().equals(user_no) && !request.isUserInRole("ROLE_ADMIN")) {
            log.warn("장소 삭제를 요청한 유저가 장소를 등록한 유저와 같지 않습니다.");
            return "redirect:/service/loc_recommend";
        }

        if (!locService.delete(entity.getLoc_no())) {
            log.warn("장소를 삭제하는 과정에서 오류 발생!");
            model.addAttribute("alertMsg", "장소를 삭제하는 과정에서 오류 발생!");
            model.addAttribute("redirectURL", "/service/loc_recommend");
            return "/alert/alert";
        }

        return "redirect:/service/loc_recommend";
    }

    @PostMapping("/service/loc_perma_delete")
    public String locPermaDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            Authentication authentication, Long locNo) {
        if (authentication == null) {
            return "redirect:/service/loc_recommend";
        }

        Location entity = locService.selectLoc(locNo);

        if (entity == null) {
            return "redirect:/service/loc_recommend";
        }

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
        Long user_no = authUserModel.getUser_no();
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) authUserModel.getAuthorities();

        if (!request.isUserInRole("ROLE_ADMIN")) {
            log.warn("장소 삭제를 요청한 유저가 어드민 권한이 없습니다");
            return "redirect:/service/loc_recommend";
        }

        locService.delete(entity.getLoc_no());
        locService.permaDelete(entity);

        return "redirect:/service/loc_recommend";
    }

    @PostMapping("/service/loc_rollback")
    public String locRollback(HttpServletRequest request,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              Authentication authentication, Long locNo) {
        if (authentication == null) {
            return "redirect:/service/loc_recommend";
        }

        Location entity = locService.selectLoc(locNo);

        if (entity == null) {
            return "redirect:/service/loc_recommend";
        }

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
        Long user_no = authUserModel.getUser_no();
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) authUserModel.getAuthorities();

        if (!request.isUserInRole("ROLE_ADMIN")) {
            log.warn("장소 복원을 요청한 유저가 어드민 권한을 가지고 있지 않습니다.");
            return "redirect:/service/loc_recommend";
        }

        if (!locService.rollback(entity.getLoc_no())) {
            log.warn("장소를 복원하는 과정에서 오류 발생!");
            model.addAttribute("alertMsg", "장소를 복원하는 과정에서 오류 발생!");
            model.addAttribute("redirectURL", "/service/loc_recommend");
            return "/alert/alert";
        }

        return "redirect:/service/loc_recommend";
    }

    @GetMapping("/service/loc_district_map")
    public String locDistrict(HttpServletRequest request, HttpServletResponse response,
                              Model model, Authentication authentication) {

        return "/service/loc_district_map";
    }

    @PostMapping("/service/loc_district_map/search_loc")
    @ResponseBody
    public List<List<String>> locDistrictLocSearch(HttpServletRequest request, HttpServletResponse response,
                                                   @RequestParam String distName){
        List<LocationDTO> locList = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .size(3)
                .searchType(SearchType.NONE)
                .sortCriterion(SortCriterion.VIEW)
                .sortingOrder(SortingOrder.DES)
                .districtType(KoreanDistrict.valueOf(distName))
                .page(1)
                .build();

        PageResultDTO<LocationDTO, Location> pageResultDTO = locService.getList(pageRequestDTO);

        locList = pageResultDTO.getDtoList();

        for (int i = 0; i < locList.size(); i++) {
            List<String> locInfoHolder = new ArrayList<>();
            locInfoHolder.add(String.valueOf(locList.get(i).getLoc_no()));
            locInfoHolder.add(locList.get(i).getLoc_name());
            locInfoHolder.add(locList.get(i).getThumbnail());
            locInfoHolder.add(String.valueOf(locList.get(i).getViewCount()));
            locInfoHolder.add(String.valueOf(locList.get(i).getCmtList().size()));
            locInfoHolder.add(String.valueOf(locList.get(i).getLikeCount()));
            result.add(locInfoHolder);
        }

        return result;
    }
}