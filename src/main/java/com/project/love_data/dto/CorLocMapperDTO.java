package com.project.love_data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorLocMapperDTO {
    private Long cl_No;
    private Long loc_no;
    private Long cor_no;
    private int loc_index;

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
