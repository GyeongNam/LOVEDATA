package com.project.love_data.dto;

import com.project.love_data.model.base.TimeEntity;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDislikeRevDTO extends TimeEntity {
    private Long userDislikeRev_no;
    private Long rev_no;
    private Long user_no;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
}
