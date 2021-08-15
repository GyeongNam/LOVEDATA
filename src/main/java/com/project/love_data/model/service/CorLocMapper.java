package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "cor_loc_mapper")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorLocMapper extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long clm_No;

    @Column(name = "loc_no", nullable = false)
    private Long loc_no;

    @Column(name = "loc_index", nullable = false)
    private int loc_index;

    @Column(name = "cor_no", nullable = false)
    private Long cor_no;

    @Column(name = "clm_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private String clm_uuid = UUID.randomUUID().toString();

//    @Transactional
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        stringBuilder.append("CorLocMapper(");
//        stringBuilder.append("cl_no : " + cl_No);
//        stringBuilder.append(", loc_no : " + loc_no);
//        stringBuilder.append(", cor_no : " + cor_no);
//        stringBuilder.append(", clm_uuid : " + clm_uuid);
//        stringBuilder.append(")");
//
//        return stringBuilder.toString();
//    }
}
