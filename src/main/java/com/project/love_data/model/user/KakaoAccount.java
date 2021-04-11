package com.project.love_data.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoAccount {
    private String profile_needs_agreement;
    private KakaoProfile profile;
    private Boolean has_email;
    private Boolean email_needs_agreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
    private String has_age_range;
    private Boolean age_range_needs_agreement;
    private String has_birthday;
    private Boolean birthday_needs_agreement;
    private String has_gender;
    private Boolean gender_needs_agreement;
}
