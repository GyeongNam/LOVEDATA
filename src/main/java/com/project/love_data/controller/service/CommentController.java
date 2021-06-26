package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.CommentPageType;
import com.project.love_data.businessLogic.service.CommentService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import static com.project.love_data.util.ConstantValues.*;

@Controller
public class CommentController {
    @Autowired
    LocationService locService;
    @Autowired
    CommentService comService;

    @PostMapping("/service/com_registration")
    public String regComment(HttpServletRequest request, Model model,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        Long locNo = Long.valueOf(request.getParameter("locNo"));

        LocationDTO dto = locService.selectLocDTO(locNo);
        pageRequestDTO.setSize(MAX_COM_COUNT);
        PageResultDTO<CommentDTO, Comment> resultCommentDTO
//                    = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC);
                = comService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION);

        model.addAttribute("dto", dto);
        model.addAttribute("resComDTO", resultCommentDTO);

        return null;
    }
}
