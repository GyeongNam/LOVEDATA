package com.project.love_data.repository;

import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface LocationRepository extends JpaRepository<Location, Long>
        , QuerydslPredicateExecutor<Location> {
    @Query(value = "SELECT * from Location l WHERE  l.loc_name LIKE :name", nativeQuery = true)
    List<Location> findAllByName(@Param("name") String name);

    @Query(value = "SELECT * from Location l WHERE  l.loc_no = :loc_no", nativeQuery = true)
    Optional<Location> findLocByLoc_no(@Param("loc_no") Long loc_no);

    @Query(value = "SELECT * from Location l WHERE  l.loc_uuid = :loc_uuid", nativeQuery = true)
    Optional<Location> findLocByUUID(@Param("loc_uuid") String loc_uuid);

    @Modifying
    @Query(value = "DELETE FROM location  WHERE loc_uuid = :loc_uuid", nativeQuery = true)
    @Transactional
    void deleteByLoc_uuid(@Param("loc_uuid") String loc_uuid);

//    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from Location l WHERE l.loc_no = :loc_no")
//    Optional<Location> findLoc_TagByNo(@Param("loc_no") Long loc_no);
//
//    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from Location l WHERE l.loc_uuid = :loc_uuid")
//    Optional<Location> findLoc_TagByUUID(@Param("loc_uuid") String loc_uuid);

    List<Location> findByRoadAddrContaining(@Param("roadaddr") String roadaddr);

    List<Location> findAllByRoadAddrAndAddrDetail(@Param("roadaddr") String roadaddr, @Param("addrdetail") String addrdetail);

    @Query(value = "SELECT l from Location l WHERE  l.loc_name LIKE :loc_name")
    Optional<List<Location>> findByLoc_nameContaining(@Param("loc_name") String loc_name);

    @Query(value = "SELECT l FROM Location l WHERE l.user_no IN :user_no")
    Optional<List<Location>> findByAllUser_no(@Param("user_no")Long userNo);

    @Query(value = "SELECT * from Location l WHERE  l.loc_no = :loc_no AND l.is_deleted = false", nativeQuery = true)
    Optional<Location> findLiveLocByLoc_no(@Param("loc_no") Long loc_no);

    @Query(value = "SELECT * from Location l WHERE  l.loc_uuid = :loc_uuid AND l.is_deleted = false", nativeQuery = true)
    Optional<Location> findLiveLocByUUID(@Param("loc_uuid") String loc_uuid);

//    @Query(value = "SELECT DISTINCT ls.location_loc_no from location_tag_set ls WHERE ls.tag_set LIKE :tag")
//    Long findLocationNoByTag(@Param("tag")String tag);

//    @Query(value = "SELECT DISTINCT l from Location l WHERE l.tagSet IN :tag")
//    Optional<List<Location>> findLocationByTag(@Param("tag")Set<String> tag);
}