package com.project.love_data.repository;

import com.project.love_data.model.service.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface CourseRepository extends JpaRepository<Course, Long>
        , QuerydslPredicateExecutor<Course> {
    @Query(value = "SELECT * from Course c WHERE  c.cor_name LIKE :name", nativeQuery = true)
    List<Course> findAllByName(@Param("name") String name);

    @Query(value = "SELECT * from Course c WHERE  c.cor_no = :cor_no", nativeQuery = true)
    Optional<Course> findCorByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from Course c WHERE  c.cor_uuid = :cor_uuid", nativeQuery = true)
    Optional<Course> findCorByUUID(@Param("cor_uuid") String cor_uuid);

    @Modifying
    @Query(value = "DELETE FROM Course  WHERE cor_uuid = :cor_uuid", nativeQuery = true)
    @Transactional
    void deleteByCor_uuid(@Param("cor_uuid") String cor_uuid);

//    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from Course c WHERE c.cor_no = :cor_no")
//    Optional<Course> findLoc_TagByNo(@Param("cor_no") Long cor_no);
//
//    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from Course c WHERE c.loc_uuid = :loc_uuid")
//    Optional<Course> findLoc_TagByUUID(@Param("loc_uuid") String loc_uuid);

    @Query(value = "SELECT c from Course c WHERE  c.cor_name LIKE :cor_name")
    Optional<List<Course>> findByCor_nameContaining(@Param("cor_name") String cor_name);

    @Query(value = "SELECT c FROM Course c WHERE c.user_no IN :user_no")
    Optional<List<Course>> findByAllUser_no(@Param("user_no")Long userNo);

//    @Query(value = "SELECT DISTINCT ls.Course_cor_no from Course_tag_set ls WHERE ls.tag_set LIKE :tag")
//    Long findCourseNoByTag(@Param("tag")String tag);

//    @Query(value = "SELECT DISTINCT l from Course l WHERE l.tagSet IN :tag")
//    Optional<List<Course>> findCourseByTag(@Param("tag")Set<String> tag);
}