package com.project.love_data.repository;

import com.project.love_data.model.service.Notice;
import com.project.love_data.model.service.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value = "select * from Notice where  noti_activation=true order by noti_no desc", nativeQuery = true)
    Optional<List<Notice>> noti_find_All();

    @Query(value = "select * from Notice where noti_no = :no", nativeQuery = true)
    Notice noti_find_no(String no);
}
