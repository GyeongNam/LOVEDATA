package com.project.love_data.wrapper;

import com.project.love_data.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query("select t from Test t where t.seq=?1")
    Test selectSEQ(int seq);

//    @Query("insert INTO TEST t VALUE (t.seq=NULL, t.country=:country, t.name=:name)")
//    void insertValue(@Param("country") String country, @Param("name") String name);

//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Transactional
//    @Query("UPDATE Test t SET t.country=:country,t.name=:name WHERE t.seq=:seq")
//    void updateSEQ(@Param("seq") int seq,@Param("country") String country,@Param("name") String name);
    
    @Modifying
    @Query("UPDATE Test t SET t.country = :country, t.name = :name WHERE t.seq = :seq")
    void updateSEQ(@Param("seq")int seq, @Param("name") String name, @Param("country") String country);

    @Modifying
    @Transactional
    @Query("DELETE FROM Test t WHERE t.seq=:seq")
    void deleteSEQ(@Param("seq")int seq);
}
