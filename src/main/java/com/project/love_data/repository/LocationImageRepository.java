package com.project.love_data.repository;

import com.project.love_data.model.resource.LocationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface LocationImageRepository extends JpaRepository<LocationImage, Long> {
    @Query(value = "SELECT * from loc_image i WHERE  i.user_no = :user_no AND is_deleted = false", nativeQuery = true)
    List<LocationImage> findAllLiveImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from loc_image i WHERE  i.location_loc_no = :loc_no AND is_deleted = false", nativeQuery = true)
    Optional<List<LocationImage>> findAllLiveImageByLoc_no(@Param("loc_no") Long loc_no);

//    @Query(value = "SELECT * from loc_image i WHERE  i.loc_uuid = :loc_uuid", nativeQuery = true)
//    List<Image> findAllByLoc_uuid(@Param("loc_uuid") String loc_uuid);

    @Query(value = "SELECT * from loc_image i WHERE  i.img_uuid = :img_uuid AND is_deleted = false", nativeQuery = true)
    Optional<LocationImage> findLiveImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from loc_image i WHERE  i.user_no = :user_no", nativeQuery = true)
    List<LocationImage> findAllImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from loc_image i WHERE  i.img_uuid = :img_uuid", nativeQuery = true)
    Optional<LocationImage> findImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from loc_image i WHERE  i.location_loc_no = :loc_no", nativeQuery = true)
    List<LocationImage> findAllImageByLoc_no(@Param("loc_no") Long loc_no);

    @Query(value = "SELECT * from loc_image i WHERE  i.is_deleted = true", nativeQuery = true)
    Optional<LocationImage> findAllRemovedImage();

    @Query(value = "SELECT * from loc_image i WHERE  i.img_no = :img_no AND i.is_deleted = true", nativeQuery = true)
    Optional<LocationImage> findAllRemovedImageById(@Param("img_no") Long img_no);

    @Query(value = "SELECT * from loc_image i WHERE  i.img_uuid LIKE :img_uuid AND i.is_deleted = true", nativeQuery = true)
    Optional<LocationImage> findAllRemovedImageByUUID(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT max(img_no) from loc_image WHERE  location_loc_no = :loc_no AND img_idx = 0 AND is_deleted = true", nativeQuery = true)
    Optional<Long> findFirstIndexOfLastDeletedLocationImageByLocNo(@Param("loc_no") Long loc_no);

    @Query(value = "SELECT * from loc_image WHERE  location_loc_no = :loc_no AND img_no >= :img_no", nativeQuery = true)
    Optional<List<LocationImage>> findLocationImageListOfLocNoAndImgNo(@Param("loc_no") Long loc_no, @Param("img_no") Long img_no);

    @Modifying
    @Query(value = "DELETE FROM loc_image  WHERE img_uuid = :img_uuid", nativeQuery = true)
    @Transactional
    void deleteByImg_uuid(@Param("img_uuid") String img_uuid);
}
