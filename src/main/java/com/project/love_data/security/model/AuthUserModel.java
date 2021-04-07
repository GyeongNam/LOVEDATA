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
    private String user_gen;
    private boolean user_social;
    private String user_emil_re;
    // 유저 활동 관련 변수 (활동정지 및 등등)
    private String user_Activation;
    private LocalDateTime user_time;

    public AuthUserModel(String user_email, String password, boolean social, Collection<? extends GrantedAuthority> authorities) {
        super(user_email, password, authorities);
        this.user_email = user_email;
        this.user_social = social;
    }
}
