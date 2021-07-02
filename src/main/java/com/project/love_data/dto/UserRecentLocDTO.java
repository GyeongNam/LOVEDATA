package com.project.love_data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRecentLocDTO {
    private Long userRecentLoc_no;
    private Long loc_no;
    private Long user_no;
    private String uuid;
}
