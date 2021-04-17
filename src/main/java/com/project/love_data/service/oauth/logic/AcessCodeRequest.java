package com.project.love_data.service.oauth.logic;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;

public interface AcessCodeRequest {
    public String excute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository);
}
