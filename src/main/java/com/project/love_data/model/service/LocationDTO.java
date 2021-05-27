package com.project.love_data.model.service;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDTO {
    private Long loc_no;
    private String loc_name;
    private Long user_no;
    private String tel;
    private String roadAddr;
    private String addrDetail;
    private String siDo;
    private String siGunGu;
    private String info;

    @Builder.Default
    private String loc_uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();
    @Builder.Default
    private Long likeCount = 0L;

    // Todo 여기에 댓글 칼럼도 추가

    public void addLocTag(String str) {
        LocationTag tag = null;
        String temp = "";

        temp = str.replaceAll(" ", "_").toUpperCase(Locale.ROOT);

        tag = LocationTag.valueOf(temp);

        tagSet.add(tag.name());
    }

    public String getFullAddr() {
        return this.roadAddr + this.addrDetail;
    }
}
