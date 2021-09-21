package com.project.love_data.repository;

import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface ReviewRepository extends JpaRepository<Review, Long>,
        QuerydslPredicateExecutor<Review> {
    @Query(value = "SELECT * FROM review r WHERE r.cor_no = :cor_no", nativeQuery = true)
    Optional<List<Review>> findAllByCor_no(@Param("cor_no") Long corNo);

    @Query(value = "SELECT * FROM review r WHERE r.user_no = :user_no", nativeQuery = true)
    List<Review> findAllByUser_no(@Param("user_no") Long userNo);

    @Query(value = "SELECT * FROM review r WHERE r.rev_uuid = :rev_uuid", nativeQuery = true)
    Optional<Review> findByRev_uuid(@Param("rev_uuid") String revUuid);

    @Query(value = "SELECT * FROM review r WHERE r.rev_no = :rev_no", nativeQuery = true)
    Optional<Review> findByRev_no(@Param("rev_no") Long revNo);

    @Query(value = "SELECT * FROM review r WHERE r.cor_no = :cor_no AND r.is_deleted = false", nativeQuery = true)
    Optional<List<Review>> findLiveByCor_no(@Param("cor_no") Long corNo);

    // 베스트 리뷰로 불러오는 갯수는 최대 3개이고, 최소 추천수 3개 이상이어야함
    @Query(value = "SELECT * FROM review r WHERE r.cor_no = :cor_no AND r.rev_like > 3 ORDER BY r.rev_like desc limit 3", nativeQuery = true)
    Optional<List<Review>> getBestReview(@Param("cor_no") Long corNo);

    @Modifying
    @Query(value = "DELETE FROM review WHERE rev_no = :rev_no", nativeQuery = true)
    @Transactional
    void deleteByRev_no(@Param("rev_no") Long rev_no);

    @Modifying
    @Query(value = "DELETE FROM review WHERE rev_uuid = :rev_uuid", nativeQuery = true)
    @Transactional
    void deleteByRev_uuid(@Param("rev_uuid") String revUuid);
}
