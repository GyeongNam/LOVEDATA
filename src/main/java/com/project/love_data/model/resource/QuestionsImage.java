package com.project.love_data.model.resource;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "qu_image")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsImage extends TimeEntity {
    @Id
    @Column(name = "qu_img_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long qu_img_no;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "qu_no", nullable = false)
    private Long qu_no;

    @Column(name = "qu_img_url", nullable = false)
    private String qu_img_url;

    @Builder.Default
    @Column(length = 1, nullable = true, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean qu_img_Activation = true;
}
