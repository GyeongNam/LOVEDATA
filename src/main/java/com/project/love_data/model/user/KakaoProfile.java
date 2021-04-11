package com.project.love_data.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
}
