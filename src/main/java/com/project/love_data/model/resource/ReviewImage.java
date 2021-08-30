package com.project.love_data.model.resource;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table (name = "rev_image")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage extends TimeEntity {
    @Id
    @Column(name = "img_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long img_no;

    @Column(name = "img_idx", nullable = false)
    @Builder.Default
    private Long idx = 0L;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "cor_no", nullable = false)
    private Long cor_no;

    @Column(name = "rev_no", nullable = false)
    private Long rev_no;

    @Column(name = "img_url", nullable = false, length = 200)
    @Builder.Default
    private String img_url = "";

    @Column(name = "img_uuid", nullable = false, length = 45)
    @Builder.Default
    private String img_uuid = UUID.randomUUID().toString();

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean is_deleted = false;
}
