package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.dto.ReviewDTO;
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

@Service
@AllArgsConstructor
@Log4j2
public class ReviewService {
    private final ReviewRepository repository;
    private final CourseRepository corRepository;
    private final UserRepository userRepository;

    public Review dtoToEntity(ReviewDTO dto) {
        Review entity = Review.builder()
                .corNo(dto.getCorNo())
                .is_deleted(dto.is_deleted())
                .rev_point(dto.getRev_point())
                .revContent(dto.getRevContent())
                .revIdx(dto.getRevNo())
                .revUuid(dto.getRevUuid())
                .user_no(dto.getUserNo())
                .revNo(dto.getRevNo())
                .user_name(dto.getUserName())
                .rev_like(dto.getRev_like())
                .rev_dislike(dto.getRev_dislike())
                .view_count(dto.getView_count())
                .reported_count(dto.getReported_count())
                .sc_loc(dto.getSc_loc())
                .sc_move(dto.getSc_move())
                .sc_revisit(dto.getSc_revisit())
                .sc_time(dto.getSc_time())
                .build();

        return entity;
    }

    public ReviewDTO entityToDto(Review entity) {
        ReviewDTO dto = ReviewDTO.builder()
                .corNo(entity.getCorNo())
                .is_deleted(entity.is_deleted())
                .rev_point(entity.getRev_point())
                .revContent(entity.getRevContent())
                .revIdx(entity.getRevNo())
                .revUuid(entity.getRevUuid())
                .userNo(entity.getUser_no())
                .revNo(entity.getRevNo())
                .reported_count(entity.getReported_count())
                .userName(entity.getUser_name())
                .rev_like(entity.getRev_like())
                .rev_dislike(entity.getRev_dislike())
                .view_count(entity.getView_count())
                .sc_loc(entity.getSc_loc())
                .sc_move(entity.getSc_move())
                .sc_revisit(entity.getSc_revisit())
                .sc_time(entity.getSc_time())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    public Map<String, Integer> getScoreMap(int sc_loc, int sc_time, int sc_move, int sc_revisit) {
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

    public Review createRevEntity(Long corNo, Long userNo, String revContent, float revPoint, Map<String, Integer> scoreMap) {
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
                index = reviewList.get().size();
            }

            Review entity = Review.builder()
                    .corNo(corNo)
                    .user_no(userNo)
                    .revIdx(index)
                    .revContent(revContent)
                    .rev_point(revPoint)
                    .sc_time(scoreMap.get("sc_time"))
                    .sc_loc(scoreMap.get("sc_loc"))
                    .sc_move(scoreMap.get("sc_move"))
                    .sc_revisit(scoreMap.get("sc_revisit"))
                    .user_name(user.get().getUser_name())
                    .build();

            return entity;
        }

        return null;
    }

    public PageResultDTO<ReviewDTO, Review> getRevPage(PageRequestDTO requestDTO) {
        return getRevPage(requestDTO, SortingOrder.ASC);
    }

    public PageResultDTO<ReviewDTO, Review> getRevPage(PageRequestDTO requestDTO,
                                                         SortingOrder sortingOrder) {
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

        booleanBuilder = getRevPage_Cor(requestDTO);

        Page<Review> result = null;
        if (booleanBuilder.hasValue()) {
            result = repository.findAll(booleanBuilder, pageable);
        } else {
            result = repository.findAll(pageable);
        }

        Function<Review, ReviewDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public BooleanBuilder getRevPage_Cor(PageRequestDTO requestDTO) {
        Long corNo = requestDTO.getCorNo();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QReview qReivew = QReview.review;

        BooleanExpression expression = qReivew.corNo.eq(corNo);

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

    public void delete(Long revNo) {
        Review rev = select(revNo);

        if (!rev.is_deleted()) {
            disable(rev);
        }
    }

    public void delete(String uuid) {
        Review rev = select(uuid);

        if (!rev.is_deleted()) {
            disable(rev);
        }
    }

    public void permaDelete(Review rev) {
        // Todo 기존에 있던 리뷰 index 하나씩 앞으로 당기기
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .size(Integer.MAX_VALUE)
                .sortingOrder(SortingOrder.ASC).build();
        PageResultDTO<ReviewDTO, Review> revResultList = getRevPage(requestDTO, SortingOrder.ASC);

        repository.deleteByRev_no(rev.getRevNo());
    }

    public Review select(String uuid) {
        Optional<Review> item = repository.findByRev_uuid(uuid);
        return item.orElse(null);
    }

    public Review select(Long revNo) {
        Optional<Review> item = repository.findByRev_no(revNo);
        return item.orElse(null);
    }
}
