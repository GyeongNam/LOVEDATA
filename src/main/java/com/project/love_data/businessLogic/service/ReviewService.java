package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.dto.ReviewDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.CourseRepository;
import com.project.love_data.repository.ReviewRepository;
import com.project.love_data.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.project.love_data.util.ConstantValues.MAX_COM_COUNT;

@Service
@AllArgsConstructor
@Log4j2
public class ReviewService {
    private final ReviewRepository repository;
    private final CourseRepository corRepository;
    private final UserRepository userRepository;
    private final ReviewImageService revImgService;
    private final DeletedImageInfoService deletedImageInfoService;

    public Review dtoToEntity(ReviewDTO dto) {
        Review entity = Review.builder()
                .corNo(dto.getCorNo())
                .is_deleted(dto.is_deleted())
                .sc_total(dto.getSc_total())
                .revContent(dto.getRevContent())
                .revIdx(dto.getRevNo())
                .revUuid(dto.getRevUuid())
                .user_no(dto.getUserNo())
                .revNo(dto.getRevNo())
                .user_nickname(dto.getUserNickname())
                .rev_like(dto.getRev_like())
                .rev_dislike(dto.getRev_dislike())
                .is_reported(dto.is_reported())
                .sc_loc(dto.getSc_loc())
                .sc_move(dto.getSc_move())
                .sc_revisit(dto.getSc_revisit())
                .sc_time(dto.getSc_time())
                .is_modified(dto.isModified())
                .build();

        return entity;
    }

    public ReviewDTO entityToDto(Review entity) {
        ReviewDTO dto = ReviewDTO.builder()
                .corNo(entity.getCorNo())
                .is_deleted(entity.is_deleted())
                .sc_total(entity.getSc_total())
                .revContent(entity.getRevContent())
                .revIdx(entity.getRevNo())
                .revUuid(entity.getRevUuid())
                .userNo(entity.getUser_no())
                .revNo(entity.getRevNo())
                .is_reported(entity.is_reported())
                .userNickname(entity.getUser_nickname())
                .rev_like(entity.getRev_like())
                .rev_dislike(entity.getRev_dislike())
                .sc_loc(entity.getSc_loc())
                .sc_move(entity.getSc_move())
                .sc_revisit(entity.getSc_revisit())
                .sc_time(entity.getSc_time())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .modified(entity.is_modified())
                .build();

        return dto;
    }

    public Map<String, Integer> getScoreMap(int sc_loc, int sc_move, int sc_time, int sc_revisit) {
        if (sc_loc < 0 || sc_loc > 5) {
            log.warn("장소 추천 점수는 0 ~ 5점 사이입니다.");
            return null;
        }

        if (sc_time < 0 || sc_time > 5) {
            log.warn("시간 점수는 0 ~ 5점 사이입니다.");
            return null;
        }

        if (sc_move < 0 || sc_move > 5) {
            log.warn("이동 점수는 0 ~ 5점 사이입니다.");
            return null;
        }

        if (sc_revisit < 0 || sc_revisit > 5) {
            log.warn("재방문 점수는 0 ~ 5점 사이입니다.");
            return null;
        }

        Map<String, Integer> map = new HashMap<>();

        map.put("sc_loc", sc_loc);
        map.put("sc_time", sc_time);
        map.put("sc_move", sc_move);
        map.put("sc_revisit", sc_revisit);

        return map;
    }

