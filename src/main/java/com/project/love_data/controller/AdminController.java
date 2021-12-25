package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.util.DefaultLocalDateTimeFormatter;
import com.project.love_data.util.FileLastDateComparator;
import com.project.love_data.util.FileSizeCalculator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static com.project.love_data.util.ConstantValues.*;

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
    @Autowired
    UserService userService;
    @Autowired
    CommentService comService;
    @Autowired
    FileManagementService fileManagementService;
    @Autowired
    LocationImageService locImageService;
    @Autowired
    CourseImageService corImageService;
    @Autowired
    ReviewImageService revImageService;
    @Autowired
    ReportManageService reportManageService;
    @Autowired
    DeletedImageInfoService deletedImageInfoService;
    DefaultLocalDateTimeFormatter defaultLocalDateTimeFormatter = new DefaultLocalDateTimeFormatter();
    FileSizeCalculator fileSizeCalculator = new FileSizeCalculator();

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
        if (pageNumber == null) {
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

        return "/admin/loc_recommend";
    }

    @GetMapping(value = "/loc_recommend/search")
    public String getLocSearchValue(HttpServletRequest request, Model model) {
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
        tagList.clear();
        for (String s : tempList) {
            if ("".equals(s)) {
                continue;
            } else {
                tagList.add(s);
            }
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
                .tagList(tagList)
                .sortCriterion(sortCriterion)
                .sortingOrder(sortingOrder)
                .districtType(KoreanDistrict.valueOf(district))
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
    public String getCorSearchValue(HttpServletRequest request, Model model) {
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
    public String adminDashboard(HttpServletRequest request, Model model) {
        final int size = 20;
        final int dateDuration = 7;
        final int minLikeCount = 0;

        List<Location> tempLocationList = new ArrayList<>();
        List<LocationDTO> recentLocationList = new ArrayList<>();
        List<LocationDTO> hotLocationList = new ArrayList<>();
        List<Course> tempCourseList = new ArrayList<>();
        List<CourseDTO> recentCourseList = new ArrayList<>();
        List<CourseDTO> hotCourseList = new ArrayList<>();
        List<Integer> recentLocCorListIndex = new ArrayList<>();
        List<String> recentLocCorTypeList = new ArrayList<>();
        List<Integer> hotLocCorListIndex = new ArrayList<>();
        List<String> hotLocCorTypeList = new ArrayList<>();

        List<String> recentLocUserNicList = new ArrayList<>();
        List<String> hotLocUserNicList = new ArrayList<>();
        List<String> recentCorUserNicList = new ArrayList<>();
        List<String> hotCorUserNicList = new ArrayList<>();

        List<Integer> recentLocReportCountList = new ArrayList<>();
        List<Integer> hotLocReportCountList = new ArrayList<>();
        List<Integer> recentCorReportCountList = new ArrayList<>();
        List<Integer> hotCorReportCountList = new ArrayList<>();

        // dateDuration 만큼의 일수 차이가 나고 size 만큼의 양을 가진  locationDTOList
        tempLocationList = locService.recentLocationList(size, dateDuration);
        for (Location entity : tempLocationList) {
            recentLocationList.add(locService.entityToDto(entity));
            User user = userService.select(entity.getUser_no());
            if (user == null) {
                recentLocUserNicList.add("삭제된 유저");
            } else {
                recentLocUserNicList.add(user.getUser_nic());
            }
            recentLocReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getLoc_no(), "LOC"), null));
        }

        tempLocationList.clear();
        tempLocationList = locService.hotLocationList(size, dateDuration, minLikeCount);
        for (Location entity : tempLocationList) {
            hotLocationList.add(locService.entityToDto(entity));
            User user = userService.select(entity.getUser_no());
            if (user == null) {
                hotLocUserNicList.add("삭제된 유저");
            } else {
                hotLocUserNicList.add(user.getUser_nic());
            }
            hotLocReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getLoc_no(), "LOC"), null));
        }

        tempCourseList = corService.recentCourseList(size, dateDuration);
        for (Course entity : tempCourseList) {
            recentCourseList.add(corService.entityToDto(entity));
            User user = userService.select(entity.getUser_no());
            if (user == null) {
                recentCorUserNicList.add("삭제된 유저");
            } else {
                recentCorUserNicList.add(user.getUser_nic());
            }
            recentCorReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getCor_no(), "COR"), null));
        }

        tempCourseList.clear();
        tempCourseList = corService.hotCourseList(size, dateDuration, minLikeCount);
        for (Course entity : tempCourseList) {
            hotCourseList.add(corService.entityToDto(entity));
            User user = userService.select(entity.getUser_no());
            if (user == null) {
                hotCorUserNicList.add("삭제된 유저");
            } else {
                hotCorUserNicList.add(user.getUser_nic());
            }
            hotCorReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getCor_no(), "COR"), null));
        }

        int x = 0;
        int y = 0;
        boolean isLocIterFinished = false;
        boolean isCorIterFinished = false;
        for (int i = 0; i < 20; i++) {
            if (!recentLocationList.isEmpty() && !recentCourseList.isEmpty()) {
                if (!isLocIterFinished && !isCorIterFinished) {
                    if (recentLocationList.get(x).getRegDate().isAfter(recentCourseList.get(y).getRegDate())) {
                        recentLocCorListIndex.add(x++);
                        recentLocCorTypeList.add("Loc");
                        if (x >= recentLocationList.size()) {
                            isLocIterFinished = true;
                        }
                    } else {
                        recentLocCorListIndex.add(y++);
                        recentLocCorTypeList.add("Cor");
                        if (y >= recentCourseList.size()) {
                            isCorIterFinished = true;
                        }
                    }
                } else {
                    if (isLocIterFinished && isCorIterFinished) {
                        break;
                    } else if (isLocIterFinished && !isCorIterFinished) {
                        recentLocCorListIndex.add(y++);
                        recentLocCorTypeList.add("Cor");
                        if (y >= recentCourseList.size()) {
                            isCorIterFinished = true;
                        }
                    } else if (!isLocIterFinished && isCorIterFinished) {
                        recentLocCorListIndex.add(x++);
                        recentLocCorTypeList.add("Loc");
                        if (x >= recentLocationList.size()) {
                            isLocIterFinished = true;
                        }
                    }
                }
            } else {
                if (isLocIterFinished && isCorIterFinished) {
                    break;
                } else if (recentLocationList.isEmpty() && recentCourseList.isEmpty()) {
                    break;
                } else if (recentLocationList.isEmpty()) {
                    isLocIterFinished = true;
                    recentLocCorListIndex.add(y++);
                    recentLocCorTypeList.add("Cor");
                    if (y >= recentCourseList.size()) {
                        isCorIterFinished = true;
                    }
                } else if (recentCourseList.isEmpty()) {
                    isCorIterFinished = true;
                    recentLocCorListIndex.add(x++);
                    recentLocCorTypeList.add("Loc");
                    if (x >= recentLocationList.size()) {
                        isLocIterFinished = true;
                    }
                }
            }
        }

        x = 0;
        y = 0;
        isLocIterFinished = false;
        isCorIterFinished = false;
        for (int i = 0; i < 20; i++) {
            if (!hotLocationList.isEmpty() && !hotCourseList.isEmpty()) {
                if (!isLocIterFinished && !isCorIterFinished) {
                    // 추천수가 같으면 코스가 우선
                    if (hotLocationList.get(x).getLikeCount() > hotCourseList.get(y).getLikeCount()) {
                        hotLocCorListIndex.add(x++);
                        hotLocCorTypeList.add("Loc");
                        if (x >= hotLocationList.size()) {
                            isLocIterFinished = true;
                        }
                    } else {
                        hotLocCorListIndex.add(y++);
                        hotLocCorTypeList.add("Cor");
                        if (y >= hotCourseList.size()) {
                            isCorIterFinished = true;
                        }
                    }
                } else {
                    if (isLocIterFinished && isCorIterFinished) {
                        break;
                    } else if (isLocIterFinished && !isCorIterFinished) {
                        hotLocCorListIndex.add(y++);
                        hotLocCorTypeList.add("Cor");
                        if (y >= hotCourseList.size()) {
                            isCorIterFinished = true;
                        }
                    } else if (!isLocIterFinished && isCorIterFinished) {
                        hotLocCorListIndex.add(x++);
                        hotLocCorTypeList.add("Loc");
                        if (x >= hotLocationList.size()) {
                            isLocIterFinished = true;
                        }
                    }
                }
            } else {
                if (isLocIterFinished && isCorIterFinished) {
                    break;
                } else if (hotLocationList.isEmpty() && hotCourseList.isEmpty()) {
                    break;
                } else if (hotLocationList.isEmpty()) {
                    isLocIterFinished = true;
                    hotLocCorListIndex.add(y++);
                    hotLocCorTypeList.add("Cor");
                    if (y >= hotCourseList.size()) {
                        isCorIterFinished = true;
                    }
                } else if (hotCourseList.isEmpty()) {
                    isCorIterFinished = true;
                    hotLocCorListIndex.add(x++);
                    hotLocCorTypeList.add("Loc");
                    if (x >= hotLocationList.size()) {
                        isLocIterFinished = true;
                    }
                }
            }
        }

        model.addAttribute("recentLocList", recentLocationList);
        model.addAttribute("hotLocList", hotLocationList);
        model.addAttribute("recentCorList", recentCourseList);
        model.addAttribute("hotCorList", hotCourseList);

        model.addAttribute("recentLocUserNicList", recentLocUserNicList);
        model.addAttribute("hotLocUserNicList", hotLocUserNicList);
        model.addAttribute("recentCorUserNicList", recentCorUserNicList);
        model.addAttribute("hotCorUserNicList", hotCorUserNicList);

        model.addAttribute("recentLocCorListIndex", recentLocCorListIndex);
        model.addAttribute("recentLocCorTypeList", recentLocCorTypeList);
        model.addAttribute("hotLocCorListIndex", hotLocCorListIndex);
        model.addAttribute("hotLocCorTypeList", hotLocCorTypeList);


        List<Comment> tempCommentList = new ArrayList<>();
        List<CommentDTO> recentCommentList = new ArrayList<>();
        List<CommentDTO> hotCommentList = new ArrayList<>();
        List<Review> tempReviewList = new ArrayList<>();
        List<ReviewDTO> recentReviewList = new ArrayList<>();
        List<ReviewDTO> hotReviewList = new ArrayList<>();
        List<Integer> recentComRevListIndex = new ArrayList<>();
        List<String> recentComRevTypeList = new ArrayList<>();
        List<Integer> hotComRevListIndex = new ArrayList<>();
        List<String> hotComRevTypeList = new ArrayList<>();

        List<String> recentComUserNicList = new ArrayList<>();
        List<String> hotComUserNicList = new ArrayList<>();
        List<String> recentRevUserNicList = new ArrayList<>();
        List<String> hotRevUserNicList = new ArrayList<>();

        List<Integer> recentComReportCountList = new ArrayList<>();
        List<Integer> hotComReportCountList = new ArrayList<>();
        List<Integer> recentRevReportCountList = new ArrayList<>();
        List<Integer> hotRevReportCountList = new ArrayList<>();

        // dateDuration 만큼의 일수 차이가 나고 size 만큼의 양을 가진  commentDTOList
        tempCommentList = comService.recentCommentList(size, dateDuration);
        for (Comment entity : tempCommentList) {
            recentCommentList.add(comService.entityToDto(entity));
            User user = userService.select(entity.getUser().getUser_no());
            if (user == null) {
                recentComUserNicList.add("삭제된 유저");
            } else {
                recentComUserNicList.add(user.getUser_nic());
            }
            recentComReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getCmtNo(), "COM"), null));
        }

        tempCommentList.clear();
        tempCommentList = comService.hotCommentList(size, dateDuration, minLikeCount);
        for (Comment entity : tempCommentList) {
            hotCommentList.add(comService.entityToDto(entity));
            User user = userService.select(entity.getUser().getUser_no());
            if (user == null) {
                hotComUserNicList.add("삭제된 유저");
            } else {
                hotComUserNicList.add(user.getUser_nic());
            }
            hotComReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getCmtNo(), "COM"), null));
        }

        tempReviewList = reviewService.recentReviewList(size, dateDuration);
        for (Review entity : tempReviewList) {
            recentReviewList.add(reviewService.entityToDto(entity));
            User user = userService.select(entity.getUser_no());
            if (user == null) {
                recentRevUserNicList.add("삭제된 유저");
            } else {
                recentRevUserNicList.add(user.getUser_nic());
            }
            recentRevReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getRevNo(), "REV"), null));
        }

        tempReviewList.clear();
        tempReviewList = reviewService.hotReviewList(size, dateDuration, minLikeCount);
        for (Review entity : tempReviewList) {
            hotReviewList.add(reviewService.entityToDto(entity));
            User user = userService.select(entity.getUser_no());
            if (user == null) {
                hotRevUserNicList.add("삭제된 유저");
            } else {
                hotRevUserNicList.add(user.getUser_nic());
            }
            hotRevReportCountList.add(reportManageService.reportCount(reportManageService.getRcNo(entity.getRevNo(), "REV"), null));
        }

        x = 0;
        y = 0;
        boolean isComIterFinished = false;
        boolean isRevIterFinished = false;
        for (int i = 0; i < 20; i++) {
            if (!recentCommentList.isEmpty() && !recentReviewList.isEmpty()) {
                if (!isComIterFinished && !isRevIterFinished) {
                    if (recentCommentList.get(x).getRegDate().isAfter(recentReviewList.get(y).getRegDate())) {
                        recentComRevListIndex.add(x++);
                        recentComRevTypeList.add("Com");
                        if (x >= recentCommentList.size()) {
                            isComIterFinished = true;
                        }
                    } else {
                        recentComRevListIndex.add(y++);
                        recentComRevTypeList.add("Rev");
                        if (y >= recentReviewList.size()) {
                            isRevIterFinished = true;
                        }
                    }
                } else {
                    if (isComIterFinished && isRevIterFinished) {
                        break;
                    } else if (isComIterFinished && !isRevIterFinished) {
                        recentComRevListIndex.add(y++);
                        recentComRevTypeList.add("Rev");
                        if (y >= recentReviewList.size()) {
                            isRevIterFinished = true;
                        }
                    } else if (!isComIterFinished && isRevIterFinished) {
                        recentComRevListIndex.add(x++);
                        recentComRevTypeList.add("Com");
                        if (x >= recentCommentList.size()) {
                            isComIterFinished = true;
                        }
                    }
                }
            } else {
                if (isComIterFinished && isRevIterFinished) {
                    break;
                } else if (recentCommentList.isEmpty() && recentReviewList.isEmpty()) {
                    break;
                } else if (recentCommentList.isEmpty()) {
                    isComIterFinished = true;
                    recentComRevListIndex.add(y++);
                    recentComRevTypeList.add("Rev");
                    if (y >= recentReviewList.size()) {
                        isRevIterFinished = true;
                    }
                } else if (recentReviewList.isEmpty()) {
                    isRevIterFinished = true;
                    recentComRevListIndex.add(x++);
                    recentComRevTypeList.add("Com");
                    if (x >= recentCommentList.size()) {
                        isComIterFinished = true;
                    }
                }
            }
        }

        x = 0;
        y = 0;
        isComIterFinished = false;
        isRevIterFinished = false;
        for (int i = 0; i < 20; i++) {
            if (!hotCommentList.isEmpty() && !hotReviewList.isEmpty()) {
                if (!isComIterFinished && !isRevIterFinished) {
                    // 추천수가 같으면 리뷰가 우선
                    if (hotCommentList.get(x).getLikeCount() > hotReviewList.get(y).getRev_like()) {
                        hotComRevListIndex.add(x++);
                        hotComRevTypeList.add("Com");
                        if (x >= hotCommentList.size()) {
                            isComIterFinished = true;
                        }
                    } else {
                        hotComRevListIndex.add(y++);
                        hotComRevTypeList.add("Rev");
                        if (y >= hotReviewList.size()) {
                            isRevIterFinished = true;
                        }
                    }
                } else {
                    if (isComIterFinished && isRevIterFinished) {
                        break;
                    } else if (isComIterFinished && !isRevIterFinished) {
                        hotComRevListIndex.add(y++);
                        hotComRevTypeList.add("Rev");
                        if (y >= hotReviewList.size()) {
                            isRevIterFinished = true;
                        }
                    } else if (!isComIterFinished && isRevIterFinished) {
                        hotComRevListIndex.add(x++);
                        hotComRevTypeList.add("Com");
                        if (x >= hotCommentList.size()) {
                            isComIterFinished = true;
                        }
                    }
                }
            } else {
                if (isComIterFinished && isRevIterFinished) {
                    break;
                } else if (hotCommentList.isEmpty() && hotReviewList.isEmpty()) {
                    break;
                }else if (hotCommentList.isEmpty()) {
                    isComIterFinished = true;
                    hotComRevListIndex.add(y++);
                    hotComRevTypeList.add("Rev");
                    if (y >= hotReviewList.size()) {
                        isRevIterFinished = true;
                    }
                } else if (hotReviewList.isEmpty()) {
                    isRevIterFinished = true;
                    hotComRevListIndex.add(x++);
                    hotComRevTypeList.add("Com");
                    if (x >= hotCommentList.size()) {
                        isComIterFinished = true;
                    }
                }
            }
        }

        model.addAttribute("recentComList", recentCommentList);
        model.addAttribute("hotComList", hotCommentList);
        model.addAttribute("recentRevList", recentReviewList);
        model.addAttribute("hotRevList", hotReviewList);

        model.addAttribute("recentComUserNicList", recentComUserNicList);
        model.addAttribute("hotComUserNicList", hotComUserNicList);
        model.addAttribute("recentRevUserNicList", recentRevUserNicList);
        model.addAttribute("hotRevUserNicList", hotRevUserNicList);

        model.addAttribute("recentComRevListIndex", recentComRevListIndex);
        model.addAttribute("recentComRevTypeList", recentComRevTypeList);
        model.addAttribute("hotComRevListIndex", hotComRevListIndex);
        model.addAttribute("hotComRevTypeList", hotComRevTypeList);

        model.addAttribute("recentLocReportCountList", recentLocReportCountList);
        model.addAttribute("hotLocReportCountList", hotLocReportCountList);
        model.addAttribute("recentCorReportCountList", recentCorReportCountList);
        model.addAttribute("hotCorReportCountList", hotCorReportCountList);

        model.addAttribute("recentComReportCountList", recentComReportCountList);
        model.addAttribute("hotComReportCountList", hotComReportCountList);
        model.addAttribute("recentRevReportCountList", recentRevReportCountList);
        model.addAttribute("hotRevReportCountList", hotRevReportCountList);


        tempLocationList.clear();
        tempCourseList.clear();
        tempCommentList.clear();
        tempReviewList.clear();

        tempLocationList = locService.recentLocationList(Integer.MAX_VALUE, 0);
        tempCourseList = corService.recentCourseList(Integer.MAX_VALUE, 0);
        tempCommentList = comService.recentCommentList(Integer.MAX_VALUE, 0);
        tempReviewList = reviewService.recentReviewList(Integer.MAX_VALUE, 0);

        long todayLocCorCount = 0;
        long todayComRevCount = 0;

        if (tempLocationList != null) {
            todayLocCorCount += tempLocationList.size();
        }

        if (tempCourseList != null) {
            todayLocCorCount += tempCourseList.size();
        }

        if (tempCommentList != null) {
            todayComRevCount += tempCommentList.size();
        }

        if (tempReviewList != null) {
            todayComRevCount += tempReviewList.size();
        }

        model.addAttribute("todayLocCorCount", todayLocCorCount);
        model.addAttribute("todayComRevCount", todayComRevCount);
        model.addAttribute("todayReportCount", reportManageService.recentReportCount(1));

        List<Integer> recentComRevPageNum = new ArrayList<>();
        List<Integer> hotComRevPageNum = new ArrayList<>();

        for (int i = 0; i < recentComRevListIndex.size(); i++) {
            if (recentComRevTypeList.get(i).equalsIgnoreCase("Com")) {
                Integer temp = comService.getCommentCurrentPageNum(recentCommentList.get(recentComRevListIndex.get(i)).getCmtNo());
                if (temp == null) {
                    temp = -1;
                }
                recentComRevPageNum.add(temp);
            } else if (recentComRevTypeList.get(i).equalsIgnoreCase("Rev")) {
                Integer temp = reviewService.getReviewCurrentPageNum(recentReviewList.get(recentComRevListIndex.get(i)).getRevNo());
                if (temp == null) {
                    temp = -1;
                }
                recentComRevPageNum.add(temp);
            }
        }

        for (int i = 0; i < hotComRevListIndex.size(); i++) {
            if (hotComRevTypeList.get(i).equalsIgnoreCase("Com")) {
                Integer temp = comService.getCommentCurrentPageNum(hotCommentList.get(hotComRevListIndex.get(i)).getCmtNo());
                if (temp == null) {
                    temp = -1;
                }
                hotComRevPageNum.add(temp);
            } else if (hotComRevTypeList.get(i).equalsIgnoreCase("Rev")) {
                Integer temp = reviewService.getReviewCurrentPageNum(hotReviewList.get(hotComRevListIndex.get(i)).getRevNo());
                if (temp == null) {
                    temp = -1;
                }
                hotComRevPageNum.add(temp);
            }
        }

        model.addAttribute("recentComRevPageNum", recentComRevPageNum);
        model.addAttribute("hotComRevPageNum", hotComRevPageNum);

        return "/admin/admin_dash";
    }

    @GetMapping("/upload_cache")
    public String adminUploadCache(HttpServletRequest request, Model model) {
        List<File> fileList = new ArrayList<>();
        List<LocalDateTime> lastModifiedTimeList = new ArrayList<>();
        List<String> fileSizeList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        List<String> fileTypeList = new ArrayList<>();
        List<Long> fileNoList = new ArrayList<>();
        List<String> fileUploadUserList = new ArrayList<>();
        List<LocalDateTime> fileDeletedTimeList = new ArrayList<>();
        List<String> fileOriginURLList = new ArrayList<>();

        fileList = fileManagementService.getAllFilesList(PathType.UPLOAD);
        if (fileList != null && !fileList.isEmpty()) {
            Collections.sort(fileList, new FileLastDateComparator().reversed());

            for (File file : fileList) {
                String fileName = file.getName();
                String type = "";
                String uuid = "";
                String uuidAndExtenstion = "";
                DeletedImageInfo imageInfo = null;
                lastModifiedTimeList.add(LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), TimeZone.getTimeZone("Asia/Seoul").toZoneId()));
                fileSizeList.add(fileSizeCalculator.execute(file.length()));
                fileNameList.add(fileName);

                imageInfo = deletedImageInfoService.findByImgUuid(fileName);
                if (imageInfo == null) {
                    fileDeletedTimeList.add(null);
                    fileUploadUserList.add("삭제 된 사용자");
                } else {
                    fileDeletedTimeList.add(imageInfo.getRegDate());
                    User user = userService.select(imageInfo.getImgUserNo());
                    if (user == null) {
                        fileUploadUserList.add("삭제 된 사용자");
                    } else {
                        fileUploadUserList.add(user.getUser_nic());
                    }
                }

                int index = fileName.indexOf("^");
                type = fileName.substring(0, index);
//            fileTypeList.add(type);

                uuidAndExtenstion = fileName.substring(index + 1, fileName.length());
                index = uuidAndExtenstion.indexOf(".");
                uuid = uuidAndExtenstion.substring(0, index);

                switch (type) {
                    case "LOC" :
                        fileTypeList.add("장소");
                        LocationImage locImg = locImageService.getAllImage(uuidAndExtenstion);
                        if (locImg == null) {
                            fileNoList.add(null);
                            fileOriginURLList.add(null);
                        } else {
                            fileNoList.add(locImg.getImg_no());
                            fileOriginURLList.add("/service/loc_detail?locNo=" + locImg.getLocation().getLoc_no());
                        }
                        break;
                    case "COR" :
                        fileTypeList.add("코스");
                        CourseImage corImg = corImageService.getAllImage(uuidAndExtenstion);
                        if (corImg == null) {
                            fileNoList.add(null);
                            fileOriginURLList.add(null);
                        } else {
                            fileNoList.add(corImg.getImg_no());
                            fileOriginURLList.add("/service/cor_detail?corNo=" + corImg.getCor_no());
                        }
                        break;
                    case "REV" :
                        fileTypeList.add("리뷰");
                        ReviewImage revImg = revImageService.getAllImage(uuidAndExtenstion);
                        if (revImg == null) {
                            fileNoList.add(null);
                            fileOriginURLList.add(null);
                        } else {
                            fileNoList.add(revImg.getImg_no());
                            Integer pageNum = reviewService.getReviewCurrentPageNum(revImg.getRev_no());
                            if (pageNum == null) {
                                fileOriginURLList.add(null);
                            } else {
                                fileOriginURLList.add("/service/cor_detail?corNo=" + revImg.getCor_no() + "&page=" + pageNum + "&revNo=" + revImg.getRev_no());
                            }
                        }
                        break;
                    case "PIC" :
                        fileTypeList.add("프로필");
                        fileNoList.add(null);
                        fileOriginURLList.add(null);
                        break;
                    case "QNA" :
                        fileTypeList.add("문의사항");
                        fileNoList.add(null);
                        fileOriginURLList.add(null);
                        break;
                    case "NTC" :
                        fileTypeList.add("공지사항");
                        fileNoList.add(null);
                        fileOriginURLList.add(null);
                        break;
                    default :
                        fileTypeList.add("NULL");
                        fileNoList.add(null);
                        fileOriginURLList.add(null);
                        break;
                }
            }
        }

        model.addAttribute("lastModifiedTimeList", lastModifiedTimeList);
        model.addAttribute("fileSizeList", fileSizeList);
        model.addAttribute("fileNameList", fileNameList);
        model.addAttribute("fileTypeList", fileTypeList);
        model.addAttribute("fileNoList", fileNoList);
        model.addAttribute("fileUploadUserList", fileUploadUserList);
        model.addAttribute("fileDeletedTimeList", fileDeletedTimeList);
        model.addAttribute("fileOriginURLList", fileOriginURLList);

        return "/admin/admin_upload_cache";
    }

    @PostMapping("/del_upload_cache")
    @ResponseBody
    public List<Boolean> deleteUploadCache(HttpServletRequest request,
                                           Model model,
                                           @RequestParam("pathName[]") String[] pathNameAry,
                                           @RequestParam("pathType") String pathType,
                                           Authentication authentication) {
        if (pathNameAry.length > 0) {
            List<String> pathNameList = new ArrayList<>();
            pathNameList = Arrays.asList(pathNameAry);
            List<Boolean> result = new ArrayList<>();

            for (String s : pathNameList) {
                AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
                String str = authUserModel.getUser_nic() + "(" + authUserModel.getUser_no() + ") 관리자가 "
                        + LocalDateTime.now().format(defaultLocalDateTimeFormatter.getDateTimeFormatter()) + "에 " + s + "삭제를 진행함";
                if (!deletedImageInfoService.updateStatusToDelete(s, str)) {
                    log.warn("DeletedImageInfo를 상태를 변경하는 과정에서 오류 발생!");
                    log.warn("Error 파일 명 : " + s);
                    log.warn("결과 : " + str);
                }
            }
            result = fileManagementService.deleteFiles(PathType.valueOf(pathType), pathNameList);


            for (int i = 0; i < pathNameList.size(); i++) {
                log.info("File " + pathNameList.get(i) + " " + (result.get(i) ? "successfully deleted" : "delete failed"));
            }

            return result;
        }

        return null;
    }

    @GetMapping("/report_center")
    public String reportCenter(HttpServletRequest request,
                               Model model) {
        List<String> userNickList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        int page = 1;
        ReportPageCompleteType completeType = null;
        if (request.getParameter("completeType") == null) {
            completeType = ReportPageCompleteType.PROGRESS;
        } else {
            switch (request.getParameter("completeType")) {
                case "ALL" :
                    completeType = ReportPageCompleteType.ALL;
                    break;
                case "PROGRESS" :
                    completeType = ReportPageCompleteType.PROGRESS;
                    break;
                case "COMPLETE" :
                default :
                    completeType = ReportPageCompleteType.COMPLETE;
            }
        }
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        ReportClusterPageRequestDTO clusterPageRequestDTO = null;
        switch (completeType) {
            case ALL :
                clusterPageRequestDTO = ReportClusterPageRequestDTO.builder()
                        .completeType(ReportPageCompleteType.ALL)
                        .page(page)
                        .build();
                break;
            case COMPLETE :
                clusterPageRequestDTO = ReportClusterPageRequestDTO.builder()
                        .completeType(ReportPageCompleteType.COMPLETE)
                        .page(page)
                        .build();
                break;
            case PROGRESS :
            default :
                clusterPageRequestDTO = ReportClusterPageRequestDTO.builder()
                        .page(page)
                        .build();
        }
        ReportClusterPageResultDTO<ReportClusterDTO, ReportCluster> clusterPageResultDTO =
                reportManageService.getReportClusterPage(clusterPageRequestDTO);

        for (ReportClusterDTO reportClusterDTO : clusterPageResultDTO.getDtoList()) {
            User user = null;
            int pageNum = 1;
            switch (reportClusterDTO.getPostType()) {
                case "LOC" :
                    Location locEntity = locService.selectLoc(reportClusterDTO.getPostNo());
                    if (locEntity == null) {
                        reportClusterDTO.setPostNo(null);
                        userNickList.add("삭제된 유저");
                        urlList.add(null);
                    } else {
                        user = userService.select(locEntity.getUser_no());
                        if (user == null) {
                            userNickList.add("삭제된 유저");
                        } else {
                            userNickList.add(user.getUser_nic());
                        }
                        urlList.add("/service/loc_detail?locNo=" + locEntity.getLoc_no());
                    }
                    break;
                case "COR" :
                    Course corEntity = corService.selectCor(reportClusterDTO.getPostNo());
                    if (corEntity == null) {
                        reportClusterDTO.setPostNo(null);
                        userNickList.add("삭제된 유저");
                        urlList.add(null);
                    } else {
                        user = userService.select(corEntity.getUser_no());
                        if (user == null) {
                            userNickList.add("삭제된 유저");
                        } else {
                            userNickList.add(user.getUser_nic());
                        }
                        urlList.add("/service/cor_detail?corNo=" + corEntity.getCor_no());
                    }
                    break;
                case "COM" :
                    Comment comEntity = comService.select(reportClusterDTO.getPostNo());
                    if (comEntity == null) {
                        reportClusterDTO.setPostNo(null);
                        userNickList.add("삭제된 유저");
                        urlList.add(null);
                    } else {
                        if (comEntity.getUser() == null) {
                            userNickList.add("삭제된 유저");
                        } else {
                            userNickList.add(comEntity.getUser().getUser_nic());
                        }
                        pageNum = comService.getCommentCurrentPageNum(comEntity.getCmtNo());
                        urlList.add("/service/loc_detail?locNo=" + comEntity.getLocation().getLoc_no() + "&page=" + pageNum + "&cmtNo=" + comEntity.getCmtNo());
                    }
                    break;
                case "REV" :
                    Review revEntity = reviewService.select(reportClusterDTO.getPostNo());
                    if (revEntity == null) {
                        reportClusterDTO.setPostNo(null);
                        userNickList.add("삭제된 유저");
                    } else {
                        user = userService.select(revEntity.getUser_no());
                        if (user == null) {
                            userNickList.add("삭제된 유저");
                        } else {
                            userNickList.add(user.getUser_nic());
                        }
                        pageNum = reviewService.getReviewCurrentPageNum(revEntity.getRevNo());
                        urlList.add("/service/cor_detail?corNo=" + revEntity.getCorNo() + "&page=" + pageNum + "&revNo=" + revEntity.getRevNo());
                    }
                    break;
                case "PROFILE_PIC" :
                    User userEntity = userService.select(reportClusterDTO.getPostNo());
                    if (userEntity == null) {
                        reportClusterDTO.setPostNo(null);
                        userNickList.add("삭제된 유저");
                    } else {
                        userNickList.add(userEntity.getUser_nic());
                        urlList.add(userEntity.getProfile_pic());
                    }
                    break;
                default :
                    urlList.add(null);
                    userNickList.add("삭제된 유저");
            }
        }

        model.addAttribute("clusterPageResultDTO", clusterPageResultDTO);
        model.addAttribute("clusterUserNickList", userNickList);
        model.addAttribute("urlList", urlList);

        return "/admin/admin_report_center";
    }

    @GetMapping("/report_center/report_detail")
    public String reportDetail(HttpServletRequest request,
                               Model model, @RequestParam Long rcNo){
        ReportClusterDTO reportClusterDTO = reportManageService.getReportClusterDTO(reportManageService.getReportClusterByRcNo(rcNo));
        ReportPageCompleteType completeType = ReportPageCompleteType.PROGRESS;
        String repType = null;
        List<String> repTypeList = new ArrayList<>();
        List<String> resultDTORepTypeKRList = new ArrayList<>();
        ReportPageRequestDTO pageRequestDTO = null;
        ReportPageResultDTO<ReportDTO, Report> pageResultDTO = null;

        if (request.getParameter("completeType") != null) {
            switch (request.getParameter("completeType")) {
                case "COMPLETE" :
                    completeType = ReportPageCompleteType.COMPLETE;
                    break;
                case "ALL" :
                    completeType = ReportPageCompleteType.ALL;
                    break;
                case "PROGRESS" :
                default:
                    completeType = ReportPageCompleteType.PROGRESS;
            }
        }

        if (request.getParameter("repType") != null) {
            repType = reportTypeMapKR2EN.get(request.getParameter("repType"));
//            repType = ReportType.valueOf(request.getParameter("repType")).toString();
        }

        switch (completeType) {
            case COMPLETE:
                pageRequestDTO = ReportPageRequestDTO.builder()
                        .rcNo(rcNo)
                        .repType(repType)
                        .completeType(ReportPageCompleteType.COMPLETE)
                        .build();
                break;
            case ALL:
                pageRequestDTO = ReportPageRequestDTO.builder()
                        .rcNo(rcNo)
                        .repType(repType)
                        .completeType(ReportPageCompleteType.ALL)
                        .build();
                break;
            case PROGRESS:
            default:
                pageRequestDTO = ReportPageRequestDTO.builder()
                        .rcNo(rcNo)
                        .repType(repType)
                        .completeType(ReportPageCompleteType.PROGRESS)
                        .build();
        }

        pageResultDTO = reportManageService.getReportPage(pageRequestDTO);
        repTypeList = reportManageService.getKRReportTypeList(rcNo);

        for (ReportDTO reportDTO : pageResultDTO.getDtoList()) {
            resultDTORepTypeKRList.add(reportTypeMapEN2KR.get(reportDTO.getRepType()));
        }

        model.addAttribute("pageResultDTO", pageResultDTO);
        model.addAttribute("repTypeList", repTypeList);
        model.addAttribute("resultDTORepTypeKRList", resultDTORepTypeKRList);
        model.addAttribute("rcDTO", reportClusterDTO);

        return "/popup/reportClusterDetailPopup";
    }

    @PostMapping("/report_center/report_process")
    @ResponseBody
    public boolean processReport(HttpServletRequest request, Model model,
                                 @RequestParam("rcNo") Long rcNo,
                                 @RequestParam("repNoList") Long[] repNoList,
                                 @RequestParam String result) {
        if (repNoList == null) {
            log.warn("rcNoList is null");
            return false;
        }

        if (repNoList.length == 0) {
            log.warn("rcNoList is empty");
            return false;
        }

        if (!reportManageService.processReport(Arrays.asList(repNoList), result)) {
            return false;
        }

        reportManageService.syncReportClusterRepCount(rcNo);

        return true;
    }

    @PostMapping("/report_center/post_perma_delete")
    @ResponseBody
    public String processPermaDeletePost(HttpServletRequest request, Model model,
                                         Authentication authentication, @RequestParam("rcNo") Long rcNo,
                                         @RequestParam("repNoList[]") Long[] repNoList, @RequestParam("postNo") Long postNo,
                                         @RequestParam("postType") String postType, @RequestParam("result") String result,
                                         @RequestParam("processType") String processType) {
        if (authentication == null) {
            return "authentication failed";
        }

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
        Long user_no = authUserModel.getUser_no();
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) authUserModel.getAuthorities();

        if (!request.isUserInRole("ROLE_ADMIN")) {
            log.warn("게시글 삭제를 요청한 유저가 어드민 권한이 없습니다");
            return "USER is not ADMIN";
        }

        switch (postType) {
            case "LOC" :
                Location locEntity = locService.selectLoc(postNo);

                if (locEntity == null) {
                    return "Location Entity Null";
                }

                locService.delete(locEntity.getLoc_no());
                locService.permaDelete(locEntity);

//                if (locService.selectLoc(postNo) == null) {
//                    reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
//                    return "Post Perma Delete Successful";
//                }
//
//                break;
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";
            case "COR" :
                Course corEntity = corService.selectCor(postNo);

                if (corEntity == null) {
                    return "Course cEntity Null";
                }

                corService.delete(corEntity.getCor_no());
                corService.permaDelete(corEntity.getCor_no());

//                if (corService.selectCor(postNo) == null) {
//                    reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
//                    return "Post Perma Delete Successful";
//                }
//
//                break;
            reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
            return "Post Perma Delete Successful";
            case "COM" :
                Comment comEntity = comService.select(postNo);

                if (comEntity == null) {
                    return "Comment Entity Null";
                }

                comService.delete(comEntity.getCmtNo());
                comService.permaDelete(comEntity);

//                if (comService.select(postNo) == null) {
//                    reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
//                    return "Post Perma Delete Successful";
//                }
//
//                break;
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";
            case "REV" :
                Review revEntity = reviewService.select(postNo);

                if (revEntity == null) {
                    return "Review Entity Null";
                }

                reviewService.delete(revEntity.getRevNo());
                reviewService.permaDelete(revEntity);

//                if (reviewService.select(postNo) == null) {
//                    reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
//                    return "Post Perma Delete Successful";
//                }
//
//                break;
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";
            case "PROFILE_PIC" :
                User userEntity = userService.select(postNo);

                if (userEntity == null) {
                    return "User Entity Null";
                }

                if (userService.changeProfilePicToDefault(userEntity.getUser_no())) {
                    processType = "기본 프로필 사진으로 변경";
                    reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                    return "User ProfilePic Perma Delete Successful";
                }

                break;
        }

        return "fail";
    }

    @PostMapping("/report_center/user_suspension")
    @ResponseBody
    public String rc_user_suspension(HttpServletRequest request, Model model,
                                         Authentication authentication, @RequestParam("rcNo") Long rcNo,
                                         @RequestParam("repNoList[]") Long[] repNoList, @RequestParam("postNo") Long postNo,
                                         @RequestParam("postType") String postType, @RequestParam("result") String result,
                                         @RequestParam("userNo") Long userNo, @RequestParam("stopDay") String stopDay,
                                         @RequestParam("reText") String reText,
                                         @RequestParam("processType") String processType) {
        if (authentication == null) {
            return "authentication failed";
        }

        if (!request.isUserInRole("ROLE_ADMIN")) {
            log.warn("게시글 삭제를 요청한 유저가 어드민 권한이 없습니다");
            return "USER is not ADMIN";
        }

        List<UserSuspension> userSuspensionList = userService.findStopByUser_no(userNo, "1");
        User user = userService.select(userNo);
        user.setUser_Activation(false);
        userService.update(user);

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE,Integer.parseInt(stopDay));

        String progress;
        if(userSuspensionList.size() > 0){
            progress = "2";
        }else {
            progress = "1";
        }

        UserSuspension userSuspension = UserSuspension.builder()
                .user_no(userNo)
                .rc_no(rcNo)
                .re_content(reText)
                .start_day(format1.format(today))
                .stop_day(stopDay)
                .end_day(format1.format(cal.getTime()))
                .progress(progress)
                .build();
        userService.su_update(userSuspension);

        ReportCluster reportCluster = reportManageService.getReportClusterByRcNo(rcNo);
        reportCluster.setRcResult(reportCluster.getRcResult() + "\r\n" + reText);

        switch (postType) {
            case "LOC" :
                Location locEntity = locService.selectLoc(postNo);

                if (locEntity == null) {
                    return "Location Entity Null";
                }

                locService.delete(locEntity.getLoc_no());
                locService.permaDelete(locEntity);
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";
            case "COR" :
                Course corEntity = corService.selectCor(postNo);

                if (corEntity == null) {
                    return "Course cEntity Null";
                }

                corService.delete(corEntity.getCor_no());
                corService.permaDelete(corEntity.getCor_no());
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";
            case "COM" :
                Comment comEntity = comService.select(postNo);

                if (comEntity == null) {
                    return "Comment Entity Null";
                }

                comService.delete(comEntity.getCmtNo());
                comService.permaDelete(comEntity);
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";

            case "REV" :
                Review revEntity = reviewService.select(postNo);

                if (revEntity == null) {
                    return "Review Entity Null";
                }

                reviewService.delete(revEntity.getRevNo());
                reviewService.permaDelete(revEntity);
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Perma Delete Successful";
            case "PROFILE_PIC" :
                User userEntity = userService.select(postNo);

                if (userEntity == null) {
                    return "User Entity Null";
                }

                processType = "기본 프로필 사진으로 변경";
                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "User ProfilePic Perma Delete Successful";
        }

        return "fail";
    }

    @PostMapping("/report_center/post_unblind")
    @ResponseBody
    public String processUnblindPost(HttpServletRequest request, Model model,
                                         Authentication authentication, @RequestParam("rcNo") Long rcNo,
                                         @RequestParam("repNoList[]") Long[] repNoList, @RequestParam("postNo") Long postNo,
                                         @RequestParam("postType") String postType, @RequestParam("result") String result,
                                         @RequestParam("processType") String processType) {
        if (authentication == null) {
            return "authentication failed";
        }

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
        Long user_no = authUserModel.getUser_no();
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) authUserModel.getAuthorities();

        if (!request.isUserInRole("ROLE_ADMIN")) {
            log.warn("게시글 삭제를 요청한 유저가 어드민 권한이 없습니다");
            return "USER is not ADMIN";
        }

        switch (postType) {
            case "LOC" :
                Location locEntity = locService.selectLoc(postNo);

                if (locEntity == null) {
                    return "Location Entity Null";
                }

                locEntity.set_reported(false);
                locEntity.set_deleted(false);
                locService.update(locEntity);

                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Unblind Successful";
            case "COR" :
                Course corEntity = corService.selectCor(postNo);

                if (corEntity == null) {
                    return "Course cEntity Null";
                }

                corEntity.set_reported(false);
                corEntity.set_deleted(false);
                corService.update(corEntity);

                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Unblind Successful";
            case "COM" :
                Comment comEntity = comService.select(postNo);

                if (comEntity == null) {
                    return "Comment Entity Null";
                }

                comEntity.set_reported(false);
                comEntity.set_deleted(false);
                comService.update(comEntity);

                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Unblind Successful";
            case "REV" :
                Review revEntity = reviewService.select(postNo);

                if (revEntity == null) {
                    return "Review Entity Null";
                }

                revEntity.set_reported(false);
                revEntity.set_deleted(false);
                reviewService.update(revEntity);

                reportManageService.processReportCluster(rcNo, Arrays.asList(repNoList), result, processType);
                return "Post Unblind Successful";
            case "PROFILE_PIC" :
                return "User ProfilePic Unblind Successful";
        }

        return "fail";
    }
}
