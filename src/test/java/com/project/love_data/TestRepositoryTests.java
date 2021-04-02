package com.project.love_data;

import com.project.love_data.wrapper.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
public class TestRepositoryTests {
    @Autowired
    TestRepository testRepository;

    @Test
    public void testClass(){
        System.out.println(testRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().name("Sample..." + i).country("kr").build();
            testRepository.save(test);
        });
//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            if (i % 3 == 0) {
//                com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().name("Sample..."+i).country("kr").build();
//                testRepository.save(test);
//            } else if (i % 2 == 0) {
//                com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().name("Sample..."+i).country("us").build();
//                testRepository.save(test);
//            } else if (i % 5 == 0) {
//                com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().name("Sample..."+i).country("jp").build();
//                testRepository.save(test);
//            } else {
//                com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().name("Sample..."+i).country("uk").build();
//                testRepository.save(test);
//            }
//        });
    }

    @Test
    public void testSelect(){
        // 데이터베이스에 존재하는 seq
        Integer seq = 100;

        Optional<com.project.love_data.model.Test> result = testRepository.findById(seq);

        System.out.println("====================================");

        if (result.isPresent()){
            com.project.love_data.model.Test test = result.get();
            System.out.println(test);
        }
    }

    @Transactional
    @Test
    public void TestSelect2() {
//        데이터베이스에 존재하는 seq
        Integer seq = 5;

        com.project.love_data.model.Test test = testRepository.getOne(seq);

        System.out.println("========================================");

        System.out.println(test);
    }

    @Test
    public void testUpdate(){
        com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().seq(9).name("Sam").country("CN").build();

        System.out.println(testRepository.save(test));
    }

    @Test
    public void testDelete(){
        Integer seq = 6;

        testRepository.deleteById(seq);
    }
}
