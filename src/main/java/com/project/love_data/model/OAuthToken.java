package com.project.love_data.model;

import com.google.gson.annotations.SerializedName;

public interface OAuthToken {
    public String getAccess_token();
    public String getToken_type();
    public String getRefresh_token();
    public String getExpires_in();
    public String getScope();
    public String getRefresh_token_expires_in();
}
