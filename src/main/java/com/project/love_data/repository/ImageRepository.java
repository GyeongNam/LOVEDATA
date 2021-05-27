package com.project.love_data.repository;

import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT * from image i WHERE  i.user_no = :user_no", nativeQuery = true)
    List<Image> findAllByUser_no(@Param("user_no") Long user_no);

//    @Query(value = "SELECT * from image i WHERE  i.loc_uuid = :loc_uuid", nativeQuery = true)
//    List<Image> findAllByLoc_uuid(@Param("loc_uuid") String loc_uuid);

    @Query(value = "SELECT * from image i WHERE  i.img_uuid = :img_uuid", nativeQuery = true)
    Optional<Image> findImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Modifying
    @Query(value = "DELETE FROM image  WHERE img_uuid = :img_uuid", nativeQuery = true)
    @Transactional
    void deleteByImg_uuid(@Param("img_uuid") String img_uuid);
}
