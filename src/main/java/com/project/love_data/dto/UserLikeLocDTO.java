package com.project.love_data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLikeLocDTO {
    private Long userLikeLoc_no;
    private Long loc_no;
    private Long user_no;
    private String uuid;
}
