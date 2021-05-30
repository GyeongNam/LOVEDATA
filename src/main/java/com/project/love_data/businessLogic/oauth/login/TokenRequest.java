package com.project.love_data.businessLogic.oauth.login;

import com.project.love_data.model.oauth.OAuthToken;

import javax.servlet.http.HttpServletRequest;

public interface TokenRequest {
    public OAuthToken excute(HttpServletRequest request);
}
