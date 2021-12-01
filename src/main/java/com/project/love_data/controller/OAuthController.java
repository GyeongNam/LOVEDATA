package com.project.love_data.controller;

import com.project.love_data.businessLogic.service.ControllerScriptUtils;
import com.project.love_data.model.oauth.KakaoAuthToken;
import com.project.love_data.model.oauth.NaverAuthToken;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.model.user.KakaoUserInfo;
import com.project.love_data.model.user.NaverUserInfo;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.security.service.AlreadyRegisteredEmailException;
import com.project.love_data.security.service.UserDetailsService;
import com.project.love_data.businessLogic.oauth.login.*;
import com.project.love_data.businessLogic.oauth.logout.LogoutProcess;
import com.project.love_data.businessLogic.oauth.logout.LogoutProcessKakao;
import com.project.love_data.businessLogic.oauth.logout.LogoutUser;
import com.project.love_data.businessLogic.oauth.logout.LogoutUserKakao;
import com.project.love_data.util.EmailParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Log4j2
@Controller
public class OAuthController {
    private String decodedURL;
    private AcessCodeRequest acessCodeRequest;
    private TokenRequest tokenRequest;
    private OAuthToken token;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    ControllerScriptUtils scriptUtils;

    @GetMapping(value = "/login_kakao")
    public String kakaoLogin(
            HttpServletRequest request,
            HttpSessionCsrfTokenRepository csrfTokenRepository
    ) {
        decodedURL = null;
        acessCodeRequest = new AcessCodeRequestKakao();

        log.info("## kakaoLogin Called!!");

        decodedURL = acessCodeRequest.excute(request, csrfTokenRepository);

        if (decodedURL == null) {
            log.info("AcessCodeRequestKakao Failed");
            return "redirect:/";
        }

        return "redirect:" + decodedURL;
    }

    @RequestMapping(value = "/login_kakao/process")
    public String kakaoLogin_Process(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            Model model
    ) throws IOException {
        token = new KakaoAuthToken();
        decodedURL = "/";
        tokenRequest = new TokenRequestKakao();
        RequestUserInfoKakao infoKakao = new RequestUserInfoKakao();
        KakaoUserInfo kakaoUserInfo = null;
        List<String> email = null;

        // kakao REST API Documentation
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
        log.info("## kakaoLogin_Process Called!!");

        token = tokenRequest.excute(request);

        if (token == null) {
            return "redirect:/";
        }

        log.info("token : " + token);
        session.setAttribute("token", token);

        kakaoUserInfo = infoKakao.excute(request, token);

        if (kakaoUserInfo == null || kakaoUserInfo.getEmail().equals("") || kakaoUserInfo.getId().equals("")) {
            String alertMessage = "필수로 제공해야할 정보에 동의하지 않았습니다.";

            if (kakaoUserInfo.getEmail().equals("")) {
                alertMessage += "\\n(이메일)";
            }

            if (kakaoUserInfo.getId().equals("")) {
                alertMessage += "\\n(프로필)";
            }

            model.addAttribute("alertMsg", alertMessage);
            model.addAttribute("redirectURL", "/guide/agreePersonal");

            return "/alert/alert";
        }

        try {
            // https://cusonar.tistory.com/17
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    kakaoUserInfo.getEmail(), kakaoUserInfo.getId() + "!@#$");
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            // 세션이 30분 동안 유지
            session.setMaxInactiveInterval(30 * 60 * 60);
//            AuthUserModel authUserModel = (AuthUserModel) userDetailsService.loadUserByUsername(kakaoUserInfo.getEmail());
        } catch (InternalAuthenticationServiceException e) {
            String msg = e.getMessage();
            log.info(msg);

            // 이미 유저 이메일로 등록한 계정이 있는 경우 발생하는 오류
            if ("Can't proceed signup with this email. Please try another email".equals(msg)) {
                model.addAttribute("alertMsg", "삭제된 계정과 동일한 이메일을 사용할 수 없습니다.");
                model.addAttribute("redirectURL", "/");

                return "/alert/alert";
            }

            log.info("UserInfo Not in the DB");
            log.info("Redirect to signUp page");


            email = new EmailParser().emailParser(kakaoUserInfo.getEmail());

            if (!email.isEmpty()) {
                redirectAttributes.addFlashAttribute("str_email01", email.get(0));
                redirectAttributes.addFlashAttribute("str_email02", email.get(1));
            }

            if (kakaoUserInfo.getId() != null) {
                redirectAttributes.addFlashAttribute("pwd", kakaoUserInfo.getId() + "!@#$");
            }

            if (kakaoUserInfo.getNickname() != null) {
                redirectAttributes.addFlashAttribute("nickname", kakaoUserInfo.getNickname());
            }

            if (kakaoUserInfo.getProfile_image_url() != null) {
                redirectAttributes.addFlashAttribute("profile_pic", kakaoUserInfo.getProfile_image_url());
            }

            redirectAttributes.addFlashAttribute("social", true);
            redirectAttributes.addFlashAttribute("social_info", "kakao");
            redirectAttributes.addFlashAttribute("social_id", kakaoUserInfo.getId());

            return "redirect:/signup";
        }

        log.info("Kakao Login Successful");

