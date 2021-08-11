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
public class ReviewDTO {
    private Long revNo;
    private String revContent;
    private Long userNo;
    private Long corNo;
    private String userName;

    @Builder.Default
    private Long revIdx = 0L;
    @Builder.Default
    private float rev_point = 2.5f;
    @Builder.Default
    private boolean is_deleted = false;
    @Builder.Default
    private String revUuid = UUID.randomUUID().toString();
}
