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
        registry.addViewController("/NewPassword").setViewName("user/NewPassword");
        registry.addViewController("/Notice").setViewName("user/Notice");


        //header
        registry.addViewController("/mypage").setViewName("user/mypage");

        // sample
        registry.addViewController("/sample/index").setViewName("/sample/index");
        registry.addViewController("/member").setViewName("/sample/member");
        registry.addViewController("/all").setViewName("/sample/all");
        registry.addViewController("/admin").setViewName("/sample/admin");
//        registry.addViewController("/form_test").setViewName("/sample/form_test");


        // bootstarp test
        registry.addViewController("/bootstrap").setViewName("Bootstrap/index");
        registry.addViewController("/bootstrap_start").setViewName("Bootstrap/BootStrapTest");
        registry.addViewController("/test/dropdown").setViewName("Bootstrap/Dropdown_Test");

        // Service
        registry.addViewController("/service/loc_index").setViewName("/service/loc_index");
//        registry.addViewController("/service/loc_recommend").setViewName("/service/loc_recommend");
        registry.addViewController("/service/loc_detail/ex").setViewName("/service/loc_detail_example");
//        registry.addViewController("/service/loc_registration").setViewName("/service/loc_registration");

        //Popup
//        registry.addViewController("/popup/jusoPopup").setViewName("/popup/jusoPopup");
//        registry.addViewController("/service/loc_registration").setViewName("/service/loc_registration");
        registry.addViewController("/service/calender").setViewName("/service/service_calender");

    }
}
