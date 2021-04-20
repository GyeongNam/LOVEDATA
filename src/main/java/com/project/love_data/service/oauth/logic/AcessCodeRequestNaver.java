package com.project.love_data.service.oauth.logic;

import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

@Log4j2
public class AcessCodeRequestNaver implements AcessCodeRequest{

    private URI setURI(String csrf) {
        return  URISetter.getNaver_Code(csrf);
    }

    @Override
    public String excute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository) {
        log.info("AcessCodeRequestNaver Called....");

        // https://developers.naver.com/docs/login/api/api.md
        URI NaverCodeURI = setURI(csrfTokenRepository.loadToken(request).getToken());

        try {
            log.info("NaverCodeURI : " + URLDecoder.decode(NaverCodeURI.toASCIIString(), "ASCII"));
            return URLDecoder.decode(NaverCodeURI.toASCIIString(), "ASCII");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }

        return null;
    }
}
