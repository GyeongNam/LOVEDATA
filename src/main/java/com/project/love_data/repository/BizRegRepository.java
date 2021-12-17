package com.project.love_data.repository;

import com.project.love_data.model.service.BizReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface BizRegRepository extends JpaRepository<BizReg, Long>,
        QuerydslPredicateExecutor<BizReg> {
    @Query(value = "SELECT * FROM bizreg br WHERE br.uuid = :uuid", nativeQuery = true)
    Optional<BizReg> findBizRegByUuid(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM bizreg br WHERE br.user_no = :user_no", nativeQuery = true)
    Optional<List<BizReg>> findAllByUserNo(@Param("user_no") Long userNo);

    @Query(value = "SELECT * FROM bizreg br ORDER BY br.certified desc", nativeQuery = true)
    Optional<List<BizReg>> findAllByUserAll();

    @Query(value = "SELECT * FROM bizreg br WHERE br.user_no = :user_no AND br.deleted = :deleted", nativeQuery = true)
    Optional<List<BizReg>> findAllByUserNoAndDeleted(@Param("user_no") Long userNo, @Param("deleted") Boolean deleted);

    @Query(value = "SELECT * FROM bizreg br WHERE br.certified = :certified AND br.deleted = false", nativeQuery = true)
    Optional<List<BizReg>> findAllLiveByCertified(@Param("certified") Boolean certified);

    @Query(value = "SELECT * FROM bizreg br WHERE br.certified = :certified AND br.deleted = :deleted", nativeQuery = true)
    Optional<List<BizReg>> findAllByCertifiedAndDeleted(@Param("certified") Boolean certified, @Param("deleted") Boolean deleted);

    @Modifying
    @Query(value = "DELETE FROM bizreg WHERE br_no = :br_no", nativeQuery = true)
    @Transactional
    void deleteByBrNo(@Param("br_no") Long brNo);

    @Modifying
    @Query(value = "DELETE FROM bizreg WHERE uuid = :uuid", nativeQuery = true)
    @Transactional
    void deleteByUuid(@Param("uuid") String uuid);
}
