package com.project.love_data.repository;

import java.util.*;

import com.project.love_data.dto.UserDTO;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.project.love_data.model.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select * from user where user_no = :user_no AND is_deleted = false", nativeQuery = true)
	Optional<User> selectLiveUser (@Param("user_no")long userNo);

	@Query(value = "select * from user where user_email = :email", nativeQuery = true)
	String email_check(@Param("email")String email);

	@Query(value = "select user_email from user where user_email = :email", nativeQuery = true)
	String email_send(@Param("email")String email);

	@Query(value = "select * from user where user_nic = :nickname", nativeQuery = true)
	String nick_check(@Param("nickname")String nickname);

	@Query(value = "select * from user u where user_email = :email", nativeQuery = true)
	Optional<User> findUserByEmail(String email);

	@Query(value = "select * from user u where user_email = :email AND user_social=:social", nativeQuery = true)
	Optional<User> findUserByEmail_Social(String email, boolean social);

	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
	@Query(value = "SELECT u FROM User u where u.user_email = :email")
	Optional<User> findUserByEmail_Privilege(@Param("email") String email);

	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
	@Query(value = "SELECT u FROM User u where u.user_email = :email AND u.is_deleted = false")
	Optional<User> findLiveUserByEmail_Privilege( String email);

	//CHOI
	@Query(value = "select user_email from user where user_phone = :phonenumber", nativeQuery = true)
	Optional<List<String>> findId(String phonenumber);

	@Query(value = "select * from user u where user_email = :email", nativeQuery = true)
	User findEmail(String email);

	@Query(value = "select * from user u where user_pw = :pw", nativeQuery = true)
	User findPw2(String pw);

	@Query(value = "select * from user u where user_no = :no", nativeQuery = true)
	User find_user_no(String no);

	@Query(value = "select user_no from user u where user_email = :email", nativeQuery = true)
	String finduserNo(String email);

	@Query(value = "select * from user q where q.user_name LIKE :text order by q.user_no desc", nativeQuery = true)
	Optional<List<User>> name_search(String text);

	@Query(value = "select * from user q where q.user_nic LIKE :text order by q.user_no desc", nativeQuery = true)
	Optional<List<User>> nic_search(String text);
//	@Modifying
//	@Transactional
//	@Query(value = "DELETE  FROM user_role_set r WHERE r.user_user_no = :user_no", nativeQuery = true)
//	void deleteUserRoleByEmail(@Param("user_no") Long user_no);

	@Query(value = "select * from user where profile_pic = :profile_pic", nativeQuery = true)
	Optional<List<User>> selectUserByProfilePic (@Param("profile_pic")String profile_pic);

	@Modifying
	@Query(value = "DELETE  FROM user  WHERE user_email = :email", nativeQuery = true)
	@Transactional
	void deleteUserByEmail(@Param("email") String email);
}
