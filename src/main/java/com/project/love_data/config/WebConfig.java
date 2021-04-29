package com.project.love_data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // home
        registry.addViewController("/").setViewName("/home");

        // user
//        registry.addViewController("/login_test").setViewName("loginPage");
        registry.addViewController("/find_id").setViewName("user/find-info-new");
        registry.addViewController("/find-info").setViewName("user/find-info");
        registry.addViewController("/infoFind").setViewName("user/infoFind");
        registry.addViewController("/user/deleteAccount").setViewName("user/deleteAccount");
        registry.addViewController("/login").setViewName("user/loginPage");


        // sample
        registry.addViewController("/member").setViewName("/sample/member");
        registry.addViewController("/all").setViewName("/sample/all");
        registry.addViewController("/admin").setViewName("/sample/admin");


        // bootstarp test
        registry.addViewController("/bootstrap").setViewName("Bootstrap/index");
        registry.addViewController("/bootstrap_start").setViewName("Bootstrap/BootStrapTest");
        registry.addViewController("/test/dropdown").setViewName("Bootstrap/Dropdown_Test");
        registry.addViewController("/test/collapse").setViewName("Bootstrap/Collapse_Test");

        // Service
        registry.addViewController("/service_sample").setViewName("/service/service_sample");
    }
}
