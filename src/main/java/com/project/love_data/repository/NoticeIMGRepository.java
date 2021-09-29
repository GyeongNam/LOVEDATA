package com.project.love_data.repository;

import com.project.love_data.model.service.Notice;
import com.project.love_data.model.service.NoticeIMG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeIMGRepository extends JpaRepository<NoticeIMG, Long> {

    @Query(value = "select * from noticeimg where notiimg_activation=true and notiimg_user = :usernic and notiimg_postno = '0' ", nativeQuery = true)
    Optional<List<NoticeIMG>> select_notiimg(String usernic);

    @Query(value = "select * from noticeimg where notiimg_activation=true and notiimg_postno = :num ", nativeQuery = true)
    Optional<List<NoticeIMG>> select_notiimg_num(String num);
}
