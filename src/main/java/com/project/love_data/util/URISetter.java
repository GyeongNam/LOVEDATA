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
    private static final String kakao_Redirect = "/login_kakao/process";
    private static final  String kakao_LogoutRedirect = "/logout_kakao/process";
    private static final String local_host = "http://localhost:8080";
    private static final String mon0mon_iptime = "http://mon0mon.iptime.org:18080";
    private static final String mon0mon_iptime_2 = "http://mon0mon.iptime.org:28080";
    private static final String loveData_kr = "https://lovedata.kr";
    private static final String loveData_ddns = "https://lovedata.ddns.net";
    private static final String loveData_duck = "https://lovedata.duckdns.org";
    private static final String naver_ClientID = "GsitFCDRzSFJYx73nqfz";
    private static final String naver_ClientSecret= "piOPrC_WRe";
    private static final String naver_Redirect = "/login_naver/process";

    private static String setURL(ServerDomain domain) {
        String URL = "";

        switch (domain){
            case LOCALHOST:
                URL += local_host;
                return URL;
            case MON_IPTIME:
                URL += mon0mon_iptime;
                return URL;
            case MON_IPTIME_2:
                URL += mon0mon_iptime_2;
                return URL;
            case LOVEDATA_DDNS:
                URL += loveData_ddns;
                return URL;
            case LOVEDATA_DUCK:
                URL += loveData_duck;
                return URL;
            case LOVEDATA_KR:
                URL += loveData_kr;
                return URL;
            default:
                URL = null;
                log.warn("Current Domain is not listed in the ServerDomain.java");
                log.warn("Please Add Current Domain ServerDomain.java or Access by another listed domain");
                return URL;
        }
    }

    public static URI getKakao_Code(String csrf, ServerDomain domain) {
        String URL = setURL(domain);

        if (URL == null) {
            log.warn("(getKakao_Code) Kakao 로그인 redirect_url을 설정하지 못했습니다.");
            return null;
        }

        try {
            URL += kakao_Redirect;
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("kauth.kakao.com")
                    .setPath("oauth/authorize")
                    .setParameter("client_id", kakao_REST)
                    .setParameter("redirect_uri", URL)
                    .setParameter("response_type", "code")
                    .setParameter("state", csrf)
                    .build();
            return uri;
        } catch (Exception e) {
            log.warn("getKaKao_Code URI 생성 과정 중 예외 발생");
            log.warn(e.getStackTrace());
            return null;
        }
    }

    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
    public static URI getKaKao_Token(String code, ServerDomain domain){
        String URL = setURL(domain);

        if (URL == null) {
            log.warn("(getKaKao_Token) Kakao 로그인 redirect_url을 설정하지 못했습니다.");
            return null;
        }

        try {
            URL += kakao_Redirect;
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("kauth.kakao.com")
                    .setPath("oauth/token")
                    .setParameter("grant_type", "authorization_code")
                    .setParameter("client_id", kakao_REST)
                    .setParameter("redirect_uri", URL)
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

    public static URI getKakao_Logout(String csrf, ServerDomain domain, int social_id) {
        String URL = setURL(domain);

        if (URL == null) {
            log.warn("(getKakao_Logout) Kakao 로그아웃 redirect_url을 설정하지 못했습니다.");
            return null;
        }

        try {
            URL += kakao_LogoutRedirect;
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("kapi.kakao.com")
                    .setPath("v1/user/logout")
                    .setParameter("target_id_type", "user_id")
                    .setParameter("target_id", String.valueOf(social_id))
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getKakao_Logout URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }

//    https://developers.naver.com/docs/login/api/api.md
    public static URI getNaver_Code(String csrf, ServerDomain domain) {
        String URL = setURL(domain);

        if (URL == null) {
            log.warn("(getNaver_Code) Naver 로그인 redirect_url을 설정하지 못했습니다.");
            return null;
        }

        try {
            URL += naver_Redirect;
            log.info(URL);
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("nid.naver.com")
                    .setPath("oauth2.0/authorize")
                    .setParameter("response_type", "code")
                    .setParameter("client_id", naver_ClientID)
                    .setParameter("redirect_uri", URL)
                    .setParameter("state", csrf)
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getNaver_Code URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }

    // https://developers.naver.com/docs/login/api/api.md
    public static URI getNaver_Token(String code, String state){
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("nid.naver.com")
                    .setPath("oauth2.0/token")
                    // 발급 - authorization_code, 갱신 - refresh_token, 삭제 - delete
                    .setParameter("client_id", naver_ClientID)
                    .setParameter("client_secret", naver_ClientSecret)
                    .setParameter("grant_type", "authorization_code")
                    .setParameter("state", state)
                    .setParameter("code", code)
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getKaKao_Token URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }

    // https://developers.naver.com/docs/login/web/web.md
    public static URI getNaver_UserInfo() {
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("openapi.naver.com")
                    .setPath("v1/nid/me")
                    .build();
            return uri;
        } catch (Exception e) {
            log.info("getKakao_UserInfo URI 생성 과정 중 예외 발생");
            log.info(e.getStackTrace());
            return null;
        }
    }
}
