package com.project.love_data.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.love_data.Repository.UserRepository;
import com.project.love_data.model.User;

@Controller
public class UserController {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
				@RequestParam(value = "gender")String gender,
				@RequestParam(value = "assent")String assent
    		) {
    	User user = new User(
    			email1 + "@" + email2,
    			passwordEncoder.encode(pwd),
    			nickname,
    			userName,
    			phone01 + phone02 + phone03,
    			birthday,
    			gender,
					assent
        		);
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
}
