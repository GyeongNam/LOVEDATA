package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.FileUploadService;
import com.project.love_data.businessLogic.service.UploadFileCount;
import com.project.love_data.businessLogic.service.UploadFileType;
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

    final static int maxUploadCount = 7;
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
        
        // @Todo 입력시 최소 1개의 태그는 추가하도록 하는 javascript 및 백엔드 서버 기능 추가
        if (tagList.isEmpty()) {
            log.warn("No Location Tag Found (Must add tag before submit location)");
            return "redirect:/service/loc_recommend";
        }

        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, maxUploadCount, request);

        if (filePath == null) {
            log.warn("파일이 제대로 저장되지 않았습니다.");
            return "redirect:/service/loc_recommend";
        }

//        for (String s : filePath) {
//            System.out.println("s = " + s);
//        }

        return "redirect:/service/loc_recommend";
    }
}
