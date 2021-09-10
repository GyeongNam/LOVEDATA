package com.project.love_data.repository;

import com.project.love_data.model.service.UserLikeLoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface UserLikeLocRepository extends JpaRepository<UserLikeLoc, Long> {
    @Query(value = "select * from user_like_loc ull where  ull.loc_no = :loc_no", nativeQuery = true)
    public Optional<List <UserLikeLoc>> findByLoc_no(@Param("loc_no") Long locNo);

    @Query(value = "select * from user_like_loc ull where  ull.user_no = :user_no", nativeQuery = true)
    public Optional<List <UserLikeLoc>> findByUser_no(@Param("user_no") Long userNo);

    @Query(value = "select * from user_like_loc ull where  ull.loc_no = :loc_no AND ull.user_no = :user_no", nativeQuery = true)
    public Optional<UserLikeLoc> findByLoc_noAndUser_no(@Param("loc_no") Long locNo, @Param("user_no") Long userNo);

    @Query(value = "select * from user_like_loc ull where  ull.uuid Like :uuid", nativeQuery = true)
    public Optional<UserLikeLoc> findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Query(value = "DELETE FROM user_like_loc WHERE user_no = :user_no AND loc_no = :loc_no", nativeQuery = true)
    @Transactional
    void deleteByLoc_noAndUser_no(@Param("loc_no") Long loc_no, @Param("user_no") Long user_no);
}
