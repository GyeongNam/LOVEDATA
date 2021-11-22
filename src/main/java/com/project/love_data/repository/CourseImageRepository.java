package com.project.love_data.repository;

import com.project.love_data.model.resource.CourseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface CourseImageRepository extends JpaRepository<CourseImage, Long> {
    @Query(value = "SELECT * from cor_image i WHERE  i.user_no = :user_no AND is_deleted = false", nativeQuery = true)
    Optional<List<CourseImage>> findAllLiveImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.cor_no = :cor_no AND is_deleted = false", nativeQuery = true)
    Optional<List<CourseImage>> findAllLiveImageByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.img_uuid = :img_uuid AND is_deleted = false", nativeQuery = true)
    Optional<CourseImage> findLiveImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from cor_image i WHERE  i.user_no = :user_no AND is_deleted = true", nativeQuery = true)
    Optional<List<CourseImage>> findAllRemovedImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.cor_no = :cor_no AND is_deleted = true", nativeQuery = true)
    Optional<List<CourseImage>> findAllRemovedImageByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.img_uuid = :img_uuid AND is_deleted = true", nativeQuery = true)
    Optional<CourseImage> findRemovedImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from cor_image i WHERE  is_deleted = true", nativeQuery = true)
    Optional<CourseImage> findAllRemovedImages();

    @Query(value = "SELECT * from cor_image i WHERE  i.user_no = :user_no", nativeQuery = true)
    Optional<List<CourseImage>> findAllImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.cor_no = :cor_no", nativeQuery = true)
    Optional<List<CourseImage>> findAllImageByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.img_uuid = :img_uuid", nativeQuery = true)
    Optional<CourseImage> findImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from cor_image i WHERE  i.img_no = (SELECT MAX(img_no) FROM cor_image WHERE cor_no = :cor_no AND img_idx = 0)", nativeQuery = true)
    Optional<CourseImage> findThumbnailOnAllByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from cor_image i WHERE  i.img_no " +
            "= (SELECT MAX(img_no) FROM cor_image WHERE cor_no = :cor_no AND is_deleted = false AND img_idx = 0)", nativeQuery = true)
    Optional<CourseImage> findThumbnailOnLiveByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * FROM cor_image WHERE cor_no = :cor_no AND img_no >= (SELECT max(img_no) from cor_image WHERE  cor_no = :cor_no AND img_idx = 0 AND is_deleted = true)", nativeQuery = true)
    Optional<List<CourseImage>> findLastDeletedCourseImagesByCorNo(@Param("cor_no") Long cor_no);

    @Modifying
    @Query(value = "DELETE FROM cor_image WHERE img_uuid = :img_uuid", nativeQuery = true)
    @Transactional
    void deleteByImg_uuid(@Param("img_uuid") String img_uuid);
}
