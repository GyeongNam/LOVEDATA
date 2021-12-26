package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "point")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class Point extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long point_no;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "point", nullable = false)
    private Long point;

    @Column(name = "point_get_out", nullable = false)
    private String point_get_out;

    @Column(name = "get_plus_mi", nullable = false)
    private boolean get_plus_mi;

    @Column(name = "get_no_use_no", nullable = false)
    private Long get_no_use_no;

}
