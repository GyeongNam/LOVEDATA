package com.project.love_data.repository;

import com.project.love_data.model.Test;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestRepository extends JpaRepository<Test, Integer> {
	@Query(value = "select * from Test where seq = :seq",nativeQuery = true)
	Test selectSEQ(@Param("seq") int seq);

	@Query(value = "select * from Test where  name = :name",nativeQuery = true)
	List<Test> selectNAME(@Param("name") String name);

	@Query(value = "select * from Test where country = :country",nativeQuery = true)
	List<Test> selectCOUNTRY(@Param("country") String country);

	@Query(value = "select * from Test",nativeQuery = true)
	List<Test> selectALL();
	
	@Modifying
	@Query(value = "insert into Test(country,name) value( :country , :name)",nativeQuery = true)
	@Transactional
	void insert(@Param("name") String name, @Param("country") String country);
	
	@Modifying
	@Query(value = "update Test set country = :country , name = :name where seq = :seq ",nativeQuery = true)
	@Transactional
	void updateSEQ(@Param("seq")int seq, @Param("name") String name, @Param("country") String country);
	
	@Modifying
	@Query(value = "delete from Test where seq = :seq ",nativeQuery = true)
	@Transactional
	void deleteSEQ(@Param("seq")int seq);
}
