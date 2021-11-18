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
public class ReportPageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private Long repNo;
    private Long userNo;
    private Long postNo;
    private String postType;
    private String repType;
    private String result;
    @Builder.Default
    private ReportPagePostType searchPostType = ReportPagePostType.ALL;
    @Builder.Default
    private Set<ReportPageSearchType> searchType = new HashSet<>();
    @Builder.Default
    private ReportPageCompleteType completeType = ReportPageCompleteType.ALL;
    @Builder.Default
    private ReportPageSortCriterion sortCriterion = ReportPageSortCriterion.DATE;
    @Builder.Default
    private SortingOrder sortingOrder = SortingOrder.DES;


    public ReportPageRequestDTO() {
        this.page = 1;
        this.size = 10;
        this.sortCriterion = ReportPageSortCriterion.DATE;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
