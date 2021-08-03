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
    private String user_mail;
    private String title;
    private String start;
    private String end;
    private String color;
    private String road;
    private String road2;
    private String text;
    private boolean all_day;
    private boolean cal_activation;
}
