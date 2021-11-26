package com.project.love_data.repository;

import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long>,
        QuerydslPredicateExecutor<Comment> {
    @Query(value = "SELECT * FROM comment c WHERE c.location_loc_no = :loc_no", nativeQuery = true)
    Optional<List<Comment>> findAllByLoc_no(@Param("loc_no") Long locNo);

    @Query(value = "SELECT * FROM comment c WHERE c.user_user_no = :user_no", nativeQuery = true)
    List<Comment> findAllByUser_no(@Param("user_no") Long userNo);

    @Query(value = "SELECT * FROM comment WHERE location_loc_no = :loc_no AND cmt_idx = :cmt_idx", nativeQuery = true)
    Optional<Comment> findByLoc_noAndCmtIdx(@Param("loc_no") Long locNo, @Param("cmt_idx") Long cmtIdx);

    @Query(value = "SELECT * FROM comment c WHERE c.cmt_uuid = :cmt_uuid", nativeQuery = true)
    Optional<Comment> findByCmt_uuid(@Param("cmt_uuid") String cmtUuid);

    @Query(value = "SELECT * FROM comment c WHERE c.cmt_no = :cmt_no", nativeQuery = true)
    Optional<Comment> findByCmt_no(@Param("cmt_no") Long cmtNo);

    @Query(value = "SELECT max(cmt_idx) FROM comment c WHERE c.location_loc_no = :loc_no", nativeQuery = true)
    Long findMaxIdxByLoc_no(@Param("loc_no") Long locNo);

    // 베스트 댓글로 불러오는 갯수는 최대 3개이고, 최소 추천수 3개 이상이어야함, 삭제된 댓글은 포함 X
    @Query(value = "SELECT * FROM comment c WHERE c.location_loc_no = :loc_no AND c.is_deleted = false AND c.likecount > 3 ORDER BY c.likecount desc limit 3", nativeQuery = true)
    Optional<List<Comment>> getBestComment(@Param("loc_no") Long locNo);

    // 어드민 대쉬보드 최근 댓글
    @Query(value = "SELECT * FROM comment c WHERE datediff(now(), c.regdate) <= :date ORDER BY c.regdate desc limit :count", nativeQuery = true)
    Optional<List<Comment>> getRecentCommentsByCountAndDateDuration(@Param("count")int count, @Param("date") int dateDuration);

    // 어드민 대쉬보드 인기 댓글
    @Query(value = "SELECT * FROM comment c WHERE datediff(now(), c.regdate) <= :date AND c.likecount >= :min_like ORDER BY c.likecount desc limit :count", nativeQuery = true)
    Optional<List<Comment>> getHotCommentsByCountAndDateDuration(@Param("count")int count, @Param("date") int dateDuration, @Param("min_like") int minLikeCount);

    @Modifying
    @Query(value = "DELETE FROM comment WHERE cmt_no = :cmt_no", nativeQuery = true)
    @Transactional
    void deleteByCmt_no(@Param("cmt_no") String cmt_no);

    @Modifying
    @Query(value = "DELETE FROM comment WHERE cmt_uuid = :cmt_uuid", nativeQuery = true)
    @Transactional
    void deleteByCmt_uuid(@Param("cmt_uuid") String cmtUuid);
}
