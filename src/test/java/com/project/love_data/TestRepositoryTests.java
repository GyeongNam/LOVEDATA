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
//            com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().name("Sample..." + i).country("kr").build();
            com.project.love_data.model.Test test = new com.project.love_data.model.Test(i, "Sample.."+i, "kr");
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
    public void testSelectMultiSeq(){
        // �뜲�씠�꽣踰좎씠�뒪�뿉 議댁옱�븯�뒗 seq
        Integer seq = 1;

//        Optional<com.project.love_data.model.Test> result = testRepository.findById(seq);
//
//        System.out.println("====================================");
//
//        if (result.isPresent()){
//            com.project.love_data.model.Test test = result.get();
//            System.out.println(test);
//        }

        // selectID
        // 오직 하나의 값만 찾아서 리턴하는 SQL
        // SELECT * FROM test WHERE test.seq = seq;
        Optional<com.project.love_data.model.Test> result = Optional.ofNullable(testRepository.selectSEQ(seq));

        if (result.isPresent()){
            com.project.love_data.model.Test test = result.get();
            System.out.println(test);
            System.out.println("#### Complete ####");
        }
    }

    @Transactional
    @Test
    public void TestSelect2() {
//        �뜲�씠�꽣踰좎씠�뒪�뿉 議댁옱�븯�뒗 seq
        Integer seq = 5;

        com.project.love_data.model.Test test = testRepository.getOne(seq);

        System.out.println("========================================");

        System.out.println(test);
    }

//    @Test
//    public void testUpdate(){
//        int seq = 9;
//        String name = "Sam";
//        String country = "CN";
////        com.project.love_data.model.Test test = com.project.love_data.model.Test.builder().seq(9).name("Sam").country("CN").build();
////        com.project.love_data.model.Test test = new com.project.love_data.model.Test(9, "Sam", "cn");
////        System.out.println(testRepository.save(test));
//        com.project.love_data.model.Test test = testRepository.updateSEQ(seq, name, country);
//        System.out.println(test);
//    }

    @Test
    public void testDelete(){
        Integer seq = 6;

        testRepository.deleteById(seq);
    }
}
