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

import java.security.Principal;

import static com.project.love_data.util.ConstantValues.*;

@Controller
@Log4j2
public class CommentController {
    @Autowired
    LocationService locService;
    @Autowired
    CommentService cmtService;

    @PostMapping("/service/com_registration")
    public String regComment(HttpServletRequest request, Model model,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
//        log.info("locNo : " + request.getParameter("locNo"));
//        log.info("userNo : " + request.getParameter("userNo"));
//        log.info("cmtContent : " + request.getParameter("cmtContent"));

        Long locNo = Long.valueOf(request.getParameter("locNo"));
        Long userNo = Long.valueOf(request.getParameter("userNo"));
        String cmtContent = request.getParameter("cmtContent");

       Comment entity = cmtService.createCmtEntity(locNo, userNo, cmtContent);

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
                = cmtService.getCmtPage(pageRequestDTO);

        model.addAttribute("dto", dto);
        model.addAttribute("resComDTO", resultCommentDTO);

//        return "redirect:/service/loc_recommend";
        return "redirect:/service/loc_detail?locNo=" + locNo;
    }

    @PostMapping("/service/com_edit")
    public String editComment(HttpServletRequest request, Model model, Principal principal,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        Long locNo = Long.valueOf(request.getParameter("locNo"));
        String userEmail = request.getParameter("userEmail");
        String cmt_uuid = request.getParameter("cmt_uuid");
        String cmtContent = request.getParameter("cmtContent");
        boolean returnFlag = false;

        Location loc_temp = locService.selectLoc(locNo);
        Comment cmt_temp = cmtService.select(cmt_uuid);

        if (!userEmail.equals(principal.getName())){
            log.warn(principal.getName());
            log.warn("Not Match with Comment Posted User and Current Logined User");
            log.warn("Please Check User");
            returnFlag = true;
        }

        if (cmt_temp == null) {
            log.warn(cmt_uuid);
            log.warn("Can't not find Comment");
            log.warn("Please Check Comment uuid is match with current Comment");
            returnFlag = true;
        }

        if (returnFlag || !cmt_temp.getLocation().getLoc_no().equals(loc_temp.getLoc_no())){
            log.warn("Current Comment is not match with Location");
            log.warn("Please Check Location");
            returnFlag = true;
        }

        if (!returnFlag) {
            cmt_temp.setCmtContent(cmtContent);
            cmtService.update(cmt_temp);
        }

        LocationDTO dto = locService.selectLocDTO(locNo);
        pageRequestDTO.setSize(MAX_COM_COUNT);
        PageResultDTO<CommentDTO, Comment> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
                = cmtService.getCmtPage(pageRequestDTO);

        model.addAttribute("dto", dto);
        model.addAttribute("resComDTO", resultCommentDTO);

        return "redirect:/service/loc_detail?locNo=" + locNo;
    }

    @PostMapping("/service/com_del")
    public String delComment(HttpServletRequest request, Model model, Principal principal,
                              @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        Long locNo = Long.valueOf(request.getParameter("locNo"));
        String userEmail = request.getParameter("userEmail");
        String cmt_uuid = request.getParameter("cmt_uuid");
        boolean returnFlag = false;

        Location loc_temp = locService.selectLoc(locNo);
        Comment cmt_temp = cmtService.select(cmt_uuid);

        if (!userEmail.equals(principal.getName())){
            log.warn(principal.getName());
            log.warn("Not Match with Comment Posted User and Current Logined User");
            log.warn("Please Check User");
            returnFlag = true;
        }

        if (cmt_temp == null) {
            log.warn(cmt_uuid);
            log.warn("Can't not find Comment");
            log.warn("Please Check Comment uuid is match with current Comment");
            returnFlag = true;
        }

        if (returnFlag || !cmt_temp.getLocation().getLoc_no().equals(loc_temp.getLoc_no())){
            log.warn("Current Comment is not match with Location");
            log.warn("Please Check Location");
            returnFlag = true;
        }

        if (!returnFlag) {
            cmtService.delete(cmt_temp.getCmtNo());
        }

        LocationDTO dto = locService.selectLocDTO(locNo);
        pageRequestDTO.setSize(MAX_COM_COUNT);
        PageResultDTO<CommentDTO, Comment> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
                = cmtService.getCmtPage(pageRequestDTO);

        model.addAttribute("dto", dto);
        model.addAttribute("resComDTO", resultCommentDTO);

        return "redirect:/service/loc_detail?locNo=" + locNo;
    }
}
