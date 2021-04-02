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
            com.project.love_data.model.Test test = new com.project.love_data.model.Test(i, "Sample.."+i, "kr");
            testRepository.save(test);
        });
    }

    @Transactional
    @Test
    public void TestSelect2() {
        Integer seq = 5;
        com.project.love_data.model.Test test = testRepository.getOne(seq);
        System.out.println("========================================");
        System.out.println(test);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Test
    public void testSelectAll() {
    	System.out.println(testRepository.selectallSEQ());
    }
    @Test
    public void testInsert() {
        String name = "111";
        String country = "12";
    	testRepository.insertSEQ(name,country);
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
