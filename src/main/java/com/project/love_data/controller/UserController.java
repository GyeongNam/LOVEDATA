package com.project.love_data.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.project.love_data.security.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.love_data.repository.UserRepository;
import com.project.love_data.model.user.User;

@Controller
public class UserController {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @RequestMapping(value = "/login_check", method = RequestMethod.POST)
//	public String login(
//			@RequestParam(value = "user_mail")String email,
//			@RequestParam(value = "password")String pwd){
//	}

    @RequestMapping(value="/signup_add",method = RequestMethod.POST)
    public String signup(
	    		@RequestParam(value = "str_email01")String email1,
	    		@RequestParam(value = "str_email02")String email2,
				@RequestParam(value = "userPwd")String pwd,
	    		@RequestParam(value = "nickname")String nickname,
				@RequestParam(value = "userName")String userName,
				@RequestParam(value = "str_phone01")String phone01,
				@RequestParam(value = "str_phone02")String phone02,
				@RequestParam(value = "str_phone03")String phone03,
				@RequestParam(value = "birthday")String birthday,
				@RequestParam(value = "gender")boolean gender,
				@RequestParam(value = "recv_email")boolean recv_email
    		) {

		User user = User.builder()
				.user_email(email1 + "@" + email2)
				.user_pw(passwordEncoder.encode(pwd))
				.user_nic(nickname)
				.user_name(userName)
				.user_phone(phone01 + phone02 + phone03)
				.user_birth(birthday)
				.user_sex(gender)
				.user_email_re(recv_email)
				.user_time(LocalDateTime.now())
				.user_social(false)
				.user_Activation(true)
				.build();
		user.addUserRole(UserRole.USER);

    	userRepository.save(user);

    	return "home";
    }

    @ResponseBody
    @RequestMapping(value="/email_check",method = RequestMethod.POST)
    public Map<String,String> email_check(@RequestBody HashMap<String, String> data){

    	Map<String, String> map = new HashMap<String, String>();
    	String a = userRepository.email_check(data.get("mail"));

      	if(a == null || a.length() == 0) {
      		map.put("msg","1");
      	}
      	else {
      		map.put("msg","0");
      	}
      	return map;
    }

	@ResponseBody
    @RequestMapping(value="/nick_check",method = RequestMethod.POST)
    public Map<String,String> nick_check(@RequestBody HashMap<String, String> data){

    	Map<String, String> map = new HashMap<String, String>();
    	String a = userRepository.nick_check(data.get("nickname"));

      	if(a == null || a.length() == 0) {
      		map.put("msg","1");
      	}
      	else {
      		map.put("msg","0");
      	}
      	return map;
    }
}
