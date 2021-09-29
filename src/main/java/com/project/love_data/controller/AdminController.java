package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.CourseDTO;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.*;
import com.project.love_data.security.model.AuthUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.project.love_data.util.ConstantValues.MAX_COR_LIST_SIZE;
import static com.project.love_data.util.ConstantValues.MAX_LOC_LIST_SIZE;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    UserLikeLocService userLikeLocService;
    @Autowired
    UserLikeCorService userLikeCorService;
    @Autowired
    UserRecentLocService userRecentLocService;
    @Autowired
    ReviewService reviewService;

    List<String> tagList = new ArrayList<>();

    @GetMapping(value = "/index")
    public String adminIndex() {
        return "admin/admin_index";
    }

    @GetMapping(value = "/loc_recommend")
    public String goToLocRecommendList() {
        return "redirect:/admin/loc_recommend/list";
    }

    @GetMapping(value = "/loc_recommend/list")
    public String locRecommendList(HttpServletRequest request,
//                                   PageRequestDTO pageRequestDTO,
                                   Authentication authentication,
                                   Model model) {
        String pageNumber = request.getParameter("page");
        if (pageNumber == null){
            pageNumber = "1";
        }
        int pageNum = Integer.parseInt(pageNumber);

        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .size(MAX_LOC_LIST_SIZE)
                .sortCriterion(SortCriterion.VIEW)
                .page(pageNum)
                .searchType(SearchType.DISABLED)
                .build();
        PageResultDTO<LocationDTO, Location> resultDTO = locService.getList(pageRequestDTO);
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
                if (item != null){
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

        return "/admin/loc_recommend";
    }

    @GetMapping(value = "/loc_recommend/search")
    public String getLocSearchValue(HttpServletRequest request, Model model){
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
                .size(MAX_LOC_LIST_SIZE)
                .searchType(searchType)
                .keyword(keyword)
                .tagList(tagList)
                .sortCriterion(sortCriterion)
                .sortingOrder(sortingOrder)
                .page(pageNum)
                .build();

        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);

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

        return "/admin/loc_recommend_search";
    }

    @GetMapping(value = "/cor_recommend")
    public String goToCorRecommendList() {
        return "redirect:/admin/cor_recommend/list";
    }

    @GetMapping(value = "/cor_recommend/list")
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
                .searchType(SearchType.DISABLED)
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
        model.addAttribute("isAdminPage", true);

//        if(authentication != null) {
//            AuthUserModel authUser = (AuthUserModel) authentication.getPrincipal();
////            log.info(authUser.getUser_no());
//        }

        return "/admin/cor_recommend";
    }

    @GetMapping(value = "/cor_recommend/search")
    public String getCorSearchValue(HttpServletRequest request, Model model){
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

        List<Integer> corLiveRevCountList = new ArrayList<>();

        for (int i = 0; i < resultDTO.getDtoList().size(); i++) {
            List<Review> list = reviewService.getReviewsByCorNo(resultDTO.getDtoList().get(i).getCor_no());

            if (list == null) {
                corLiveRevCountList.add(0);
            } else {
                int liveRevCount = 0;
                for (Review review : list) {
                    if (!review.is_deleted()) {
                        liveRevCount++;
                    }
                }

                corLiveRevCountList.add(liveRevCount);
            }
        }

        List<LocationTag> tags = Arrays.asList(LocationTag.values());
        List<String> activeTags = tagList;

        model.addAttribute("result", resultDTO);
        model.addAttribute("tagList", tags);
        model.addAttribute("activeTags", activeTags);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortOrder", order);
        model.addAttribute("searchType", searchType);
        model.addAttribute("liveRevCountList", corLiveRevCountList);

//        log.info("active tags : " + activeTags);

        return "/admin/cor_recommend_search";
    }

    @GetMapping("/dash")
    public String adminDashboadr() {
        return "/admin/admin_dash";
    }
}
