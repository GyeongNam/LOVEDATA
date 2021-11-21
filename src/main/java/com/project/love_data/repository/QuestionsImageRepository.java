package com.project.love_data.repository;

import com.project.love_data.model.resource.QuestionsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuestionsImageRepository extends JpaRepository<QuestionsImage, Long> {

    @Query(value = "select * from qu_image  where qu_no = :qu_no and qu_img_Activation = true", nativeQuery = true)
    Optional<List<QuestionsImage>> qu_no_imgselect(String qu_no);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update qu_image set qu_img_Activation = false where qu_no = :qu_no ", nativeQuery = true)
    void qu_no_img_false(String qu_no);

    @Query(value = "select * from qu_image  where qu_img_url = :name and qu_img_Activation = true", nativeQuery = true)
    List<QuestionsImage> qu_name_imgselect(String name);

    @Query(value = "select * from qu_image  where qu_no = :qu_no and qu_img_Activation = false ", nativeQuery = true)
    List<QuestionsImage> qu_no_img_out(String qu_no);

    @Query(value = "select * from qu_image  where user_no = :user_no and qu_img_Activation = true", nativeQuery = true)
    Optional<List<QuestionsImage>> user_no_imgselect(String user_no);
}
