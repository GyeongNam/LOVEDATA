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
@Table(name = "user_suspension")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Log4j2
public class UserSuspension extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long user_suspension_no;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "rc_no", nullable = true)
    private Long rc_no;

    @Column(name = "re_content", nullable = false)
    private String re_content;

    @Column(name = "start_day", nullable = false)
    private String start_day;

    @Column(name = "stop_day", nullable = false)
    private String stop_day;

    @Column(name = "end_day", nullable = false)
    private String end_day;

    // 0 = 끝남 , 1 = 진행 증 , 2 = 대기
    @Column(name = "progress", nullable = false)
    private String progress;
}
