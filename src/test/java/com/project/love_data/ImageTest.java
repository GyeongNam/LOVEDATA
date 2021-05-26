package com.project.love_data;

import com.project.love_data.model.resource.Image;
import com.project.love_data.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImageTest {
    @Autowired
    ImageRepository imageRepository;

    @Test
    public void ImageInsertTest() {
        for (int i = 0; i < 10; i++) {
            Image img = Image.builder()
                    .img_url("path/" + i)
                    .user_no(Long.valueOf(i))
                    .loc_no(Long.valueOf(i))
                    .img_uuid("uuid_" + i)
                    .build();

            imageRepository.save(img);
        }
    }
}
