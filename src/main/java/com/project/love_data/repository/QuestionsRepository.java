package com.project.love_data.repository;

import com.project.love_data.model.service.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    @Query(value = "select * from Questions where qu_activation=true", nativeQuery = true)
    Optional<List<Questions>> qu_find_All();

    @Query(value = "select * from Questions where qu_no = :no", nativeQuery = true)
    Questions qu_find_no(String no);
}
