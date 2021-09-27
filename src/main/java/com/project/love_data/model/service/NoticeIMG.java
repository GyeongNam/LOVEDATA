package com.project.love_data.model.service;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "noticeimg")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeIMG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long notiimg_no;

    @Column(name = "notiimg_name", nullable = false)
    private String notiimg_name;

    @Column(name = "notiimg_user", nullable = false)
    private String notiimg_user;

    @Column(name = "notiimg_postno")
    private String notiimg_postno;

    @Column(name = "notiimg_activation", nullable = false)
    @Builder.Default
    private boolean noti_activation = true;
}
