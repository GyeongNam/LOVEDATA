package com.project.love_data.dto;

import com.project.love_data.businessLogic.service.SearchType;
import com.project.love_data.businessLogic.service.SortCriterion;
import com.project.love_data.businessLogic.service.SortingOrder;
import com.project.love_data.model.service.KoreanDistrict;
import com.project.love_data.model.service.LocationTag;
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
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private List<String> tagList;
    private String keyword;
    private Long locNo;
    private Long userNo;
    private Long corNo;
    @Builder.Default
    private SearchType searchType = SearchType.NONE;
    @Builder.Default
    private SortCriterion sortCriterion = SortCriterion.VIEW;
    @Builder.Default
    private SortingOrder sortingOrder = SortingOrder.DES;
    @Builder.Default
    private KoreanDistrict districtType = KoreanDistrict.전국;


    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
        this.sortCriterion = SortCriterion.VIEW;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
