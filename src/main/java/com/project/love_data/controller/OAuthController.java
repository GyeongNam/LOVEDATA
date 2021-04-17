package com.project.love_data.controller;

import com.project.love_data.model.oauth.KakaoAuthToken;
import com.project.love_data.model.oauth.NaverAuthToken;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.model.user.KakaoUserInfo;
import com.project.love_data.model.user.NaverUserInfo;
import com.project.love_data.security.model.AuthUserModel;
import com.project.love_data.security.service.UserDetailsService;
import com.project.love_data.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @RequestMapping("/login_kakao/process")
    public String kakaoLogin_Process(
            HttpServletRequest request,
            HttpSession session
    ){
        token = new KakaoAuthToken();
        decodedURL = "/";
        tokenRequest = new TokenRequestKakao();
        RequestUserInfoKakao infoKakao = new RequestUserInfoKakao();
        KakaoUserInfo kakaoUserInfo = null;


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

            log.info("UserInfo Not in the Table");
            log.info("Passing to signIn");
            // Todo 회원가입시 html에 요소에 값 넣는 것 마저 구현하기
//            request.setAttribute("str_email01", );
            return "redirect:"+"/signup";
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

        log.info("## kakaoLogin Called!!");

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
            HttpSessionCsrfTokenRepository csrfTokenRepository
    ){
        token = new NaverAuthToken();
//        tokenRequest = new TokenRequestNaver();
        TokenRequestNaver tokenRequest = new TokenRequestNaver();
        RequestUserInfoNaver infoNaver = new RequestUserInfoNaver();
        NaverUserInfo naverUserInfo = null;

        // kakao REST API Documentation
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
        log.info("## naverLogin_Process Called!!");

        token = tokenRequest.excute(request);

        if (token == null) {
            return "redirect:/";
        }

        log.info("token : " + token);

        naverUserInfo = infoNaver.excute(request, token);

        return "redirect:/";
    }
}
