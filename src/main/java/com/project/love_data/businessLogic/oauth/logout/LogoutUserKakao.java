package com.project.love_data.businessLogic.oauth.logout;

import com.project.love_data.util.ServerDomain;
import com.project.love_data.util.ServerHostHandler;
import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

@Service
@Log4j2
public class LogoutUserKakao implements LogoutUser {

    private URI setURI(String csrf, HttpServletRequest request) {
        ServerDomain currentDom = ServerHostHandler.getServerHost(request);
        return  URISetter.getKakao_Logout(csrf, currentDom);
    }

    @Override
    public String execute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository) {
        URI kakaoLogoutURI = setURI(csrfTokenRepository.loadToken(request).getToken(), request);

        try {
            log.info("kakaoLogoutURI : " + URLDecoder.decode(kakaoLogoutURI.toASCIIString(), "ASCII"));
            return URLDecoder.decode(kakaoLogoutURI.toASCIIString(), "ASCII");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }

        return "redirect:/";
    }
}
