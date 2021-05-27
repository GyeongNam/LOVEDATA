package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Log4j2
@Controller
public class ServiceController {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    LocationService locService;
    @Autowired
    ImageService imgService;

    final static int MAX_LOC_REC_PAGE_SIZE_COUNT = 4;
    final static int MAX_UPLOAD_COUNT = 10;
    final static int MIN_UPLOAD_COUNT = 3;
    List<String> tagList = new ArrayList<>();

    @RequestMapping("/service/loc_registration")
    public String loc_Reg() {
        return "/service/loc_registration";
    }

    @RequestMapping("/popup/jusoPopup")
    public String popup_juso() {
        return "/popup/jusoPopup";
    }

    @PostMapping("/service/loc/tags")
    public void locGetTagsList(@RequestParam("tags[]") String[] tagArray) {
        tagList = Arrays.asList(tagArray);
    }

    @GetMapping("/service/loc_registration/regData")
    public String locRegistartionDataNoAccess() {
        log.info("Access Invalid(Shouldn't Access with GET Method)");
        return "redirect:/service/loc_recommend";
    }

    @PostMapping("/service/loc_registration/regData")
    public String locRegistrationData(@RequestParam("files") List<MultipartFile> fileList,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        List<String> filePath = null;
        Map<String, String> reqParam = new HashMap<>();

        reqParam.put("name", request.getParameter("name"));
        reqParam.put("tel", request.getParameter("tel"));
        reqParam.put("info", request.getParameter("info"));
        reqParam.put("zipNo", request.getParameter("zipNo"));
        reqParam.put("roadAddr", request.getParameter("roadAddrPart1"));
        reqParam.put("addrDetail", request.getParameter("addrDetail"));
        reqParam.put("siDoName", request.getParameter("siNm"));
        reqParam.put("siGunGuName", request.getParameter("sggNm"));
//        // Todo Debug 목적용 코드 나중에 삭제할 것
        if (request.getParameter("user_no") != null) {
            reqParam.put("user_no", (request.getParameter("user_no")));
        } else {
            reqParam.put("user_no", (request.getParameter("user_no_debug")));
        }

        // Todo 입력시 최소 1개의 태그는 추가하도록 하는 javascript 및 백엔드 서버 기능 추가
        // Todo 전화번호 입력 포맷 완성시키기
        if (tagList.isEmpty()) {
            log.warn("No Location Tag Found (Must add tag before submit location)");
            return "redirect:/service/loc_recommend";
        }

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/loc_recommend";
        }

        LocationDTO dto = locService.entityToDto(locService.register(reqParam, tagList, filePath));
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/service/loc_recommend";
    }

    @GetMapping(value = "/service/loc_recommend")
    public String goToLocRecommendList() {
        return "redirect:/service/loc_recommend/list";
    }

    @GetMapping(value = "/service/loc_detail")
    public String locDetail(Long locNo, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                            Model model,
                            HttpServletRequest request) {
        LocationDTO dto = locService.select(locNo);

        model.addAttribute("dto", dto);

        return "/service/loc_detail";
    }

    @GetMapping(value = "/service/loc_recommend/list")
    public String locRecommendList(HttpServletRequest request,
                                   PageRequestDTO pageRequestDTO,
                                   Model model) {
        pageRequestDTO.setSize(MAX_LOC_REC_PAGE_SIZE_COUNT);
        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);

        log.info("list................." + pageRequestDTO);

        model.addAttribute("result", resultDTO);

        System.out.println("Result.page = " + resultDTO.getPage());
        System.out.println("Result start = " + resultDTO.getStart());
        System.out.println("Result end = " + resultDTO.getEnd());

        System.out.println("PREV = " + resultDTO.isPrev());
        System.out.println("NEXT = " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------------------");
        System.out.println("Content Count : " + resultDTO.getDtoList().size());
        for (LocationDTO locationDTO : resultDTO.getDtoList()) {
            System.out.println(locationDTO.getLoc_no() + "\tlocationDTO = " + locationDTO);
        }

        System.out.println("=================================================");
        List<Integer> temp = resultDTO.getPageList();
        resultDTO.getPageList().forEach(i -> System.out.println(i));

        resultDTO.getDtoList().get(0).getImgList().isEmpty();
        return "/service/loc_recommend";
    }
}