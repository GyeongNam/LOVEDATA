package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.FileUploadService;
import com.project.love_data.businessLogic.service.UploadFileCount;
import com.project.love_data.businessLogic.service.UploadFileType;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.ImageRepository;
import com.project.love_data.repository.LocationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        log.info("Access Invalid(Shouldn\'t Access with GET Method)");
        return "redirect:/service/loc_recommend";
    }

    @PostMapping("/service/loc_registration/regData")
    public String locRegistrationData(@RequestParam("files") List<MultipartFile> fileList,
                                      HttpServletRequest request) {
        List<String> filePath = null;

        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String info = request.getParameter("info");
        String zipNo = request.getParameter("zipNo");
        String roadAddr = request.getParameter("roadAddrPart1");
        String addrDetail = request.getParameter("addrDetail");
        // 시 도 명칭 (ex 경기도, 서울 특별시 등)
        String siDoName = request.getParameter("siNm");
        // 시 군 구 명칭 (ex 고양시, 덕양구, 강화군 등)
        String siGunGuName = request.getParameter("sggNm");
        // 유저 넘버
        Long user_no = null;
        // Todo Debug 목적용 코드 나중에 삭제할 것
        if (request.getParameter("user_no") != null) {
            user_no = Long.valueOf(request.getParameter("user_no"));
        } else {
            user_no = Long.parseLong(request.getParameter("user_no_debug"));
        }

        // @Todo 입력시 최소 1개의 태그는 추가하도록 하는 javascript 및 백엔드 서버 기능 추가
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

        Location loc = Location.builder()
                .loc_name(name)
                .user_no(user_no)
                .roadAddr(roadAddr)
                .addrDetail(addrDetail)
                .siDo(siDoName)
                .siGunGu(siGunGuName)
                .info(info)
                .build();

        for (String item : tagList) {
            loc.addLocTag(item);
        }

        // Todo 이미지랑 장소 어떻게 연관지을 건지 생각해보기
        // 1) 다대일 관계 하지 말고 그냥 조인해버리기
//        for (int i = 1; i < filePath.size()-1; i++) {
//            loc.addLocImg(filePath.get(i));
//        }

        locationRepository.save(loc);
        Optional<Location> item = locationRepository.findLocByName(name);
        if (item.isPresent()) {
            loc = item.get();
        }

        for (int i = 1; i < filePath.size()-1; i++) {
            Image img = Image.builder()
                    .img_url(filePath.get(0) + File.separator + filePath.get(i))
                    .user_no(user_no)
                    .loc_no(loc.getLoc_no())
                    .img_uuid(filePath.get(i))
                    .build();

            imageRepository.save(img);
        }

//        for (String s : filePath) {
//            System.out.println("s = " + s);
//        }

        return "redirect:/service/loc_recommend";
    }
}
