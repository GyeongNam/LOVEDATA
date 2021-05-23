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

    @RequestMapping("/service/loc_registration")
    public String loc_Reg() {
        return "/service/loc_registration";
    }

    @RequestMapping("/popup/jusoPopup")
    public String popup_juso() {
        return "/popup/jusoPopup";
    }

    @PostMapping("/service/loc_registration/regData")
    public String locRegistrationData(@RequestParam("files") List<MultipartFile> fileList
            , HttpServletRequest request) {
//        // 현재 프로젝트의 경로를 불러옴
//        // https://j-dev.tistory.com/entry/Java-%ED%98%84%EC%9E%AC-ROOT-%EA%B2%BD%EB%A1%9C-%EA%B0%80%EC%A7%80%EA%B3%A0-%EC%98%A4%EA%B8%B0
//        String uploadPath = System.getProperty("user.dir");
//        int count = 0;
//
//        log.info("Uploaded File List Count : " + fileList.size());
//        log.info("uploadPath : " + uploadPath);
//
//        Path tempPath = Paths.get(uploadPath + File.separator + "images");
//        if(!Files.exists(tempPath)) {
//            try {
//                log.info("Creating File path");
//                log.info("-\t" + tempPath.toAbsolutePath());
//                Files.createDirectory(tempPath);
//            } catch (IOException e) {
//                for (StackTraceElement item : e.getStackTrace()) {
//                    log.warn(item);
//                }
//            }
//        }
//
//        for (MultipartFile file : fileList) {
//            if (file.getContentType().startsWith("image") == false) {
//                log.warn("This file is not image types : " + file.getOriginalFilename());
//            }
//
//            count ++;
//            if (count >= 8) {
//                log.info("Image Upload Count Reached Max (Maximum Count is 7)");
//                log.info("Current Uploaded Image Count is : " + count);
//                log.info("Last Uploaded Image won't save");
//                count--;
//                break;
//            }
//
//            String originalName = file.getOriginalFilename();
//            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//            String uuid = UUID.randomUUID().toString();
//
//            String saveName = uploadPath + File.separator + "images" + File.separator + uuid + "_" + fileName;
//            Path savePath = Paths.get(saveName);
//
//            log.info("saveName : " + saveName);
//            log.info("savePath : " + savePath);
//
//            try {
//                file.transferTo(savePath);
//            } catch (IOException e) {
//                for (StackTraceElement item: e.getStackTrace()) {
//                    log.warn(item);
//                }
//            }
//        }
//
//        log.info("Uploaded Image Count : " + count);

        fileUploadService.execute(fileList, UploadFileType.IMAGE,
                UploadFileCount.MULTIPLE, maxUploadCount, request);

        return "redirect:/service/loc_recommend";
    }
}
