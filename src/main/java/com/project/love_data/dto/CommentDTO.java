package com.project.love_data.dto;

import com.project.love_data.model.service.Location;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long cmtNo;
    @ToString.Exclude
    private Location location;
    private String cmtContent;
    private Long userNo;

    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
    @Builder.Default
    private Long cmtIdx = 0L;
    @Builder.Default
    private String cmtUuid = UUID.randomUUID().toString();
}
