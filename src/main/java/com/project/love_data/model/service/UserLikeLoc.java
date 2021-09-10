package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_like_loc")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@ToString
public class UserLikeLoc extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long userLikeLoc_no;

    @Column(name = "loc_no", nullable = false)
    private Long loc_no;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "uuid", nullable = false, length = 45)
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
}
