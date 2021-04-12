package com.project.love_data.model.user;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoUserInfo {
    @SerializedName("id")
    private String id;
    // db에서 user_time과 매핑

    @SerializedName("connected_at")
    private String connected_at;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("nickname")
    private String thumbnail_image_url;

    @SerializedName("nickname")
    private String profile_image_url;

    @SerializedName("has_email")
    private Boolean has_email;

    @SerializedName("is_email_valid")
    private Boolean is_email_valid;

    @SerializedName("is_email_verified")
    private Boolean is_email_verified;

    @SerializedName("email")
    private String email;
}
