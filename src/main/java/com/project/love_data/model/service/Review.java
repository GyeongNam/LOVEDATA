package com.project.love_data.model.service;

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
public class Review {
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

    @Column(name = "rev_point", nullable = false)
    private float rev_point;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Column(length = 60, nullable = false)
    @Builder.Default
    private String revUuid = UUID.randomUUID().toString();
}
