package com.project.love_data.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long revNo;
    private String revContent;
    private Long userNo;
    private Long corNo;
    private String userNickname;

    @Builder.Default
    private Long revIdx = 0L;
    @Builder.Default
    private float sc_total = 0f;
    @Builder.Default
    private int sc_move = 0;
    @Builder.Default
    private int sc_loc = 0;
    @Builder.Default
    private int sc_time = 0;
    @Builder.Default
    private int sc_revisit = 0;
    @Builder.Default
    private boolean is_deleted = false;
    @Builder.Default
    private boolean modified = false;
    @Builder.Default
    private boolean is_reported = false;
    @Builder.Default
    private String revUuid = UUID.randomUUID().toString();
    @Builder.Default
    private int rev_like = 0;
    @Builder.Default
    private int rev_dislike = 0;
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
}
