package com.project.love_data.repository;

import com.project.love_data.model.service.UserLikeCmt;
import com.project.love_data.model.service.UserLikeCmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserLikeCmtRepository extends JpaRepository<UserLikeCmt, Long> {
    @Query(value = "select * from user_like_cmt ulc where  ulc.cmt_no = :cmt_no", nativeQuery = true)
    public Optional<List <UserLikeCmt>> findByCmt_no(@Param("cmt_no") Long cmtNo);

    @Query(value = "select * from user_like_cmt ulc where  ulc.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserLikeCmt>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_like_cmt ulc where  ulc.cmt_no = :cmt_no AND ulc.user_no = :user_no", nativeQuery = true)
    public Optional<UserLikeCmt> findByCmt_noAndUser_no(@Param("cmt_no") Long cmtNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_like_cmt ulc where  ulc.uuid Like :uuid", nativeQuery = true)
    public Optional<UserLikeCmt> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_like_cmt WHERE user_no = :user_no AND cmt_no = :cmt_no", nativeQuery = true)
    @Transactional
    void deleteByCmt_noAndUser_no(@Param("cmt_no") Long cmt_no, @Param("user_no") Long user_no);
}
