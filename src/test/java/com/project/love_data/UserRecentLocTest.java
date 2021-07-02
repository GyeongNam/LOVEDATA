package com.project.love_data;

import com.project.love_data.businessLogic.service.UserRecentLocService;
import com.project.love_data.model.service.UserRecentLoc;
import com.project.love_data.repository.UserRecentLocRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class UserRecentLocTest {
    @Autowired
    UserRecentLocService recentLocService;

    @Test
    public void InsertTest() {
        UserRecentLoc item = recentLocService.register(1L, 1L);

        if (item == null) {
            System.out.println("UserRecentLoc 생성중 문제 발생!");
        } else {
            System.out.println("UserRecentLoc 생성 성공!");
        }
    }

    @Test
    public void deleteTest() {
        switch (recentLocService.remove(1L, 1L)) {
            case ALREADY_DELETED:
                System.out.println("Already Deleted! or Not Existed!");
                break;
            case SUCCESSFUL:
                System.out.println("Delete Complete!");
                break;
            case FAILED:
                System.out.println("Delete Failed");
                break;
        }
    }

    @Test
    public void maxLimitInsertTest() {
        for (int i = 0; i < 10; i++) {
            UserRecentLoc item = recentLocService.register((long) i, 1L);

            System.out.println(i + "\t" + item);
        }

        UserRecentLoc item = recentLocService.register(11L, 1L);

        System.out.println("Last \t " + item);

        List<UserRecentLoc> lists = recentLocService.selectByAllUserNo(1L);

        for (UserRecentLoc list : lists) {
            System.out.println(list);
        }
    }
}
