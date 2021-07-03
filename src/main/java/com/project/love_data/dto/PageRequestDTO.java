package com.project.love_data.dto;

import com.project.love_data.businessLogic.service.SearchType;
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
    private int page;
    private int size;
    private List<String> tagList;
    private String keyword;
    private Long locNo;
    private SearchType searchType;
    private String userNo;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
