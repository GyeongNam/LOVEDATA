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
//@ToString(exclude = "calSet")
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

    @Column(name = "user_mail",length = 50, nullable = false)
    private String user_mail;

    @Column(name = "title", length = 15, nullable = false)
    private String title;

    @Column(name = "start", length = 20, nullable = false)
    private String start;

    @Column(name = "end", length = 20, nullable = false)
    private String end;

    @Column(name = "color", length = 15, nullable = false)
    private String color;

    @Column(name = "text", length = 100, nullable = true)
    private String text;

    @Column(name = "road",length = 50, nullable = true)
    private String road;

    @Column(name = "road2",length = 50, nullable = true)
    private String road2;

    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean all_day;

    @Builder.Default
    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean cal_Activation = true;
}
