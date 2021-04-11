package com.project.love_data.util;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLEncoder;

@Log4j2
public class URISetter {
    private static final String kakao_REST = "0b7965078037392ee3569b5979ad1d16";
    private static final String kakao_Redirect = "http://localhost:8080/login_kakao/process";
    private static final String naver_REST = "";
    private static final String naver_Redirect = "";

    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
    public static URI getKaKao_Code(String csrf){
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("kauth.kakao.com")
                    .setPath("oauth/authorize")
                    .setParameter("client_id", kakao_REST)
                    .setParameter("redirect_uri", kakao_Redirect)
                    .setParameter("response_type", "code")
                    .setParameter("state", csrf)
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getKaKao_Code URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }

    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
    public static URI getKaKao_Token(String code){
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("kauth.kakao.com")
                    .setPath("oauth/token")
                    .setParameter("grant_type", "authorization_code")
                    .setParameter("client_id", kakao_REST)
                    .setParameter("redirect_uri", kakao_Redirect)
                    .setParameter("code", code)
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getKaKao_Token URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }

    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
    public static URI getKakao_UserInfo() {
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("kapi.kakao.com")
                    .setPath("/v2/user/me")
                    .setParameter("target_id_type", "")
                    .setParameter("target_id", "")
                    .setParameter("secure_resource", "true")
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getKakao_UserInfo URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }
}
