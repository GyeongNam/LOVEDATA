package com.project.love_data;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class HttpTest {
    @Test
    public void getTest() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpGet sendReqCode = new HttpGet("https://kauth.kakao.com/oauth/authorize?client_id=0b7965078037392ee3569b5979ad1d16&redirect_uri=http://localhost:8080/login_kakao/code&response_type=code&state=4f824cf2-4eba-4b76-9231-e3ad5b9a6bf6");

        try {
            httpResponse = httpClient.execute(sendReqCode);
            System.out.println("sendReqCode");
//            System.out.println(httpResponse);
            HttpEntity entity = httpResponse.getEntity();
            String text = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8.name());
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
