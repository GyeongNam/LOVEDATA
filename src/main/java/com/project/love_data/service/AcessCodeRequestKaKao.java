package com.project.love_data.service;

import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

@Service
@Log4j2
public class AcessCodeRequestKaKao implements AcessCodeRequest{
    private URI kakaoCodeURI = null;

    @Override
    public void setURI(String csrf) {
        kakaoCodeURI = URISetter.getKaKao_Code(csrf);
    }

    @Override
    public void excute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository) {
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
        if (kakaoCodeURI == null) {
            setURI(csrfTokenRepository.loadToken(request).getToken());
        }

        try {
            log.info("kakaoCodeURI : " + URLDecoder.decode(kakaoCodeURI.toASCIIString(), "ASCII"));
        } catch (UnsupportedEncodingException e) {
            log.info(e.getStackTrace());
        }
    }
}
