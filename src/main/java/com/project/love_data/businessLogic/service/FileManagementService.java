package com.project.love_data.businessLogic.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@Log4j2
public class FileManagementService {
    public List<File> getAllFilesList(PathType pathType) {
        List<Path> filesPathList = new ArrayList<>();
        String pathStr = "";
        List<File> filesList = new ArrayList<>();

        if ("Windows_NT".equals(System.getenv().get("OS"))) {
            // 윈도우 OS에서 돌아갈 경우(디버깅 및 테스트) 현재 프로젝트설치 위치로 지정
            pathStr = System.getProperty("user.dir");
        } else {
            // pathStr은 아래와 같이 설정
            pathStr = "/opt/apache-tomcat-9.0.46/webapps/love_data/WEB-INF/classes/static/image";
            // 윈도우 이외의 OS에서 돌아갈 경우 /home/tomcat/LoveData-Storage에 위치로 지정
        }

        Path path = getFilePath(pathStr, pathType);
        filesPathList = getDirectoryFilesList(path);

        if (filesPathList == null) {
            log.warn("No Files Under (" + path + ")");
            return null;
        }

        for (Path filePath : filesPathList) {
            filesList.add(filePath.toFile());
        }

        if (filesList.isEmpty()) {
            return null;
        }

        return filesList;
    }

    private List<Path> getDirectoryFilesList(Path path) {
        List<Path> result = new ArrayList<>();
        try {
            Stream<Path> files = Files.list(path);
            Iterator<Path> iter = files.iterator();
            while(iter.hasNext()) {
                result.add(iter.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    private Path getFilePath(String pathStr, PathType pathType) {
        Path path;

        if ("Windows_NT".equals(System.getenv().get("OS"))) {
            switch (pathType) {
                case LOC:
                    path = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "location");
                    break;
                case COR:
                    path = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "course");
                    break;
                case REV:
                    path = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "review");
                    break;
                case QNA:
                    path = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "qna");
                    break;
                case USER_PIC:
                    path = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "user_pic");
                    break;
                default :
                    path = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "upload");
                    break;
            }
        } else {
            switch (pathType) {
                case LOC:
                    path = Paths.get(pathStr + File.separator +
                            "location");
                    break;
                case COR:
                    path = Paths.get(pathStr + File.separator +
                            "course");
                    break;
                case REV:
                    path = Paths.get(pathStr + File.separator +
                            "review");
                    break;
                case QNA:
                    path = Paths.get(pathStr + File.separator +
                            "qna");
                    break;
                case USER_PIC:
                    path = Paths.get(pathStr + File.separator +
                            "user_pic");
                    break;
                default :
                    path = Paths.get(pathStr + File.separator +
                            "upload");
                    break;
            }
        }

        return path;
    }
}
