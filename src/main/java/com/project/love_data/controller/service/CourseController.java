package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.service.*;
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

import static com.project.love_data.util.ConstantValues.*;
import static com.project.love_data.util.ConstantValues.MAX_UPLOAD_COUNT;

@Log4j2
@Controller
public class CourseController {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    UserRecentCorService userRecentCorService;
    @Autowired
    UserLikeCorService userLikeCorService;
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    CorLocMapperService corLocMapperService;
    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    CourseImageService courseImageService;
    List<String> tagList = new ArrayList<>();

    @RequestMapping("/service/cor_registration")
    public String cor_Reg(Model model) {
        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
        model.addAttribute("tagList", tagList);

        return "/service/cor_registration";
    }

    @PostMapping("/service/cor/tags")
    public void corGetTagsList(@RequestParam("tags[]") String[] tagArray) {
        tagList = Arrays.asList(tagArray);

//        if (tagList != null) {
//            log.info("태그 등록 성공");
//        } else {
//            log.info("태그 등록 실패");
//        }
    }

    @GetMapping("/service/cor_registration/regData")
    public String corRegistrationDataNoAccess() {
        log.info("Access Invalid(Shouldn't Access with GET Method)");
        return "redirect:/service/cor_recommend";
    }

    @PostMapping("/service/cor_registration/regData")
    public String corRegistrationData(@RequestParam("files") List<MultipartFile> fileList,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        List<String> filePath = null;
        Map<String, String> reqParam = new HashMap<>();

        reqParam.put("name", request.getParameter("name"));
//        reqParam.put("est_time", request.getParameter("est_time"));
        reqParam.put("transportation", request.getParameter("transportation"));
        reqParam.put("cost", request.getParameter("cost"));
        reqParam.put("info", request.getParameter("info"));
        reqParam.put("est_value", request.getParameter("est_value"));
        reqParam.put("est_type", request.getParameter("est_type"));
        reqParam.put("location_length", request.getParameter("location_length"));
        if (reqParam.get("est_type").equals("date")) {
            reqParam.put("accommodations", request.getParameter("accommodations"));
        }
//        // Todo Debug 목적용 코드 나중에 삭제할 것
        if (request.getParameter("user_no") != null) {
            reqParam.put("user_no", (request.getParameter("user_no")));
        } else {
            reqParam.put("user_no", (request.getParameter("user_no_debug")));
        }

        if (tagList.isEmpty()) {
            log.warn("No Location Tag Found (Must add tag before submit location)");
            return "redirect:/service/cor_recommend";
        } else {
            log.info("tagList : " + tagList);
        }

        for (String s : request.getParameterMap().keySet()) {
            log.info(s + "\t:\t" + Arrays.toString(request.getParameterMap().get(s)));
        }

        for (int i = 1; i <= Integer.parseInt(reqParam.get("location_length")); i++) {
            reqParam.put("loc_no_" + i, request.getParameter("loc_no_" + i));
            reqParam.put("loc_id_" + i, request.getParameter("loc_id_" + i));
//            reqParam.put("loc_name_" + i, request.getParameter("loc_name_" + i));
//            reqParam.put("loc_addr_" + i, request.getParameter("loc_addr_" + i));
//            reqParam.put("loc_tel_" + i, request.getParameter("loc_tel_" + i));
//            reqParam.put("loc_info_" + i, request.getParameter("loc_info_" + i));
        }

        for (String s : reqParam.keySet()) {
            log.info(s + "\t:\t" + reqParam.get(s));
        }

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/cor_recommend";
        }

        Course entity = corService.register(reqParam, tagList, filePath);
        CourseDTO dto = corService.entityToDto(entity);

        redirectAttributes.addFlashAttribute("dto", dto);

        tagList = new ArrayList<>();

