package com.project.love_data.repository;

import com.project.love_data.model.service.UserLikeRev;
import com.project.love_data.model.service.UserLikeRev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserLikeRevRepository extends JpaRepository<UserLikeRev, Long> {
    @Query(value = "select * from user_like_rev ulr where  ulr.rev_no = :rev_no", nativeQuery = true)
    public Optional<List <UserLikeRev>> findByCor_no(@Param("rev_no") Long corNo);

    @Query(value = "select * from user_like_rev ulr where  ulr.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserLikeRev>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_like_rev ulr where  ulr.rev_no = :rev_no AND ulr.user_no = :user_no", nativeQuery = true)
    public Optional<UserLikeRev> findByRev_noAndUser_no(@Param("rev_no") Long revNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_like_rev ulr where  ulr.uuid Like :uuid", nativeQuery = true)
    public Optional<UserLikeRev> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_like_rev WHERE user_no = :user_no AND rev_no = :rev_no", nativeQuery = true)
    @Transactional
    void deleteByCmt_noAndUser_no(@Param("rev_no") Long rev_no, @Param("user_no") Long user_no);
}
