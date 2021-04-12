package com.project.love_data.model.oauth;

import com.google.gson.annotations.SerializedName;

public interface OAuthToken {
    public String getAccess_token();
    public String getToken_type();
}
