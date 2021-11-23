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
public class DeletedImageInfoDTO {
    private Long diiNo;
    private String imgType;
    private Long imgNo;
    private String imgUuid;
    private Long imgUserNo;

    @Builder.Default
    private String diiUuid = UUID.randomUUID().toString();
    @Builder.Default
    private String result = "";
    @Builder.Default
    private boolean deleted = false;
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
}
