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

@NoRepositoryBean
public interface LocationRepository extends JpaRepository<Long, Location> {

    @Query(value = "SELECT * from Location l WHERE  l.loc_name = :name", nativeQuery = true)
    Optional<Location> findLocByName(@Param("name") String name);

    @EntityGraph(attributePaths = {"tagSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT * from Location l WHERE l.loc_name = :name", nativeQuery = true)
    Optional<Location> findLocByName_Tag(@Param("name") String name);
}
