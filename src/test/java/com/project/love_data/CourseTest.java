package com.project.love_data;

import com.project.love_data.businessLogic.service.CorLocMapperService;
import com.project.love_data.businessLogic.service.CourseImageService;
import com.project.love_data.businessLogic.service.CourseService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.model.*;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.service.CorLocMapper;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.repository.CorLocMapperRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
public class CourseTest {
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    CourseImageService corImageService;
    @Autowired
    CorLocMapperRepository corLocMapperRepository;
    @Autowired
    CorLocMapperService corLocMapperService;

    @Test
    @Transactional
    @Rollback(false)
    public void InsertInitTest() {
        List<Location> loc = locService.locationNameSearch("중부대학교 충청캠퍼스");
//        List<Location> loc2 = locService.locationNameSearch("서울숲");
        List<Location> loc3 = locService.locationNameSearch("중부대학교 고양캠퍼스");

        List<Location> locationList = new ArrayList<>();

        if (loc.isEmpty() && loc3.isEmpty()) {
            System.out.println("등록된 장소가 없습니다. LocationTest의 InsertInitLocation()을 먼저 실행해주세요");
            return;
        }

        locationList.add(loc.get(0));

//        if (!loc2.isEmpty()) {
//            locationList.add(loc2.get(0));
//        }

        locationList.add(loc3.get(0));

        Set<String> tagSet = new HashSet<>();

        tagSet.add(LocationTag.학교.name());
        tagSet.add(LocationTag.야외.name());
        tagSet.add(LocationTag.산.name());

        Course entity = Course.builder()
                .cost("1.5만원")
                .transportation("대중교통")
                .cor_name("학교가기")
                .info("중부대")
                .user_no(1L)
                .location_count(locationList.size())
                .est_type("time")
                .est_value("1시간 30분")
                .tagSet(tagSet)
                .thumbnail("testCode")
                .build();

        entity = corService.update(entity);

        for (int i = 1; i <= 3; i++) {
            CourseImage courseImage = CourseImage.builder()
                    .idx((long) (i-1))
                    .cor_no(entity.getCor_no())
                    .img_url("/image/init/Jungbu-Logo-0" + i + ".jpg")
                    .img_uuid("Jungbu-Logo-0" + i + ".jpg")
                    .user_no(0L)
                    .build();

            corImageService.update(courseImage);

            if (i == 1) {
                entity.setThumbnail(courseImage.getImg_url());
            }
        }

        List<Long> locNoList = new ArrayList<>();

        for (Location locItem :
                locationList) {
            locNoList.add(locItem.getLoc_no());
        }

        List<CorLocMapper> corLocList = corLocMapperService.register(entity.getCor_no(), locNoList);

        if (entity == null) {
            System.out.println("코스 저장 및 검색 실패!");
        } else {
            System.out.println("코스 저장 및 검색 성공!");
            System.out.println(entity);
        }

        for (int i = 0; i < corLocList.size(); i++) {
            System.out.println((i+1) + "번째 장소\t:\t" + corLocList.get(i));
        }
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    public void InsertTest() {
//        Location loc = locService.selectLoc(1L);
//        Location loc2 = locService.selectLoc(2L);
//        Location loc3 = locService.selectLoc(3L);
//
//        List<Location> locationList = new ArrayList<>();
//
//        locationList.add(loc);
//        locationList.add(loc2);
//        locationList.add(loc3);
//
//        Set<String> tagSet = new HashSet<>();
//
//        tagSet.add(LocationTag.학교.name());
//        tagSet.add(LocationTag.야외.name());
//        tagSet.add(LocationTag.키즈.name());
//
//        Course course = Course.builder()
//                .cost("10만")
//                .transportation("대중교통")
//                .cor_name("학교가기")
//                .info("중부대")
//                .user_no(0L)
//                .location_count(locationList.size())
//                .est_type("시간")
//                .est_value("1시간 30분")
//                .thumbnail(null)
//                .tagSet(tagSet)
//                .thumbnail("testCode")
//                .build();
//
//        corService.update(course);
//
//        Course item = corService.selectCor(course.getCor_uuid());
//        List<Long> locNoList = new ArrayList<>();
//
//        for (Location locItem :
//                locationList) {
//            locNoList.add(locItem.getLoc_no());
//        }
//
//        List<CorLocMapper> corLocList = corLocMapperService.register(item.getCor_no(), locNoList);
//
//        if (item == null) {
//            System.out.println("코스 저장 및 검색 실패!");
//        } else {
//            System.out.println("코스 저장 및 검색 성공!");
//            System.out.println(item);
//        }
//
//        for (int i = 0; i < corLocList.size(); i++) {
//            System.out.println(i + "번째 장소\t:\t" + corLocList.get(i));
//        }
//    }
}
