package com.project.love_data.model.user;

import com.project.love_data.security.model.UserRole;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="User")
@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{
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
    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean user_sex;

    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean user_social;

    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean user_email_re;

    // 유저 활동 관련 변수 (활동정지 및 등등)
    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean user_Activation;
    
    @Column(length = 50, nullable = false)
    private LocalDateTime user_time;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<String> roleSet = new HashSet<>();

    public void addUserRole(UserRole role) {
        roleSet.add(role.name());
    }

    public String getUserSexString(){
        if (this.isUser_sex()) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public void setUserSexString(String sex) {
        switch (sex.toUpperCase()) {
            case "MALE" :
                this.setUser_sex(true);
                break;
            default :
                this.setUser_sex(false);
                break;
        }
    }
}
