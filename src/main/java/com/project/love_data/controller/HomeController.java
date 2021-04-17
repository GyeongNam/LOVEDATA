package com.project.love_data.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RestController
@Log4j2
public class HomeController {	
//	@RequestMapping(value="/", method = RequestMethod.GET)
//	public String homepage() {
//		return "home";
//	}
//
	@RequestMapping(value="/infoFind", method = RequestMethod.GET)
	public String infoFindpage() {
		return "user/infoFind";
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

		log.info("str_email01 : " + str_email01);
		log.info("str_email02 : " + str_email02);
		log.info("pwd1 : " + pwd1);
		log.info("pwd2 : " + pwd2);
		log.info("nickname : " + nickname);

		return "user/signup";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(){
		return "user/loginPage";
	}

	@RequestMapping(value="/find-info", method = RequestMethod.GET)
	public String findinfo(){
		return "user/find-info";
	}
	
	@RequestMapping(value="/find_id", method = RequestMethod.GET)
	public String find_id(){ return "user/find-info-new";
	}
}
