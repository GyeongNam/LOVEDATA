package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
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
    @Autowired
    UserService userService;
    @Autowired
    CommentService comService;

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
    public String getLocSearchValue(HttpServletRequest request, Model model) {
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
    public String adminDashboadr(HttpServletRequest request, Model model) {
        final int size = 20;
        final int dateDuration = 30;
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

        return "/admin/admin_dash";
    }
}
