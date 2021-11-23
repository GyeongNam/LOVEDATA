package com.project.love_data.controller;

import com.project.love_data.businessLogic.MailService;
import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.CalenderDTO;
import com.project.love_data.dto.UserDTO;
import com.project.love_data.model.resource.QuestionsImage;
import com.project.love_data.model.resource.ReviewImage;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.project.love_data.util.ConstantValues.*;
import static com.project.love_data.util.ConstantValues.Linux_Image_Upload_Path;

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
	private MailService mailService;
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
	ReviewImageService reviewImageService;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	CommentService cmtService;
	@Autowired
	ServiceCenterService serviceCenterService;
	@Autowired
	UserLikeLocService userLikeLocService;
	@Autowired
	UserLikeCorService userLikeCorService;
	@Autowired
	UserRecentLocService userRecentLocService;
	@Autowired
	UserRecentCorService userRecentCorService;
	@Autowired
	QuestionsImageService questionsImageService;


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
		smsService.sendSMS(data.get("phones"), num);
		return map;
	}

	//2021-11-24
	@ResponseBody
	@PostMapping(value = "/admin_send_sms")
	public Map<String, String> adminsms(
			@RequestParam("list[]") String[] list,
			@RequestParam("smscontent")String smscontent) {
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0 ; i< list.length; i++){
			if(!list[i].equals("on")) {
				User user = userService.select(Long.parseLong(list[i]));
				if (user != null) {
					smsService.managersendSMS(user.getUser_phone(), smscontent);
				}
			}
		}

//		log.info("list : "+ list.length);
//		log.info("smstestcontent: "+ smscontent);
		map.put("msg", "sms 성공");
		return map;
	}
	//email
	@ResponseBody
	@PostMapping(value = "/admin_send_email")
	public Map<String, String> adminemail(
			@RequestParam("list[]") String[] list,
			@RequestParam("smscontent")String smscontent) {
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0 ; i< list.length; i++){
			if(!list[i].equals("on")){
				User user = userService.select(Long.parseLong(list[i]));
				if(user != null) {
					mailService.adminmailSend(user.getUser_email(), smscontent);
				}
			}
		}

