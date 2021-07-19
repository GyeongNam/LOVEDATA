package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "calender")
@ToString(exclude = "calSet")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calender extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long cal_no;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "title", length = 15, nullable = false)
    private String title;

    @Column(name = "place", length = 15, nullable = false)
    private String place;

    @Column(name = "start", length = 15, nullable = false)
    private String start;

    @Column(name = "end", length = 15, nullable = false)
    private String end;

    @Column(name = "color", length = 15, nullable = false)
    private String color;

    @Column(name = "text", length = 100, nullable = false)
    private String text;

    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean all_day;
}