    public Review createRevEntity(Long corNo, Long userNo, String revContent, Map<String, Integer> scoreMap, float sc_total) {
        Optional<Course> cor = corRepository.findCorByCor_no(corNo);
        Optional<User> user = userRepository.findById(userNo);

        if (cor.isPresent() && user.isPresent()) {
//            Comment entity = Comment.builder()
//                    .location(loc.get())
//                    .cmtUuid(UUID.randomUUID().toString())
//                    .user(user.get())
//                    .cmtIdx((long) loc.get().getCmtSet().size())
//                    .cmtContent(cmtContent).build();

            Optional<List<Review>> reviewList = repository.findAllByCor_no(corNo);

            long index = 0;

            if (reviewList.isPresent()) {
                List<Review> temp = reviewList.get();
                if (!temp.isEmpty()) {
                    index = temp.get(temp.size()-1).getRevIdx() + 1;
                }
            }

            Review entity = Review.builder()
                    .corNo(corNo)
                    .user_no(userNo)
                    .revIdx(index)
                    .revContent(revContent)
                    .sc_total(sc_total)
                    .sc_time(scoreMap.get("sc_time"))
                    .sc_loc(scoreMap.get("sc_loc"))
                    .sc_move(scoreMap.get("sc_move"))
                    .sc_revisit(scoreMap.get("sc_revisit"))
                    .user_nickname(user.get().getUser_nic())
                    .build();

            return entity;
        }

        return null;
    }

    public PageResultDTO<ReviewDTO, Review> getRevPage(PageRequestDTO requestDTO) {
        return getRevPage(requestDTO, SortingOrder.DES, ReviewSearchType.Live);
    }

