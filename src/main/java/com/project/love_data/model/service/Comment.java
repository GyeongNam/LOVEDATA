package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.user.User;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "comment")
@ToString(exclude = {"location", "user"})
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class Comment extends TimeEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Column(insertable = false, updatable = false, columnDefinition="serial")
    private Long cmtNo;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Location location;

    @Column(nullable = false)
    @Builder.Default
    private Long cmtIdx = 0L;

    @Column(length = 100, nullable = false)
    private String cmtContent;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @Id
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Column(length = 60, nullable = false)
    @Builder.Default
    private String cmtUuid = UUID.randomUUID().toString();
}
