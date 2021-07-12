package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.QComment;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.CommentRepository;
import com.project.love_data.repository.LocationRepository;
import com.project.love_data.repository.UserRepository;
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
    private final UserRepository userRepository;

    public Comment dtoToEntity(CommentDTO dto){
        Comment entity = Comment.builder()
                .cmtNo(dto.getCmtNo())
                .cmtIdx(dto.getCmtIdx())
                .cmtUuid(dto.getCmtUuid())
                .cmtContent(dto.getCmtContent())
                .user(userRepository.findById(dto.getUser().getUser_no()).get())
                .is_deleted(dto.is_deleted())
                .build();

        return entity;
    }

    public CommentDTO entityToDto(Comment cmt) {
        CommentDTO dto = CommentDTO.builder()
                .cmtNo(cmt.getCmtNo())
                .cmtIdx(cmt.getCmtIdx())
                .cmtUuid(cmt.getCmtUuid())
                .cmtContent(cmt.getCmtContent())
                .user(cmt.getUser())
                .location(cmt.getLocation())
                .regDate(cmt.getRegDate())
                .modDate(cmt.getModDate())
                .is_deleted(cmt.is_deleted())
                .build();

        return dto;
    }

    public Comment createCmtEntity(Long locNo, Long userNo, String cmtContent) {
        Optional<Location> loc = locRepository.findLocByLoc_no(locNo);
        Optional<User> user = userRepository.findById(userNo);

        if (loc.isPresent() && user.isPresent()) {
            Comment entity = Comment.builder()
                    .location(loc.get())
                    .cmtUuid(UUID.randomUUID().toString())
                    .user(user.get())
                    .cmtIdx((long) loc.get().getCmtSet().size())
                    .cmtContent(cmtContent).build();

            return entity;
        }

        return null;
    }

    public PageResultDTO<CommentDTO, Comment> getCmtPage(PageRequestDTO requestDTO,
                                                         CommentPageType commentType) {
       return getCmtPage(requestDTO, commentType, CommentSortType.IDX_ASC);
    }

    public PageResultDTO<CommentDTO, Comment> getCmtPage(PageRequestDTO requestDTO,
                                                         CommentPageType commentType,
                                                         CommentSortType commentSortType) {
        Pageable pageable;
        switch (commentSortType) {
            case IDX_DES:
                pageable = requestDTO.getPageable(Sort.by("cmtIdx").descending());
                break;
            case IDX_ASC:
            default:
                pageable = requestDTO.getPageable(Sort.by("cmtIdx").ascending());
                break;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        switch (commentType) {
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

    private Comment disable(Comment cmt) {
        cmt.set_deleted(true);

        update(cmt);

        return select(cmt.getCmtNo());
    }

    private Comment enable(Comment cmt) {
        cmt.set_deleted(false);

        update(cmt);

        return select(cmt.getCmtNo());
    }

    public void delete(Long cmtNo) {
        Comment cmt = select(cmtNo);

        if (!cmt.is_deleted()) {
            disable(cmt);
        }
    }

    public void permaDelete(Comment cmt) {
        Location loc = cmt.getLocation();

        Set<Comment> cmtSet = loc.getCmtSet();

        cmtSet.remove(cmt);

        loc.setCmtSet(cmtSet);

        locRepository.save(loc);

        cmtRepository.deleteByCmt_uuid(cmt.getCmtUuid());
    }

    public Comment select(String uuid) {
        Optional<Comment> item = cmtRepository.findByCmt_uuid(uuid);
        return item.orElse(null);
    }

    public Comment select(Long cmtNo) {
        Optional<Comment> item = cmtRepository.findByCmt_no(cmtNo);
        return item.orElse(null);
    }
}
