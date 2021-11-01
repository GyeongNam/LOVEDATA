package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.CalenderDTO;
import com.project.love_data.dto.UserDTO;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.CalenderRepository;
import com.project.love_data.repository.ReviewRepository;
import com.project.love_data.repository.UserRepository;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.security.model.UserRole;
import com.project.love_data.businessLogic.SmsService;
import com.project.love_data.businessLogic.account.UserAccountDelete;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.project.love_data.util.ConstantValues.MAX_UPLOAD_COUNT;
import static com.project.love_data.util.ConstantValues.MIN_UPLOAD_COUNT;

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
	private ReviewRepository reviewRepository;
	@Autowired
	UserService userService;
	@Autowired
	CalenderService calenderService;
	@Autowired
	LocationService locService;
	@Autowired
	CourseService corService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	FileUploadService fileUploadService;

	@RequestMapping(value = "/signup_add", method = RequestMethod.POST)
	public String signup(
			@RequestParam(value = "str_email01") String email1,
			@RequestParam(value = "str_email02") String email2,
			@RequestParam(value = "userPwd") String pwd,
			@RequestParam(value = "nickname") String nickname,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "str_phone01") String phone01,
			@RequestParam(value = "str_phone02") String phone02,
			@RequestParam(value = "str_phone03") String phone03,
			@RequestParam(value = "birthday") String birthday,
			@RequestParam(value = "gender") boolean gender,
			@RequestParam(value = "recv_email") boolean recv_email,
			@RequestParam(value = "social") boolean social,
			@RequestParam(value = "social_info") String social_info,
			@RequestParam(value = "profile_pic") String profile_pic,
			HttpServletRequest request
	) {

		String tempStr = request.getParameter("social_id");
		int social_id = 0;
		if (tempStr == null || !tempStr.equals("")) {
			Integer.parseInt(tempStr);
		}
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
				.social_info(social_info.equals("") ? "웹페이지" : social_info)
				.social_id(social_id)
				.build();
		user.addUserRole(UserRole.USER);
		log.info("profile_pic : " + profile_pic.length());
		if (profile_pic != null) {
			user.setProfile_pic(profile_pic);
		}
		if(profile_pic.length()==0){
			user.setProfile_pic("/image/icon/user/user.png");
		}

		userRepository.save(user);

		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/email_check", method = RequestMethod.POST)
	public Map<String, String> email_check(@RequestBody HashMap<String, String> data) {

		log.info("email ajax : " + data.get("mail"));
		Map<String, String> map = new HashMap<String, String>();
		String a = userRepository.email_check(data.get("mail"));

		if (a == null || a.length() == 0) {
			map.put("msg", "1");
		} else {
			map.put("msg", "0");
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/nick_check", method = RequestMethod.POST)
	public Map<String, String> nick_check(@RequestBody HashMap<String, String> data) {

		Map<String, String> map = new HashMap<String, String>();
		String a = userRepository.nick_check(data.get("nickname"));

		if (a == null || a.length() == 0) {
			map.put("msg", "1");
		} else {
			map.put("msg", "0");
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/sendsms", method = RequestMethod.POST)
	public Map<String, String> sendsms(@RequestBody HashMap<String, String> data) {
		Map<String, String> map = new HashMap<String, String>();
		String num = smsService.RandomNum();
		log.info("phone:" + data.get("phones"));
		log.info("num:" + num);
		map.put("msg", "성공");
		map.put("num", num);
//		smsService.sendSMS(data.get("phones"), num);
		return map;
	}

	//	@RequestMapping(value="/signup",method = RequestMethod.GET)
	@GetMapping("/signup")
	@PostMapping("/signup")
	public String signup( Model model) {
		Policy policy = new Policy();
		policy.model_add(model, policy);
		return "user/signup";
	}

	@PostMapping(value = "/user/deleteAccount/process")
	public String deleteAccount(HttpServletRequest request,
								Principal principal) {
		String redirectURL = "redirect:/";

		redirectURL = accountDelete.execute(request, principal, userRepository);
		return redirectURL;
	}

//mypage updata page
	@RequestMapping("/mypage_update")
	public String mypagedatachange(@RequestParam("file")List<MultipartFile> fileList, HttpServletRequest request,
								   RedirectAttributes redirectAttributes, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		} else {

			User user = userService.select(principal.getName());

			List<String> filePath = null;
			Map<String, String> mypgup = new HashMap<>();

			filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
					1, MAX_UPLOAD_COUNT, UploadPathType.USER_PIC, request);

			if(filePath != null){
				user.setProfile_pic(filePath.get(0) + "/" + filePath.get(1));
			}

			request.getParameter("nic");
			request.getParameter("first-phone-number");
			request.getParameter("second_num");
			request.getParameter("third_num");
			request.getParameter("birthday");
			request.getParameter("gender");

			boolean gender;
			if(request.getParameter("gender").equals("true")){
				gender = true;
			}else{
				gender = false;
			}

			user.setUser_nic(request.getParameter("nic"));
			user.setUser_phone(request.getParameter("first-phone-number") + request.getParameter("second_num") + request.getParameter("third_num"));
			user.setUser_birth(request.getParameter("birthday"));
			user.setUser_sex(gender);
			userService.update(user);


			return "redirect:/mypage";
		}
	}
	@ResponseBody
	@PostMapping(value = "/mypropicdel")
	public Map<String, String>  mypropicdele(Principal principal, Model model, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		if (principal == null) {
			map.put("msg","1");
		} else {
			User user = userService.select(principal.getName());
			log.info("여기와요프로필삭제");
			try{
				String imageFile = user.getProfile_pic();
				log.info("여기와요 :"+ imageFile);
				if(!imageFile.equals("/image/icon/user/user.png")){
					String r = request.getSession().getServletContext().getRealPath("/");
					int idx =  r.indexOf("main");
					String imgpath =r.substring(0, idx)+"main/resources/static"+ imageFile;
					log.info("imgpath 확인 :" + imgpath);
					File file = FileUtils.getFile(imgpath);
					imageFile = imageFile.replace("/image/user_pic/","/image/upload/");
					File fileToMove = FileUtils.getFile(r.substring(0, idx)+"main/resources/static"+ imageFile);
					FileUtils.moveFile(file, fileToMove);
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
			user.setProfile_pic("/image/icon/user/user.png");
			userService.update(user);

			map.put("msg","0");
		}
		return map;
	}

	//CHOI
	@GetMapping(value = "/mypage")
	public String myinfo(Principal principal, Model model) {
		if (principal == null) {
			return "redirect:/login";
		} else {
			UserDTO userDTO = userService.DTOselect(principal.getName());
			model.addAttribute("UserDTO", userDTO);

			return "user/mypage";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_myreview")
	public String myreview(Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Review> myRevList = reviewService.findAllByCor_no(authUserModel.getUser_no());
			model.addAttribute("my_rev", myRevList);

			log.info("my RevList확인 :" , myRevList);
			return "user/mypage_myreview";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_mycorse")
	public String mycorse(Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Course> myCorList = corService.findCorByUserNo(authUserModel.getUser_no());
			model.addAttribute("my_cor", myCorList);

			return "user/mypage_mycorse";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_myplace")
	public String myplace(Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		}else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Location> myLocList = locService.findLocByUserNo(authUserModel.getUser_no());
			model.addAttribute("my_place", myLocList);

			return "user/mypage_myplace";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_mylike")
	public String mylike(Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Location> myLocList = locService.findLocByUserNo(authUserModel.getUser_no());
			model.addAttribute("my_like", myLocList);

			return "user/mypage_mylike";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_recent_view_corse")
	public String myrecview(Principal principal, Model model) {
		if (principal == null) {
			return "redirect:/login";
		} else {
			UserDTO userDTO = userService.DTOselect(principal.getName());
			model.addAttribute("UserDTO", userDTO);

			return "user/mypage_recent_view_corse";
		}
	}

	// 캘린더
	@GetMapping(value = "/service/calender")
	public String calender(Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		return "service/service_calender";
	}

	// 페이지 업로드
	@ResponseBody
	@PostMapping(value = "/user/cal_all")
	public Map<String, Calender> cal_all(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		Map<String, Calender> map = new HashMap<String, Calender>();
		if (principal == null) {
		} else {
			List<Calender> caldata = calenderService.Cal_select(principal.getName());
			for (int i = 0; i < caldata.size(); i++) {
				map.put("evt" + i, caldata.get(i));
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

		return "1";
	}

	@ResponseBody
	@PostMapping(value = "/user/cal_update")
	public String cal_update(@RequestBody HashMap<String, String> data, Principal principal) {

		Calender calender = calenderService.cal_select_no(data.get("_id"));
		calender.setTitle(data.get("title"));
		calender.setAll_day(data.get("allDay").equals("true") ? true : false);
		calender.setStart(data.get("start"));
		calender.setEnd(data.get("end"));
		calender.setRoad(data.get("road"));
		calender.setRoad2(data.get("road2"));
		calender.setText(data.get("text"));
		calender.setColor(data.get("color"));

		calenderService.update(calender);

		return "1";
	}

	@ResponseBody
	@PostMapping(value = "/user/cal_delete")
	public String cal_delete(@RequestBody HashMap<String, String> data, Principal principal) {

		Calender calender = calenderService.cal_select_no(data.get("_id"));
		calender.setCal_Activation(false);
		calenderService.update(calender);
		return "1";
	}

	@ResponseBody
	@PostMapping(value = "/user/cal_update_d")
	public String cal_update_d(@RequestBody HashMap<String, String> data, Principal principal) {

		Calender calender = calenderService.cal_select_no(data.get("_id"));
		calender.setStart(data.get("start"));
		calender.setEnd(data.get("end"));
		calenderService.update(calender);

		return "1";
	}

	//CHOI
	@ResponseBody
	@PostMapping(value = "/idfind")
	public Map<String, String> idfind(@RequestBody HashMap<String, String> data) {
		Map<String, String> map = new HashMap<String, String>();
//		log.info("findfindfind");
		log.info("fkalfhalfksfkls" + data.get("phones"));
		List<String> idid = userService.findUserId(data.get("phones"));
		log.info(idid);
		for (int i = 0; i < idid.size(); i++) {
			map.put(i + "i", idid.get(i));
		}
		return map;
	}
	//CHOI
	@GetMapping(value = "/NewPassword/{ranadd}")
	public String NeuPw(@PathVariable("ranadd") String ranadd, Model model, HttpServletResponse response) throws Exception {
		User user = userService.findPw(ranadd);
		if (user == null) {
			ScriptUtils scriptUtils = new ScriptUtils();
			scriptUtils.alertAndMovePage(response, "잘못된 접근입니다. 홈 화면으로 이동합니다.", "/");

			return "/";
		}
		else {
			model.addAttribute("User", user);
			return "/user/NewPassword";
		}
	}

	@PostMapping(value = "/passwordsave")
	public String Newpwsave(HttpServletRequest request){
		User user = userService.savenewpw( request.getParameter("user_no"));
		user.setUser_pw(passwordEncoder.encode(request.getParameter("npk")));
		userService.update(user);

		return "/user/loginPage";
	}

	@ResponseBody
	@PostMapping(value = "/password_ckd")
	public Map<String, String> password_ck(@RequestBody HashMap<String, String> data, Principal principal) {
		Map<String, String> map = new HashMap<String, String>();
		User user = userService.select(principal.getName());
		if(passwordEncoder.matches(data.get("password"), user.getUser_pw())){
			map.put("msg","1");
		}else {
			map.put("msg","0");
		}
		return map;
	}

	@GetMapping(value = "/lovedata_delete")
	public String lovedata_delete(HttpServletRequest request){

		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/admin/user")
	public String admin_user(HttpServletRequest request, Model model){
		List<User> User = userService.finduserAll();
		model.addAttribute("user", User);

		return "admin/admin_user";
	}

	//alert 창 설정 class
	public class ScriptUtils {

		public void init(HttpServletResponse response) {
			response.setContentType("text/html; charset=euc-kr");
			response.setCharacterEncoding("euc-kr");
		}

		public void alert(HttpServletResponse response, String alertText) throws IOException {
			init(response);
			PrintWriter out = response.getWriter();
			out.println("<script>alert('" + alertText + "');</script> ");
			out.flush();
		}

		public void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage)
				throws IOException {
			init(response);
			PrintWriter out = response.getWriter();
			out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
			out.flush();
		}

		public void alertAndBackPage(HttpServletResponse response, String alertText) throws IOException {
			init(response);
			PrintWriter out = response.getWriter();
			out.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
			out.flush();
		}
	}
}