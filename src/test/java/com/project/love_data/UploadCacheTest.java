package com.project.love_data;

import com.project.love_data.businessLogic.service.FileManagementService;
import com.project.love_data.businessLogic.service.PathType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UploadCacheTest {
    @Autowired
    FileManagementService fileManagementService;

    @Test
    public void test() {
        fileManagementService.getAllFilesList(PathType.UPLOAD);
    }
}
