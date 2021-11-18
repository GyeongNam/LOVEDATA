package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "report_clutser")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCluster extends TimeEntity {
    @Id
    @Column(name = "rc_no", nullable = false)
    private Long rcNo;

    @Column(name = "post_no", nullable = false)
    private Long postNo;

    @Column(name = "post_type", nullable = false)
    private String postType;

    @Column(name = "rc_uuid", nullable = false)
    @Builder.Default
    private String rcUuid = UUID.randomUUID().toString();

    @Column(name = "rc_status", nullable = false)
    @Builder.Default
    private String rcStatus = "";

    @Column(name = "rc_complete", nullable = false)
    @Builder.Default
    private boolean rcComplete = false;
}
