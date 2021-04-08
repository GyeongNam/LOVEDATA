package com.project.love_data.controller;

import org.springframework.stereotype.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RestController
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

	@RequestMapping(value="/signup",method = RequestMethod.GET)
	public String signup() {
		return "user/signup";
	}

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(){
		return "user/login";
	}

	@RequestMapping(value="/find-info", method = RequestMethod.GET)
	public String findinfo(){
		return "user/find-info";
	}
	
	@RequestMapping(value="/find-info-new", method = RequestMethod.GET)
	public String test_jsp(){
		return "temp/find-info-new";
	}
}
