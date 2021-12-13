package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "bizreg")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizReg extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long brNo;

    @Column(nullable = false)
    private Long userNo;

    @Column(nullable = false)
    private String bizName;

    @Column(nullable = false)
    private String bizCeoName;

    @Column(nullable = false)
    private String bizCall;

    @Column(nullable = false)
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @Column(nullable = true)
    private String url;

    @Column(nullable = false)
    @Builder.Default
    private Boolean certified = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;
}
