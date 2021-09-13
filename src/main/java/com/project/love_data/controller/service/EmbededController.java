package com.project.love_data.controller.service;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.model.service.CorLocMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/embeded")
public class EmbededController {
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    CorLocMapperService corLocMapperService;
    @Autowired
    UserService userService;
    @Autowired
    ControllerScriptUtils scriptUtils;

    @RequestMapping("/map")
    public String map(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Long locNo) throws IOException {
        boolean isNullLoc = false;

        if (locNo == null) {
            isNullLoc = true;
            scriptUtils.alert(response, "존재하지 않는 장소 번호입니다.");
        }

        LocationDTO dto = locService.selectLocDTO(locNo);

        if (dto == null && !isNullLoc) {
            isNullLoc = true;
            scriptUtils.alert(response, "장소가 존재하지 않습니다.");
        }

        model.addAttribute("dto", dto);
        model.addAttribute("isNullLoc", isNullLoc);

        return "/Embeded/tmapMap";
    }

    @RequestMapping("/pathFinding")
    public String pathFinding(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Long corNo) throws IOException {
        boolean isNullCor = false;
        List<CorLocMapper> corLocMapperList = new ArrayList<>();
        List<LocationDTO> locDTOList = new ArrayList<>();
        List<Integer> nullLocIdxList = new ArrayList<>();

        if (corNo == null) {
            isNullCor = true;
            scriptUtils.alert(response, "존재하지 않는 코스 번호입니다.");
        } else {
            corLocMapperList = corLocMapperService.getLocationsByCorNo(corNo);
            locDTOList = new ArrayList<>();

            for (int i = 0; i < corLocMapperList.size(); i++) {
                LocationDTO locDTO = locService.selectLiveLocDTO(corLocMapperList.get(i).getLoc_no());
                if (locDTO != null) {
                    locDTOList.add(locDTO);
                } else {
                    nullLocIdxList.add(corLocMapperList.get(i).getLoc_index());
                }
            }
        }

        model.addAttribute("isNullCor", isNullCor);
        model.addAttribute("locDTOList", locDTOList);
        model.addAttribute("nullLocIdxList", nullLocIdxList);

        return "/Embeded/tmapPathFinding";
    }
}
