package com.project.love_data.repository;

import com.project.love_data.model.service.Comment;
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
    List<Comment> findAllByLoc_no(@Param("loc_no") Long locNo);

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

    @Modifying
    @Query(value = "DELETE FROM comment WHERE cmt_no = :cmt_no", nativeQuery = true)
    @Transactional
    void deleteByCmt_no(@Param("cmt_no") String cmt_no);

    @Modifying
    @Query(value = "DELETE FROM comment WHERE cmt_uuid = :cmt_uuid", nativeQuery = true)
    @Transactional
    void deleteByCmt_uuid(@Param("cmt_uuid") String cmtUuid);
}
