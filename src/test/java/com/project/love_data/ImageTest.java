package com.project.love_data;

import com.project.love_data.model.resource.Image;
import com.project.love_data.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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
                    .loc_uuid("UUID_" + i)
                    .img_uuid("uuid_" + i)
                    .build();

            imageRepository.save(img);
        }

        Image img = Image.builder()
                .img_url("test")
                .user_no(0L)
                .loc_uuid("Random UUID")
                .img_uuid("test.png")
                .build();

        imageRepository.save(img);
    }

    @Test
    public void ImageReadByUserNo() {
        List<Image> list = imageRepository.findAllByUser_no(0L);

        for (Image image : list) {
            System.out.println("image = " + image);
        }
    }

    @Test
    public void ImageReadByLocUUID() {
        List<Image> list = imageRepository.findAllByLoc_uuid("UUID_0");

        for (Image image : list) {
            System.out.println("image = " + image);
        }
    }

    @Test
    public void ImageReadByUUID() {
        Optional<Image> image = imageRepository.findImageByImg_uuid("test.png");

        image.ifPresent(value -> System.out.println("image = " + value.toString()));
    }
}
