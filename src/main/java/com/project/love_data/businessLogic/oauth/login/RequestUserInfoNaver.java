package com.project.love_data.businessLogic.oauth.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.project.love_data.model.oauth.OAuthToken;
import com.project.love_data.model.user.NaverUserInfo;
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
public class RequestUserInfoNaver {

    public NaverUserInfo excute(HttpServletRequest request, OAuthToken token) {
        URI uri = URISetter.getNaver_UserInfo();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        NaverUserInfo userInfo = new NaverUserInfo();

        httpPost.setHeader("Authorization", "Bearer " + token.getAccess_token());
        log.info(httpPost.getFirstHeader("Authorization"));
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            log.info("response code : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            JsonElement element = JsonParser.parseString(text).getAsJsonObject().get("response");

            userInfo.setId(element.getAsJsonObject().get("id").getAsString());

            if (!element.getAsJsonObject().has("email")) {
                userInfo.setEmail("");
            } else {
                userInfo.setEmail(element.getAsJsonObject().get("email").getAsString().replaceAll("@\"\\s+\"", ""));
            }

            if (!element.getAsJsonObject().has("nickname")) {
                userInfo.setNickname("");
            } else {
                if (element.getAsJsonObject().get("nickname").getAsString().equals("")) {
                    userInfo.setNickname("");
                } else {
                    userInfo.setNickname(element.getAsJsonObject().get("nickname").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("profile_image")) {
                userInfo.setProfile_image("");
            } else {
                if (element.getAsJsonObject().get("profile_image").getAsString().equals("")) {
                    userInfo.setProfile_image("");
                } else {
                    userInfo.setProfile_image(element.getAsJsonObject().get("profile_image").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("age")) {
                userInfo.setAge("");
            } else {
                if (element.getAsJsonObject().get("age").getAsString().equals("")){
                    userInfo.setAge("");
                } else {
                    userInfo.setAge(element.getAsJsonObject().get("age").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("gender")) {
                userInfo.setSex("");
            } else {
                if (element.getAsJsonObject().get("gender").getAsString().equals("")) {
                    userInfo.setSex("");
                } else {
                    userInfo.setSex(element.getAsJsonObject().get("gender").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("mobile")) {
                userInfo.setPhone("");
            } else {
                if (element.getAsJsonObject().get("mobile").getAsString().equals("")) {
                    userInfo.setPhone("");
                } else {
                    userInfo.setPhone(element.getAsJsonObject().get("mobile").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("mobile_e164")) {
                userInfo.setMobile_e164("");
            } else {
                if (element.getAsJsonObject().get("mobile_e164").getAsString().equals("")) {
                    userInfo.setMobile_e164("");
                } else {
                    userInfo.setMobile_e164(element.getAsJsonObject().get("mobile_e164").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("name")) {
                userInfo.setName("");
            } else {
                if (element.getAsJsonObject().get("name").getAsString().equals("")) {
                    userInfo.setName("");
                } else {
                    userInfo.setName(element.getAsJsonObject().get("name").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("birthday")) {
                userInfo.setBirthday("");
            } else {
                if (element.getAsJsonObject().get("birthday").getAsString().equals("")) {
                    userInfo.setBirthday("");
                } else {
                    userInfo.setBirthday(element.getAsJsonObject().get("birthday").getAsString());
                }
            }

            if (!element.getAsJsonObject().has("birthyear")) {
                userInfo.setBirthyear("");
            } else {
                if (element.getAsJsonObject().get("birthyear").getAsString().equals("")) {
                    userInfo.setBirthyear("");
                } else {
                    userInfo.setBirthyear(element.getAsJsonObject().get("birthyear").getAsString());
                }
            }
        } catch (ClientProtocolException e) {
            log.info(e.getStackTrace());
        } catch (IOException e) {
            log.info(e.getStackTrace());
        } catch (NullPointerException e) {
            log.info(e.getStackTrace());
        } catch (Exception e) {
            log.warn(e.getStackTrace());
        }

        log.info(userInfo);

        return userInfo;
    }
}
