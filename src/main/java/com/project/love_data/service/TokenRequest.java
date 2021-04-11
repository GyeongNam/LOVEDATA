package com.project.love_data.service;

import com.project.love_data.model.OAuthToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenRequest {
    public OAuthToken excute(HttpServletRequest request);
}
