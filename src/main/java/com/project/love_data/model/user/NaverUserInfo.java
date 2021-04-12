package com.project.love_data.model.user;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverUserInfo {
    @SerializedName("id")
    private String id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("profile_image")
    private String profile_image;

    @SerializedName("age")
    private String age;

    @SerializedName("sex")
    private String sex;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("mobile_e164")
    private String mobile_e164;

    @SerializedName("name")
    private String name;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("birthyear")
    private String birthyear;
}
