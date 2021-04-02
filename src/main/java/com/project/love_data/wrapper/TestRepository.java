package com.project.love_data.wrapper;

import com.project.love_data.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query("select t from Test t where t.seq=?1")
    Test selectSEQ(int seq);

//    @Query("UPDATE Test t SET t.country=#{country},t.name=#{name} WHERE t.seq=#{seq}")
//    Test updateSEQ(@Param("seq") int seq,@Param("country") String country,@Param("name") String name);
}
