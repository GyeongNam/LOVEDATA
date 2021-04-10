package com.project.love_data.service;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public interface AcessCodeRequest {
    public void setURI(String csrf);
    public void excute(HttpServletRequest request, HttpSessionCsrfTokenRepository csrfTokenRepository);
}
