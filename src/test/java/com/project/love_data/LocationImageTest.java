package com.project.love_data;

import com.project.love_data.businessLogic.service.LocationImageService;
import com.project.love_data.dto.LocationImageDTO;
import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.repository.LocationImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class LocationImageTest {
    @Autowired
    LocationImageRepository locationImageRepository;

    @Autowired
    LocationImageService imgService;

    @Test
    public void ImageDelete() {
        LocationImage img1 = imgService.getImage(4L);

        imgService.permaDelete(img1.getImg_uuid());

        LocationImage tempImg1 = imgService.getImage(4L);

        if (tempImg1 != null) {
            System.out.println("지워지지 않음");
        } else{
            System.out.println("지워짐");
        }
    }

    @Test
    public void ImageGetTest() {
        LocationImage img1 = imgService.getImage(1L);
        LocationImage img2 = imgService.getLiveImage("44ae4245-25da-470e-9260-0ff59e29eb95.jpg");

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
            LocationImageDTO dto = LocationImageDTO.builder()
                    .img_url("path/" + i)
                    .user_no(Long.valueOf(i))
//                    .loc_uuid("UUID_" + i)
                    .img_uuid("uuid_" + i)
                    .build();

            locationImageRepository.save(imgService.dtoToEntity(dto));
        }

        LocationImageDTO img = LocationImageDTO.builder()
                .img_url("test")
                .user_no(0L)
//                .loc_uuid("Random UUID")
                .img_uuid("test.png")
                .build();

        locationImageRepository.save(imgService.dtoToEntity(img));
    }

    @Test
    public void ImageReadByUserNo() {
        List<LocationImage> list = locationImageRepository.findAllLiveImageByUser_no(0L);

        for (LocationImage locationImage : list) {
            System.out.println("image = " + locationImage);
        }
    }

    @Test
    public void ImageReadByUUID() {
        Optional<LocationImage> image = locationImageRepository.findLiveImageByImg_uuid("test.png");

        image.ifPresent(value -> System.out.println("image = " + value.toString()));
    }
}
