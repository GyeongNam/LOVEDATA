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
public class ReportDTO extends TimeEntity {
    private Long repNo;
    private Long rcNo;
    private Long userNo;
    private String repType;

    @Builder.Default
    private String repUuid = String.valueOf(UUID.randomUUID());
    @Builder.Default
    private String repContent = "";
    @Builder.Default
    private boolean complete = false;
    @Builder.Default
    private String result = "";
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
}
