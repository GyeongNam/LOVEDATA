package com.project.love_data.repository;

import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.resource.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    @Query(value = "SELECT * from rev_image i WHERE  i.user_no = :user_no AND is_deleted = false", nativeQuery = true)
    Optional<List<ReviewImage>> findAllLiveImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.rev_no = :rev_no AND is_deleted = false", nativeQuery = true)
    Optional<List<ReviewImage>> findAllLiveImageByRev_no(@Param("rev_no") Long rev_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.cor_no = :cor_no AND is_deleted = false", nativeQuery = true)
    Optional<List<ReviewImage>> findAllLiveImageByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.cor_no = :cor_no AND i.rev_no = :rev_no AND is_deleted = false", nativeQuery = true)
    Optional<List<ReviewImage>> findAllLiveImageByCor_noAndRev_no(@Param("cor_no") Long cor_no, @Param("rev_no") Long rev_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.cor_no = :cor_no AND i.rev_no = :rev_no AND img_no >= (SELECT max(img_no) from rev_image WHERE  rev_no = :rev_no AND img_idx = 0)", nativeQuery = true)
    Optional<List<ReviewImage>> findAllImageByCor_noAndRev_no(@Param("cor_no") Long cor_no, @Param("rev_no") Long rev_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.img_uuid = :img_uuid AND is_deleted = false", nativeQuery = true)
    Optional<ReviewImage> findLiveImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from rev_image i WHERE  i.user_no = :user_no AND is_deleted = true", nativeQuery = true)
    Optional<List<ReviewImage>> findAllRemovedImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.cor_no = :cor_no AND is_deleted = true", nativeQuery = true)
    Optional<List<ReviewImage>> findAllRemovedImageByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.img_uuid = :img_uuid AND is_deleted = true", nativeQuery = true)
    Optional<ReviewImage> findRemovedImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * from rev_image i WHERE  is_deleted = true", nativeQuery = true)
    Optional<ReviewImage> findAllRemovedImages();

    @Query(value = "SELECT * from rev_image i WHERE  i.user_no = :user_no", nativeQuery = true)
    Optional<List<ReviewImage>> findAllImageByUser_no(@Param("user_no") Long user_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.cor_no = :cor_no", nativeQuery = true)
    Optional<List<ReviewImage>> findAllImageByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from rev_image i WHERE  i.img_uuid = :img_uuid", nativeQuery = true)
    Optional<ReviewImage> findImageByImg_uuid(@Param("img_uuid") String img_uuid);

    @Query(value = "SELECT * FROM rev_image WHERE rev_no = :rev_no AND img_no >= (SELECT max(img_no) from rev_image WHERE  rev_no = :rev_no AND is_deleted = true AND img_idx = 0)", nativeQuery = true)
    Optional<List<ReviewImage>> findLastDeletedReviewImagesByRevNo(@Param("rev_no") Long revNo);

    @Modifying
    @Query(value = "DELETE FROM rev_image  WHERE img_uuid = :img_uuid", nativeQuery = true)
    @Transactional
    void deleteByImg_uuid(@Param("img_uuid") String img_uuid);
}
