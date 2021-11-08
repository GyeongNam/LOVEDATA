package com.project.love_data.repository;

import com.project.love_data.model.service.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository extends JpaRepository<Questions, Long> , QuerydslPredicateExecutor<Questions> {
    @Query(value = "select * from questions where qu_activation=true order by qu_no desc ", nativeQuery = true)
    Optional<List<Questions>> qu_find_All();

    @Query(value = "select * from questions where qu_activation=true and qu_answer = false ", nativeQuery = true)
    Optional<List<Questions>> qua_All();

    @Query(value = "select * from questions where qu_no = :no ", nativeQuery = true)
    Questions qu_find_no(String no);

    @Query(value = "select * from questions where qu_no = :qu_no and qu_user_no= :user_no", nativeQuery = true)
    Questions qu_secret_check(String qu_no, String user_no);

    @Query(value = "select * from questions q where q.qu_activation=true and q.qu_title LIKE :text or q.qu_text LIKE :text order by q.qu_no desc ", nativeQuery = true)
    Optional<List<Questions>> qu_search_all(String text);

    @Query(value = "select * from questions q where q.qu_title LIKE :text and q.qu_activation=true order by q.qu_no desc ", nativeQuery = true)
    Optional<List<Questions>> qu_search_title(String text);

    @Query(value = "select * from questions q where q.qu_text LIKE :text and q.qu_activation=true order by q.qu_no desc", nativeQuery = true)
    Optional<List<Questions>> qu_search_text(String text);

    @Query(value = "select * from questions where qu_user_no = :no ", nativeQuery = true)
    Optional<List<Questions>> qu_findAllByUser_no(String no);
}
