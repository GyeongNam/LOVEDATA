package com.project.love_data.security.model;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Getter
@Setter
@ToString
public class AuthUserModel extends User {
    private Long user_no;
    private String user_email;
    private String user_pw;
    private String user_nic;
    private String user_name;
    private String user_phone;
    private String user_birth;
    private String social_info;
    private String user_profilePic;
    private LocalDateTime user_regDate;
    private boolean user_sex;
    private boolean user_social;
    private boolean user_emil_re;
    private boolean user_Activation;

    public AuthUserModel(String user_email, String password, String user_profilePic, boolean social, Collection<? extends GrantedAuthority> authorities) {
        super(user_email, password, authorities);
        this.user_email = user_email;
        this.user_social = social;
    }
}
