package com.project.love_data.controller;

import com.project.love_data.model.user.User;
import com.project.love_data.repository.UserRepository;
import com.project.love_data.security.model.UserRole;
import com.project.love_data.service.SmsService;
import com.project.love_data.service.account.UserAccountDelete;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
public class UserController {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private SmsService smsService;
	@Autowired
	private UserAccountDelete accountDelete;

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
				@RequestParam(value = "recv_email")boolean recv_email,
				@RequestParam(value = "social") boolean social
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
				.user_social(social)
				.user_Activation(true)
				.build();
		user.addUserRole(UserRole.USER);

    	userRepository.save(user);

    	return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value="/email_check",method = RequestMethod.POST)
    public Map<String,String> email_check(@RequestBody HashMap<String, String> data){

    	log.info("email ajax : " + data.get("mail"));
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

	@ResponseBody
	@RequestMapping(value="/sendsms",method = RequestMethod.POST)
	public Map<String,String> sendsms(@RequestBody HashMap<String, String> data){
		Map<String, String> map = new HashMap<String, String>();
		log.info("phone:"+data.get("phones"));
		map.put("msg","성공");
		smsService.sendSMS(data.get("phones"));
		return map;
	}

	//	@RequestMapping(value="/signup",method = RequestMethod.GET)
	@GetMapping("/signup")
	@PostMapping("/signup")
	public String signup(
			String str_email01,
			String str_email02,
			String pwd1,
			String pwd2,
			String nickname,
			HttpServletRequest request
	) {

//		log.info("str_email01 : " + str_email01);
//		log.info("str_email02 : " + str_email02);
//		log.info("pwd1 : " + pwd1);
//		log.info("pwd2 : " + pwd2);
//		log.info("nickname : " + nickname);

		return "user/signup";
	}

	@PostMapping(value = "/user/deleteAccount/process")
	public String deleteAccount(HttpServletRequest request,
			Principal principal) {
		String redirectURL = "redirect:/";

		redirectURL = accountDelete.execute(request,  principal, userRepository);
		return redirectURL;
	}
}
