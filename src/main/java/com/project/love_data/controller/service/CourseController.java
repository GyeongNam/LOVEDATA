package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.SortCriterion;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.model.service.UserLikeLoc;
import com.project.love_data.security.model.AuthUserModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.project.love_data.util.ConstantValues.MAX_LOC_LIST_SIZE;

@Controller
public class CourseController {

//    @GetMapping (name = "/service/cor_recommend")
//    public String corRecommend(HttpServletRequest request) {
//        return "redirect:/service/cor_recommend/list";
//    }

//    @GetMapping(value = "/service/cor_recommend/list")
//    public String locRecommendList(HttpServletRequest request,
////                                   PageRequestDTO pageRequestDTO,
//                                   Authentication authentication,
//                                   Model model) {
//        List<LocationTag> tagList = Arrays.asList(LocationTag.values());
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .size(MAX_LOC_LIST_SIZE)
//                .sortCriterion(SortCriterion.VIEW)
//                .build();
//        PageResultDTO<LocationDTO, Location> resultDTO = locService.getList(pageRequestDTO);
//        List<Boolean> isLikedList = new ArrayList<>();
//
//        if (authentication == null) {
//            for (int i = 0; i < resultDTO.getSize(); i++) {
//                isLikedList.add(false);
//            }
//        } else {
//            AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
//            Long user_no = authUserModel.getUser_no();
//            for (int i = 0; i < resultDTO.getDtoList().size(); i++) {
//                Long loc_no = resultDTO.getDtoList().get(i).getLoc_no();
//                UserLikeLoc item = userLikeLocService.selectByLocNoAndUserNo(loc_no, user_no);
//                if (item != null){
//                    isLikedList.add(true);
//                } else {
//                    isLikedList.add(false);
//                }
//            }
//        }
}
