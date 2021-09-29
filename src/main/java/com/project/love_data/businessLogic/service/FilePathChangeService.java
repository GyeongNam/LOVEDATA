package com.project.love_data.businessLogic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Log4j2
public class FilePathChangeService {
    public boolean execute(String uuid, UploadPathType pathType, FileExtension fileExtension) {
        String pathStr;
        Path srcPath;
        Path destPath;
        File srcFile;
        File destFile;
        String extension;

        switch (fileExtension) {
            case JPG:
                extension = ".jpg";
                break;
            case JPEG:
                extension = ".jpeg";
                break;
            case PNG:
                extension = ".png";
                break;
            case GIF:
                extension = ".gif";
                break;
            case WEBP:
                extension = ".webp";
                break;
            default:
                extension = null;
                log.warn("Wrong File Extension Type!");
                return false;
        }

        if ("Windows_NT".equals(System.getenv().get("OS"))) {
            pathStr = System.getProperty("user.dir");
            switch (pathType) {
                case LOC:
                    srcPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "location" + File.separator + uuid + extension);
                    break;
                case COR:
                    srcPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "course" + File.separator + uuid + extension);
                    break;
                case REV:
                    srcPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "review" + File.separator + uuid + extension);
                    break;
                case QNA:
                    srcPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "qna" + File.separator + uuid + extension);
                    break;
                case USER_PIC:
                    srcPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "user_pic" + File.separator + uuid + extension);
                    break;
                default:
                    return false;
            }

            destPath = Paths.get(pathStr + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "static" + File.separator +
                    "image" + File.separator +
                    "upload" + File.separator + uuid + extension);
        } else {
            // 윈도우 이외의 OS에서 돌아갈 경우 /home/tomcat/LoveData-Storage에 위치로 지정
            pathStr = "/home/tomcat/LoveData-Storage";
            switch (pathType) {
                case LOC:
                    srcPath = Paths.get(pathStr + File.separator + "location" + File.separator + uuid + extension);
                    break;
                case COR:
                    srcPath = Paths.get(pathStr + File.separator + "course" + File.separator + uuid + extension);
                    break;
                case REV:
                    srcPath = Paths.get(pathStr + File.separator + "review" + File.separator + uuid + extension);
                    break;
                case QNA:
                    srcPath = Paths.get(pathStr + File.separator + "qna" + File.separator + uuid + extension);
                    break;
                case USER_PIC:
                    srcPath = Paths.get(pathStr + File.separator + "user_pic" + File.separator + uuid + extension);
                    break;
                default:
                    return false;
            }

            destPath = Paths.get(pathStr + File.separator + "user_pic" + File.separator + uuid + extension);
        }

        if (!Files.exists(srcPath)) {
            log.warn("There is no File");
            return false;
        } else {
            try {
//                Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
                srcFile = new File(srcPath.toString());
                destFile = new File(destPath.toString());
                FileUtils.moveFile(srcFile, destFile);
                return true;
            } catch (IOException e) {
                log.warn(e.getStackTrace());
                return false;
            }
        }
    }

    public String getFileExtension(String path) {
        if (path == null | path.equals("")) {
            return null;
        }

        String[] ary = path.split(".");

        return ary[ary.length-1];
    }
}