    public PageResultDTO<ReviewDTO, Review> getRevPage(PageRequestDTO requestDTO,
                                                         SortingOrder sortingOrder, ReviewSearchType reviewSearchType) {
        Pageable pageable;
        switch (sortingOrder) {
            case DES:
                pageable = requestDTO.getPageable(Sort.by("revIdx").descending());
                break;
            case ASC:
            default:
                pageable = requestDTO.getPageable(Sort.by("revIdx").ascending());
                break;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder = getRevPage_Cor(requestDTO, reviewSearchType);

        Page<Review> result = null;
        if (booleanBuilder.hasValue()) {
            result = repository.findAll(booleanBuilder, pageable);
        } else {
            result = repository.findAll(pageable);
        }

        Function<Review, ReviewDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public BooleanBuilder getRevPage_Cor(PageRequestDTO requestDTO, ReviewSearchType searchType) {
        Long corNo = requestDTO.getCorNo();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QReview qReview = QReview.review;

        BooleanExpression expression = qReview.corNo.eq(corNo);

        switch (searchType) {
            case ALL:
                break;
            case Deleted:
                booleanBuilder.and(qReview.is_deleted.eq(true));
                break;
            case Live:
            default:
                booleanBuilder.and(qReview.is_deleted.eq(false));
                break;
        }

        booleanBuilder.and(expression);

        return booleanBuilder;
    }

    public Review register(Review review) {
        return repository.save(review);
    }

    public Review update(Review review) {
        return repository.save(review);
    }

    private Review disable(Review review) {
        review.set_deleted(true);

        return update(review);
    }

    private Review enable(Review review) {
        review.set_deleted(false);

        return update(review);
    }

    public boolean delete(Long revNo) {
        Review rev = select(revNo);

        if (!rev.is_deleted()) {
//            List<ReviewImage> list = revImgService.getAllLiveImageByRevNo(revNo);
//            List<ReviewImage> finishedList = new ArrayList<>();
//            boolean isRevImageDeleted = true;
//
//            for (ReviewImage reviewImage : list) {
//                revImgService.delete(reviewImage.getImg_no());
//                if (!revImgService.getImage(reviewImage.getImg_no()).is_deleted()) {
//                    for (ReviewImage image : finishedList) {
//                        revImgService.rollback(image.getImg_no());
//                    }
//                    isRevImageDeleted = false;
//                }
//            }
//
//            if (isRevImageDeleted) {
//                disable(rev);
//                return true;
//            }

            List<ReviewImage> imageList = revImgService.getAllLiveImageByRevNo(revNo);
            for (ReviewImage image : imageList) {
                revImgService.delete(image.getImg_no());
            }

            disable(rev);
            return true;
        }

        return false;
    }

    public boolean delete(String uuid) {
        Review rev = select(uuid);

        if (!rev.is_deleted()) {
//            List<ReviewImage> list = revImgService.getAllLiveImageByRevNo(rev.getRevNo());
//            List<ReviewImage> finishedList = new ArrayList<>();
//            boolean isRevImageDeleted = true;
//
//            for (ReviewImage reviewImage : list) {
//                revImgService.delete(reviewImage.getImg_no());
//                if (!revImgService.getImage(reviewImage.getImg_no()).is_deleted()) {
//                    for (ReviewImage image : finishedList) {
//                        revImgService.rollback(image.getImg_no());
//                    }
//                    isRevImageDeleted = false;
//                }
//            }
//
//            if (isRevImageDeleted) {
//                disable(rev);
//                return true;
//            }
            Review item = select(uuid);

            List<ReviewImage> imageList = revImgService.getAllLiveImageByRevNo(item.getRevNo());
            for (ReviewImage image : imageList) {
                revImgService.delete(image.getImg_no());
            }

            disable(rev);
            return true;
        }

        return false;
    }

    public boolean permaDeleteReviewsByCorNo(Long corNo){
        if (corNo == null) {
            return false;
        }

        if (corNo < 0) {
            return false;
        }

        List<Review> items = findAllByCor_no(corNo);

        if (items == null) {
            return false;
        }

        for (Review review : items) {
            permaDelete(review);
        }

        items = findAllByCor_no(corNo);

        if (items != null) {
            return false;
        }

        return true;
    }

    public void permaDelete(Review rev) {
        if(rev == null) {
            return;
        }

        List<ReviewImage> list = revImgService.getAllLiveImageByRevNo(rev.getRevNo());

        if (list != null) {
            for (ReviewImage reviewImage : list) {
                revImgService.delete(reviewImage.getImg_no());
                if (revImgService.getImage(reviewImage.getImg_no()).is_deleted()) {
                    deletedImageInfoService.register(reviewImage.getImg_no(), "REV_IMG",
                            reviewImage.getUser_no(), "REV^" + reviewImage.getImg_uuid());
                    revImgService.permaDelete(reviewImage.getImg_uuid());
                }
            }
        }

        repository.deleteByRev_no(rev.getRevNo());
    }

    public boolean rollback(Long revNo) {
        Review rev = select(revNo);

        if (rev.is_deleted()) {
//            List<ReviewImage> list = revImgService.getLastDeletedImageByRevNo(revNo);
//            List<ReviewImage> finishedList = new ArrayList<>();
//            boolean isRevImageRollback = true;
//
//            for (ReviewImage reviewImage : list) {
//                revImgService.rollback(reviewImage.getImg_no());
//                if (!revImgService.getImage(reviewImage.getImg_no()).is_deleted()) {
//                    for (ReviewImage image : finishedList) {
//                        revImgService.delete(image.getImg_no());
//                    }
//                    isRevImageRollback = false;
//                }
//            }
//
//            if (isRevImageRollback) {
//                enable(rev);
//                return true;
//            }

            List<ReviewImage> imageList = revImgService.getLastDeletedImageByRevNo(revNo);
            for (ReviewImage image : imageList) {
                revImgService.rollback(image.getImg_no());
            }

            enable(rev);
            return true;
        }

        return false;
    }

    public Review select(String uuid) {
        Optional<Review> item = repository.findByRev_uuid(uuid);
        return item.orElse(null);
    }

    public Review select(Long revNo) {
        Optional<Review> item = repository.findByRev_no(revNo);
        return item.orElse(null);
    }

    public ReviewDTO selectDTO(Long revNo) {
        Optional<Review> item = repository.findByRev_no(revNo);

        if (!item.isPresent()) {
            return null;
        }

        return entityToDto(item.get());
    }

    public List<Review> getReviewsByCorNo(Long corNo) {
        Optional<List<Review>> items = repository.findAllByCor_no(corNo);
        return items.orElse(null);
    }

    public List<Review> getLiveReviewsByCorNo(Long corNo) {
        Optional<List<Review>> items = repository.findLiveByCor_no(corNo);
        return items.orElse(null);
    }

    public List<Review> findAllByUser_no(Long userNo) {
        Optional<List<Review>>  reviewList = repository.findAllByUser_no(userNo);

        return reviewList.orElse(null);
    }

    public List<Review> findAllByCor_no(Long corNo) {
        Optional<List<Review>> reviewList = repository.findAllByCor_no(corNo);

        return reviewList.orElse(null);
    }

    public Boolean incLikeCount (Long rev_no) {
        Review rev = select(rev_no);

        if (rev == null) {
            return false;
        }

        rev.setRev_like(rev.getRev_like() + 1);

        update(rev);

        return true;
    }

    public boolean decLikeCount (Long rev_no) {
        Review rev = select(rev_no);

        if (rev == null) {
            return false;
        }

        rev.setRev_like(rev.getRev_like() - 1);

        update(rev);

        return true;
    }

    public boolean incDislikeCount (Long rev_no) {
        Review rev = select(rev_no);

        if (rev == null) {
            return false;
        }

        rev.setRev_dislike(rev.getRev_dislike() + 1);

        update(rev);

        return true;
    }

    public boolean decDislikeCount (Long rev_no) {
        Review rev = select(rev_no);

        if (rev == null) {
            return false;
        }

        rev.setRev_dislike(rev.getRev_dislike() - 1);

        update(rev);

        return true;
    }

    public List<Review> getBestReview(Long corNo) {
        Optional<List<Review>> items = repository.getBestReview(corNo);

        if (items.isPresent()) {
            List<Review> entities = items.get();
            Iterator<Review> itr = entities.iterator();
            while (itr.hasNext()) {
                Optional<User> user = userRepository.findById(itr.next().getUser_no());
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

    public List<Review> recentReviewList(int count, int dateDuration) {
        if (count <= 0) {
//            log.info("Count is below 0. Please input value above 1");
            return null;
        }

        if (dateDuration < 0) {
//            log.info("Date duration is below 0. Please input value above 0");
            return null;
        }

        Optional<List<Review>> items = repository.getRecentReviewsByCountAndDateDuration(count, dateDuration);

        return items.orElse(null);
    }

    public List<Review> hotReviewList(int count, int dateDuration, int minLikeCount) {
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

        Optional<List<Review>> items = repository.getHotReviewsByCountAndDateDuration(count, dateDuration, minLikeCount);

        return items.orElse(null);
    }

    public Integer getReviewCurrentPageNum(long revNo) {
        if (revNo < 0) {
            return null;
        }

        Review entity = select(revNo);

        if (entity == null) {
            log.info("Given revNo is not valid. No Matching Review");
            return null;
        }

        Optional<Course> corEntity = corRepository.findById(entity.getCorNo());

        if (!corEntity.isPresent()) {
            log.info("Course is not valid. It may Deleted");
            return null;
        }

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .corNo(corEntity.get().getCor_no())
                .size(MAX_COM_COUNT)
                .build();
        PageResultDTO<ReviewDTO, Review> pageResultDTO = getRevPage(pageRequestDTO, SortingOrder.DES,
                ReviewSearchType.ALL);

        int pagEnd = pageResultDTO.getEnd();
        int result = 0;

        Loop1 :
        for (int i = 1; i <= pagEnd; i++) {
            if (i != 1) {
                pageRequestDTO.setPage(i);
                pageResultDTO = getRevPage(pageRequestDTO, SortingOrder.DES,
                        ReviewSearchType.ALL);
            }

            for (int j = 0; j < pageResultDTO.getDtoList().size(); j++) {
                if (revNo == pageResultDTO.getDtoList().get(j).getRevNo()){
                    result = pageResultDTO.getPage();
                    break Loop1;
                }
            }
        }

        return result;
    }
}
