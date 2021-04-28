package com.project.love_data.service.oauth.login;

import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

@Log4j2
public class AcessCodeRequestNaver implements AcessCodeRequest{

    private URI setURI(String csrf, HttpServletRequest request) {
        if ("localhost".equals(request.getServerName())){
            return  URISetter.getNaver_Code_Local(csrf);
        } else{
            return  URISetter.getNaver_Code(csrf);
        }
    }

    @Override
    public String excute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository) {
        log.info("AcessCodeRequestNaver Called....");

        // https://developers.naver.com/docs/login/api/api.md
        URI NaverCodeURI = setURI(csrfTokenRepository.loadToken(request).getToken(), request);

        try {
            log.info("NaverCodeURI : " + URLDecoder.decode(NaverCodeURI.toASCIIString(), "ASCII"));
            return URLDecoder.decode(NaverCodeURI.toASCIIString(), "ASCII");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }

        return "redirect:/";
    }
}
