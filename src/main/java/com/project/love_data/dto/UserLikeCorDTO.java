package com.project.love_data.dto;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeCorDTO {
    private Long userLikeCor_no;
    private Long cor_no;
    private Long user_no;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
}
