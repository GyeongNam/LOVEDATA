package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "report")
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Report extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long repNo = null;

    @Column(name = "rep_uuid", nullable = false)
    @Builder.Default
    private String repUuid = String.valueOf(UUID.randomUUID());

    @Column(name = "rc_no", nullable = false)
    private Long rcNo;

    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "rep_type", nullable = false)
    private String repType;

    @Column(name = "rep_content", nullable = false)
    @Builder.Default
    private String repContent = "";

    @Column(name = "is_complete", nullable = false)
    private boolean complete = false;

    @Column(name = "result", nullable = false)
    @Builder.Default
    private String result = "";
}
