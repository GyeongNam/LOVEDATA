package com.project.love_data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login_test").setViewName("loginPage");
        registry.addViewController("/member").setViewName("/sample/member");
        registry.addViewController("/all").setViewName("/sample/all");
        registry.addViewController("/all").setViewName("/sample/all");
        registry.addViewController("/test_jsp").setViewName("/temp/test_jsp");
        registry.addViewController("/").setViewName("/home");
//        registry.addViewController("/infoFind").setViewName("/user/infoFind");
//        registry.addViewController("/user/signup").setViewName("/user/signup");
    }
}
