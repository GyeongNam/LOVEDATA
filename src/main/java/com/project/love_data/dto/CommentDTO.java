package com.project.love_data.dto;

import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long cmtNo;
    @Builder.Default
    private boolean is_deleted = false;
    @Builder.Default
    private boolean is_reported = false;
    @Builder.Default
    private boolean modified = false;
    @ToString.Exclude
    private Location location;
    private String cmtContent;
    private User user;

    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
    @Builder.Default
    private Long cmtIdx = 0L;
    @Builder.Default
    private String cmtUuid = UUID.randomUUID().toString();
    @Builder.Default
    private int likeCount = 0;
    @Builder.Default
    private int dislikeCount = 0;
}