        return "redirect:" + decodedURL;
    }

    @GetMapping(value = "/login_naver")
    public String naverLogin(
            HttpServletRequest request,
            HttpSessionCsrfTokenRepository csrfTokenRepository
    ) {
        decodedURL = null;
        acessCodeRequest = new AcessCodeRequestNaver();

        log.info("## naverLogin Called!!");

        decodedURL = acessCodeRequest.excute(request, csrfTokenRepository);
//        log.info("/login_naver : " + decodedURL);

        if (decodedURL == null) {
            log.info("AcessCodeRequestNaver Failed");
            return "redirect:/";
        }

        return "redirect:" + decodedURL;
    }

    @RequestMapping("/login_naver/process")
    public String naverLogin_Process(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        token = new NaverAuthToken();
        TokenRequestNaver tokenRequest = new TokenRequestNaver();
        RequestUserInfoNaver infoNaver = new RequestUserInfoNaver();
        NaverUserInfo naverUserInfo = null;
        Authentication authentication = null;
        List<String> email = null;

        // naver REST API Documentation
        // https://developers.naver.com/docs/login/api/api.md
        log.info("## naverLogin_Process Called!!");

        token = tokenRequest.excute(request);

        if (token == null) {
            return "redirect:/";
        }

        log.info("token : " + token);

        naverUserInfo = infoNaver.excute(request, token);

        if (naverUserInfo == null || naverUserInfo.getEmail().equals("") || naverUserInfo.getId().equals("")) {
            String alertMessage = "필수로 제공해야할 정보에 동의하지 않았습니다.";

            if (naverUserInfo.getEmail().equals("")) {
                alertMessage += "\\n(이메일)";
            }

            if (naverUserInfo.getId().equals("")) {
                alertMessage += "\\n(프로필)";
            }

//            scriptUtils.alert(response, alertMessage);

            model.addAttribute("alertMsg", alertMessage);
            model.addAttribute("redirectURL", "/guide/agreePersonal");

            return "/alert/alert";
        }

        try {
            // https://cusonar.tistory.com/17
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    naverUserInfo.getEmail(), naverUserInfo.getId());
            authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            // 세션이 30분 동안 유지
            session.setMaxInactiveInterval(30 * 60 * 60);
//            AuthUserModel authUserModel = (AuthUserModel) userDetailsService.loadUserByUsername(naverUserInfo.getEmail());
        } catch (NonUniqueResultException e) {
            log.warn("Non Unique User Result Find");
            log.warn("Please Check DB");
            log.warn(e.getStackTrace());
        } catch (InternalAuthenticationServiceException e) {
            String msg = e.getMessage();
//            log.info(msg);

            // 이미 유저 이메일로 등록한 계정이 있는 경우 발생하는 오류
            if ("Can't proceed signup with this email. Please try another email".equals(msg)) {
                model.addAttribute("alertMsg", "삭제된 계정과 동일한 이메일을 사용할 수 없습니다.");
                model.addAttribute("redirectURL", "/");

                return "/alert/alert";
            }

            log.info("UserInfo Not in the DB");
            log.info("Redirect to signUp page");

            email = new EmailParser().emailParser(naverUserInfo.getEmail());

            if (!email.isEmpty()) {
                redirectAttributes.addFlashAttribute("str_email01", email.get(0));
                redirectAttributes.addFlashAttribute("str_email02", email.get(1));
            }

            if (naverUserInfo.getId() != null) {
                redirectAttributes.addFlashAttribute("pwd", naverUserInfo.getId());
            }

            if (naverUserInfo.getNickname() != null) {
                redirectAttributes.addFlashAttribute("nickname", naverUserInfo.getNickname());
            }

            // Todo 프로필 이미지 다운받아서 저장해두기
            // https://kudolove.tistory.com/1353
            if (naverUserInfo.getProfile_image() != null) {
                redirectAttributes.addFlashAttribute("profile_pic", naverUserInfo.getProfile_image());
            }

            redirectAttributes.addFlashAttribute("social", true);
            redirectAttributes.addFlashAttribute("social_info", "naver");
            redirectAttributes.addFlashAttribute("social_id", naverUserInfo.getId());

            return "redirect:/signup";
        } catch (AuthenticationException e) {
            log.warn(e.getMessage());
            log.warn(e.getStackTrace());

            return "redirect:/";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/logout_kakao", method = {RequestMethod.GET, RequestMethod.POST})
    public String kakaoLogout(
            HttpServletRequest request,
            HttpSessionCsrfTokenRepository csrfTokenRepository,
            Authentication authentication
    ) {
        LogoutUser logoutUser = new LogoutUserKakao();
        LogoutProcess logoutProcess = new LogoutProcessKakao();
        decodedURL = null;
        String csrf = null;

        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();

        decodedURL = logoutUser.execute(request, csrfTokenRepository, authUserModel.getSocial_id());
//        logoutProcess.execute(decodedURL);

        return "redirect:/logout";
    }

    // @Todo 네이버 로그아웃도 만들기
    // @Todo 카카오, 네이버 연결끊기 생각해보기
    // 현재는 도메인이 https가 아니라서 안됨
    // https://developers.kakao.com/console/app/565410/product/login/unlink

    @ExceptionHandler(IllegalStateException.class)
    public void illegalStateException(Exception e) {
    }
}
