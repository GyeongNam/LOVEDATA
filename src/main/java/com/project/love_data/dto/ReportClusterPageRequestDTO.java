package com.project.love_data.dto;

import com.project.love_data.businessLogic.service.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@Builder
@AllArgsConstructor
@Data
public class ReportClusterPageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private Long rcNo;
    private Long postNo;
    private String postType;
    private String rcStatus;
    private Boolean rcComplete;
    private Integer repCount;
    @Builder.Default
    private ReportPagePostType searchPostType = ReportPagePostType.ALL;
    @Builder.Default
    private ReportPageCompleteType completeType = ReportPageCompleteType.PROGRESS;
    @Builder.Default
    private ReportClusterPageSortCriterion sortCriterion = ReportClusterPageSortCriterion.REPORT_COUNT;
    @Builder.Default
    private SortingOrder sortingOrder = SortingOrder.DES;


    public ReportClusterPageRequestDTO() {
        this.page = 1;
        this.size = 10;
        this.sortCriterion = ReportClusterPageSortCriterion.REPORT_COUNT;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
