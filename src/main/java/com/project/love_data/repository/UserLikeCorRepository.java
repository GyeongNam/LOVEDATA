package com.project.love_data.repository;

import com.project.love_data.model.service.UserLikeCor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserLikeCorRepository extends JpaRepository<UserLikeCor, Long> {
    @Query(value = "select * from user_like_cor ull where  ull.cor_no = :cor_no", nativeQuery = true)
    public Optional<List <UserLikeCor>> findByCor_no(@Param("cor_no") Long locNo);

    @Query(value = "select * from user_like_cor ull where  ull.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserLikeCor>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_like_cor ull where  ull.cor_no = :cor_no AND ull.user_no = :user_no", nativeQuery = true)
    public Optional<UserLikeCor> findByCor_noAndUser_no(@Param("cor_no") Long locNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_like_cor ull where  ull.uuid Like :uuid", nativeQuery = true)
    public Optional<UserLikeCor> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_like_cor WHERE user_no = :user_no AND cor_no = :cor_no", nativeQuery = true)
    @Transactional
    void deleteByCor_noAndUser_no(@Param("cor_no") Long cor_no, @Param("user_no") Long user_no);
}
