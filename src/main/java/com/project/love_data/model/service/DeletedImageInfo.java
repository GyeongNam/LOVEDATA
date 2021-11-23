package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "del_img_info")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletedImageInfo extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long diiNo;

    @Column(name = "dii_uuid", nullable = false)
    @Builder.Default
    private String diiUuid = UUID.randomUUID().toString();

    @Column(name = "img_type", nullable = false)
    private String imgType;

    @Column(name = "img_no", nullable = false)
    private Long imgNo;

    @Column(name = "img_uuid", nullable = false)
    private String imgUuid;

    @Column(name = "img_user_no", nullable = false)
    private Long imgUserNo;

    @Column(name = "result", nullable = false)
    @Builder.Default
    private String result = "";

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}
