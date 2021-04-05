package com.project.love_data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.project.love_data.Repository.TestRepository;

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
            com.project.love_data.model.Test test = new com.project.love_data.model.Test("Sample.."+i, "kr");
            testRepository.save(test);
        });

//        com.project.love_data.model.Test test = new com.project.love_data.model.Test("Sam", "us");
//        com.project.love_data.model.Test test1 = new com.project.love_data.model.Test("Tom", "ph");
//        com.project.love_data.model.Test test2 = new com.project.love_data.model.Test("Dan", "cn");
//
//        testRepository.save(test);
//        testRepository.save(test1);
//        testRepository.save(test2);

        testRepository.insert("Sam", "us");
        testRepository.insert("Tom", "ph");
        testRepository.insert("Dan", "cn");
    }

    @Transactional
    @Test
    public void TestSelect2() {
        Integer seq = 5;
//        com.project.love_data.model.Test test = testRepository.getOne(seq);
//        System.out.println("========================================");
//        System.out.println(test);

        System.out.println("SELECT * FROM TEST WHERE seq = 5");
        com.project.love_data.model.Test temp = testRepository.selectSEQ(seq);
        System.out.println("Temp : " + temp.toString());

        System.out.println("SELECT * FROM TEST WHERE name = Sam");
        List<com.project.love_data.model.Test> tempList = testRepository.selectNAME("Sam");
        int index = 0;
        for (com.project.love_data.model.Test item : tempList) {
            System.out.println("# item " + index++);
            System.out.println("item seq : " + item.getSeq());
            System.out.println("item country : " + item.getCountry());
            System.out.println("item name : " + item.getName());
        }

        System.out.println("SELECT * FROM TEST WHERE country = ph");
        index = 0;
        for (com.project.love_data.model.Test item : tempList) {
            System.out.println("# item " + index++);
            System.out.println("item seq : " + item.getSeq());
            System.out.println("item country : " + item.getCountry());
            System.out.println("item name : " + item.getName());
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testSelectAll() {
        int index = 0;
        List<com.project.love_data.model.Test> tempList = testRepository.selectALL();
        for (com.project.love_data.model.Test item : tempList) {
            System.out.println("# item " + index++);
            System.out.println("item seq : " + item.getSeq());
            System.out.println("item country : " + item.getCountry());
            System.out.println("item name : " + item.getName());     
        }
        System.out.println("Result Size : " + tempList.size());
    }

    @Test
    public void testUpdate(){
        int seq = 5;
        String name = "525";
        String country = "52";
        testRepository.updateSEQ(seq,name,country);
    }

    @Test
    public void testDelete(){
        Integer seq = 5;
        testRepository.deleteSEQ(seq);
    }
}
