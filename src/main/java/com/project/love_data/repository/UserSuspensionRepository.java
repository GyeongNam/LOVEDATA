package com.project.love_data.repository;

import com.project.love_data.model.service.Review;
import com.project.love_data.model.service.UserSuspension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSuspensionRepository extends JpaRepository<UserSuspension, Long> {

    @Query(value = "SELECT * FROM user_suspension us WHERE us.user_no = :user_no", nativeQuery = true)
    Optional<List<UserSuspension>> findAllByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * FROM user_suspension us WHERE us.progress = :progress", nativeQuery = true)
    Optional<List<UserSuspension>> findAllByprogress(@Param("progress") String progress);

    @Query(value = "SELECT * FROM user_suspension us WHERE us.user_no = :user_no and us.progress = :progress ", nativeQuery = true)
    Optional<List<UserSuspension>> findStopByUser_no(@Param("user_no") Long user_no ,@Param("progress") String progress);
}
