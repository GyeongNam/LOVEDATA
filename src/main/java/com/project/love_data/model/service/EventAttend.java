package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "eventattend")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventAttend extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long at_no;

    @Column(name = "ev_no", nullable = false)
    private Long ev_no;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "point_no", nullable = false)
    private Long point_no;
}
