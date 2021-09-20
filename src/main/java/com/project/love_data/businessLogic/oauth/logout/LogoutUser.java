package com.project.love_data.businessLogic.oauth.logout;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;

public interface LogoutUser {
    public String execute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository, int social_id);
}
