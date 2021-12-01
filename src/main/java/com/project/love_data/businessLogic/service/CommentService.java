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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.project.love_data.util.ConstantValues.MAX_COM_COUNT;

@Service
@Log4j2
public class CommentService {
    @Autowired
    CommentRepository cmtRepository;
    @Autowired
    LocationRepository locRepository;
    @Autowired
    UserRepository userRepository;

    public Comment dtoToEntity(CommentDTO dto){
        Comment entity = Comment.builder()
                .cmtNo(dto.getCmtNo())
                .cmtIdx(dto.getCmtIdx())
                .cmtUuid(dto.getCmtUuid())
                .cmtContent(dto.getCmtContent())
                .user(userRepository.findById(dto.getUser().getUser_no()).get())
                .is_deleted(dto.is_deleted())
                .dislikeCount(dto.getDislikeCount())
                .likeCount(dto.getLikeCount())
                .is_reported(dto.is_reported())
                .modified(dto.isModified())
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
                .dislikeCount(cmt.getDislikeCount())
                .likeCount(cmt.getLikeCount())
                .is_reported(cmt.is_reported())
                .modified(cmt.isModified())
                .build();

        return dto;
    }

    public Comment createCmtEntity(Long locNo, Long userNo, String cmtContent) {
        Optional<Location> loc = locRepository.findLocByLoc_no(locNo);
        Optional<User> user = userRepository.findById(userNo);
        List<Comment> cmtList = findAllByLocNo(locNo);

        long index = 0;

        if (!cmtList.isEmpty()) {
            index = cmtList.get(cmtList.size()-1).getCmtIdx() + 1;
        }

        if (loc.isPresent() && user.isPresent()) {
            Comment entity = Comment.builder()
                    .location(loc.get())
                    .cmtUuid(UUID.randomUUID().toString())
                    .user(user.get())
                    .cmtIdx(index)
                    .cmtContent(cmtContent).build();

            return entity;
        }

        return null;
    }

    public PageResultDTO<CommentDTO, Comment> getCmtPage(PageRequestDTO requestDTO) {
       return getCmtPage(requestDTO,
               CommentSortType.IDX_DES, CommentSearchType.Live);
    }

