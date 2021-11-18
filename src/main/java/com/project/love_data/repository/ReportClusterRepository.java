package com.project.love_data.repository;

import com.project.love_data.model.service.Report;
import com.project.love_data.model.service.ReportCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface ReportClusterRepository extends JpaRepository<ReportCluster, Long>,
        QuerydslPredicateExecutor<ReportCluster> {
    @Query(value = "SELECT * FROM report_cluster rc WHERE r.post_no = :post_no", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByPostNo(@Param("post_no") Long postNo);

    @Query(value = "SELECT * FROM report_cluster rc WHERE r.user_no = :user_no", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByUserNo(@Param("user_no") Long userNo);

    @Query(value = "SELECT * FROM report_cluster rc WHERE r.rep_uuid = :rep_uuid", nativeQuery = true)
    Optional<ReportCluster> findByRepUuid(@Param("rep_uuid") String repUuid);

    @Query(value = "SELECT * FROM report_cluster rc WHERE  r.post_type = :post_type", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByPostType(@Param("post_type") String postType);

    @Query(value = "SELECT * FROM report_cluster rc WHERE  r.rep_type = :rep_type", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByRepType(@Param("rep_type") String repType);

    @Modifying
    @Query(value = "DELETE FROM report_cluster rc WHERE rep_no = :rep_no", nativeQuery = true)
    @Transactional
    void deleteByRepNo(@Param("rep_no") Long repNo);

    @Modifying
    @Query(value = "DELETE FROM report_cluster rc WHERE rep_uuid = :rep_uuid", nativeQuery = true)
    @Transactional
    void deleteByRepUuid(@Param("rep_uuid") String repUuid);
}
