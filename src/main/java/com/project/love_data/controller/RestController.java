package com.project.love_data.controller;


import com.google.gson.Gson;
import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
import com.project.love_data.util.DefaultLocalDateTimeFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Log4j2
@Controller
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    UserLikeLocService likeLocService;
    @Autowired
    UserLikeCorService likeCorService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService cmtService;
    @Autowired
    ReviewService revService;
    @Autowired
    UserLikeCmtService userLikeCmtService;
    @Autowired
    UserDislikeCmtService userDislikeCmtService;
    @Autowired
    UserLikeRevService userLikeRevService;
    @Autowired
    UserDislikeRevService userDislikeRevService;
    @Autowired
    ReportManageService reportManageService;
    @Autowired
    BizRegService bizRegService;
    DefaultLocalDateTimeFormatter defaultDateTimeFormatter = new DefaultLocalDateTimeFormatter();

    @PostMapping("/authenticationCheck")
    public boolean onAuthenticationCheck(Authentication authentication,
                                         @RequestBody HashMap<String, Boolean> data) {
//        boolean debug = data.get("debug");
//
//        log.info(data);
//        log.info(debug);
//
//        if(debug) {
//            return true;
//        }

        if (authentication == null) {
            return false;
        }

        return authentication.isAuthenticated();
    }

    @PostMapping("/onClickLike")
    public boolean onLikeClicked(@RequestBody HashMap<String, String> data) {
//        log.info("data : " + data);

        Long item_no = Long.valueOf(data.get("item_no"));
        Long user_no = Long.valueOf(data.get("user_no"));
        String type = data.get("type");

        if (user_no == -1) {
            log.info("Not Registered!");
            return false;
        }

        if (item_no == null) {
            log.warn("item_no null value");
            return false;
        }

        if ("".equals(item_no)) {
            log.warn("item_no empty value");
            return false;
        }

        User user = userService.select(user_no);

        if (user == null) {
            log.warn("Not Registerd User!");
            log.warn("Please Check");
            return false;
        }

        if ("loc".equals(type)) {
            UserLikeLoc item = likeLocService.selectByLocNoAndUserNo(item_no, user_no);

            if (item != null) {
                log.warn(item_no + "(loc_no) has been liked before!");
                log.warn("Please Check");
                return false;
            }

            if (locService.onClickLike(item_no)) {
                if (likeLocService.register(item_no, user_no) == null) {
                    locService.onClickLikeDec(item_no);
                    log.warn("UserLikeLocation register not Completed");
                    return false;
                }
                log.info("Location Liked Successful");
                return true;
            } else {
                log.info("loc_no is not correct");
                return false;
            }
        }

        if ("cor".equals(type)) {
            UserLikeCor item = likeCorService.selectByCorNoAndUserNo(item_no, user_no);

            if (item != null) {
                log.warn(item_no + "(cor_no) has been liked before!");
                log.warn("Please Check");
                return false;
            }

            if (corService.onClickLike(item_no)) {
                if (likeCorService.register(item_no, user_no) == null) {
                    corService.onClickLikeDec(item_no);
                    log.warn("UserLikeCourse register not Completed");
                    return false;
                }
                log.info("Course Liked Successful");
                return true;
            } else {
                log.info("cor_no is not correct");
                return false;
            }
        }

        return false;
    }

    @PostMapping("/onClickLikeUndo")
    public boolean onLikeClickUndo(@RequestBody HashMap<String, String> data) {
        log.info("data : " + data);

        Long item_no = Long.valueOf(data.get("item_no"));
        Long user_no = Long.valueOf(data.get("user_no"));
        String type = data.get("type");

        if (item_no == null) {
            log.warn("item_no null value");
            return false;
        }

        if ("".equals(item_no)) {
            log.warn("item_no empty value");
            return false;
        }

        if ("loc".equals(type)) {
            UserLikeLoc item = likeLocService.selectByLocNoAndUserNo(item_no, user_no);

            if (item == null) {
                log.warn(item_no + " has not been like before!");
                log.warn("Please Check");
                return false;
            }

            if (locService.onClickLikeDec(item_no)) {
                if (!likeLocService.delete(item_no, user_no)) {
                    locService.onClickLike(item_no);
                    log.warn("UserLikeLocation delete not Completed");
                    return false;
                }
                log.info("Location Like Undo Successful");
                return true;
            } else {
                log.info("Location Like Undo failed");
                return false;
            }
        }

        if ("cor".equals(type)) {
            UserLikeCor item = likeCorService.selectByCorNoAndUserNo(item_no, user_no);

            if (item == null) {
                log.warn(item_no + " has not been like before!");
                log.warn("Please Check");
                return false;
            }

            if (corService.onClickLikeDec(item_no)) {
                if (!likeCorService.delete(item_no, user_no)) {
                    corService.onClickLike(item_no);
                    log.warn("UserLikeCourse delete not Completed");
                    return false;
                }
                log.info("Course Like Undo Successful");
                return true;
            } else {
                log.info("Course Like Undo failed");
                return false;
            }
        }

        return false;
    }

    @PostMapping("/service/loc/search")
    @ResponseBody
    public String onSearchLocation(HttpServletRequest request, @RequestBody HashMap<String, String> data) {
        String keyword = data.get("keyword");
        String sortOrder = data.get("sortOrder");
        String type = data.get("searchType");

        SortingOrder sortingOrder = null;
        SortCriterion sortCriterion = null;
        SearchType searchType = SearchType.valueOf(type);

        switch (sortOrder) {
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
                .size(Integer.MAX_VALUE)
                .searchType(searchType)
                .keyword(keyword)
                .tagList(null)
                .sortCriterion(sortCriterion)
                .sortingOrder(sortingOrder)
                .page(1)
                .build();

        PageResultDTO<LocationDTO, Location> resultDTO = locService.getList(pageRequestDTO);

        HashMap<String, String> response = new HashMap<>();

        response.put("size", String.valueOf(resultDTO.getDtoList().size()));
        for (int i = 0; i < resultDTO.getDtoList().size(); i++) {
            response.put("locName_" + i, resultDTO.getDtoList().get(i).getLoc_name());
            response.put("locAddr_" + i, resultDTO.getDtoList().get(i).getRoadAddr());
            response.put("locNo_" + i, String.valueOf(resultDTO.getDtoList().get(i).getLoc_no()));
            response.put("locID_" + i, String.valueOf(resultDTO.getDtoList().get(i).getLoc_uuid()));
            response.put("locRegDate_" + i,
                    String.valueOf(resultDTO.getDtoList().get(i).getRegDate().format(defaultDateTimeFormatter.getDateTimeFormatter())));
            response.put("locLikeCount_" + i, String.valueOf(resultDTO.getDtoList().get(i).getLikeCount()));
            response.put("locViewCount_" + i, String.valueOf(resultDTO.getDtoList().get(i).getViewCount()));

            User user = userService.select(resultDTO.getDtoList().get(i).getUser_no());
            if (user == null) {
                response.put("locUserName_" + i, "");
            } else {
                response.put("locUserName_" + i, user.getUser_nic());
            }
        }

        Gson gson = new Gson();

        String jsonObject = gson.toJson(response);

        return jsonObject;
    }

    @PostMapping("/service/loc/select")
    @ResponseBody
    public String onSelectLocation(HttpServletRequest request, @RequestBody HashMap<String, String> data) {
        Long locNo = Long.valueOf(data.get("locNo"));
        String locID = data.get("locID");

        Location locationByNo = locService.selectLoc(locNo);
        Location locationByID = locService.selectLoc(locID);

        if (!locationByID.equals(locationByNo)) {
            return null;
        }

        HashMap<String, String> response = new HashMap<>();

        response.put("locNo", String.valueOf(locationByID.getLoc_no()));
        response.put("locID", locationByID.getLoc_uuid());
        response.put("locName", locationByID.getLoc_name());
        response.put("locAddr", locationByID.getFullAddr());
        response.put("locTel", locationByID.getTel());
        response.put("locInfo", locationByID.getInfo());

        String reqURL = String.valueOf(request.getRequestURL());
        String serverURL = reqURL.substring(0, reqURL.indexOf("/", 9));
//        log.info(serverURL);
        response.put("locThumbnail", serverURL + locationByID.getThumbnail());

        String tags = "";

        for (String tag : locationByID.getTagSet()) {
            tags = tags + tag + ", ";
        }
        tags = tags.substring(0, tags.length() - 2);

        response.put("tags", tags);

        Gson gson = new Gson();

        String jsonObject = gson.toJson(response);

//        log.info(jsonObject);

        return jsonObject;
    }

    @PostMapping("/service/cor/search")
    @ResponseBody
    public String onSearchCourse(HttpServletRequest request, @RequestBody HashMap<String, String> data) {
        String keyword = data.get("keyword");
        String sortOrder = data.get("sortOrder");
        String type = data.get("searchType");

        SortingOrder sortingOrder = null;
        SortCriterion sortCriterion = null;
        SearchType searchType = SearchType.valueOf(type);

        switch (sortOrder) {
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
                .size(Integer.MAX_VALUE)
                .searchType(searchType)
                .keyword(keyword)
                .tagList(null)
                .sortCriterion(sortCriterion)
                .sortingOrder(sortingOrder)
                .page(1)
                .build();

        PageResultDTO<CourseDTO, Course> resultDTO = corService.getList(pageRequestDTO);

        HashMap<String, String> response = new HashMap<>();

        response.put("size", String.valueOf(resultDTO.getDtoList().size()));
        for (int i = 0; i < resultDTO.getDtoList().size(); i++) {
            response.put("corName_" + i, resultDTO.getDtoList().get(i).getCor_name());
//            response.put("locAddr_" + i, resultDTO.getDtoList().get(i).getRoadAddr());
            response.put("corNo_" + i, String.valueOf(resultDTO.getDtoList().get(i).getCor_no()));
            response.put("corID_" + i, String.valueOf(resultDTO.getDtoList().get(i).getCor_uuid()));
            response.put("corRegDate_" + i,
                    String.valueOf(resultDTO.getDtoList().get(i).getRegDate().format(defaultDateTimeFormatter.getDateTimeFormatter())));
            response.put("corLikeCount_" + i, String.valueOf(resultDTO.getDtoList().get(i).getLikeCount()));
            response.put("corViewCount_" + i, String.valueOf(resultDTO.getDtoList().get(i).getViewCount()));

            User user = userService.select(resultDTO.getDtoList().get(i).getUser_no());
            if (user == null) {
                response.put("corUserName_" + i, "");
            } else {
                response.put("corUserName_" + i, user.getUser_nic());
            }
        }

        Gson gson = new Gson();

        String jsonObject = gson.toJson(response);

        return jsonObject;
    }

    @PostMapping("/onCmtBtnClicked")
    public boolean onCmtBtnClicked(@RequestBody HashMap<String, String> data) {
//        log.info("data : " + data);

        Long cmt_no = Long.valueOf(data.get("cmt_no"));
        Long user_no = Long.valueOf(data.get("user_no"));
        String type = data.get("type");

        if (user_no == -1) {
            log.info("Not Registered!");
            return false;
        }

        if (cmt_no == null) {
            log.warn("cmt_no null value");
            return false;
        }

        if ("".equals(cmt_no)) {
            log.warn("item_no empty value");
            return false;
        }

        User user = userService.select(user_no);

        if (user == null) {
            log.warn("Not Registerd User!");
            log.warn("Please Check");
            return false;
        }

        if (type.equals("cmt_like")) {
            Comment cmt = cmtService.select(cmt_no);

            if (cmt == null) {
                log.warn("No matching result of cmt_no");
                return false;
            }

            UserLikeCmt likeCmt = userLikeCmtService.selectBycmtNoAndUserNo(cmt_no, user_no);
            UserDislikeCmt dislikeCmt = userDislikeCmtService.selectBycmtNoAndUserNo(cmt_no, user_no);

            if (likeCmt != null) {
                log.warn("User already liked comment.");
                return false;
            }

            if (dislikeCmt != null) {
                log.info("User dislike this comment before.");
                log.info("Remove the dislike history");

                userDislikeCmtService.delete(dislikeCmt.getCmt_no(), dislikeCmt.getUser_no());
                cmtService.decDislikeCount(cmt_no);
            }

            userLikeCmtService.register(cmt_no, user_no);

            cmtService.incLikeCount(cmt_no);

            log.info("Comment Like Successful");

            return true;
        }

        if (type.equals("cmt_dislike")) {
            Comment cmt = cmtService.select(cmt_no);

            if (cmt == null) {
                log.warn("No matching result of cmt_no");
                return false;
            }

            UserLikeCmt likeCmt = userLikeCmtService.selectBycmtNoAndUserNo(cmt_no, user_no);
            UserDislikeCmt dislikeCmt = userDislikeCmtService.selectBycmtNoAndUserNo(cmt_no, user_no);

            if (dislikeCmt != null) {
                log.warn("User already disliked comment.");
                return false;
            }

            if (likeCmt != null) {
                log.info("User like this comment before.");
                log.info("Remove the like history");

                userLikeCmtService.delete(likeCmt.getCmt_no(), likeCmt.getUser_no());
                cmtService.decLikeCount(cmt_no);
            }

            userDislikeCmtService.register(cmt_no, user_no);

            cmtService.incDislikeCount(cmt_no);

            log.info("Comment Dislike Successful");

            return true;
        }

        return false;
    }

    @PostMapping("/onCmtBtnClickUndo")
    public boolean onCmtBtnClickUndo(@RequestBody HashMap<String, String> data) {
        log.info("data : " + data);

        Long cmt_no = Long.valueOf(data.get("cmt_no"));
        Long user_no = Long.valueOf(data.get("user_no"));
        String type = data.get("type");

        if (cmt_no == null) {
            log.warn("item_no null value");
            return false;
        }

        if ("".equals(cmt_no)) {
            log.warn("item_no empty value");
            return false;
        }

        if ("cmt_like".equals(type)) {
            UserLikeCmt item = userLikeCmtService.selectBycmtNoAndUserNo(cmt_no, user_no);

            if (item == null) {
                log.warn(cmt_no + " has not been like before!");
                log.warn("Delete Comment Dislike History");

                userDislikeCmtService.delete(cmt_no, user_no);
            }

            if (cmtService.decLikeCount(cmt_no)) {
                if (!userLikeCmtService.delete(cmt_no, user_no)) {
                    cmtService.incLikeCount(cmt_no);
                    log.warn("UserLikeCmt {0} delete not Completed", cmt_no);
                    return false;
                }
                log.info("Comment Like Undo Successful");
                return true;
            } else {
                log.info("Comment Like Undo failed");
                return false;
            }
        }

        if ("cmt_dislike".equals(type)) {
            UserLikeCmt item = userLikeCmtService.selectBycmtNoAndUserNo(cmt_no, user_no);

            if (item == null) {
                log.info(cmt_no + " has not been like before!");
                log.info("Delete Comment Like history");

                userLikeCmtService.delete(cmt_no, user_no);
            }

            if (cmtService.decDislikeCount(cmt_no)) {
                if (!userDislikeCmtService.delete(cmt_no, user_no)) {
                    cmtService.incDislikeCount(cmt_no);
                    log.warn("UserDislikeCmt delete not Completed");
                    return false;
                }
                log.info("Comment Dislike Undo Successful");
                return true;
            } else {
                log.info("Comment Dislike Undo failed");
                return false;
            }
        }

        return false;
    }

    @PostMapping("/onRevBtnClicked")
    public boolean onRevBtnClicked(@RequestBody HashMap<String, String> data) {
//        log.info("data : " + data);

        Long rev_no = Long.valueOf(data.get("rev_no"));
        Long user_no = Long.valueOf(data.get("user_no"));
        String type = data.get("type");

        if (user_no == -1) {
            log.info("Not Registered!");
            return false;
        }

        if (rev_no == null) {
            log.warn("rev_no null value");
            return false;
        }

        if ("".equals(rev_no)) {
            log.warn("item_no empty value");
            return false;
        }

        User user = userService.select(user_no);

        if (user == null) {
            log.warn("Not Registerd User!");
            log.warn("Please Check");
            return false;
        }

        if (type.equals("rev_like")) {
            Review rev = revService.select(rev_no);

            if (rev == null) {
                log.warn("No matching result of rev_no");
                return false;
            }

            UserLikeRev likeRev = userLikeRevService.selectByRevNoAndUserNo(rev_no, user_no);
            UserDislikeRev dislikeRev = userDislikeRevService.selectByRevNoAndUserNo(rev_no, user_no);

            if (likeRev != null) {
                log.warn("User already liked review.");
                return false;
            }

            if (dislikeRev != null) {
                log.info("User dislike this review before.");
                log.info("Remove the dislike history");

                userDislikeRevService.delete(dislikeRev.getRev_no(), dislikeRev.getUser_no());
                revService.decDislikeCount(rev_no);
            }

            userLikeRevService.register(rev_no, user_no);

            revService.incLikeCount(rev_no);

            log.info("Review Like Successful");

            return true;
        }

        if (type.equals("rev_dislike")) {
            Review rev = revService.select(rev_no);

            if (rev == null) {
                log.warn("No matching result of rev_no");
                return false;
            }

            UserLikeRev likeRev = userLikeRevService.selectByRevNoAndUserNo(rev_no, user_no);
            UserDislikeRev dislikeRev = userDislikeRevService.selectByRevNoAndUserNo(rev_no, user_no);

            if (dislikeRev != null) {
                log.warn("User already disliked review.");
                return false;
            }

            if (likeRev != null) {
                log.info("User like this comment before.");
                log.info("Remove the like history");

                userLikeRevService.delete(likeRev.getRev_no(), likeRev.getUser_no());
                revService.decLikeCount(rev_no);
            }

            userDislikeRevService.register(rev_no, user_no);

            revService.incDislikeCount(rev_no);

            log.info("Review Dislike Successful");

            return true;
        }

        return false;
    }

    @PostMapping("/onRevBtnClickUndo")
    public boolean onRevBtnClickUndo(@RequestBody HashMap<String, String> data) {
        log.info("data : " + data);

        Long rev_no = Long.valueOf(data.get("rev_no"));
        Long user_no = Long.valueOf(data.get("user_no"));
        String type = data.get("type");

        if (rev_no == null) {
            log.warn("item_no null value");
            return false;
        }

        if ("".equals(rev_no)) {
            log.warn("item_no empty value");
            return false;
        }

        if ("rev_like".equals(type)) {
            UserLikeRev item = userLikeRevService.selectByRevNoAndUserNo(rev_no, user_no);

            if (item == null) {
                log.warn(rev_no + " has not been like before!");
                log.warn("Delete Comment Dislike History");

                userDislikeRevService.delete(rev_no, user_no);
            }

            if (revService.decLikeCount(rev_no)) {
                if (!userLikeRevService.delete(rev_no, user_no)) {
                    revService.incLikeCount(rev_no);
                    log.warn("UserLikeRev {0} delete not Completed", rev_no);
                    return false;
                }
                log.info("Review Like Undo Successful");
                return true;
            } else {
                log.info("Review Like Undo failed");
                return false;
            }
        }

        if ("rev_dislike".equals(type)) {
            UserLikeRev item = userLikeRevService.selectByRevNoAndUserNo(rev_no, user_no);

            if (item == null) {
                log.info(rev_no + " has not been like before!");
                log.info("Delete Comment Like history");

                userLikeRevService.delete(rev_no, user_no);
            }

            if (revService.decDislikeCount(rev_no)) {
                if (!userDislikeRevService.delete(rev_no, user_no)) {
                    revService.incDislikeCount(rev_no);
                    log.warn("UserDislikeRev delete not Completed");
                    return false;
                }
                log.info("Review Dislike Undo Successful");
                return true;
            } else {
                log.info("Review Dislike Undo failed");
                return false;
            }
        }

        return false;
    }

    @PostMapping("/report_reg")
    @ResponseBody
    public String regReport(HttpServletRequest request, Model model,
                             @RequestParam("repType")String repType,
                             @RequestParam("repContent")String repContent,
                             @RequestParam("postNo")Long postNo,
                             @RequestParam("postType")String postTye,
                             @RequestParam("userNo")Long userNo) {
        Map<String, String> reqParam = new HashMap<>();
        reqParam.put("repType", repType);
        reqParam.put("repContent", repContent);
        reqParam.put("postNo", String.valueOf(postNo));
        reqParam.put("postType", postTye);
        reqParam.put("userNo", String.valueOf(userNo));
        if (request.getParameter("dupCheck") == null) {
            reqParam.put("dupCheck", "false");
        } else {
            reqParam.put("dupCheck", request.getParameter("dupCheck"));
        }

        Report repEntity = reportManageService.registerReport(reqParam);

        if (repEntity == null) {
            return "Fail";
        }

        if (!reportManageService.syncReportClusterRepCount(repEntity.getRcNo())){
            log.warn("Sync Report Count Fail");
            return "Sync Report Count Fail";
        }

        return "Report Successful";
    }
}
