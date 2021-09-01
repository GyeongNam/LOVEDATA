package com.project.love_data;

import com.project.love_data.businessLogic.service.CourseService;
import com.project.love_data.businessLogic.service.ReviewImageService;
import com.project.love_data.businessLogic.service.ReviewService;
import com.project.love_data.businessLogic.service.UserService;
import com.project.love_data.dto.ReviewDTO;
import com.project.love_data.dto.ReviewImageDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.Review;
import com.project.love_data.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class ReviewTest {
    @Autowired
    UserService userService;
    @Autowired
    CourseService corService;
    @Autowired
    ReviewService revService;
    @Autowired
    ReviewImageService revImgService;

    @Test
    public void insertTest() {
        List<Course> corList = corService.courseNameSearch("학교가기");
        User user = userService.select("tpye97@gmail.com");

        if (corList == null) {
            System.out.println("해당하는 코스가 없습니다.");
            return;
        }

        if (user == null) {
            System.out.println("해당하는 유저가 없습니다.");
            return;
        }

        Course corEntity = corList.get(0);
        String content = "리뷰 테스트!";
        List<Integer> rndInt = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            rndInt.add((rnd.nextInt(6)));
        }
        Map<String, Integer> scoreMap = revService.getScoreMap(rndInt.get(0), rndInt.get(1), rndInt.get(2),
                rndInt.get(3));

        Review entity = revService.createRevEntity(corEntity.getCor_no(), user.getUser_no(),
                content, scoreMap, 3.5f);

        if (entity == null) {
            System.out.println("리뷰 생성과정에 문제 발생!");
            return;
        }

        Review result = revService.update(entity);
        ReviewDTO resultDto = revService.entityToDto(result);

        if (result == null) {
            System.out.println("등록한 리뷰를 찾을 수 없습니다!");
            return;
        }

//        System.out.println(result);
        System.out.println(resultDto);

        corList = corService.courseNameSearch("학교가기");
        user = userService.select("tpye97@gmail.com");

        if (corList == null) {
            System.out.println("해당하는 코스가 없습니다.");
            return;
        }

        if (user == null) {
            System.out.println("해당하는 유저가 없습니다.");
            return;
        }

        corEntity = corList.get(0);
        content = "리뷰 이미지 테스트!";
        rndInt = new ArrayList<>();
        rnd = new Random();

        for (int i = 0; i < 4; i++) {
            rndInt.add((rnd.nextInt(6)));
        }
        scoreMap = revService.getScoreMap(rndInt.get(0), rndInt.get(1),
                rndInt.get(2), rndInt.get(3));

        entity = revService.createRevEntity(corEntity.getCor_no(), user.getUser_no(),
                content, scoreMap, 3.5f);

        if (entity == null) {
            System.out.println("리뷰 생성과정에 문제 발생!");
            return;
        }

        result = revService.update(entity);
        resultDto = revService.entityToDto(result);

        if (result == null) {
            System.out.println("등록한 리뷰를 찾을 수 없습니다!");
            return;
        }

        String fileRootPath = "/image/icon/review";
        List<String> fileURI = new ArrayList<>();
        fileURI.add("thinking.png");
        fileURI.add("391909180_THINKING_FACE_400px.gif");
        fileURI.add("391909180_THINKING_FACE_400.png");

        for (int i = 0; i < fileURI.size(); i++) {
            ReviewImage revImg = revImgService.getImageEntity(user.getUser_no(), fileRootPath,
                    fileURI.get(i), result.getCorNo(), result.getRevNo(), (long) i);

            revImgService.update(revImg);
        }

        List<ReviewImage> revImgList = revImgService.getAllLiveImageByCorNoAndRevNo(result.getCorNo(), result.getRevNo());
        List<ReviewImageDTO> revImgDTOList = new ArrayList<>();

        if (revImgList == null) {
            System.out.println("리뷰 이미지를 불러오는데 실패했습니다.");
            return;
        }

        for (ReviewImage image : revImgList) {
            revImgDTOList.add(revImgService.entityToDTO(image));
        }

//        System.out.println(result);
        System.out.println(resultDto);

        for (ReviewImageDTO dto : revImgDTOList) {
            System.out.println(dto);
        }
    }
}
