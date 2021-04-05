package com.project.love_data.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.love_data.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select * from User where user_email = :email", nativeQuery = true)
	String email_check(@Param("email")String email);

	@Query(value = "select * from User where user_nic = :nickname", nativeQuery = true)
	String nick_check(@Param("nickname")String nickname);

	@Query(value = "select * from User u where user_email = :email", nativeQuery = true)
	Optional<User> findUserByEmail(String email);

	@Query(value = "select * from User u where user_email = :email AND user_social=:social", nativeQuery = true)
	Optional<User> findUserByEmail_Social(String email, boolean social);
}
