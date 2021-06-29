package com.project.love_data.businessLogic.oauth.login;

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
public class AcessCodeRequestKakao implements AcessCodeRequest{

    private URI setURI(String csrf, HttpServletRequest request) {
        ServerDomain currentDom = ServerHostHandler.getServerHost(request);
        return URISetter.getKakao_Code(csrf, currentDom);
    }

    @Override
    public String excute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository) {
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
        URI kakaoCodeURI = setURI(csrfTokenRepository.loadToken(request).getToken(), request);

        try {
            log.info("kakaoCodeURI : " + URLDecoder.decode(kakaoCodeURI.toASCIIString(), "ASCII"));
            return URLDecoder.decode(kakaoCodeURI.toASCIIString(), "ASCII");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }

        return "redirect:/";
    }
}
