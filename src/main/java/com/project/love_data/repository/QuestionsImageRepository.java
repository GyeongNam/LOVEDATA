package com.project.love_data.repository;

import com.project.love_data.model.resource.QuestionsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionsImageRepository extends JpaRepository<QuestionsImage, Long> {

    @Query(value = "select * from qu_image  where qu_no = :qu_no and qu_img_Activation = true", nativeQuery = true)
    Optional<List<QuestionsImage>> qu_no_imgselect(String qu_no);
}
