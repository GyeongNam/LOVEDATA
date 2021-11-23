package com.project.love_data.repository;

import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.DeletedImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface DeletedImageInfoRepository extends JpaRepository<DeletedImageInfo, Long> {
    @Query(value = "SELECT * from del_img_info dii WHERE  dii.img_uuid = :img_uuid", nativeQuery = true)
    Optional<DeletedImageInfo> findDIIByImgUuid(@Param("img_uuid") String imgUuid);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.dii_uuid = :dii_uuid", nativeQuery = true)
    Optional<DeletedImageInfo> findDIIByDiiUuid(@Param("dii_uuid") String diiUuid);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.img_type = :img_type AND dii.img_no = :img_no", nativeQuery = true)
    Optional<DeletedImageInfo> findDIIByImgTypeAndImgNo(@Param("img_type") String imgType, @Param("img_no") Long imgNo);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.img_type = :img_type", nativeQuery = true)
    Optional<List<DeletedImageInfo>> findDIIsByImgType(@Param("img_type") String imgType);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.user_no = :user_no", nativeQuery = true)
    Optional<List<DeletedImageInfo>> findDIIsByUserNo(@Param("user_no") Long userNo);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.dii_no = :dii_no AND dii.deleted = :deleted", nativeQuery = true)
    Optional<DeletedImageInfo> findDIIByDiiNoAndDeleted(@Param("dii_no") Long diiNo, @Param("deleted") boolean deleted);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.dii_uuid = :dii_uuid AND dii.deleted = :deleted", nativeQuery = true)
    Optional<DeletedImageInfo> findDIIByDiiUuidAndDeleted(@Param("dii_uuid") String diiUuid, @Param("deleted") boolean deleted);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.img_type = :img_type AND dii.img_no = :img_no AND dii.deleted = :deleted", nativeQuery = true)
    Optional<DeletedImageInfo> findDIIByImgTypeAndImgNoAndDeleted(@Param("img_type") String imgType, @Param("img_no") Long imgNo, @Param("deleted") boolean deleted);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.img_type = :img_type AND dii.deleted = :deleted", nativeQuery = true)
    Optional<List<DeletedImageInfo>> findDIIsByImgTypeAndDeleted(@Param("img_type") String imgType, @Param("deleted") boolean deleted);

    @Query(value = "SELECT * from del_img_info dii WHERE  dii.user_no = :user_no AND dii.deleted = :deleted", nativeQuery = true)
    Optional<List<DeletedImageInfo>> findDIIsByUserNoAndDeleted(@Param("user_no") Long userNo, @Param("deleted") boolean deleted);

    @Modifying
    @Query(value = "DELETE FROM del_img_info WHERE dii_uuid = :dii_uuid", nativeQuery = true)
    @Transactional
    void deleteByDIIUuid(@Param("dii_uuid") String DIIUuid);
}
