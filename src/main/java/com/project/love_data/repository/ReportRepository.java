package com.project.love_data.repository;

import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface ReportRepository extends JpaRepository<Report, Long>,
        QuerydslPredicateExecutor<Report> {
    @Query(value = "SELECT * FROM report r WHERE r.rc_no = :rc_no", nativeQuery = true)
    Optional<List<Report>> findAllByRcNo(@Param("rc_no") Long rcNo);

    @Query(value = "SELECT * FROM report r WHERE r.user_no = :user_no", nativeQuery = true)
    Optional<List<Report>> findAllByUserNo(@Param("user_no") Long userNo);

    @Query(value = "SELECT * FROM report r WHERE r.rep_uuid = :rep_uuid", nativeQuery = true)
    Optional<Report> findByRepUuid(@Param("rep_uuid") String repUuid);

    @Query(value = "SELECT * FROM report r WHERE  r.post_type = :post_type", nativeQuery = true)
    Optional<List<Report>> findAllByPostType(@Param("post_type") String postType);

    @Query(value = "SELECT * FROM report r WHERE  r.rep_type = :rep_type", nativeQuery = true)
    Optional<List<Report>> findAllByRepType(@Param("rep_type") String repType);

    @Query(value = "SELECT r.rep_type FROM report r WHERE  r.rc_no = :rc_no", nativeQuery = true)
    Optional<List<String>> findRepTypesByRcNo(@Param("rc_no") Long rcNo);

    @Query(value = "SELECT * FROM report r WHERE r.rc_no = :rc_no AND r.is_complete = :complete", nativeQuery = true)
    Optional<List<Report>> findAllByRcNoAndComplete(@Param("rc_no") Long rcNo, @Param("complete") boolean complete);

    // 어드민 대쉬보드 최근 신고
    @Query(value = "SELECT * FROM report r WHERE datediff(now(), r.regdate) <= :date ORDER BY r.regdate", nativeQuery = true)
    Optional<List<Report>> getRecentReportsByDateDuration(@Param("date") int dateDuration);

    @Modifying
    @Query(value = "DELETE FROM report WHERE rep_no = :rep_no", nativeQuery = true)
    @Transactional
    void deleteByRepNo(@Param("rep_no") Long repNo);

    @Modifying
    @Query(value = "DELETE FROM report WHERE rep_uuid = :rep_uuid", nativeQuery = true)
    @Transactional
    void deleteByRepUuid(@Param("rep_uuid") String repUuid);
}
