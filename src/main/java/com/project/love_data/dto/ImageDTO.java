package com.project.love_data.dto;

import com.project.love_data.model.service.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private Long img_no;
    private Long user_no;
//    private String loc_uuid;
    private Location location;
    // @Todo 코스, 리뷰 칼럼 추가
    private String img_url;
    private String img_uuid;
    @Builder.Default
    private Long idx = 0L;
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
    @Builder.Default
    private boolean is_deleted = false;
}
