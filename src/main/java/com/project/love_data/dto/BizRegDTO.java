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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BizRegDTO extends TimeEntity {
    private Long brNo;
    private Long userNo;
    private String url;
    private String bizName;
    private String bizCeoName;
    private String bizCall;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Boolean certified = false;
    @Builder.Default
    private Boolean deleted = false;
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
}
