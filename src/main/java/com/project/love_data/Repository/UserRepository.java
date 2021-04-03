package com.project.love_data.Repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.love_data.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select * from User where = :string",nativeQuery = true)
	boolean email_check(String string);

}
