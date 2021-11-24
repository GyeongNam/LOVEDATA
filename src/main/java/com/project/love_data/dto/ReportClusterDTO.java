package com.project.love_data.dto;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportClusterDTO extends TimeEntity {
    private Long rcNo;
    private Long postNo;
    private Long rcUserNo;
    private String postType;

    @Builder.Default
    private String rcUuid = UUID.randomUUID().toString();
    @Builder.Default
    private String rcResult = "";
    @Builder.Default
    private boolean rcComplete = false;
    @Builder.Default
    private int repCount = 0;
}
