package com.project.love_data.model.service;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "notice")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long noti_no;

    @Column(name = "noti_title", nullable = false)
    private String noti_title;

    @Column(name = "noti_manager", nullable = false)
    private String noti_manager;

    @Column(name = "noti_text", length =10000000, nullable = false)
    private String noti_text;

    @Column(name = "noti_date", nullable = false)
    private String noti_date;

    @Column(name = "noti_viewCount", nullable = false, columnDefinition = "int default 0")
    @Builder.Default
    private int noti_viewCount = 0;

    @Column(name = "noti_activation", nullable = false)
    @Builder.Default
    private boolean noti_activation = true;
}
