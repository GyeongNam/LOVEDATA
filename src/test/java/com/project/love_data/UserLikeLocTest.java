package com.project.love_data;

import com.project.love_data.businessLogic.service.UserLikeLocService;
import com.project.love_data.model.service.UserLikeLoc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserLikeLocTest {
    @Autowired
    UserLikeLocService likeLocService;

    @Test
    public void test() {
        UserLikeLoc item = likeLocService.selectByLocNoAndUserNo(1L, 1L);

        System.out.println(item);
    }

    @Test
    public void delete() {
        UserLikeLoc item = likeLocService.selectByLocNoAndUserNo(1L, 1L);

        System.out.println(item);

        likeLocService.delete(item.getLoc_no(), item.getUser_no());
    }
}
