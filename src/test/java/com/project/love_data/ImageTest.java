package com.project.love_data;

import com.project.love_data.businessLogic.service.ImageService;
import com.project.love_data.dto.ImageDTO;
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

    @Autowired
    ImageService imgService;

    @Test
    public void ImageGetTest() {
        Image img1 = imgService.getImage(1L);
        Image img2 = imgService.getImage("44ae4245-25da-470e-9260-0ff59e29eb95.jpg");

        if (img1 != null) {
            System.out.println(img1);
        } else {
            System.out.println("img1 is null");
        }

        if (img2 != null) {
            System.out.println(img2);
        } else {
            System.out.println("img2 is null");
        }
    }

    @Test
    public void ImageInsertTest() {
        for (int i = 0; i < 10; i++) {
            ImageDTO dto = ImageDTO.builder()
                    .img_url("path/" + i)
                    .user_no(Long.valueOf(i))
//                    .loc_uuid("UUID_" + i)
                    .img_uuid("uuid_" + i)
                    .build();

            imageRepository.save(imgService.dtoToEntity(dto));
        }

        ImageDTO img = ImageDTO.builder()
                .img_url("test")
                .user_no(0L)
//                .loc_uuid("Random UUID")
                .img_uuid("test.png")
                .build();

        imageRepository.save(imgService.dtoToEntity(img));
    }

    @Test
    public void ImageReadByUserNo() {
        List<Image> list = imageRepository.findAllByUser_no(0L);

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
