package com.project.love_data.repository;

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
    @Query(value = "SELECT * FROM Review r WHERE r.cor_no = :cor_no", nativeQuery = true)
    List<Review> findAllByCor_no(@Param("cor_no") Long locNo);

    @Query(value = "SELECT * FROM Review r WHERE r.user_no = :user_no", nativeQuery = true)
    List<Review> findAllByUser_no(@Param("user_no") Long userNo);

    @Query(value = "SELECT * FROM Review r WHERE r.rev_uuid = :rev_uuid", nativeQuery = true)
    Optional<Review> findByRev_uuid(@Param("rev_uuid") String revUuid);

    @Query(value = "SELECT * FROM Review r WHERE r.rev_no = :rev_no", nativeQuery = true)
    Optional<Review> findByRev_no(@Param("rev_no") Long revNo);

    @Modifying
    @Query(value = "DELETE FROM Review WHERE rev_no = :rev_no", nativeQuery = true)
    @Transactional
    void deleteByRev_no(@Param("rev_no") String rev_no);

    @Modifying
    @Query(value = "DELETE FROM Review WHERE rev_uuid = :rev_uuid", nativeQuery = true)
    @Transactional
    void deleteByRev_uuid(@Param("rev_uuid") String revUuid);
}
