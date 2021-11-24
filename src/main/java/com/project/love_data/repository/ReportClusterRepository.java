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
    @Query(value = "SELECT * FROM report_cluster rc WHERE rc.post_no = :post_no AND rc.post_type = :post_type", nativeQuery = true)
    Optional<ReportCluster> findByPostNoAndPostType(@Param("post_no") Long postNo, @Param("post_type") String postType);

    @Query(value = "SELECT * FROM report_cluster rc WHERE rc.rc_uuid = :rc_uuid", nativeQuery = true)
    Optional<ReportCluster> findByRcUuid(@Param("rc_uuid") String rcUuid);

    @Query(value = "SELECT * FROM report_cluster rc WHERE  rc.post_type = :post_type", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByPostType(@Param("post_type") String postType);

    @Query(value = "SELECT * FROM report_cluster rc WHERE  rc.rc_status = :rc_status", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByRcStatus(@Param("rc_status") String rcStatus);

    @Query(value = "SELECT * FROM report_cluster rc WHERE rc.complete = :rc_complete", nativeQuery = true)
    Optional<List<ReportCluster>> getAllByRcComplete(@Param("rc_complete") boolean rcComplete);

    @Query(value = "SELECT * FROM report_cluster rc WHERE rc.rc_user_no = :rc_user_no", nativeQuery = true)
    Optional<List<ReportCluster>> findAllByRcUserNo(@Param("rc_user_no") Long rcUserNo);

    @Modifying
    @Query(value = "DELETE FROM report_cluster rc WHERE rc_no = :rc_no", nativeQuery = true)
    @Transactional
    void deleteByRcNo(@Param("rc_no") Long rcNo);

    @Modifying
    @Query(value = "DELETE FROM report_cluster rc WHERE rc_uuid = :rc_uuid", nativeQuery = true)
    @Transactional
    void deleteByRcUuid(@Param("rc_uuid") String rcUuid);
}
