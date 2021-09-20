package com.project.love_data.security.service;

import com.project.love_data.security.model.AuthUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Service
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUserModel authUserModel = (AuthUserModel) authentication.getPrincipal();

        if (authentication != null && authentication.getDetails() != null) {
            if (authUserModel.isUser_social()) {
                switch (authUserModel.getSocial_info()) {
                    case "naver" :
                        log.info("Naver logout!");
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.sendRedirect("/");
                        break;
                    case "kakao" :
                        log.info("Kakao logout!");
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.sendRedirect("/logout_kakao");
                        break;
                }
            } else {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/");
            }
        }
    }
}
