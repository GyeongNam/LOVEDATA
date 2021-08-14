//package com.project.love_data;
//
//import com.project.love_data.util.TagPropertiesReader;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.*;
//
//@SpringBootTest
//public class PropertiesTest {
//    @Autowired
//    TagPropertiesReader tagReader;
//
////    @Test
////    public void getTags() {
////        Map<String, Object> map = new HashMap<>();
////
////        Properties prop = tagReader.getTags();
////
////        for (Object s : prop.keySet()) {
////            System.out.println(s + "(key)\t:\t" + prop.getProperty(String.valueOf(s)));
////        }
////    }
//
//    @Test
//    public void getTagList() {
//        // https://sas-study.tistory.com/273
//        List<String> tagList = new ArrayList<>();
//
//        tagList = tagReader.getTagList();
//
//        for (String s : tagList) {
//            System.out.println(s);
//        }
//
//        String outdoor = tagList.get(tagList.indexOf("야외"));
//        System.out.println(outdoor);
//    }
//}
