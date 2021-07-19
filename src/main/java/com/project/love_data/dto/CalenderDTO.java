package com.project.love_data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalenderDTO {

    private Long cal_no;
    private Long user_no;
    private String title;
    private String place;
    private String start;
    private String end;
    private String color;
    private String text;
    private boolean all_day;
}
