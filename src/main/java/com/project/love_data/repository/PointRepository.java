package com.project.love_data.repository;

import com.project.love_data.model.service.Notice;
import com.project.love_data.model.service.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long>{

    @Query(value = "select sum(p.point) as 'sum' from point p where p.user_no=:user_no and get_plus_mi = true group by p.user_no", nativeQuery = true)
    Long findplupoint(String user_no);

    @Query(value = "select sum(p.point) as 'sum' from point p where p.user_no=:user_no and get_plus_mi = false group by p.user_no", nativeQuery = true)
    Long findmapoint(String user_no);

    @Query(value = "select * from point p where p.user_no=:user_no", nativeQuery = true)
    Optional<List<Point>> points_find_user_no(String user_no);
}
