package com.project.love_data.repository;

import com.project.love_data.model.service.UserDislikeRev;
import com.project.love_data.model.service.UserDislikeRev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserDislikeRevRepository extends JpaRepository<UserDislikeRev, Long> {
    @Query(value = "select * from user_dislike_rev udr where  udr.rev_no = :rev_no", nativeQuery = true)
    public Optional<List <UserDislikeRev>> findByCor_no(@Param("rev_no") Long corNo);

    @Query(value = "select * from user_dislike_rev udr where  udr.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserDislikeRev>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_dislike_rev udr where  udr.rev_no = :rev_no AND udr.user_no = :user_no", nativeQuery = true)
    public Optional<UserDislikeRev> findByRev_noAndUser_no(@Param("rev_no") Long revNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_dislike_rev udr where  udr.uuid Like :uuid", nativeQuery = true)
    public Optional<UserDislikeRev> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_dislike_rev WHERE user_no = :user_no AND rev_no = :rev_no", nativeQuery = true)
    @Transactional
    void deleteByCmt_noAndUser_no(@Param("rev_no") Long rev_no, @Param("user_no") Long user_no);
}
