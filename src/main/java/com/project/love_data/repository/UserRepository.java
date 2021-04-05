package com.project.love_data.repository;

import java.util.*;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.love_data.model.User;
import com.project.love_data.security.model.UserRole;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select * from User where = :email",nativeQuery = true)
	boolean email_check(String email);

	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
	@Query(value = "SELECT u FROM User u where u.user_email = :email")
	Optional<User> findUserByEmail_Privilege(@Param("email") String email);

	@Query(value = "select * from User u where user_email = :email", nativeQuery = true)
	Optional<User> findUserByEmail(@Param("email") String email);

	@Query(value = "select * from User u where user_email = :email AND user_social=:social", nativeQuery = true)
	Optional<User> findUserByEmail_Social(@Param("email") String email,@Param("social") boolean social);
}
