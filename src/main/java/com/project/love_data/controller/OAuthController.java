package com.project.love_data.controller;

import com.google.gson.Gson;
import com.project.love_data.model.KakaoAuthToken;
import com.project.love_data.service.AcessCodeRequestKaKao;
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
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;
    private URI uri;
    private String decodedURL;

    @GetMapping(value = "/login_kakao")
    public String kakaoLogin(@RequestParam(value = "_csrf")String csrf){
        decodedURL = null;

        log.info("## kakaoLogin Called!!");

        /* 코드 발급 uri
        https://kauth.kakao.com/oauth/authorize?response_type=code&client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}   */
        uri = URISetter.getKaKao_Code(csrf);

        if (uri == null) {
            log.info("kakaoLogin 중 예외 발생");
            return "redirect:/";
        }

        try {
            decodedURL = URLDecoder.decode(uri.toASCIIString(), "ASCII");
            log.info("Kakao Login URL : " + decodedURL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:" + decodedURL;
    }

    @RequestMapping("/login_kakao/code")
    public String kakaoLogin_Code(
            @RequestParam(value = "code")String code,
            @RequestParam(value = "state")String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false)String error_desc,
            HttpServletRequest request
    ){
        decodedURL = null;

        // kakao REST API Documentation
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
        log.info("## kakaoLogin_Code Called!!");

        // 에러 발생시
        if(error != null) {
            log.info("error : " + error);
            log.info(error_desc);

            return "redirect:/";
        }

        // 현재 csrf 토큰 확인
        if(state != null) {
            log.info("state(csrf) : " + state);
            log.info("server(csrf) : " + new HttpSessionCsrfTokenRepository().loadToken(request));
            log.info("csrf token check : " + new HttpSessionCsrfTokenRepository().loadToken(request).equals(state));
        }

        if (code != null) {
            log.info("Kakao Login Successful");
            log.info("kakao code : " + code);
        } else {
            log.info("Kakao Login Failed");
            return "redirect:/";
        }

        try {
            uri = URISetter.getKaKao_LoginProcess(code);
            decodedURL = URLDecoder.decode(uri.toASCIIString(), "ASCII");
            log.info("kakao login process URL : " + decodedURL);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }

        return "redirect:"+decodedURL;
    }

    @RequestMapping(value = "login_kakao/process", produces = "application/json")
    public String kakaoLogin_Process(
            @RequestParam(value = "code") String code,
            HttpServletRequest request
            ){
        httpClient = HttpClients.createDefault();
        uri = URISetter.getKaKao_Token(code);
        httpPost = new HttpPost(uri);

        // uri 예외 체크
        if (uri == null) {
            log.info("kakao_login_process 중 예외 발생");
            return "redirect:/";
        }

        // Todo 로그 지우기
        try {
            decodedURL = URLDecoder.decode(uri.toASCIIString(), "ASCII");
            log.info("kakao token url : " +  decodedURL);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            httpPost.setURI(uri);

            log.info("response : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();

            //Todo json 형식으로 오는 파일 파싱하기
            log.info(entity.getContent());

            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            KakaoAuthToken auth = new Gson().fromJson(text, KakaoAuthToken.class);

            log.info("Kakao Token : " + auth);

            httpClient.close();
        } catch (ClientProtocolException e) {
            log.info("104) kakao_login_process 중 예외 발생");
            log.info(e.getStackTrace());
            return "redirect:/";
        } catch (IOException e) {
            log.info("108) kakao_login_process 중 예외 발생");
            log.info(e.getStackTrace());
            return "redirect:/";
        }

        return "redirect:/";
    }
}
