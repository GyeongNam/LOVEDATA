package com.project.love_data.businessLogic.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Log4j2
public class FileUploadService {
    public void execute(List<MultipartFile> fileList,
                        UploadFileType fileType,
                        HttpServletRequest request) {
        execute(fileList, fileType, UploadFileCount.SINGLE, 0, request);
    }

    public void execute(List<MultipartFile> fileList,
                        UploadFileType fileType,
                        UploadFileCount fileCount,
                        int maxFileUploadCount,
                        HttpServletRequest request) {
        // 업로드 폴더의 상위폴더 (예시로 현재 프로젝트 폴더)
        String pathStr = System.getProperty("user.dir");

        switch (fileCount) {
            case SINGLE:
                singleFileUpload(fileList.get(0), pathStr, fileType, request);
                break;
            case MULTIPLE:
                multiFileUpload(fileList, pathStr,fileType, maxFileUploadCount,  request);
                break;
            default:
                log.warn("Not Defined UploadFileType Enum");
                return;
        }
    }

    private boolean singleFileUpload(MultipartFile file,
                                     String pathStr,
                                     UploadFileType fileType,
                                     HttpServletRequest request) {
        String filePath = getUploadPath(pathStr);
        String fileName = null;
        if (filePath == null) {
            log.warn("파일 업로드 경로에 문제 발생");
            return false;
        }

        if (!checkFileType(fileType, file)){
            log.warn("잘못된 형식의 파일 입니다.");
            return false;
        }

        fileName = getFileName(filePath, file);

        if (fileName == null) {
            log.warn("파일 이름이 지정되지 않았습니다.");
            return false;
        }

        if (!saveFile(fileName, file)){
            log.warn("파일을 저장하는 중에 오류가 발생하였습니다.");
            return false;
        }

        log.info("파일 저장 위치 : " + filePath);
        log.info("파일 이름 : " + fileName);
        return true;
    }

    private boolean multiFileUpload(List<MultipartFile> fileList,
                                    String pathStr,
                                    UploadFileType fileType,
                                    int maxFileUploadCount,
                                    HttpServletRequest request){
        String filePath = getUploadPath(pathStr);
        String fileName = null;
        int maxFileCount = fileList.size();
        int count = 0;
        if (filePath == null) {
            log.warn("파일 업로드 경로에 문제 발생");
            return false;
        }

        for (MultipartFile file : fileList) {
            ++count;
            if (count >= 8) {
                log.info("파일 최대 업로드 갯수는 " + maxFileCount + "개 입니다. (현재 업로드된 파일 갯수 " + count + ")");
                log.info("이후의 파일은 저장되지 않습니다.");
                count--;
                break;
            }

            if (!checkFileType(fileType, file)){
                log.warn("잘못된 형식의 파일 입니다.");
                continue;
            }

            fileName = getFileName(filePath, file);

            if (fileName == null) {
                log.warn("파일 이름이 지정되지 않았습니다.");
                continue;
            }

            if (!saveFile(fileName, file)){
                log.warn("파일을 저장하는 중에 오류가 발생하였습니다.");
                continue;
            }

            log.info("파일 저장 위치 : " + filePath);
            log.info("파일 이름 : " + fileName);
        }
        log.info("업로드 된 파일 갯수 : " + maxFileCount);
        log.info("저장된 파일 갯수 : " + count);

        return true;
    }

    private boolean checkFileType(UploadFileType fileType, MultipartFile file) {
        switch (fileType){
            case IMAGE:
                if (file.getContentType().startsWith("image") == false) {
                    log.warn("This file is not image types : " + file.getOriginalFilename());
                }
                return true;
            default:
                log.info("지원되지 않는 파일 형식입니다.");
                return false;
        }
    }

    // 업로드된 파일의 저장 위치 설정
    private String getUploadPath(String pathStr) {
        // 현재 프로젝트 폴더 하위 폴더인 images 폴더를 업로드 폴더로 지정
        Path path = Paths.get(pathStr + File.separator + "images");
        if (!Files.exists(path)) {
            try {
                log.info("Creating File path");
                log.info("-\t" + path.toAbsolutePath());
                Files.createDirectory(path);
            } catch (IOException e) {
                for (StackTraceElement item : e.getStackTrace()) {
                    log.warn(item);
                }
                return null;
            }
        }
        return path.toString();
    }

    private String getFileName(String path, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
        String uuid = UUID.randomUUID().toString();
        String saveName = path + File.separator + uuid + "_" + fileName;

        return saveName;
    }

    private boolean saveFile(String path, MultipartFile file) {
        Path savePath = Paths.get(path);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            for (StackTraceElement item: e.getStackTrace()) {
                log.warn(item);
            }
            return false;
        }

        return true;
    }
}
