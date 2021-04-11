package com.project.love_data.controller;

import com.google.gson.Gson;
import com.project.love_data.model.KakaoAuthToken;
import com.project.love_data.model.OAuthToken;
import com.project.love_data.service.AcessCodeRequest;
import com.project.love_data.service.AcessCodeRequestKakao;
import com.project.love_data.service.TokenRequest;
import com.project.love_data.service.TokenRequestKakao;
import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Log4j2
@Controller
public class OAuthController {
    private String decodedURL;
    private AcessCodeRequest acessCodeRequest;
    private TokenRequest tokenRequest;
    private OAuthToken token;

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

        // kakao REST API Documentation
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
        log.info("## kakaoLogin_Code Called!!");

        token = tokenRequest.excute(request);

        if (token == null) {
            return "redirect:/";
        }

        log.info("token : " + token);

        return "redirect:"+decodedURL;
    }
}
