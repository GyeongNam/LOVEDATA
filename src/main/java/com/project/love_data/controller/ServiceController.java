package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationDTO;
import com.project.love_data.repository.ImageRepository;
import com.project.love_data.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Log4j2
@Controller
public class ServiceController {
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    LocationService locService;
    @Autowired
    ImageService imgService;

    final static int maxUploadCount = 10;
    final static int minUploadCount = 3;
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
                                      HttpServletRequest request) {
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
                UploadFileCount.MULTIPLE, minUploadCount, maxUploadCount, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/loc_recommend";
        }

        LocationDTO locDTO = locService.getLocationDTO(reqParam, tagList);
        Location loc = locService.dtoToEntity(locDTO);

        locationRepository.save(loc);

        for (int i = 1; i < filePath.size()-1; i++) {
            Image img = imgService.dtoToEntity(imgService.getImageDTO(locDTO, filePath.get(0), filePath.get(i)));

            imageRepository.save(img);
        }

        return "redirect:/service/loc_recommend";
    }
}
