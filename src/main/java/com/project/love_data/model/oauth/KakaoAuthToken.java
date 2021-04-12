package com.project.love_data.model.oauth;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoAuthToken implements OAuthToken {

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("token_type")
    private String token_type;

    @SerializedName("refresh_token")
    private String refresh_token;

    @SerializedName("expires_in")
    private String expires_in;

    @SerializedName("scope")
    private String scope;

    @SerializedName("refresh_token_expires_in")
    private String refresh_token_expires_in;
}