package com.project.love_data.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.love_data.model.user.KakaoUserInfo;
import com.project.love_data.model.OAuthToken;
import com.project.love_data.model.user.User;
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
public class RequestUserInfoKakao implements RequestUserInfo{

    @Override
    public User excute(HttpServletRequest request, OAuthToken token) {
        URI uri = URISetter.getKakao_UserInfo();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        KakaoUserInfo userInfo = new KakaoUserInfo();

        httpPost.setHeader("Authorization", "Bearer " + token.getAccess_token());
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
            log.info("response code : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            userInfo = new Gson().fromJson(text, KakaoUserInfo.class);
            log.info(text);
        } catch (ClientProtocolException e) {
            log.info(e.getStackTrace());
        } catch (IOException e) {
            log.info(e.getStackTrace());
        }

        log.info(userInfo.getKakao_account().getProfile());
        log.info(userInfo.getKakao_account());

        return null;
    }
}