//		log.info("list : "+ list.length);
//		log.info("smstestcontent: "+ smscontent);
		map.put("msg", "이메일 성공");
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
					1, MAX_UPLOAD_COUNT, PathType.USER_PIC, request);

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
	@GetMapping(value = "/mypage_mycomment/{page}")
	public String mycomment(@PathVariable("page") String page, Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Comment> myComList = cmtService.findAllByLocNo(authUserModel.getUser_no());
			List<Comment> comlist_page = null;
			List<Integer> comPageNumList = new ArrayList<>();

			model.addAttribute("search", false);
			long qu_size = myComList.size();
			long qu_page = myComList.size()/8;
			long qu_page_na = myComList.size()%8;
			long qu_page_size = qu_page/10;
			long qu_page_size_na = qu_page%10;
			Date today = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("qu_time", format1.format(today));

			if(qu_page_size_na >= 1){
				qu_page_size = qu_page_size+1;
				model.addAttribute("qu_page_size",qu_page_size);
			}
			else {
				model.addAttribute("qu_page_size",qu_page_size);
			}


			if(qu_page_na >= 1){
				qu_page = qu_page+1;
				model.addAttribute("qu_page",qu_page);
			}
			else {
				model.addAttribute("qu_page",qu_page);
			}
			model.addAttribute("qu_size",myComList.size());

			// 페이지네이션
			long j=0;

			if(myComList.size()<8){
				model.addAttribute("my_com",myComList);
				for (Comment comEntity : myComList) {
					comPageNumList.add(cmtService.getCommentCurrentPageNum(comEntity.getCmtNo()));
				}
			}else {
				for (int i = 0; i < qu_size; i++) {
					comlist_page = myComList.subList(0,8);

					if (i % 8 == 0) {
						j = j + 1;
						if (j == Long.parseLong(page)) {
							model.addAttribute("my_com",comlist_page);
							for (Comment comEntity : comlist_page) {
								comPageNumList.add(cmtService.getCommentCurrentPageNum(comEntity.getCmtNo()));
							}
							break;
						} else {
							myComList.subList(0,8).clear();

							if(myComList.size()<8){
								model.addAttribute("my_com",myComList);
								for (Comment comEntity : myComList) {
									comPageNumList.add(cmtService.getCommentCurrentPageNum(comEntity.getCmtNo()));
								}
								break;
							}
						}
					}
				}
			}

			model.addAttribute("comPageNumList", comPageNumList);


			return "user/mypage_mycomment";
		}
	}
	//CHOI
	@GetMapping(value = "/mypage_myreview/{page}")
	public String myreview(@PathVariable("page") String page, Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Review> myRevList = reviewService.findAllByUser_no(authUserModel.getUser_no());

//			model.addAttribute("my_rev", myRevList);

			List<Review> revlist_page = null;
			model.addAttribute("search", false);
			long qu_size = myRevList.size();
			long qu_page = myRevList.size()/8;
			long qu_page_na = myRevList.size()%8;
			long qu_page_size = qu_page/10;
			long qu_page_size_na = qu_page%10;
			Date today = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("qu_time", format1.format(today));

			if(qu_page_size_na >= 1){
				qu_page_size = qu_page_size+1;
				model.addAttribute("qu_page_size",qu_page_size);
			}
			else {
				model.addAttribute("qu_page_size",qu_page_size);
			}


			if(qu_page_na >= 1){
				qu_page = qu_page+1;
				model.addAttribute("qu_page",qu_page);
			}
			else {
				model.addAttribute("qu_page",qu_page);
			}
			model.addAttribute("qu_size",myRevList.size());

			// 페이지네이션
			long j=0;

			if(myRevList.size()<8){
				model.addAttribute("my_rev",myRevList);
			}else {
				for (int i = 0; i < qu_size; i++) {
					revlist_page = myRevList.subList(0,8);

					if (i % 8 == 0) {
						j = j + 1;
						if (j == Long.parseLong(page)) {
							model.addAttribute("my_rev",revlist_page);
							break;
						} else {
							myRevList.subList(0,8).clear();

							if(myRevList.size()<8){
								model.addAttribute("my_rev",myRevList);
								break;
							}
						}
					}
				}
			}


			return "user/mypage_myreview";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_mycorse/{page}")
	public String mycorse(@PathVariable("page") String page, Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Course> myCorList = corService.findCorByUserNo(authUserModel.getUser_no());
			model.addAttribute("my_cor", myCorList);

			List<Course> corlist_page = null;
			model.addAttribute("search", false);
			long qu_size = myCorList.size();
			long qu_page = myCorList.size()/8;
			long qu_page_na = myCorList.size()%8;
			long qu_page_size = qu_page/10;
			long qu_page_size_na = qu_page%10;
			Date today = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("qu_time", format1.format(today));

			if(qu_page_size_na >= 1){
				qu_page_size = qu_page_size+1;
				model.addAttribute("qu_page_size",qu_page_size);
			}
			else {
				model.addAttribute("qu_page_size",qu_page_size);
			}


			if(qu_page_na >= 1){
				qu_page = qu_page+1;
				model.addAttribute("qu_page",qu_page);
			}
			else {
				model.addAttribute("qu_page",qu_page);
			}
			model.addAttribute("qu_size",myCorList.size());

			// 페이지네이션
			long j=0;

			if(myCorList.size()<8){
				model.addAttribute("my_cor",myCorList);
			}else {
				for (int i = 0; i < qu_size; i++) {
					corlist_page = myCorList.subList(0,8);

					if (i % 8 == 0) {
						j = j + 1;
						if (j == Long.parseLong(page)) {
							model.addAttribute("my_cor",corlist_page);
							break;
						} else {
							myCorList.subList(0,8).clear();

							if(myCorList.size()<8){
								model.addAttribute("my_cor",myCorList);
								break;
							}
						}
					}
				}
			}

			return "user/mypage_mycorse";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_myplace/{page}")
	public String myplace(@PathVariable("page") String page, Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		}else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<Location> myLocList = locService.findLocByUserNo(authUserModel.getUser_no());

//			model.addAttribute("my_place", myLocList);

			List<Location> locationplace_page = null;
			model.addAttribute("search", false);
			long qu_size = myLocList.size();
			long qu_page = myLocList.size()/8;
			long qu_page_na = myLocList.size()%8;
			long qu_page_size = qu_page/10;
			long qu_page_size_na = qu_page%10;
			Date today = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("qu_time", format1.format(today));

			if(qu_page_size_na >= 1){
				qu_page_size = qu_page_size+1;
				model.addAttribute("qu_page_size",qu_page_size);
			}
			else {
				model.addAttribute("qu_page_size",qu_page_size);
			}


			if(qu_page_na >= 1){
				qu_page = qu_page+1;
				model.addAttribute("qu_page",qu_page);
			}
			else {
				model.addAttribute("qu_page",qu_page);
			}
			model.addAttribute("qu_size",myLocList.size());

			// 페이지네이션
			long j=0;

			if(myLocList.size()<8){
				model.addAttribute("my_place",myLocList);
			}else {
				for (int i = 0; i < qu_size; i++) {
					locationplace_page = myLocList.subList(0,8);

					if (i % 8 == 0) {
						j = j + 1;
						if (j == Long.parseLong(page)) {
							model.addAttribute("my_place",locationplace_page);
							break;
						} else {
							myLocList.subList(0,8).clear();

							if(myLocList.size()<8){
								model.addAttribute("my_place",myLocList);
								break;
							}
						}
					}
				}
			}

			return "user/mypage_myplace";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_mylike/{page}")
	public String mylike(@PathVariable("page") String page, Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<UserLikeLoc> myLoclikeList = userLikeLocService.selectByAllUserNo(authUserModel.getUser_no());
			List<Location> location = new ArrayList<>();
			for(int i =0; i < myLoclikeList.size(); i++)
			{
				Location locationlike = locService.selectLoc(myLoclikeList.get(i).getLoc_no());
				location.add(locationlike);
			}
//			model.addAttribute("my_Loclike", location);

			List<Location> locations_page = null;
			model.addAttribute("search", false);
			long qu_size = location.size();
			long qu_page = location.size()/8;
			long qu_page_na = location.size()%8;
			long qu_page_size = qu_page/10;
			long qu_page_size_na = qu_page%10;
			Date today = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("qu_time", format1.format(today));

			if(qu_page_size_na >= 1){
				qu_page_size = qu_page_size+1;
				model.addAttribute("qu_page_size",qu_page_size);
			}
			else {
				model.addAttribute("qu_page_size",qu_page_size);
			}


			if(qu_page_na >= 1){
				qu_page = qu_page+1;
				model.addAttribute("qu_page",qu_page);
			}
			else {
				model.addAttribute("qu_page",qu_page);
			}
			model.addAttribute("qu_size",location.size());

			// 페이지네이션
			long j=0;

			if(location.size()<8){
				model.addAttribute("my_Loclike",location);
			}else {
				for (int i = 0; i < qu_size; i++) {
					locations_page = location.subList(0,8);

					if (i % 8 == 0) {
						j = j + 1;
						if (j == Long.parseLong(page)) {
							model.addAttribute("my_Loclike",locations_page);
							break;
						} else {
							location.subList(0,8).clear();

							if(location.size()<8){
								model.addAttribute("my_Loclike",location);
								break;
							}
						}
					}
				}
			}

			return "user/mypage_mylike";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_myCorlike/{page}")
	public String myCorlike(@PathVariable("page") String page, Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<UserLikeCor> myCorlikeList = userLikeCorService.selectByAllUserNo(authUserModel.getUser_no());
			List<Course> course = new ArrayList<>();
			for(int i =0; i < myCorlikeList.size(); i++)
			{
				Course courselike = corService.selectCor(myCorlikeList.get(i).getCor_no());
				course.add(courselike);
			}
//			model.addAttribute("my_Corlike", course);

			List<Course> course_page = null;
			model.addAttribute("search", false);
			long qu_size = course.size();
			long qu_page = course.size()/8;
			long qu_page_na = course.size()%8;
			long qu_page_size = qu_page/10;
			long qu_page_size_na = qu_page%10;
			Date today = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("qu_time", format1.format(today));

			if(qu_page_size_na >= 1){
				qu_page_size = qu_page_size+1;
				model.addAttribute("qu_page_size",qu_page_size);
			}
			else {
				model.addAttribute("qu_page_size",qu_page_size);
			}


			if(qu_page_na >= 1){
				qu_page = qu_page+1;
				model.addAttribute("qu_page",qu_page);
			}
			else {
				model.addAttribute("qu_page",qu_page);
			}
			model.addAttribute("qu_size",course.size());

			// 페이지네이션
			long j=0;

			if(course.size()<8){
				model.addAttribute("my_Corlike",course);
			}else {
				for (int i = 0; i < qu_size; i++) {
					course_page = course.subList(0,8);

					if (i % 8 == 0) {
						j = j + 1;
						if (j == Long.parseLong(page)) {
							model.addAttribute("my_Corlike",course_page);
							break;
						} else {
							course.subList(0,8).clear();

							if(course.size()<8){
								model.addAttribute("my_Corlike",course);
								break;
							}
						}
					}
				}
			}

			return "user/mypage_myCorlike";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_recent_view_location")
	public String recviewloc(Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<UserRecentLoc> myRecentLoc = userRecentLocService.selectByUserNo(authUserModel.getUser_no());
			List<Location> location = new ArrayList<>();
			for(int i=0; i< myRecentLoc.size(); i++)
			{
				Location viewlocation = locService.selectLoc(myRecentLoc.get(i).getLoc_no());
				location.add(viewlocation);
			}
			model.addAttribute("RecviewLoc", location);

			return "user/mypage_recent_view_location";
		}
	}

	//CHOI
	@GetMapping(value = "/mypage_recent_view_corse")
	public String recviewcor(Authentication authentication, Model model) {
		if (authentication == null) {
			return "redirect:/login";
		} else {
			AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();
			List<UserRecentCor> myRecentCor = userRecentCorService.selectByUserNo(authUserModel.getUser_no());
			List<Course> course = new ArrayList<>();
			for(int i=0; i< myRecentCor.size(); i++)
			{
				Course viewCourse = corService.selectCor(myRecentCor.get(i).getCor_no());
				course.add(viewCourse);
			}
			model.addAttribute("RecviewCor", course);

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

	@PostMapping(value = "/lovedata_delete")
	public String lovedata_delete(HttpServletRequest request){
		String user_no = request.getParameter("user_no");
		User user = userService.select(Long.parseLong(user_no));
		List<Comment> comment = cmtService.findAllByUserNo(Long.parseLong(user_no));
		List<Review> reviews = reviewService.findAllByUser_no(Long.parseLong(user_no));
		List<ReviewImage> reviewImages = reviewImageService.getImage_UserNo(Long.parseLong(user_no));
		List<Questions> questions = serviceCenterService.qu_findAllByUser_no(user_no);
		List<QuestionsImage> questionsImages = questionsImageService.user_no_imgselect(user_no);

		List<Calender> calenders = calenderService.Cal_select(user.getUser_email());
		user.set_deleted(true);
		user.setUser_Activation(false);
		userService.update(user);
		for(int i = 0; i<comment.size(); i++){
			comment.get(i).set_deleted(true);
			cmtService.update(comment.get(i));
		}
		for(int i = 0; i<reviews.size(); i++){
			reviews.get(i).set_deleted(true);
			reviewService.update(reviews.get(i));
		}
		for(int i = 0; i<reviewImages.size(); i++){
			reviewImages.get(i).set_deleted(true);
			reviewImageService.update(reviewImages.get(i));

			try {
				// 기존 이미지 review -> upload
				String imgpath;
				if ("Windows_NT".equals(System.getenv().get("OS"))) {
					String r = request.getSession().getServletContext().getRealPath("/");
					int idx = r.indexOf("main");
					imgpath = r.substring(0, idx) + "main/resources/static" + reviewImages.get(i).getImg_url();

					File file = FileUtils.getFile(imgpath);
					File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/REV^" + reviewImages.get(i).getImg_uuid());
					FileUtils.moveFile(file, fileToMove);
				} else {
					imgpath = Linux_Image_Upload_Path + "review/" + reviewImages.get(i).getImg_uuid();

					File file = FileUtils.getFile(imgpath);
					File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path + "upload/REV^" + reviewImages.get(i).getImg_uuid());
					FileUtils.moveFile(file, fileToMove);
				}

			} catch(IOException e){
				e.printStackTrace();
			}
		}
		for(int i = 0; i<questions.size(); i++){
			questions.get(i).setQu_activation(false);
			serviceCenterService.qu_update(questions.get(i));
		}
		for(int i = 0; i<questionsImages.size(); i++){
			questionsImages.get(i).setQu_img_Activation(false);
			questionsImageService.update(questionsImages.get(i));

			try {
				// 기존 이미지 qna -> upload
				String imgpath;
				if ("Windows_NT".equals(System.getenv().get("OS"))) {
					String r = request.getSession().getServletContext().getRealPath("/");
					int idx = r.indexOf("main");
					imgpath = r.substring(0, idx) + "main/resources/static/image/qna/" + questionsImages.get(i).getQu_img_url();

					File file = FileUtils.getFile(imgpath);
					File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/QNA^" + questionsImages.get(i).getQu_img_url());
					FileUtils.moveFile(file, fileToMove);
				} else {
					imgpath = Linux_Image_Upload_Path + "qna/" + questionsImages.get(i).getQu_img_url();

					File file = FileUtils.getFile(imgpath);
					File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path + "upload/QNA^" + questionsImages.get(i).getQu_img_url());
					FileUtils.moveFile(file, fileToMove);
				}

			} catch(IOException e){
				e.printStackTrace();
			}
		}
		for(int i = 0; i<calenders.size(); i++){
			calenders.get(i).setCal_Activation(false);
			calenderService.update(calenders.get(i));
		}
		return "redirect:/logout";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("admin/SendMessage")
	public String adminSendMessage(HttpServletRequest request, Model model) {
		List<User> User = userService.finduserAll();
		model.addAttribute("user", User);

		return "/admin/admin_sendMessage_user";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/admin/SendMessage/search/{menu}/{text}/{page}")
	public String Msgsearch( @PathVariable("page") String page,
							 @PathVariable("text") String text,
							 @PathVariable("menu") String menu,
							 Model model,
							 Principal principal)  {
		List<User> user = userService.search_user(menu, text);
		model.addAttribute("user", user);
		return "admin/admin_sendMessage_user";
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/admin/user/{page}")
	public String admin_user(@PathVariable("page") String page, HttpServletRequest request, Model model){
		List<User> User = userService.finduserAll();
		model.addAttribute("search", false);
		List<String> loc = new ArrayList<>();
		List<String> cor = new ArrayList<>();
		List<String> re = new ArrayList<>();
		List<String> com = new ArrayList<>();
		List<User> user_page = null;
		List<String> user_loc = null;
		List<String> user_cor = null;
		List<String> user_re = null;
		List<String> user_com = null;
		long no_size = User.size();
		long no_page = User.size()/10;
		long no_page_na = User.size()%10;
		long no_page_size = no_page/10;
		long no_page_size_na = no_page%10;

		for(int i= 0; i<User.size(); i++){
			List<Location> locations = locService.findLocByUserNo(User.get(i).getUser_no());
			loc.add(Integer.toString(locations.size()));
			List<Course> courses = corService.findCorByUserNo(User.get(i).getUser_no());
			cor.add(Integer.toString(courses.size()));
			List<Review> reviews = reviewService.findAllByUser_no(User.get(i).getUser_no());
			re.add(Integer.toString(reviews.size()));
			List<Comment> comments = cmtService.findAllByUserNo(User.get(i).getUser_no());
			com.add(Integer.toString(comments.size()));
		}

		if(no_page_size_na >= 1){
			no_page_size = no_page_size+1;
			model.addAttribute("qu_page_size",no_page_size);
		}
		else {
			model.addAttribute("qu_page_size",no_page_size);
		}

		if(no_page_na >= 1){
			no_page = no_page+1;
			model.addAttribute("qu_page",no_page);
		}
		else {
			model.addAttribute("qu_page",no_page);
		}
		model.addAttribute("qu_size",User.size());

		long j=0;

		if(User.size()<10){
			model.addAttribute("user",User);
			model.addAttribute("loc", loc);
			model.addAttribute("cor", cor);
			model.addAttribute("re", re);
			model.addAttribute("com", com);

		}else {
			for (int i = 0; i < no_size; i++) {
				user_page = User.subList(0,10);
				user_loc = loc.subList(0,10);;
				user_cor = cor.subList(0,10);;
				user_re = re.subList(0,10);;
				user_com = com.subList(0,10);;
				if (i % 10 == 0) {
					j = j + 1;
					if (j == Long.parseLong(page)) {
						model.addAttribute("user",user_page);
						model.addAttribute("loc", user_loc);
						model.addAttribute("cor", user_cor);
						model.addAttribute("re", user_re);
						model.addAttribute("com", user_com);
						break;
					} else {
						User.subList(0,10).clear();
						loc.subList(0,10).clear();
						cor.subList(0,10).clear();
						re.subList(0,10).clear();
						com.subList(0,10).clear();
						if(User.size()<10){
							model.addAttribute("user",User);
							model.addAttribute("loc", loc);
							model.addAttribute("cor", cor);
							model.addAttribute("re", re);
							model.addAttribute("com", com);
							break;
						}
					}
				}
			}
		}

		return "admin/admin_user";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/admin/user/search/{menu}/{text}/{page}")
	public String Nsearch( @PathVariable("page") String page,
						   @PathVariable("text") String text,
						   @PathVariable("menu") String menu,
						   Model model,
						   Principal principal)  {
		List<User> User = userService.search_user(menu, text);
		model.addAttribute("search", true);
		List<String> loc = new ArrayList<>();
		List<String> cor = new ArrayList<>();
		List<String> re = new ArrayList<>();
		List<String> com = new ArrayList<>();
		List<User> user_page = null;
		List<String> user_loc = null;
		List<String> user_cor = null;
		List<String> user_re = null;
		List<String> user_com = null;
		long no_size = User.size();
		long no_page = User.size()/10;
		long no_page_na = User.size()%10;
		long no_page_size = no_page/10;
		long no_page_size_na = no_page%10;

		for(int i= 0; i<User.size(); i++){
			List<Location> locations = locService.findLocByUserNo(User.get(i).getUser_no());
			loc.add(Integer.toString(locations.size()));
			List<Course> courses = corService.findCorByUserNo(User.get(i).getUser_no());
			cor.add(Integer.toString(courses.size()));
			List<Review> reviews = reviewService.findAllByUser_no(User.get(i).getUser_no());
			re.add(Integer.toString(reviews.size()));
			List<Comment> comments = cmtService.findAllByUserNo(User.get(i).getUser_no());
			com.add(Integer.toString(comments.size()));
		}

		if(no_page_size_na >= 1){
			no_page_size = no_page_size+1;
			model.addAttribute("qu_page_size",no_page_size);
		}
		else {
			model.addAttribute("qu_page_size",no_page_size);
		}

		if(no_page_na >= 1){
			no_page = no_page+1;
			model.addAttribute("qu_page",no_page);
		}
		else {
			model.addAttribute("qu_page",no_page);
		}
		model.addAttribute("qu_size",User.size());

		long j=0;

		if(User.size()<10){
			model.addAttribute("user",User);
			model.addAttribute("loc", loc);
			model.addAttribute("cor", cor);
			model.addAttribute("re", re);
			model.addAttribute("com", com);

		}else {
			for (int i = 0; i < no_size; i++) {
				user_page = User.subList(0,10);
				user_loc = loc.subList(0,10);;
				user_cor = cor.subList(0,10);;
				user_re = re.subList(0,10);;
				user_com = com.subList(0,10);;
				if (i % 10 == 0) {
					j = j + 1;
					if (j == Long.parseLong(page)) {
						model.addAttribute("user",user_page);
						model.addAttribute("loc", user_loc);
						model.addAttribute("cor", user_cor);
						model.addAttribute("re", user_re);
						model.addAttribute("com", user_com);
						break;
					} else {
						User.subList(0,10).clear();
						loc.subList(0,10).clear();
						cor.subList(0,10).clear();
						re.subList(0,10).clear();
						com.subList(0,10).clear();
						if(User.size()<10){
							model.addAttribute("user",User);
							model.addAttribute("loc", loc);
							model.addAttribute("cor", cor);
							model.addAttribute("re", re);
							model.addAttribute("com", com);
							break;
						}
					}
				}
			}
		}


		return "admin/admin_user";
	}
	// 어드민 유저
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/admin_user_detail/{num}")
	public String admin_user_datail(@PathVariable("num") Long num, Model model) {
		User user = userService.select(num);
		List<Location> locationList = locService.findLocByUserNo(num);
		List<Course> courseList = corService.findCorByUserNo(num);
		List<Review> reviewList = reviewService.findAllByUser_no(num);
		List<Comment> commentList = cmtService.findAllByUserNo(num);
		List<UserSuspension> userSuspensionList = userService.su_findAllByUser_no(num);
		// 리포트 신고 기록 추가

		model.addAttribute("user" ,user);
		model.addAttribute("loc" ,locationList);
		model.addAttribute("cor" ,courseList);
		model.addAttribute("rev" ,reviewList);
		model.addAttribute("com" ,commentList);
		model.addAttribute("us" ,userSuspensionList);

		return "admin/admin_user_detail";
	}
	// 유저 정지
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/admin/user_suspension")
	public String user_suspension(HttpServletRequest request, Principal principal) {
		long user_no = Long.parseLong(request.getParameter("user_no"));
		User user = userService.select(user_no);
		List<UserSuspension> userSuspensionList = userService.findStopByUser_no(user_no, "1");

		Date today = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE,Integer.parseInt(request.getParameter("stop_day")));

		String progress;
		if(userSuspensionList.size() > 0){
			progress = "2";
		}else {
			progress = "1";
		}

		UserSuspension userSuspension = UserSuspension.builder()
				.user_no(user_no)
				.re_no(Long.parseLong("0"))
				.re_content(request.getParameter("re_content"))
		 		.start_day(format1.format(today))
				.stop_day(request.getParameter("stop_day"))
				.end_day(format1.format(cal.getTime()))
				.progress(progress)
				.build();
		userService.su_update(userSuspension);

		user.setUser_Activation(false);
		userService.update(user);

		return "redirect:/admin_user_detail/"+user_no;
	}

	// 정지 해제
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/admin/user_release/{user_no}")
	public String user_release(@PathVariable("user_no") Long user_no){
		List<UserSuspension> StopList = userService.findStopByUser_no(user_no, "1");
		List<UserSuspension> WaitingList = userService.findStopByUser_no(user_no, "2");
		User user = userService.select(user_no);
		for(int i = 0; i<StopList.size(); i++){
			StopList.get(i).setProgress("0");
			userService.su_update(StopList.get(i));
		}

		if(WaitingList.size()>0){
			WaitingList.get(0).setProgress("1");
			userService.su_update(WaitingList.get(0));
		}else {
			user.setUser_Activation(true);
			userService.update(user);
		}

		return "redirect:/admin_user_detail/"+user_no;
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