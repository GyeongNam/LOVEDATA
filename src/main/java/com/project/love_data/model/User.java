package com.project.love_data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="User")
@ToString
@Setter
@Getter
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_no;

    @Column(length = 100, nullable = false)
    private String user_email;

    // BCrypt 알고리즘 사용시 모든 비밀번호의 길이는 60자(60Byte)
    // https://github.com/kelektiv/node.bcrypt.js/issues/534
    @Column(length = 60, nullable = false)
    private String user_pw;
    
    @Column(length = 50, nullable = false)
    private String user_nic;
    
    @Column(length = 50, nullable = false)
    private String user_name;
    
    @Column(length = 50, nullable = false)
    private String user_phone;
    
    @Column(length = 50, nullable = false)
    private String user_birth;

    // 성별
    @Column(length = 50, nullable = false)
    private String user_gen;

    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean user_social;
    
    @Column(length = 50, nullable = true)
    private String user_emil_re;

    // 유저 활동 관련 변수 (활동정지 및 등등)
    @Column(length = 50, nullable = false)
    private String user_Activation;
    
    @Column(length = 50, nullable = false)
    private LocalDateTime user_time;

    public User(){
        String user_email = null;
        String user_pw = null;
        String user_nic = null;
        String user_name = null;
        String user_phone = null;
        String user_birth = null;
        String user_gen = null;
        String user_emil_re = null;
    }

    public User(
    		String user_email, 
    		String user_pw, 
    		String user_nic, 
    		String user_name, 
    		String user_phone,
    		String user_birth,
    		String user_gen,
    		String user_emil_re
    		) {
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nic = user_nic;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_birth = user_birth;
        this.user_gen=user_gen;
        this.user_emil_re = user_emil_re;
        this.user_Activation = "1";
        this.user_time = LocalDateTime.now();
    }
}
