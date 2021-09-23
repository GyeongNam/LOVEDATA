package com.project.love_data.dto;

import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.security.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long user_no;
    private String user_email;
    private String user_pw;
    private String user_nic;
    private String user_name;
    private String user_phone;
    private String user_birth;
    private boolean user_sex;
    private boolean user_social;
    private boolean user_email_re;
    private String social_info;
    private int social_id;
    @Builder.Default
    private boolean is_deleted = false;

    @Builder.Default
    private String profile_pic = "/image/icon/user/user.png";
    @Builder.Default
    private List<Comment> cmtList = new ArrayList<>();
    @Builder.Default
    private Set<String> roleSet = new HashSet<>();
//    @Builder.Default
//    private List<Location> likeLoc = new ArrayList<>();
//    @Builder.Default
//    private List<Location> recentLoc = new LinkedList<>();
//    @Builder.Default
//    private List<Location> uploadLoc = new LinkedList<>();
    @Builder.Default
    private boolean user_Activation = true;
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();

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

    //CHOI
    public String myinfolod(String user_no) {

        return "유저디티오";
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
