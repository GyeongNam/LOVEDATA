package com.project.love_data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///home/tomcat/LoveData-Storage/")
                .setCachePeriod(20);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // home
        registry.addViewController("/").setViewName("/home");
        registry.addViewController("/fullpage").setViewName("/fullpage");

        // user
//        registry.addViewController("/login_test").setViewName("loginPage");
        registry.addViewController("/find_id").setViewName("user/find-info-new");
        registry.addViewController("/find-info").setViewName("user/find-info");
        registry.addViewController("/infoFind").setViewName("user/infoFind");
        registry.addViewController("/user/deleteAccount").setViewName("user/deleteAccount");
        registry.addViewController("/login").setViewName("user/loginPage");
        registry.addViewController("/NewPassword").setViewName("user/NewPassword");
        registry.addViewController("/ServiceCenter").setViewName("user/Service_center");
        registry.addViewController("/admin/user").setViewName("admin/admin_user");



        //header
        registry.addViewController("/mypage").setViewName("user/mypage");

        // sample
        registry.addViewController("/sample/index").setViewName("/sample/index");
        registry.addViewController("/member").setViewName("/sample/member");
        registry.addViewController("/all").setViewName("/sample/all");
        registry.addViewController("/admin").setViewName("/sample/admin");
        registry.addViewController("/sample").setViewName("/sample/index");
        registry.addViewController("/sample/tmapTest").setViewName("/sample/tmapTest");
        registry.addViewController("/sample/tmapPathFindingTest").setViewName("/sample/tmapPathFindingTest");
//        registry.addViewController("/form_test").setViewName("/sample/form_test");


        // bootstarp test
        registry.addViewController("/bootstrap").setViewName("Bootstrap/index");
        registry.addViewController("/bootstrap_start").setViewName("Bootstrap/BootStrapTest");
        registry.addViewController("/test/dropdown").setViewName("Bootstrap/Dropdown_Test");

        // Service
        registry.addViewController("/service/loc_index").setViewName("/service/loc_index");
        registry.addViewController("/service/cor_index").setViewName("/service/cor_index");
//        registry.addViewController("/service/loc_recommend").setViewName("/service/loc_recommend");
        registry.addViewController("/service/loc_detail/ex").setViewName("/service/loc_detail_example");
//        registry.addViewController("/service/loc_registration").setViewName("/service/loc_registration");
        registry.addViewController("/guide/agreePersonal").setViewName("/guide/agreePersonal");

        //Popup
//        registry.addViewController("/popup/jusoPopup").setViewName("/popup/jusoPopup");
//        registry.addViewController("/service/loc_registration").setViewName("/service/loc_registration");
        registry.addViewController("/service/calender").setViewName("/service/service_calender");

    }
}
