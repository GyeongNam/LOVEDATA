package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "report_cluster")
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCluster extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long rcNo = null;

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

    @Column(name = "rep_count", nullable = false)
    @Builder.Default
    private int repCount = 1;
}