        return "redirect:/service/cor_recommend";
    }

    @GetMapping(value = "/service/cor_recommend")
    public String goToCorRecommendList() {
        return "redirect:/service/cor_recommend/list";
    }

    @GetMapping(value = "/service/cor_detail")
    public String corDetail(Long corNo, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Authentication authentication,
                            Model model, HttpServletRequest request) {
        if (corNo != null) {
            if (corService.selectCor(corNo) == null) {
                model.addAttribute("isNullCourse", true);
                return "/service/cor_detail";
            } else {
                model.addAttribute("isNullCourse", false);
            }

            corService.incViewCount(corNo);
            CourseDTO dto = corService.selectCorDTO(corNo);
            pageRequestDTO.setSize(MAX_REV_COUNT);
            PageResultDTO<ReviewDTO, Review> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
                    = reviewService.getRevPage(pageRequestDTO);

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
                if (authentication.isAuthenticated()) {
                    UserRecentCor item = userRecentCorService.register(corNo, authUserModel.getUser_no());
//                    if (item == null) {
//                        log.warn(authUserModel.getUser_no() + "의 최근 접속한 기록을 추가하는 과정에서 문제가 발생하였습니다.");
//                        log.warn("장소 번호 (" + corNo + ")");
//                    } else {
//                        log.info("기록 추가 성공");
//                    }
                    if (userLikeCorService.selectByCorNoAndUserNo(corNo, authUserModel.getUser_no()) != null) {
                        model.addAttribute("isLiked", true);
                    } else {
                        model.addAttribute("isLiked", false);
                    }
                }
            } else {
                model.addAttribute("isLiked", false);
            }

            ArrayList<CourseImage> courseImageList = (ArrayList<CourseImage>) courseImageService.getLiveImagesByCorNo(dto.getCor_no());

            model.addAttribute("dto", dto);
            model.addAttribute("resRevDTO", resultCommentDTO);
            model.addAttribute("ImageList", courseImageList);

            return "/service/cor_detail";
        }

        return "/service/cor_recommend";
    }

    @GetMapping(value = "/service/cor_recommend/list")
    public String corRecommendList(HttpServletRequest request,
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
                .size(MAX_COR_LIST_SIZE)
                .sortCriterion(SortCriterion.VIEW)
                .page(pageNum)
                .build();
        PageResultDTO<CourseDTO, com.project.love_data.model.service.Course> resultDTO = corService.getList(pageRequestDTO);
        List<Boolean> isLikedList = new ArrayList<>();

        if (authentication == null) {
            for (int i = 0; i < resultDTO.getSize(); i++) {
                isLikedList.add(false);
            }
        } else {
            AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
            Long user_no = authUserModel.getUser_no();
            for (int i = 0; i < resultDTO.getDtoList().size(); i++) {
                Long cor_no = resultDTO.getDtoList().get(i).getCor_no();
                UserLikeCor item = userLikeCorService.selectByCorNoAndUserNo(cor_no, user_no);
                if (item != null) {
                    isLikedList.add(true);
                } else {
                    isLikedList.add(false);
                }
            }
        }

        model.addAttribute("result", resultDTO);
        model.addAttribute("tagList", tagList);
        model.addAttribute("isLikedList", isLikedList);

//        if(authentication != null) {
//            AuthUserModel authUser = (AuthUserModel) authentication.getPrincipal();
////            log.info(authUser.getUser_no());
//        }

        return "/service/cor_recommend";
    }

    @GetMapping("/service/cor_edit")
    public String corEdit(Long corNo, Model model) {
        if (corNo != null) {
            CourseDTO dto = corService.selectCorDTO(corNo);
            List<LocationDTO> locList = new ArrayList<>();
            List<CourseImage> corImageList = courseImageService.getLiveImagesByCorNo(corNo);
            List<LocationTag> tagList = Arrays.asList(LocationTag.values());
            List<CorLocMapper> corLocMappers = corLocMapperService.getLocationsByCorNo(corNo);

            for (CorLocMapper mapper : corLocMappers) {
                locList.add(locService.selectLocDTO(mapper.getLoc_no()));
            }

            model.addAttribute("dto", dto);
            model.addAttribute("imageList", corImageList);
            model.addAttribute("tagList", tagList);
            model.addAttribute("locList", locList);

            return "/service/cor_edit";
        }

        return "/service/cor_recommend";
    }

    @PostMapping("/service/cor_edit/regData")
    public String corEditData(@RequestParam("files") List<MultipartFile> fileList,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        List<String> filePath = null;
        Map<String, String> reqParam = new HashMap<>();

        reqParam.put("name", request.getParameter("name"));
        reqParam.put("transportation", request.getParameter("transportation"));
        reqParam.put("cost", request.getParameter("cost"));
        reqParam.put("info", request.getParameter("info"));
        reqParam.put("est_value", request.getParameter("est_value"));
        reqParam.put("est_type", request.getParameter("est_type"));
        reqParam.put("location_length", request.getParameter("location_length"));
        reqParam.put("cor_no", request.getParameter("cor_no"));
        reqParam.put("cor_uuid", request.getParameter("cor_uuid"));
        if (reqParam.get("est_type").equals("date")) {
            reqParam.put("accommodations", request.getParameter("accommodations"));
        }
//        // Todo Debug 목적용 코드 나중에 삭제할 것
        if (request.getParameter("user_no") != null) {
            reqParam.put("user_no", (request.getParameter("user_no")));
        } else {
            reqParam.put("user_no", (request.getParameter("user_no_debug")));
        }

        Course cor_no_Test = corService.selectCor(Long.valueOf(reqParam.get("cor_no")));
        Course cor_uuid_Test = corService.selectCor(reqParam.get("cor_uuid"));

        //코스 수정 정보에 입력된 코스가 DB에 있는 지 확인하는 과정 (Validating)
        if (cor_no_Test == null || cor_uuid_Test == null || !cor_uuid_Test.equals(cor_no_Test)) {
            log.warn("Given Location Edit Information doesn't match with DB");
            log.info("Please Check the Value");
            return "redirect:/service/cor_recommend";
        }

        if (tagList.isEmpty()) {
            log.warn("No Location Tag Found (Must add tag before submit location)");
            return "redirect:/service/cor_recommend";
        } else {
            log.info("tagList : " + tagList);
        }

        for (String s : request.getParameterMap().keySet()) {
            log.info(s + "\t:\t" + Arrays.toString(request.getParameterMap().get(s)));
        }

        for (int i = 1; i <= Integer.parseInt(reqParam.get("location_length")); i++) {
            reqParam.put("loc_no_" + i, request.getParameter("loc_no_" + i));
            reqParam.put("loc_id_" + i, request.getParameter("loc_id_" + i));
//            reqParam.put("loc_name_" + i, request.getParameter("loc_name_" + i));
//            reqParam.put("loc_addr_" + i, request.getParameter("loc_addr_" + i));
//            reqParam.put("loc_tel_" + i, request.getParameter("loc_tel_" + i));
//            reqParam.put("loc_info_" + i, request.getParameter("loc_info_" + i));
        }

        for (String s : reqParam.keySet()) {
            log.info(s + "\t:\t" + reqParam.get(s));
        }

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/cor_recommend";
        }

        Course entity = corService.update(reqParam, tagList, filePath);
        CourseDTO dto = corService.entityToDto(entity);

        redirectAttributes.addFlashAttribute("dto", dto);

        tagList = new ArrayList<>();

        return "redirect:/service/cor_recommend";
    }

    @GetMapping(value = "/service/cor_recommend/search")
    public String getSearchValue(HttpServletRequest request, Model model){
        String keyword = request.getParameter("keyword");
        String order = request.getParameter("sortOrder");
        String tagString = request.getParameter("tags");
        String type = request.getParameter("searchType");
        String pageNumber = request.getParameter("page");
        if (pageNumber == null) {
            pageNumber = "1";
        }
        int pageNum = Integer.parseInt(pageNumber);

        SortingOrder sortingOrder = null;
        SortCriterion sortCriterion = null;
        SearchType searchType = SearchType.valueOf(type);
        List<String> tempList = Arrays.asList(tagString.split(","));
        tagList.clear();
        for (String s : tempList) {
            if ("".equals(s)) {
                continue;
            } else {
                tagList.add(s);
            }
        }

        switch (order){
            case "LIKE_DES" :
                // 좋아요 순
                sortCriterion = SortCriterion.LIKE;
                sortingOrder = SortingOrder.DES;
                break;
            case "DATE_DES" :
                // 최신 등록순
                sortCriterion = SortCriterion.DATE;
                sortingOrder = SortingOrder.DES;
                break;
            case "DATE_ASC" :
                // 오래된 등록순
                sortCriterion = SortCriterion.DATE;
                sortingOrder = SortingOrder.ASC;
                break;
            case "VIEW_DES" :
                // 조회순
            default:
                sortCriterion = SortCriterion.VIEW;
                sortingOrder = SortingOrder.DES;
                break;
        }

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .size(MAX_COR_LIST_SIZE)
                .searchType(searchType)
                .keyword(keyword)
                .tagList(tagList)
                .sortCriterion(sortCriterion)
                .sortingOrder(sortingOrder)
                .page(pageNum)
                .build();

        PageResultDTO<CourseDTO, Course> resultDTO = corService.getList(pageRequestDTO);

        if (resultDTO.getTotalPage() < pageNum) {
            model.addAttribute("isRequestPageNumberExceed", true);
        } else {
            model.addAttribute("isRequestPageNumberExceed", false);
        }

        List<LocationTag> tags = Arrays.asList(LocationTag.values());
        List<String> activeTags = tagList;

        model.addAttribute("result", resultDTO);
        model.addAttribute("tagList", tags);
        model.addAttribute("activeTags", activeTags);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortOrder", order);
        model.addAttribute("searchType", searchType);

//        log.info("active tags : " + activeTags);

        return "/service/cor_recommend_search";
    }
}
