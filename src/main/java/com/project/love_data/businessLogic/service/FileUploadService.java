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
        execute(fileList, fileType, UploadFileCount.SINGLE, 1, 1, request);
    }

    public List<String> execute(List<MultipartFile> fileList,
                                UploadFileType fileType,
                                UploadFileCount fileCount,
                                int minFileUploadCount,
                                int maxFileUploadCount,
                                HttpServletRequest request) {
        // 업로드 폴더의 상위폴더 (예시로 현재 프로젝트 폴더)
        String pathStr;
        if ("Windows_NT".equals(System.getenv().get("OS"))) {
            pathStr =System.getProperty("user.dir");
        } else {
            pathStr = "/home/tomcat/LoveData_Storage";
        }
        List<String> result = null;

        switch (fileCount) {
            case SINGLE:
                result = singleFileUpload(fileList.get(0), pathStr, fileType, request);
                break;
            case MULTIPLE:
                result = multiFileUpload(fileList, pathStr, fileType, minFileUploadCount, maxFileUploadCount, request);
                break;
            default:
                log.warn("Not Defined UploadFileType Enum");
                return null;
        }

        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }

    private List<String> singleFileUpload(MultipartFile file,
                                          String pathStr,
                                          UploadFileType fileType,
                                          HttpServletRequest request) {
        String filePath = getUploadPath(pathStr);
        // Todo 서버와 로컬 구분하는 방법
        // 1) 서버 이름으로 구분하기
        // if ("localhost".equals(request.getServerName()))
        // 이 방법은 현재 서버와 로컬이 같은 이름인 내 경우엔 적용 X
        // 2) OS 이름으로 구분하기
        // System.getenv()로 현재 OS 이름 가져 올 수 있음
        // System.getenv().get("OS")
        // if (window) == Windows_NT
        // else == null
        /*
        *  LANGUAGE : en_US.UTF-8
        * PATH : /usr/local/bin:/usr/bin:/bin:/usr/games
        * SHELL : /usr/bin/zsh
        * JETBRAINS_REMOTE_RUN : 1
        * SSH_TTY : /dev/pts/3
        * SSH_CLIENT : 192.168.0.1 6285 22
        * OLDPWD : /home/tomcat
        * TERM : vt100
        * USER : tomcat
        * LANG : en_US.UTF-8
        * DBUS_SESSION_BUS_ADDRESS : unix:path=/run/user/1001/bus
        * XDG_SESSION_TYPE : tty
        * XDG_SESSION_ID : 23
        * SSH_CONNECTION : 192.168.0.1 6285 192.168.0.10 22
        * MAIL : /var/mail/tomcat
        * LC_ALL : en_US.UTF-8
        * LOGNAME : tomcat
        * PWD : /home/tomcat/spring/PtSb5FKRKt
        * XDG_RUNTIME_DIR : /run/user/1001
        * SHLVL : 0
        * HOME : /home/tomcat
        * _ : /usr/bin/env
        * XDG_SESSION_CLASS : user
         */

        String URIPath;
        // 리눅스 서버에서 돌아갈 경우
        log.info("Current OS : " + System.getenv().get("OS"));
        if ("Windows_NT".equals(System.getenv().get("OS"))) {
            URIPath = "/image/upload";
        } else {
            URIPath = "/home/tomcat/LoveData_Storage";
        }
        String fileName = null;
        List<String> result = new ArrayList<>();
        if (filePath == null) {
            log.warn("파일 업로드 경로에 문제 발생");
            return result;
        }

        if (!checkFileType(fileType, file)) {
            log.warn("잘못된 형식의 파일 입니다.");
            return result;
        }

        fileName = getFileName(file, FileNamingType.UUID);

        if (fileName == null) {
            log.warn("파일 이름이 지정되지 않았습니다.");
            return result;
        }

        if (!saveFile(filePath, fileName, file)) {
            log.warn("파일을 저장하는 중에 오류가 발생하였습니다.");
            return result;
        }

        log.info("파일 저장 위치 : " + filePath);
        log.info("URI 파일 위치 : " + URIPath);
        log.info("기존 파일 이름 : " + file.getOriginalFilename());
        log.info("저장된 파일 이름 : " + fileName);
        result.add(URIPath);
        result.add(fileName);

        return result;
    }

    private List<String> multiFileUpload(List<MultipartFile> fileList,
                                         String pathStr,
                                         UploadFileType fileType,
                                         int minFileUploadCount,
                                         int maxFileUploadCount,
                                         HttpServletRequest request) {
        String filePath = getUploadPath(pathStr);
        String URIPath;
        // 리눅스 서버에서 돌아갈 경우
        log.info("Current OS : " + System.getenv().get("OS"));
        if ("Windows_NT".equals(System.getenv().get("OS"))) {
            URIPath = "/image/upload";
        } else {
            URIPath = "/home/tomcat/LoveData_Storage";
        }
        String fileName = null;
        List<String> result = new ArrayList<>();
        int maxFileCount = fileList.size();
        int count = 0;
        if (filePath == null) {
            log.warn("파일 업로드 경로에 문제 발생");
            return result;
        }

        if (minFileUploadCount > fileList.size()) {
            log.warn("최소 업로드 파일 수는 " + minFileUploadCount + "개 입니다.");
            return result;
        }

        log.info("파일 저장 위치 : " + filePath);
        log.info("URI 파일 위치 : " + URIPath);
        result.add(URIPath);
        for (MultipartFile file : fileList) {
            ++count;
            if (count > maxFileUploadCount) {
                log.info("파일 최대 업로드 허용 갯수는 " + maxFileUploadCount + "개 입니다. (현재 메모리에 업로드된 파일 갯수 " + maxFileCount + ")");
                log.info((--count) + "번 파일까지만 저장됩니다.");
                break;
            }

            if (!checkFileType(fileType, file)) {
                log.warn("잘못된 형식의 파일 입니다.");
                continue;
            }

            fileName = getFileName(file, FileNamingType.UUID);

            if (fileName == null) {
                log.warn("파일 이름이 지정되지 않았습니다.");
                continue;
            }

            if (!saveFile(filePath, fileName, file)) {
                log.warn("파일을 저장하는 중에 오류가 발생하였습니다.");
                continue;
            }

            log.info("기존 파일 이름 : " + file.getOriginalFilename());
            log.info("저장된 파일 이름 : " + fileName);

            result.add(fileName);
        }
        log.info("현재 메모리에 업로드 된 파일 갯수 : " + maxFileCount);
        log.info("저장된 파일 갯수 : " + count);

        return result;
    }

    private boolean checkFileType(UploadFileType fileType, MultipartFile file) {
        switch (fileType) {
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
        Path path;

        if ("Windows_NT".equals(System.getenv().get("OS"))){
            path = Paths.get(pathStr + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "static" + File.separator +
                    "image" + File.separator +
                    "upload");
        } else {
            path = Paths.get(pathStr + File.separator);
        }

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

    private String getFileName(MultipartFile file, FileNamingType fileNamingType) {
        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
        String fileFormat = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveName = null;

        switch (fileNamingType) {
            case ORIGINAL:
                saveName = fileName;
                break;
            case UUID:
                saveName = uuid + fileFormat;
                break;
            case UUD_ORIGINAL:
                saveName = uuid + "_" + fileName;
                break;
            default:
                return null;
        }

        return saveName;
    }

    private boolean saveFile(String filePath, String fileName, MultipartFile file) {
        Path savePath = Paths.get(filePath + File.separator + fileName);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            for (StackTraceElement item : e.getStackTrace()) {
                log.warn(item);
            }
            return false;
        }

        return true;
    }
}
