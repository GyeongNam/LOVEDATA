package com.project.love_data.repository;

import com.project.love_data.model.service.UserRecentCor;
import com.project.love_data.model.service.UserRecentLoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserRecentCorRepository extends JpaRepository<UserRecentCor, Long> {
    @Query(value = "select distinct * from user_recent_cor url where  url.cor_no = :cor_no", nativeQuery = true)
    public Optional<List <UserRecentCor>> findByCor_no(@Param("cor_no") Long locNo);

    @Query(value = "select distinct * from user_recent_cor url where  url.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserRecentCor>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_recent_cor url where  url.cor_no = :cor_no AND url.user_no = :user_no", nativeQuery = true)
    public Optional<UserRecentCor> findByCor_noAndUser_no(@Param("cor_no") Long locNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_recent_cor url where  url.uuid Like :uuid", nativeQuery = true)
    public Optional<UserRecentCor> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_recent_cor WHERE user_no = :user_no AND cor_no = :cor_no", nativeQuery = true)
    @Transactional
    void deleteByCor_noAndUser_no(@Param("cor_no") Long cor_no, @Param("user_no") Long user_no);

    @Query(value = "select * from user_recent_cor where user_no = :userNB ORDER BY user_recent_Cor_no desc limit 8", nativeQuery = true)
    Optional<List <UserRecentCor>> findUser_no(@Param("userNB") Long userNo);
}
