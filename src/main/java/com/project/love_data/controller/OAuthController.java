package com.project.love_data.controller;

import com.project.love_data.model.oauth.KakaoAuthToken;
import com.project.love_data.model.oauth.NaverAuthToken;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.model.user.KakaoUserInfo;
import com.project.love_data.model.user.NaverUserInfo;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.security.service.UserDetailsService;
import com.project.love_data.service.oauth.logic.*;
import com.project.love_data.util.EmailParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping(value = "/login_kakao")
    public String kakaoLogin(
            HttpServletRequest request,
            HttpSessionCsrfTokenRepository csrfTokenRepository
    ){
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
            RedirectAttributes redirectAttributes,
            HttpSession session
    ){
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

        try {
            kakaoUserInfo = infoKakao.excute(request, token);
            // https://cusonar.tistory.com/17
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    kakaoUserInfo.getEmail(), kakaoUserInfo.getId());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            AuthUserModel authUserModel = (AuthUserModel) userDetailsService.loadUserByUsername(kakaoUserInfo.getEmail());
        } catch (AuthenticationException e) {
            log.info(e.getMessage());

            log.info("UserInfo Not in the DB");
            log.info("Redirect to signUp page");

            
            email = new EmailParser().emailParser(kakaoUserInfo.getEmail());

            if (!email.isEmpty()) {
                redirectAttributes.addFlashAttribute("str_email01", email.get(0));
                redirectAttributes.addFlashAttribute("str_email02", email.get(1));
            }

            if (kakaoUserInfo.getId() != null) {
//                request.setAttribute("pwd", kakaoUserInfo.getId());
                redirectAttributes.addFlashAttribute("pwd", kakaoUserInfo.getId());
            }

            if (kakaoUserInfo.getNickname() != null) {
//                request.setAttribute("nickname", kakaoUserInfo.getNickname());
                redirectAttributes.addFlashAttribute("nickname", kakaoUserInfo.getNickname());
            }

//            request.setAttribute("social", true);
            redirectAttributes.addFlashAttribute("social", true);

            return "redirect:/signup";
        }

        return "redirect:"+decodedURL;
    }

    @GetMapping(value = "/login_naver")
    public String naverLogin(
            HttpServletRequest request,
            HttpSessionCsrfTokenRepository csrfTokenRepository
    ){
        decodedURL = null;
        acessCodeRequest = new AcessCodeRequestNaver();

        log.info("## naverLogin Called!!");

        decodedURL = acessCodeRequest.excute(request, csrfTokenRepository);

        if (decodedURL == null) {
            log.info("AcessCodeRequestNaver Failed");
            return "redirect:/";
        }

        return "redirect:" + decodedURL;
    }

    @RequestMapping("/login_naver/process")
    public String naverLogin_Process(
            HttpServletRequest request,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ){
        token = new NaverAuthToken();
        TokenRequestNaver tokenRequest = new TokenRequestNaver();
        RequestUserInfoNaver infoNaver = new RequestUserInfoNaver();
        NaverUserInfo naverUserInfo = null;
        List<String> email = null;

        // naver REST API Documentation
        // https://developers.naver.com/docs/login/api/api.md
        log.info("## naverLogin_Process Called!!");

        token = tokenRequest.excute(request);

        if (token == null) {
            return "redirect:/";
        }

        log.info("token : " + token);

        try {
            naverUserInfo = infoNaver.excute(request, token);
            // https://cusonar.tistory.com/17
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    naverUserInfo.getEmail(), naverUserInfo.getId());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            AuthUserModel authUserModel = (AuthUserModel) userDetailsService.loadUserByUsername(naverUserInfo.getEmail());
        } catch (AuthenticationException e) {
            log.info(e.getMessage());

            log.info("UserInfo Not in the DB");
            log.info("Redirect to signUp page");

            email = new EmailParser().emailParser(naverUserInfo.getEmail());

            if (!email.isEmpty()) {
                redirectAttributes.addFlashAttribute("str_email01", email.get(0));
                redirectAttributes.addFlashAttribute("str_email02", email.get(1));

//                request.setAttribute("str_email01", email.get(0));
//                request.setAttribute("str_email02", email.get(1));
            }

            if (naverUserInfo.getId() != null) {
//                request.setAttribute("pwd", naverUserInfo.getId());
                redirectAttributes.addFlashAttribute("pwd", naverUserInfo.getId());
            }

            if (naverUserInfo.getNickname() != null) {
//                request.setAttribute("nickname", naverUserInfo.getNickname());
                redirectAttributes.addFlashAttribute("nickname", naverUserInfo.getNickname());
            }

            request.setAttribute("social", true);
            redirectAttributes.addFlashAttribute("social", true);

            return "redirect:/signup";
        }

        return "redirect:/";
    }
}
