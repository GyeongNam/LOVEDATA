package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.user.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "review")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long revNo;

    @Column(name = "cor_no", nullable = false)
    private Long corNo;

    @Column(nullable = false)
    @Builder.Default
    private Long revIdx = 0L;

    @Column(length = 300, nullable = false)
    private String revContent;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean is_deleted = false;

    @Column(name = "is_reported", nullable = false)
    @Builder.Default
    private boolean is_reported = false;

    @Column(name = "sc_total", nullable = false)
    @Builder.Default
    private float sc_total = 0f;

    @Column(name = "sc_move")
    @Builder.Default
    private int sc_move = 0;

    @Column(name = "sc_loc")
    @Builder.Default
    private int sc_loc = 0;

    @Column(name = "sc_time")
    @Builder.Default
    private int sc_time = 0;

    @Column(name = "sc_revisit")
    @Builder.Default
    private int sc_revisit = 0;

    @Column(name ="is_modified")
    @Builder.Default
    private boolean is_modified = false;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "user_nickname", nullable = false)
    private String user_nickname;

    @Column(name = "rev_like", nullable = false)
    @Builder.Default
    private int rev_like = 0;

    @Column(name = "rev_dislike", nullable = false)
    @Builder.Default
    private int rev_dislike = 0;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Column(length = 60, nullable = false)
    @Builder.Default
    private String revUuid = UUID.randomUUID().toString();
}
