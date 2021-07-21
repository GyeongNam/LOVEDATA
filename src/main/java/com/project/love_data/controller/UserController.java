package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.CalenderService;
import com.project.love_data.businessLogic.service.UserService;
import com.project.love_data.dto.CalenderDTO;
import com.project.love_data.dto.UserDTO;
import com.project.love_data.model.service.Calender;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.CalenderRepository;
import com.project.love_data.repository.UserRepository;
import com.project.love_data.security.model.UserRole;
import com.project.love_data.businessLogic.SmsService;
import com.project.love_data.businessLogic.account.UserAccountDelete;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
public class UserController {
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private CalenderRepository calenderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private SmsService smsService;
	@Autowired
	private UserAccountDelete accountDelete;
	@Autowired
	UserService userService;
	@Autowired
	CalenderService calenderService;

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
				@RequestParam(value = "social") boolean social,
				@RequestParam(value = "social_info") String social_info
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
				.user_social(social)
				.social_info(social_info)
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
		String num = smsService.RandomNum();
		log.info("phone:"+data.get("phones"));
		log.info("num:"+num);
		map.put("msg","성공");
		map.put("num",num);
//		smsService.sendSMS(data.get("phones"), num);
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
		return "user/signup";
	}

	@PostMapping(value = "/user/deleteAccount/process")
	public String deleteAccount(HttpServletRequest request,
			Principal principal) {
		String redirectURL = "redirect:/";

		redirectURL = accountDelete.execute(request,  principal, userRepository);
		return redirectURL;
	}
	//CHOI
	@GetMapping(value = "/mypage")
	public String myinfo(Principal principal, Model model) {
		UserDTO userDTO = userService.DTOselect(principal.getName());
		model.addAttribute("UserDTO", userDTO);
//    	log.info("data : "+ request);
//    	log.info("data2 : "+ principal);
//		log.info("DTOLOG : "+ userDTO);
    	return  "user/mypage";
	}
	// 캘린더
	@GetMapping(value="/service/calender")
	public String calender(Principal principal){
		if(principal==null){
			return "redirect:/login";
		}
		return "service/service_calender";
	}
	// 페이지 업로드
	@ResponseBody
	@PostMapping(value = "/user/cal_all")
	public Map<String,Calender> cal_all(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		Map<String, Calender> map = new HashMap<String, Calender>();
    	if(principal==null){
		}
    	else {
			List<Calender> caldata = calenderService.Cal_select(principal.getName());
			for (int i=0; i<caldata.size(); i++){
				map.put("evt"+i, caldata.get(i));
			}
		}
		return map;
	}
	// 추가
	@ResponseBody
	@PostMapping(value = "/user/cal_add")
	public String cal_add(@RequestBody HashMap<String, String> data, Principal principal) {

		Calender calender = Calender.builder()
				.title(data.get("title"))
				.start(data.get("start"))
				.end(data.get("end"))
				.text(data.get("text"))
				.road(data.get("road"))
				.road2(data.get("road2"))
				.user_mail(principal.getName())
				.color(data.get("color"))
				.all_day(data.get("allDay").equals("true") ? true : false)
				.build();

		calenderRepository.save(calender);

		return  "1";
	}
}
