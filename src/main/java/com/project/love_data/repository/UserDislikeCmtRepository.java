package com.project.love_data.repository;

import com.project.love_data.model.service.UserDislikeCmt;
import com.project.love_data.model.service.UserLikeCmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserDislikeCmtRepository extends JpaRepository<UserDislikeCmt, Long> {
    @Query(value = "select * from user_dislike_cmt udc where  udc.cmt_no = :cmt_no", nativeQuery = true)
    public Optional<List <UserDislikeCmt>> findByCmt_no(@Param("cmt_no") Long cmtNo);

    @Query(value = "select * from user_dislike_cmt udc where  udc.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserDislikeCmt>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_dislike_cmt udc where  udc.cmt_no = :cmt_no AND udc.user_no = :user_no", nativeQuery = true)
    public Optional<UserDislikeCmt> findByCmt_noAndUser_no(@Param("cmt_no") Long cmtNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_dislike_cmt udc where  udc.uuid Like :uuid", nativeQuery = true)
    public Optional<UserDislikeCmt> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_dislike_cmt WHERE user_no = :user_no AND cmt_no = :cmt_no", nativeQuery = true)
    @Transactional
    void deleteByCmt_noAndUser_no(@Param("cmt_no") Long cmt_no, @Param("user_no") Long user_no);
}
