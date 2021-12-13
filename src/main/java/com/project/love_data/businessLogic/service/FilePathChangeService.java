package com.project.love_data.businessLogic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Log4j2
public class FilePathChangeService {
    public boolean execute(String fileName, FileAction fileAction, PathType pathType, FileExtension fileExtension) {
        String pathStr;
        Path resPath;
        Path dumpPath;
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
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "location" + File.separator + fileName);
                    break;
                case COR:
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "course" + File.separator + fileName);
                    break;
                case REV:
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "review" + File.separator + fileName);
                    break;
                case QNA:
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "qna" + File.separator + fileName);
                    break;
                case USER_PIC:
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "user_pic" + File.separator + fileName);
                    break;
                case BIZ_REG:
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "biz_reg" + File.separator + fileName);
                    break;
                case UPLOAD:
                    resPath = Paths.get(pathStr + File.separator +
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "image" + File.separator +
                            "upload" + File.separator + fileName);
                    break;
                default:
                    return false;
            }

            dumpPath = Paths.get(pathStr + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "static" + File.separator +
                    "image" + File.separator +
                    "upload" + File.separator + pathType + "^" + fileName);
        } else {
            // 윈도우 이외의 OS에서 돌아갈 경우 /home/tomcat/LoveData-Storage에 위치로 지정
            pathStr = "/opt/apache-tomcat-9.0.46/webapps/love_data/WEB-INF/classes/static/image";
            switch (pathType) {
                case LOC:
                    resPath = Paths.get(pathStr + File.separator + "location" + File.separator + fileName);
                    break;
                case COR:
                    resPath = Paths.get(pathStr + File.separator + "course" + File.separator + fileName);
                    break;
                case REV:
                    resPath = Paths.get(pathStr + File.separator + "review" + File.separator + fileName);
                    break;
                case QNA:
                    resPath = Paths.get(pathStr + File.separator + "qna" + File.separator + fileName);
                    break;
                case USER_PIC:
                    resPath = Paths.get(pathStr + File.separator + "user_pic" + File.separator + fileName);
                    break;
                case UPLOAD:
                    resPath = Paths.get(pathStr + File.separator + "upload" + File.separator + fileName);
                    break;
                case BIZ_REG:
                    resPath = Paths.get(pathStr + File.separator + "biz_reg" + File.separator + fileName);
                    break;
                default:
                    return false;
            }

            dumpPath = Paths.get(pathStr + File.separator + "upload" + File.separator + pathType + "^" + fileName);
        }

//        if (!Files.exists(srcPath)) {
//            log.warn("There is no File");
//            return false;
//        } else {
//
//        }

        try {
            switch (fileAction) {
                case DELETE:
                    //                Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    srcFile = new File(resPath.toString());
//                    destFile = new File(new StringBuilder().append(pathType).append("/").append(dumpPath.toString()).toString());
                    destFile = new File(dumpPath.toString());
                    FileUtils.moveFile(srcFile, destFile);
                    return true;
                case ROLLBACK:
                default:
                    //                Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
//                    srcFile = new File(new StringBuilder().append(pathType).append("/").append(dumpPath.toString()).toString());
                    srcFile = new File(dumpPath.toString());
                    destFile = new File(resPath.toString());
                    FileUtils.moveFile(srcFile, destFile);
                    return true;
            }
        } catch (IOException e) {
            log.warn(e.getCause());
            log.warn(e.getMessage());
            return false;
        }
    }

    public String getFileExtension(String path) {
        if (path == null | path.equals("")) {
            return null;
        }

        String[] ary = path.split("\\.");

        return ary[ary.length-1];
    }
}
