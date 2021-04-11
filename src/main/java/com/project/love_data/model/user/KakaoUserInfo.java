package com.project.love_data.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@ToString
public class KakaoUserInfo {
    private String id;
    // db에서 user_time과 매핑
    private String connected_at;
    private KakaoAccount kakao_account;

//    // db에서 user_nic과 매핑
//    private String nickname;
//    private String profile_image;
//    private String thumbnail_image;
////     db에서 user_email과 매핑
//    private String email;
//    private String age_range;
////     db에서 user_birth와 매핑
//    private String birthday;
////     db에서 user_gen과 매핑
//    private String gender;
//    private String phone;
}
