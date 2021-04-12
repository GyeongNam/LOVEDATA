package com.project.love_data.controller;

import com.project.love_data.model.KakaoAuthToken;
import com.project.love_data.model.OAuthToken;
import com.project.love_data.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class OAuthController {
    private String decodedURL;
    private AcessCodeRequest acessCodeRequest;
    private TokenRequest tokenRequest;
    private OAuthToken token;
    private RequestUserInfo userInfo;

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
    public String kakaoLogin_Code(
            HttpServletRequest request
    ){
        token = new KakaoAuthToken();
        decodedURL = "/";
        tokenRequest = new TokenRequestKakao();
        userInfo = new RequestUserInfoKakao();

        // kakao REST API Documentation
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
        log.info("## kakaoLogin_Process Called!!");

        token = tokenRequest.excute(request);

        if (token == null) {
            return "redirect:/";
        }

        log.info("token : " + token);

        userInfo.excute(request, token);

        return "redirect:"+decodedURL;
    }
}
