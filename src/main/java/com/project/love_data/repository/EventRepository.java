package com.project.love_data.repository;

import com.project.love_data.model.service.Event;
import com.project.love_data.model.service.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select * from event where  ev_activation=true order by ev_no desc", nativeQuery = true)
    Optional<List<Event>> ev_find_All();

    @Query(value = "select * from event where  ev_item_activation=false order by ev_no desc", nativeQuery = true)
    Optional<List<Event>> ev_find_item();

    @Query(value = "select * from event order by ev_no desc", nativeQuery = true)
    Optional<List<Event>> ev_All();

    @Query(value = "select * from event where ev_no = :no", nativeQuery = true)
    Event ev_find_no(String no);

    @Query(value = "select * from event q where q.ev_activation=true and (q.ev_title LIKE :text or q.ev_text LIKE :text) order by q.ev_no desc ", nativeQuery = true)
    Optional<List<Event>> ev_search_all(String text);

    @Query(value = "select * from event q where q.ev_title LIKE :text and q.ev_activation=true order by q.ev_no desc ", nativeQuery = true)
    Optional<List<Event>> ev_search_title(String text);

    @Query(value = "select * from event q where q.ev_text LIKE :text and q.ev_activation=true order by q.ev_no desc", nativeQuery = true)
    Optional<List<Event>> ev_search_text(String text);
}
