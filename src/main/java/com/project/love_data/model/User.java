package com.project.love_data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
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

    @Column(length = 100, nullable = false)
    private String user_pw;
    
    @Column(length = 50, nullable = false)
    private String user_nic;
    
    @Column(length = 50, nullable = false)
    private String user_name;
    
    @Column(length = 50, nullable = false)
    private String user_phone;
    
    @Column(length = 50, nullable = false)
    private String user_bir;
    
    @Column(length = 50, nullable = false)
    private String user_gen;
    
    @Column(length = 50, nullable = true)
    private String user_sms;
    
    @Column(length = 50, nullable = true)
    private String user_emil_re;
    
    @Column(length = 50, nullable = false)
    private String user_Activation;
    
    
    @Column(length = 50, nullable = false)
    private LocalDateTime user_time;

    public User(
    		String user_email, 
    		String user_pw, 
    		String user_nic, 
    		String user_name, 
    		String user_phone,
    		String user_bir,
    		String user_gen,
    		String user_emil_re
    		) {
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nic = user_nic;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_bir = user_bir;
        this.user_gen=user_gen;
        this.user_emil_re = user_emil_re;
        this.user_Activation = "1";
        this.user_time = LocalDateTime.now();
    }
}
