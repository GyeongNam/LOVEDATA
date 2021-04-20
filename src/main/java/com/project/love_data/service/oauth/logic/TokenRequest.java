package com.project.love_data.service.oauth.logic;

import com.project.love_data.model.oauth.OAuthToken;

import javax.servlet.http.HttpServletRequest;

public interface TokenRequest {
    public OAuthToken excute(HttpServletRequest request);
}
