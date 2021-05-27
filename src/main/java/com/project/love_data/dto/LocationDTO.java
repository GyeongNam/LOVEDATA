package com.project.love_data.dto;

import com.project.love_data.model.service.LocationTag;
import lombok.*;

import java.time.LocalDateTime;
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
    private String zipNo;
    private String siDo;
    private String siGunGu;
    private String info;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder.Default
    private String loc_uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();
    @Builder.Default
    private Long likeCount = 0L;
    @Builder.Default
    private Long viewCount = 0L;
    @Builder.Default
    private List<ImageDTO> imgList = new ArrayList<>();

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
