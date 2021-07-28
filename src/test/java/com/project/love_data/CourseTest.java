package com.project.love_data;

import com.project.love_data.businessLogic.service.CourseService;
import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.model.service.CorLocMapper;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.repository.CorLocMapperRepository;
import org.hibernate.Hibernate;
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
    CorLocMapperRepository corLocMapperRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void InsertTest() {
        Location loc = locService.selectLoc(1L);
        Location loc2 = locService.selectLoc(2L);
        Location loc3 = locService.selectLoc(3L);

        List<Location> locationList = new ArrayList<>();

        locationList.add(loc);
        locationList.add(loc2);
        locationList.add(loc3);

        Set<String> tagSet = new HashSet<>();

        tagSet.add(LocationTag.학교.name());
        tagSet.add(LocationTag.야외.name());
        tagSet.add(LocationTag.키즈.name());

        Course course = Course.builder()
                .cost("10만")
                .transportation("대중교통")
                .est_time("3시간")
                .cor_name("학교가기")
                .info("중부대")
                .imgSet(null)
                .user_no(0L)
                .thumbnail(null)
                .tagSet(tagSet)
                .thumbnail("testCode")
                .cor_uuid("id")
                .build();

        corService.update(course);

        Course item = corService.selectCor(course.getCor_uuid());
        Set<Long> locNoSet = new HashSet<>();

        for (Location locItem :
                locationList) {
            locNoSet.add(locItem.getLoc_no());
        }

        CorLocMapper corLocMapper = CorLocMapper.builder()
                .cor_no(item.getCor_no())
                .loc_no(locNoSet)
                .build();

        corLocMapperRepository.save(corLocMapper);

        Optional<CorLocMapper> clmItem = corLocMapperRepository.findByUUID(corLocMapper.getClm_uuid());

        if (item == null) {
            System.out.println("코스 저장 및 검색 실패!");
        } else {
            System.out.println("코스 저장 및 검색 성공!");
            System.out.println(item);
        }

        if (clmItem.isPresent()) {
            System.out.println("코스 장소 목록 저장 성공");
//            Hibernate.initialize(clmItem.get().getLoc_no());
            System.out.println(clmItem.get());
        } else {
            System.out.println("코스 장소 목록 저장 실패");
        }
    }


}
