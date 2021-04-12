package com.project.love_data.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.model.user.NaverUserInfo;
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
public class RequestUserInfoNaver implements RequestUserInfo {

    @Override
    public User excute(HttpServletRequest request, OAuthToken token) {
        URI uri = URISetter.getNaver_UserInfo();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        NaverUserInfo userInfo = new NaverUserInfo();

        httpPost.setHeader("Authorization", "Bearer " + token.getAccess_token());
        log.info(httpPost.getFirstHeader("Authorization"));
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
            log.info("response code : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            JsonElement element = JsonParser.parseString(text).getAsJsonObject().get("response");

            userInfo.setId(element.getAsJsonObject().get("id").getAsString());
            userInfo.setNickname(element.getAsJsonObject().get("nickname").getAsString());
            userInfo.setProfile_image(element.getAsJsonObject().get("profile_image").getAsString());
            userInfo.setAge(element.getAsJsonObject().get("age").getAsString());
            userInfo.setSex(element.getAsJsonObject().get("gender").getAsString());
            userInfo.setEmail(element.getAsJsonObject().get("email").getAsString());
            userInfo.setPhone(element.getAsJsonObject().get("mobile").getAsString());
            userInfo.setMobile_e164(element.getAsJsonObject().get("mobile_e164").getAsString());
            userInfo.setName(element.getAsJsonObject().get("name").getAsString());
            userInfo.setBirthday(element.getAsJsonObject().get("birthday").getAsString());
            userInfo.setBirthyear(element.getAsJsonObject().get("birthyear").getAsString());
        } catch (ClientProtocolException e) {
            log.info(e.getStackTrace());
        } catch (IOException e) {
            log.info(e.getStackTrace());
        }

        log.info(userInfo);

        return null;
    }
}
