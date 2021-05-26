package com.project.love_data.repository;

import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value = "SELECT * from Location l WHERE  l.loc_name = :name", nativeQuery = true)
    Optional<Location> findLocByName(@Param("name") String name);

    @Query(value = "SELECT * from Location l WHERE  l.loc_no = :loc_no", nativeQuery = true)
    Optional<Location> findLocByLoc_no(@Param("loc_no") String loc_no);

    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT l from Location l WHERE l.loc_name = :name")
    Optional<Location> findLoc_TagByName(@Param("name") String name);

//    @EntityGraph(attributePaths = {"imgSet"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value = "SELECT l from Location l WHERE l.loc_no = SELECT l.loc_no FROM Location l where l.loc_name = :name")
//    Optional<Location> findLoc_ImgByName(@Param("name") String name);

    @EntityGraph(attributePaths = {"imgSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT l from Location l WHERE l.loc_no = :loc_no")
    Optional<Location> findLoc_ImgByNo(@Param("loc_no") Long loc_no);
}