package com.project.love_data.service;

import com.project.love_data.model.OAuthToken;
import com.project.love_data.model.user.User;

import javax.servlet.http.HttpServletRequest;

public interface RequestUserInfo {
    public User excute(HttpServletRequest request, OAuthToken token);
}
