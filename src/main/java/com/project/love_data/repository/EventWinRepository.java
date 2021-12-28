package com.project.love_data.repository;

import com.project.love_data.model.service.EventAttend;
import com.project.love_data.model.service.EventWin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventWinRepository extends JpaRepository<EventWin, Long> {
    @Query(value = "select * from eventwin where ev_no = :ev_no", nativeQuery = true)
    Optional<List<EventWin>> evwin_find_EvNo(String ev_no);
}
