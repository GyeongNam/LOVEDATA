package com.project.love_data.model.user;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.security.model.UserRole;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@ToString(exclude = "cmtSet")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long user_no;

    @Column(length = 50, nullable = false)
    private String user_email;

    // BCrypt 알고리즘 사용시 모든 비밀번호의 길이는 60자(60Byte)
    // https://github.com/kelektiv/node.bcrypt.js/issues/534
    @Column(length = 60, nullable = false)
    private String user_pw;

    @Column(length = 20, nullable = false)
    private String user_nic;

    @Column(length = 15, nullable = false)
    private String user_name;

    @Column(length = 15, nullable = false)
    private String user_phone;

    @Column(length = 30, nullable = false)
    private String user_birth;

//    // Todo 유저 테이블에 좋아요 누른 장소 목록 추가
//    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
//    @JoinTable(name = "user_likeLoc",
//            joinColumns = @JoinColumn(name = "user_no"),
//            inverseJoinColumns = @JoinColumn(name = "loc_no"))
//    @Builder.Default
//    private List<Location> likeLoc = new ArrayList<>();
//
//    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
//    @JoinTable(name = "user_recentLoc",
//            joinColumns = @JoinColumn(name = "user_no"),
//            inverseJoinColumns = @JoinColumn(name = "loc_no"))
//    @Builder.Default
//    private List<Location> recentLoc = new ArrayList<>();
//
//    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
//    @JoinTable(name = "user_uploadLoc",
//            joinColumns = @JoinColumn(name = "user_no"),
//            inverseJoinColumns = @JoinColumn(name = "loc_no"))
//    @Builder.Default
//    private List<Location> uploadLoc = new ArrayList<>();

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
    @Builder.Default
    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean user_Activation = true;

//    @Column(length = 50, nullable = false)
//    private LocalDateTime user_time;

    @Column(length = 20, nullable = true)
    private String social_info;

    @Column(length = 20, nullable = true)
    private int social_id;

    @Column(name = "profile_pic", nullable = false, length = 100)
    @Builder.Default
    private String profile_pic = "/image/icon/user/user.png";

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean is_deleted = false;

    @OneToMany(orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<Comment> cmtSet = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<String> roleSet = new HashSet<>();

    public void addUserRole(UserRole role) {
        roleSet.add(role.name());
    }

    public String getUserSexString() {
        if (this.isUser_sex()) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public void setUserSexString(String sex) {
        switch (sex.toUpperCase()) {
            case "MALE":
                this.setUser_sex(true);
                break;
            default:
                this.setUser_sex(false);
                break;
        }
    }

//    public void addLikeLocation(Location loc) {
//        likeLoc.add(loc);
//    }
//
//    public void removeLikeLocation(Location loc){
//        likeLoc.remove(loc);
//    }
//
//    public void removeLikeLocation(int index) {
//        likeLoc.remove(index);
//    }
//
//    public void addRecentLocation(Location loc){
//        recentLoc.add(loc);
//    }
//
//    public void removeRecentLocation(Location loc){
//        recentLoc.remove(loc);
//    }
//
//    public void removeRecentLocation(int index) {
//        recentLoc.remove(index);
//    }
//
//    public void addUploadLocation(Location loc) {
//        uploadLoc.add(loc);
//    }
//
//    public void removeUploadLocation(Location loc){
//        uploadLoc.remove(loc);
//    }
//
//    public void removeUploadLocation(int index) {
//        uploadLoc.remove(index);
//    }
}
