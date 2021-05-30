package com.project.love_data.businessLogic.oauth.logout;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class LogoutProcessKakao implements LogoutProcess{
    @Override
    public void execute(String uri) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        try(CloseableHttpResponse response = httpClient.execute(httpGet)){
            log.info("response code : " + response.getStatusLine());

        } catch (ClientProtocolException e) {
            log.info(e.getStackTrace());
        } catch (IOException e) {
            log.info(e.getStackTrace());
        }
    }
}
