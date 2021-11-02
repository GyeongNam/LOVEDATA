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
    @Query(value = "SELECT * from course c WHERE  c.cor_name LIKE :name", nativeQuery = true)
    List<Course> findAllByName(@Param("name") String name);

    @Query(value = "SELECT * from course c WHERE  c.cor_no = :cor_no", nativeQuery = true)
    Optional<Course> findCorByCor_no(@Param("cor_no") Long cor_no);

    @Query(value = "SELECT * from course c WHERE  c.cor_uuid = :cor_uuid", nativeQuery = true)
    Optional<Course> findCorByUUID(@Param("cor_uuid") String cor_uuid);

    @Modifying
    @Query(value = "DELETE FROM course  WHERE cor_uuid = :cor_uuid", nativeQuery = true)
    @Transactional
    void deleteByCor_uuid(@Param("cor_uuid") String cor_uuid);

//    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from course c WHERE c.cor_no = :cor_no")
//    Optional<Course> findLoc_TagByNo(@Param("cor_no") Long cor_no);
//
//    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from course c WHERE c.loc_uuid = :loc_uuid")
//    Optional<Course> findLoc_TagByUUID(@Param("loc_uuid") String loc_uuid);

    @Query(value = "SELECT * from course c WHERE  c.cor_name LIKE :cor_name", nativeQuery = true)
    Optional<List<Course>> findByCor_nameContaining(@Param("cor_name") String cor_name);

    @Query(value = "SELECT * FROM course c WHERE c.user_no = :user_no", nativeQuery = true)
    Optional<List<Course>> findByAllUser_no(@Param("user_no")Long userNo);

    // 어드민 대쉬보드 최근 코스
    @Query(value = "SELECT * FROM course c WHERE datediff(now(), c.regdate) <= :date ORDER BY c.regdate desc limit :count", nativeQuery = true)
    Optional<List<Course>> getRecentCoursesByCountAndDateDuration(@Param("count")int count, @Param("date") int dateDuration);

    // 어드민 대쉬보드 인기 코스
    @Query(value = "SELECT * FROM course c WHERE datediff(now(), c.regdate) <= :date AND c.likecount >= :min_like ORDER BY c.likecount desc limit :count", nativeQuery = true)
    Optional<List<Course>> getHotCoursesByCountAndDateDuration(@Param("count")int count, @Param("date") int dateDuration, @Param("min_like") int minLikeCount);

//    @Query(value = "SELECT DISTINCT ls.course_cor_no from course_tag_set ls WHERE ls.tag_set LIKE :tag")
//    Long findCourseNoByTag(@Param("tag")String tag);

//    @Query(value = "SELECT DISTINCT l from course l WHERE l.tagSet IN :tag")
//    Optional<List<Course>> findCourseByTag(@Param("tag")Set<String> tag);
}