package com.project.love_data.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.model.service.UserLikeCor;
import com.project.love_data.model.service.UserLikeLoc;
import com.project.love_data.model.user.User;
import com.project.love_data.util.DefaultLocalDateTimeFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.project.love_data.util.ConstantValues.MAX_LOC_LIST_SIZE;

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

        if (authentication == null ) {
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

        if ("".equals(item_no)){
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

            if (item != null){
                log.warn(item_no + "(loc_no) has been liked before!");
                log.warn("Please Check");
                return false;
            }

            if ( locService.onClickLike(item_no)) {
                if (likeLocService.register(item_no, user_no) == null) {
                    locService.onClickLikeUndo(item_no);
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

            if (item != null){
                log.warn(item_no + "(cor_no) has been liked before!");
                log.warn("Please Check");
                return false;
            }

            if (corService.onClickLike(item_no)) {
                if (likeCorService.register(item_no, user_no) == null) {
                    corService.onClickLikeUndo(item_no);
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

            if (item == null){
                log.warn(item_no + " has not been like before!");
                log.warn("Please Check");
                return false;
            }

            if ( locService.onClickLikeUndo(item_no)) {
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

            if (item == null){
                log.warn(item_no + " has not been like before!");
                log.warn("Please Check");
                return false;
            }

            if ( corService.onClickLikeUndo(item_no)) {
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

        switch (sortOrder){
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
                response.put("locUserName_" + i, "null");
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

        if (!locationByID.equals(locationByNo)){
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
        response.put("locThumbnail", serverURL +  locationByID.getThumbnail());

        String tags = "";

        for (String tag: locationByID.getTagSet()) {
            tags = tags + tag + ", ";
        }
        tags = tags.substring(0, tags.length()-2);

        response.put("tags", tags);

        Gson gson = new Gson();

        String jsonObject = gson.toJson(response);

//        log.info(jsonObject);

        return jsonObject;
    }
}
