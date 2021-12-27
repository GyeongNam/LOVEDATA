package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "event")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long ev_no;

    @Column(name = "ev_title", nullable = false)
    private String ev_title;

    @Column(name = "ev_manager", nullable = false)
    private String ev_manager;

    @Column(name = "ev_text", length =10000000, nullable = false)
    private String ev_text;

    @Column(name = "ev_start", nullable = false)
    private String ev_start;

    @Column(name = "ev_stop", nullable = false)
    private String ev_stop;

    @Column(name = "ev_end", nullable = false)
    private String ev_end;

    @Column(name = "ev_item", nullable = false)
    private Long ev_item;

    @Column(name = "ev_viewCount", nullable = false, columnDefinition = "int default 0")
    @Builder.Default
    private int ev_viewCount = 0;

    @Column(name = "ev_activation", nullable = false)
    @Builder.Default
    private boolean ev_activation = true;

    @Column(name = "ev_item_activation", nullable = false)
    @Builder.Default
    private boolean ev_item_activation = false;
}
