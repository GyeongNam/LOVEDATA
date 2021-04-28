package com.project.love_data.service.oauth;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;

public interface LogoutUser {
    public String execute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository);
}
