package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.CommentPageType;
import com.project.love_data.businessLogic.service.CommentService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.project.love_data.util.ConstantValues.*;

@Controller
@Log4j2
public class CommentController {
    @Autowired
    LocationService locService;
    @Autowired
    CommentService comService;

    @PostMapping("/service/com_registration")
    public String regComment(HttpServletRequest request, Model model,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
//        log.info("locNo : " + request.getParameter("locNo"));
//        log.info("userNo : " + request.getParameter("userNo"));
//        log.info("cmtContent : " + request.getParameter("cmtContent"));

        Long locNo = Long.valueOf(request.getParameter("locNo"));
        Long userNo = Long.valueOf(request.getParameter("userNo"));
        String cmtContent = request.getParameter("cmtContent");

       Comment entity = comService.createCmtEntity(locNo, userNo, cmtContent);

       if (entity == null) {
           log.info("Error Occurs during Comment entity creation");
           log.info("Please check Input value");
           return "redirect:/service/loc_detail?locNo=" + locNo;
       }

       Location locEntity = locService.selectLoc(locNo);
       locEntity.addCmt(entity);
       locService.update(locEntity);

        LocationDTO dto = locService.selectLocDTO(locNo);
        pageRequestDTO.setSize(MAX_COM_COUNT);
        PageResultDTO<CommentDTO, Comment> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
                = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION);

        model.addAttribute("dto", dto);
        model.addAttribute("resComDTO", resultCommentDTO);

//        return "redirect:/service/loc_recommend";
        return "redirect:/service/loc_detail?locNo=" + locNo;
    }
}
