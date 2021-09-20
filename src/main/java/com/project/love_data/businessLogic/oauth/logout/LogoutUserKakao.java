package com.project.love_data.businessLogic.oauth.logout;

import com.google.gson.Gson;
import com.project.love_data.util.ServerDomain;
import com.project.love_data.util.ServerHostHandler;
import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

@Service
@Log4j2
public class LogoutUserKakao implements LogoutUser {

    private URI setURI(String csrf, HttpServletRequest request, int social_id) {
        ServerDomain currentDom = ServerHostHandler.getServerHost(request);
        return  URISetter.getKakao_Logout(csrf, currentDom, social_id);
    }

    @Override
    public String execute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository, int social_id) {
        URI kakaoLogoutURI = setURI(csrfTokenRepository.loadToken(request).getToken(), request, social_id);
        String adminKey = "e571f68bf7eec06e90cdc94a47a710d9";
        String restKey = "0b7965078037392ee3569b5979ad1d16";

        try {
//            log.info("kakaoLogoutURI : " + URLDecoder.decode(kakaoLogoutURI.toASCIIString(), "ASCII"));
//            URL logout = new URL(kakaoLogoutURI.toASCIIString());
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
            param.add("target_id_type", "user_id");
            param.add("target_id", String.valueOf(social_id));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            httpHeaders.add("Authorization", "KakaoAK {" + adminKey + "}");
//            httpHeaders.add("Authorization", "KakaoAK {" + restKey + "}");

            HttpEntity<MultiValueMap<String, String>> logoutReq = new HttpEntity<>(param, httpHeaders);

            RestTemplate rest = new RestTemplate();
//            String result = rest.postForObject("https://kapi.kakao.com/v1/user/logout", logoutReq, String.class);
            ResponseEntity<String> responseEntity = rest.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, logoutReq, String.class);

            log.info(responseEntity.toString());
        } catch (Exception e) {
            log.warn(e.getMessage());
            log.warn(e.getCause());
        }

        return "redirect:/";
    }
}
