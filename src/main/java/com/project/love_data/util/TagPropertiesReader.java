//package com.project.love_data.util;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.*;
//
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
//@Log4j2
//@Configuration
//@RequiredArgsConstructor
//@PropertySource("classpath:properties/LocationTag.properties")
//public class TagPropertiesReader {
//    @Autowired
//    private final Environment environment;
//    @Value("${loc.tag_loc_list_kr}")
//    private List<String> tagUTFList = new ArrayList<>();
//    private List<String> tagList = new ArrayList<>();
//
//    public String getTagFromEnv (String key){
//        return environment.getProperty(key);
//    }
//
//    public List<String> getTagUTFList() {
//        return tagUTFList;
//    }
//
//    public List<String> getTagList() {
//        for (String s : tagUTFList) {
//            byte[] utf8 = s.getBytes(StandardCharsets.UTF_8);
//            String str = new String(utf8, StandardCharsets.UTF_8);
//            tagList.add(str);
//        }
//
//        return tagList;
//    }
//}
