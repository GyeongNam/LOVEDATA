package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.LocationImage;
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
       return getCmtPage(requestDTO, commentType,
               CommentSortType.IDX_ASC, CommentSearchType.Live);
    }

    public PageResultDTO<CommentDTO, Comment> getCmtPage(PageRequestDTO requestDTO,
                                                         CommentPageType commentType,
                                                         CommentSortType commentSortType,
                                                         CommentSearchType commentSearchType) {
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
                booleanBuilder = getCmtPage_Loc(requestDTO, commentSearchType);
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

    public BooleanBuilder getCmtPage_Loc(PageRequestDTO requestDTO, CommentSearchType commentSearchType) {
        Long locNo = requestDTO.getLocNo();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QComment qComment = QComment.comment;

        BooleanExpression expression = qComment.location.loc_no.eq(locNo);

        switch (commentSearchType) {
            case Deleted:
                booleanBuilder.and(qComment.is_deleted.eq(true));
                break;
            case Live:
            default:
                booleanBuilder.and(qComment.is_deleted.eq(false));
                break;
        }

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

    private void sortIndex(Long cmtNo) {
        Comment entity = select(cmtNo);
        LocationDTO loc = locEntityToLocDto(entity.getLocation());

        if (entity == null || loc == null) {
            log.warn("존재하지 않는 장소이거나 존재하지 않는 코멘트입니다.");
            return;
        }

        if (entity.getCmtIdx() == 0L) {
            return;
        }

        if (entity.getCmtIdx() == loc.getCmtList().size() - 1) {
            return;
        }

        List<Comment> cmtList = loc.getCmtList();
        entity.set_deleted(true);
        for (int i = Math.toIntExact(entity.getCmtIdx() + 1); i < cmtList.size()-2; i++) {
            cmtList.get(i).setCmtIdx(i - 1L);
        }

        cmtRepository.save(entity);

        loc.setCmtList(cmtList);

        locRepository.save(locDtoToLocEntity(loc));
    }

    public void delete(Long cmtNo) {
        Comment cmt = select(cmtNo);

        if (!cmt.is_deleted()) {
            disable(cmt);
//            sortIndex(cmt.getCmtNo());
        }
    }

    public void permaDelete(Comment cmt) {
        LocationDTO locDTO = locEntityToLocDto(cmt.getLocation());

        List<Comment> cmtList = locDTO.getCmtList();

        cmtList.remove(cmt);

        locDTO.setCmtList(cmtList);

        Location loc = locDtoToLocEntity(locDTO);

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

    public Comment selectByLocNoAndCmtIdx(Long locNo, Long cmtIdx) {
        Optional<Comment> item = cmtRepository.findByLoc_noAndCmtIdx(locNo, cmtIdx);
        return item.orElse(null);
    }

    private Location locDtoToLocEntity(LocationDTO dto) {
        Location entity = Location.builder()
                .loc_no(dto.getLoc_no())
                .loc_name(dto.getLoc_name())
                .loc_uuid(dto.getLoc_uuid())
                .user_no(dto.getUser_no())
                .roadAddr(dto.getRoadAddr())
                .addrDetail(dto.getAddrDetail())
                .siDo(dto.getSiDo())
                .siGunGu(dto.getSiGunGu())
                .info(dto.getInfo())
                .tel(dto.getTel())
                .zipNo(dto.getZipNo())
                .tagSet(dto.getTagSet())
                .imgSet(new HashSet<>(dto.getImgList()))
                .cmtSet(new HashSet<>(dto.getCmtList()))
                .likeCount(dto.getLikeCount())
                .thumbnail(dto.getThumbnail())
                .viewCount(dto.getViewCount())
                .is_deleted(dto.is_deleted())
                .build();

        return entity;
    }

    private LocationDTO locEntityToLocDto(Location entity) {
        LocationDTO dto = LocationDTO.builder()
                .loc_no(entity.getLoc_no())
                .loc_name(entity.getLoc_name())
                .loc_uuid(entity.getLoc_uuid())
                .user_no(entity.getUser_no())
                .roadAddr(entity.getRoadAddr())
                .addrDetail(entity.getAddrDetail())
                .siDo(entity.getSiDo())
                .siGunGu(entity.getSiGunGu())
                .info(entity.getInfo())
                .tel(entity.getTel())
                .zipNo(entity.getZipNo())
                .tagSet(entity.getTagSet())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .likeCount(entity.getLikeCount())
                .thumbnail(entity.getThumbnail())
                .viewCount(entity.getViewCount())
                .is_deleted(entity.is_deleted())
                .build();

        // Image List 변환 및 정렬
        // idx 기준 정렬
        List<LocationImage> tempList = new ArrayList<>();
        List<LocationImage> imgList = new ArrayList<>();
        boolean sortedFlag = false;
        int count = 0;

        for (LocationImage img :
                entity.getImgSet()) {
            tempList.add(img);
        }

//        for (int i = 0; i < tempList.size(); i++) {
//            for (int j = 0; j < tempList.size(); j++) {
//                if (tempList.get(j).getIdx() == i) {
//                    imgList.add(tempList.get(j));
//                    break;
//                }
//            }
//        }

        // idx가 전부 0일 경우 정리가 되지 않은 것이므로
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getIdx() == 0) {
                count++;
            }
        }

        // 플래그 설정
        if (count > 1) {
            sortedFlag = false;
        } else {
            sortedFlag = true;
        }

        if (!sortedFlag) {
            for (int i = 0; i < tempList.size(); i++) {
                tempList.get(i).setIdx((long) i);
                imgList.add(tempList.get(i));
            }
        } else {
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < tempList.size(); j++) {
                    if (tempList.get(j).getIdx() == i) {
                        imgList.add(tempList.get(j));
                        break;
                    }
                }
            }
        }


        // Comment List 변환 및 정렬
        // cmd_idx 기준 정렬
        List<Comment> tempCmtList = new ArrayList<>(entity.getCmtSet());
        List<Comment> cmtList = new ArrayList<>();
        Long cmtMaxCounter = cmtRepository.findMaxIdxByLoc_no(entity.getLoc_no());

        if (cmtMaxCounter == null) {
            cmtMaxCounter = 0L;
        }
        int maxCmtIndex = Math.toIntExact(cmtMaxCounter);

        for (int i = 0; i <= maxCmtIndex; i++) {
            for (int j = 0; j < tempCmtList.size(); j++) {
                if (tempCmtList.get(j).getCmtIdx() == i) {
                    cmtList.add(tempCmtList.get(j));
                    break;
                }
            }
        }

        dto.setImgList(imgList);
        dto.setCmtList(cmtList);

        return dto;
    }
}
