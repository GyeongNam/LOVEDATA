package com.project.love_data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String homepage() {
		return "home";
	}
	
	@RequestMapping(value="/infoFind", method = RequestMethod.GET)
	public String infoFindpage() {
		return "user/infoFind";
	}

	@RequestMapping(value="/signup",method = RequestMethod.GET)
	public String signup() {
		return "user/signup";
	}
}
