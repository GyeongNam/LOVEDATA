package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.CommentPageType;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.QComment;
import com.project.love_data.repository.CommentRepository;
import com.project.love_data.repository.LocationRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository cmtRepository;
    private final LocationRepository locRepository;

    public Comment dtoToEntity(CommentDTO dto){
        Comment entity = Comment.builder()
                .cmtNo(dto.getCmtNo())
                .cmtIdx(dto.getCmtIdx())
                .cmtUuid(dto.getCmtUuid())
                .cmtContent(dto.getCmtContent())
                .userNo(dto.getUserNo())
                .build();

        return entity;
    }

    public CommentDTO entityToDto(Comment cmt) {
        CommentDTO dto = CommentDTO.builder()
                .cmtNo(cmt.getCmtNo())
                .cmtIdx(cmt.getCmtIdx())
                .cmtUuid(cmt.getCmtUuid())
                .cmtContent(cmt.getCmtContent())
                .userNo(cmt.getUserNo())
                .location(cmt.getLocation())
                .regDate(cmt.getRegDate())
                .modDate(cmt.getModDate())
                .build();

        return dto;
    }

    public PageResultDTO<CommentDTO, Comment> getCmtPage(PageRequestDTO requestDTO, CommentPageType pageType) {
        Pageable pageable = requestDTO.getPageable(Sort.by("cmtIdx").descending());

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        switch (pageType) {
            case LOCATION:
                booleanBuilder = getCmtPage_Loc(requestDTO);
                break;
            case ALL:
            default:
//                booleanBuilder = null;
                break;
        }

        Page<Comment> result = null;
        if (booleanBuilder.hasValue()) {
            result = cmtRepository.findAll(booleanBuilder, pageable);
        } else {
            result = cmtRepository.findAll(pageable);
        }

        Function<Comment, CommentDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public BooleanBuilder getCmtPage_Loc(PageRequestDTO requestDTO) {
        Long locNo = requestDTO.getLocNo();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QComment qComment = QComment.comment;

        BooleanExpression expression = qComment.location.loc_no.eq(locNo);

        booleanBuilder.and(expression);

        return booleanBuilder;
    }

    public Comment register(Comment cmt) {
        cmtRepository.save(cmt);

        return cmt;
    }

    public void update(Comment cmt) {
        cmtRepository.save(cmt);
    }

    public void delete(Comment cmt) {
        Location loc = cmt.getLocation();

        Set<Comment> cmtSet = loc.getCmtSet();

        cmtSet.remove(cmt);

        loc.setCmtSet(cmtSet);

        locRepository.save(loc);

        cmtRepository.deleteByCmt_uuid(cmt.getCmtUuid());
    }
}
