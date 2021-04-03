package com.project.love_data.Repository;

import com.project.love_data.model.Test;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestRepository extends JpaRepository<Test, Integer> {
	
	
	@Query(value = "select * from Test",nativeQuery = true)
	List<String> selectallSEQ();
	
	@Modifying
	@Query(value = "insert into Test(country,name) value( :country , :name)",nativeQuery = true)
	@Transactional
	void insertSEQ( @Param("name") String name, @Param("country") String country);
	
	@Modifying
	@Query(value = "update Test set country = :country , name = :name where seq = :seq ",nativeQuery = true)
	@Transactional
	void updateSEQ(@Param("seq")int seq, @Param("name") String name, @Param("country") String country);
	
	@Modifying
	@Query(value = "delete from Test where seq = :seq ",nativeQuery = true)
	@Transactional
	void deleteSEQ(@Param("seq")int seq);
}