    public PageResultDTO<CommentDTO, Comment> getCmtPage(PageRequestDTO requestDTO,
                                                         CommentSortType commentSortType,
                                                         CommentSearchType commentSearchType) {
        Pageable pageable;
        switch (commentSortType) {
            case IDX_DES:
                pageable = requestDTO.getPageable(Sort.by("cmtIdx").descending());
                break;
            case IDX_ASC:
                pageable = requestDTO.getPageable(Sort.by("cmtIdx").ascending());
                break;
            case LIKE_DES:
                pageable = requestDTO.getPageable(Sort.by("likeCount").descending());
                break;
            case LIKE_ASC:
                pageable = requestDTO.getPageable(Sort.by("likeCount").ascending());
                break;
            case DISLIKE_DES:
                pageable = requestDTO.getPageable(Sort.by("dislikeCount").descending());
                break;
            case DISLIKE_ASC:
                pageable = requestDTO.getPageable(Sort.by("dislikeCount").ascending());
                break;
            default:
                pageable = requestDTO.getPageable(Sort.by("cmtIdx").descending());
                break;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder = getCmtPage_Loc(requestDTO, commentSearchType);

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
            case All:
                break;
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
//        LocationDTO locDTO = locEntityToLocDto(cmt.getLocation());
//
//        List<Comment> cmtList = locDTO.getCmtList();
//
//        cmtList.remove(cmt);
//
//        locDTO.setCmtList(cmtList);
//
//        Location loc = locDtoToLocEntity(locDTO);
//
//        locRepository.save(loc);

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

    public Boolean incLikeCount (Long cmt_no) {
        Comment cmt = select(cmt_no);

        if (cmt == null) {
            return false;
        }

        cmt.setLikeCount(cmt.getLikeCount() + 1);

        update(cmt);

        return true;
    }

    public boolean decLikeCount (Long cmt_no) {
        Comment cmt = select(cmt_no);

        if (cmt == null) {
            return false;
        }

        cmt.setLikeCount(cmt.getLikeCount() - 1);

        update(cmt);

        return true;
    }

    public boolean incDislikeCount (Long cmt_no) {
        Comment cmt = select(cmt_no);

        if (cmt == null) {
            return false;
        }

        cmt.setDislikeCount(cmt.getDislikeCount() + 1);

        update(cmt);

        return true;
    }

    public boolean decDislikeCount (Long cmt_no) {
        Comment cmt = select(cmt_no);

        if (cmt == null) {
            return false;
        }

        cmt.setDislikeCount(cmt.getDislikeCount() - 1);

        update(cmt);

        return true;
    }

    public List<Comment> getBestComment(Long locNo) {
        Optional<List<Comment>> items = cmtRepository.getBestComment(locNo);

        if (items.isPresent()) {
            List<Comment> entities = items.get();
            Iterator<Comment> itr = entities.iterator();
            while (itr.hasNext()) {
                Optional<User> user = userRepository.findById(itr.next().getUser().getUser_no());
                if (!user.isPresent()) {
                    itr.remove();
                    entities.remove(itr);
                }
            }

            return entities;
        } else {
            return null;
        }
    }

    public List<Comment> recentCommentList(int count, int dateDuration) {
        if (count <= 0) {
//            log.info("Count is below 0. Please input value above 1");
            return null;
        }

        if (dateDuration < 0) {
//            log.info("Date duration is below 0. Please input value above 0");
            return null;
        }

        Optional<List<Comment>> items = cmtRepository.getRecentCommentsByCountAndDateDuration(count, dateDuration);

        return items.orElse(null);
    }

    public List<Comment> hotCommentList(int count, int dateDuration, int minLikeCount) {
        if (count <= 0) {
//            log.info("Count is below 0. Please input value above 1");
            return null;
        }

        if (dateDuration < 0) {
//            log.info("Date duration is below 0. Please input value above 1");
            return null;
        }

        if (minLikeCount <= 0) {
//            log.info("minLikeCount Should above at least 1. Change minLike Count to 1");
            minLikeCount = 1;
        }

        Optional<List<Comment>> items = cmtRepository.getHotCommentsByCountAndDateDuration(count, dateDuration, minLikeCount);

        return items.orElse(null);
    }

    public Integer getCommentCurrentPageNum(long cmtNo) {
        if (cmtNo < 0) {
            return null;
        }

        Comment entity = select(cmtNo);

        if (entity == null) {
            log.info("Given cmtNo is not valid. No Matching Comment");
            return null;
        }

        if (entity.getLocation() == null) {
            log.info("Location is not valid. It may Deleted");
            return null;
        }

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .locNo(entity.getLocation().getLoc_no())
                .size(MAX_COM_COUNT)
                .build();
        PageResultDTO<CommentDTO, Comment> pageResultDTO = getCmtPage(pageRequestDTO,
                CommentSortType.IDX_DES, CommentSearchType.All);

        int pagEnd = pageResultDTO.getEnd();
        int result = 0;

        Loop1 :
        for (int i = 1; i <= pagEnd; i++) {
            if (i != 1) {
                pageRequestDTO.setPage(i);
                pageResultDTO = getCmtPage(pageRequestDTO,
                        CommentSortType.IDX_DES, CommentSearchType.All);
            }

            for (int j = 0; j < pageResultDTO.getDtoList().size(); j++) {
                if (cmtNo == pageResultDTO.getDtoList().get(j).getCmtNo()){
                    result = pageResultDTO.getPage();
                    break Loop1;
                }
            }
        }

        return result;
    }

    public List<Comment> findAllByUserNo(Long userNo){
        List<Comment> item = cmtRepository.findAllByUser_no(userNo);
        return item;
    }

    public List<Comment> findAllByLocNo(Long locNo){
        Optional<List<Comment>> items = cmtRepository.findAllByLoc_no(locNo);
        return items.orElse(null);
    }
}
