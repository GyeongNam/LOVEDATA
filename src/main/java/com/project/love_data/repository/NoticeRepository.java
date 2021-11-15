package com.project.love_data.repository;

import com.project.love_data.model.service.Notice;
import com.project.love_data.model.service.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value = "select * from notice where  noti_activation=true order by noti_no desc", nativeQuery = true)
    Optional<List<Notice>> noti_find_All();

    @Query(value = "select * from notice where noti_no = :no", nativeQuery = true)
    Notice noti_find_no(String no);

    @Query(value = "select * from notice q where q.noti_activation=true and (q.noti_title LIKE :text or q.noti_text LIKE :text) order by q.noti_no desc ", nativeQuery = true)
    Optional<List<Notice>> no_search_all(String text);

    @Query(value = "select * from notice q where q.noti_title LIKE :text and q.noti_activation=true order by q.noti_no desc ", nativeQuery = true)
    Optional<List<Notice>> no_search_title(String text);

    @Query(value = "select * from notice q where q.noti_text LIKE :text and q.noti_activation=true order by q.noti_no desc", nativeQuery = true)
    Optional<List<Notice>> no_search_text(String text);
}
