package com.project.love_data.businessLogic.oauth.login;

import com.google.gson.Gson;
import com.project.love_data.model.oauth.KakaoAuthToken;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.util.ServerDomain;
import com.project.love_data.util.ServerHostHandler;
import com.project.love_data.util.URISetter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Log4j2
@Service
public class TokenRequestKakao implements TokenRequest{

    @Override
    public OAuthToken excute(HttpServletRequest request) {
        URI uri = null;
        String code = request.getParameter("code");
        String error = request.getParameter("error");
        String error_desc = request.getParameter("error_description");
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        OAuthToken token = new KakaoAuthToken();

        /*
        Response에 존재하는 파라미터들 (2021-04-12 기준)

        code(String) : 토큰 받기 요청에 필요한 인가코드
        state(String) : csrf 공격을 차단하기 위해 사용되는 파라미터로, AcessCodeRequest에서 보낸 state값과 동일한 state값이 전달
        error(String) : 인증 실패시 반환되는 에러코드
        error_description(String) : 인증 실패시 반환되는 에러 메시지
         */

        // 에러 발생시
        if(error != null) {
            log.info("error : " + error);
            log.info(error_desc);

            return null;
        }

        if (code == null) {
            log.info("TokenRequestKakao Failed");
            return null;
        }

        ServerDomain currentDom = ServerHostHandler.getServerHost(request);
        uri = URISetter.getKaKao_Token(code, currentDom);

        if (uri == null) {
            log.info("URI NULL");
            return null;
        }

        httpClient = HttpClients.createDefault();
        httpPost = new HttpPost(uri);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            token = new Gson().fromJson(text, KakaoAuthToken.class);
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
