package com.project.love_data.repository;

import com.project.love_data.model.service.Calender;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CalenderRepository extends JpaRepository<Calender, Long> {

    @Query(value = "select * from calender u where user_mail = :user_mail and cal_Activation = true", nativeQuery = true)
    Optional<List<Calender>> findcal(String user_mail);

    @Query(value = "select * from calender u where cal_no = :no", nativeQuery = true)
    Calender findcal_no(String no);
}
