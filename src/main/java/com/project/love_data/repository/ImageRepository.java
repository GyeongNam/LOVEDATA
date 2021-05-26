package com.project.love_data.repository;

import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT * from image i WHERE  i.user_no = :user_no", nativeQuery = true)
    List<Image> findAllByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from image i WHERE  i.loc_no = :loc_no", nativeQuery = true)
    List<Image> findAllByLoc_no(@Param("loc_no") Long loc_no);

    @Query(value = "SELECT img_no from image i WHERE  i.img_uuid = : img_uuid", nativeQuery = true)
    Optional<List> findImageByImg_uuid(@Param("img_uuid") Long img_uuid);
}
