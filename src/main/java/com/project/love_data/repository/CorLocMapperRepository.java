package com.project.love_data.repository;

import com.project.love_data.model.service.CorLocMapper;
import com.project.love_data.model.service.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface CorLocMapperRepository extends JpaRepository<CorLocMapper, Long> {
    @Query(value = "SELECT * from cor_loc_mapper clm WHERE  clm.loc_no = :loc_no", nativeQuery = true)
    public Optional<List<CorLocMapper>> findAllByLoc_no(@Param("loc_no") Long locNo);

    @Query(value = "SELECT * from cor_loc_mapper clm WHERE  clm.cor_no = :cor_no", nativeQuery = true)
    public Optional<List<CorLocMapper>> findAllByCor_no(@Param("cor_no") Long corNo);

    @Query(value = "SELECT * from cor_loc_mapper clm WHERE  clm.cor_no = :cor_no AND clm.loc_no = :loc_no", nativeQuery = true)
    public Optional<CorLocMapper> findByCor_noAndLoc_no(@Param("cor_no") Long corNo, @Param("loc_no") Long locNo);

    @Query(value = "SELECT * FROM cor_loc_mapper clm WHERE clm.clm_uuid LIKE :uuid", nativeQuery = true)
    Optional<CorLocMapper> findByUUID(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM cor_loc_mapper WHERE clm_no = :clm_no", nativeQuery = true)
    @Transactional
    void deleteByClm_No(@Param("clm_no") Long clm_no);
}
