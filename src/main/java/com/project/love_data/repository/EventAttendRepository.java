package com.project.love_data.repository;

import com.project.love_data.model.service.Event;
import com.project.love_data.model.service.EventAttend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventAttendRepository extends JpaRepository<EventAttend, Long> {
    @Query(value = "select * from eventattend where user_no = :user_no and ev_no = :ev_no", nativeQuery = true)
    Optional<List<EventAttend>> evattd_find_UserEvNo(String user_no, String ev_no);

    @Query(value = "select * from eventattend where user_no = :user_no group by ev_no", nativeQuery = true)
    Optional<List<EventAttend>> evattd_find_UserNo(String user_no);

    @Query(value = "select * from eventattend where ev_no = :ev_no", nativeQuery = true)
    Optional<List<EventAttend>> evattd_find_EvNo(String ev_no);
}
