package com.project.love_data.dto;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long cor_no;
    private String cor_name;
    private Long user_no;
    private String est_time;
    private String transportation;
    private String cost;
    private String info;

    @Builder.Default
    private String cor_uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();
    @Builder.Default
    private int likeCount = 0;
    @Builder.Default
    private int viewCount = 0;
    @Builder.Default
    private String thumbnail = "";
    @Builder.Default
    private boolean is_deleted = false;
}
