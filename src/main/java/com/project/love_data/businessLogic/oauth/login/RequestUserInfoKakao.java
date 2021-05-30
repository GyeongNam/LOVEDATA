package com.project.love_data.businessLogic.oauth.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.love_data.model.user.KakaoUserInfo;
import com.project.love_data.model.oauth.OAuthToken;
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
public class RequestUserInfoKakao{

    public KakaoUserInfo excute(HttpServletRequest request, OAuthToken token) {
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

            JsonElement properties = JsonParser.parseString(text).getAsJsonObject().get("properties");
            JsonElement kakao_account = JsonParser.parseString(text).getAsJsonObject().get("kakao_account");
//            JsonElement profile = kakao_account.getAsJsonObject().get("profile");

            userInfo.setId(JsonParser.parseString(text).getAsJsonObject().get("id").getAsString());
            userInfo.setConnected_at(JsonParser.parseString(text).getAsJsonObject().get("connected_at").getAsString());
            userInfo.setNickname(properties.getAsJsonObject().get("nickname").getAsString());
            userInfo.setProfile_image_url(properties.getAsJsonObject().get("profile_image").getAsString());
            userInfo.setThumbnail_image_url(properties.getAsJsonObject().get("thumbnail_image").getAsString());
            userInfo.setHas_email(kakao_account.getAsJsonObject().get("has_email").getAsBoolean());
            userInfo.setIs_email_valid(kakao_account.getAsJsonObject().get("is_email_valid").getAsBoolean());
            userInfo.setIs_email_verified(kakao_account.getAsJsonObject().get("is_email_verified").getAsBoolean());
            userInfo.setEmail(kakao_account.getAsJsonObject().get("email").getAsString());

            log.info(userInfo);
        } catch (ClientProtocolException e) {
            log.info(e.getStackTrace());
        } catch (IOException e) {
            log.info(e.getStackTrace());
        }

        return userInfo;
    }
}
