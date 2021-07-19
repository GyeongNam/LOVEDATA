package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.resource.LocationImage;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "course")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long cor_no;

    @Column(name = "cor_name", nullable = false, length = 40)
    private String cor_name;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "cor_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private String cor_uuid = UUID.randomUUID().toString();

    @Column(name = "est_time", nullable = false)
    private String est_time;

    @Column(name = "transportation", nullable = false)
    private String transportation;

    @Column(name = "cost", nullable = false)
    private String cost;

    @Column(name = "info", length = 150, nullable = false)
    private String info;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "cor_no")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cor_no")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<CourseImage> imgSet = new HashSet<>();

    @Column(name = "likecount", nullable = false, columnDefinition = "int default 0")
    @Builder.Default
    private int likeCount = 0;

    @Column(name = "viewcount", nullable = false, columnDefinition = "int default 0")
    @Builder.Default
    private int viewCount = 0;

    @Column(name = "thumbnail", nullable = false)
    @Builder.Default
    private String thumbnail = "";

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean is_deleted = false;
}