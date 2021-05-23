package com.project.love_data.businessLogic.oauth.login;

import com.google.gson.Gson;
import com.project.love_data.model.oauth.NaverAuthToken;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Log4j2
public class TokenRequestNaver implements TokenRequest{

    @Override
    public OAuthToken excute(HttpServletRequest request) {
        URI uri = null;
        String code = request.getParameter("code");
        String error = request.getParameter("error");
        String error_desc = request.getParameter("error_description");
        String state = request.getParameter("state");
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        HttpGet httpGet = null;
        OAuthToken token = new NaverAuthToken();

        log.info("TokenRequestNaver Called....");

        /*
        Response에 존재하는 파라미터들 (2021-04-12 기준)

        code(String) : 네이버 아이디로 로그인 인증에 성공하면 반환받는 인증 코드, 접근 토큰(access token) 발급에 사용
        state(String) : 사이트 간 요청 위조 공격을 방지하기 위해 애플리케이션에서 생성한 상태 토큰으로 URL 인코딩을 적용한 값
        error(String) : 네이버 아이디로 로그인 인증에 실패하면 반환받는 에러 코드
        error_description(String) : 네이버 아이디로 로그인 인증에 실패하면 반환받는 에러 메시지
         */

        // 에러 발생시
        if(error != null) {
            log.info("error : " + error);
            log.info(error_desc);

            return null;
        }

        if (code == null) {
            log.info("TokenRequestNaver Failed");
            return null;
        }


        uri = URISetter.getNaver_Token(code, state);

        log.info(uri);

        if (uri == null) {
            log.info("URI NULL");
            return null;
        }

        httpClient = HttpClients.createDefault();
        httpPost = new HttpPost(uri);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            log.info(text);
            token = new Gson().fromJson(text, NaverAuthToken.class);
            httpClient.close();
        } catch (ClientProtocolException e) {
            log.info(e.getStackTrace());
            return null;
        } catch (IOException e) {
            return null;
        }

        return token;
    }
}
