package com.project.love_data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private Long img_no;
    private Long user_no;
    private String loc_uuid;
    // @Todo 코스, 리뷰 칼럼 추가
    private String img_url;
    private String img_uuid;
}
