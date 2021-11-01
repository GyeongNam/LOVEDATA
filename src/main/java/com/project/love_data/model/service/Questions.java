package com.project.love_data.model.service;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "questions")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long qu_no;

    @Column(name = "qu_title", nullable = false)
    private String qu_title;

    @Column(name = "qu_user", nullable = false)
    private String qu_user;

    @Column(name = "qu_type", nullable = false)
    private String qu_type;

    @Column(name = "qu_text", nullable = false)
    private String qu_text;

    @Column(name = "qu_answer_manager", nullable = true)
    private String qu_answer_manager;

    @Column(name = "qu_answer_text", nullable = true)
    private String qu_answer_text;

    @Column(name = "qu_date", nullable = false)
    private String qu_date;

    @Column(name = "qu_user_no", nullable = false)
    private String qu_user_no;

    @Column(name = "qu_secret", nullable = false)
    @Builder.Default
    private boolean qu_secret = false;

    @Column(name = "qu_answer", nullable = false)
    @Builder.Default
    private boolean qu_answer = false;

    @Column(name = "qu_activation", nullable = false)
    @Builder.Default
    private boolean qu_activation = true;
}
