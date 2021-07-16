package com.project.love_data.dto;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseImageDTO {
    private Long img_no;
    private Long user_no;
    private Long cor_no;
    private String img_url;

    @Builder.Default
    private Long idx = 0L;
    @Builder.Default
    private String img_uuid = UUID.randomUUID().toString();
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
    @Builder.Default
    private boolean is_deleted = false;
}
