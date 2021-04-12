package com.project.love_data.model.oauth;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverAuthToken implements OAuthToken{
    @SerializedName("access_token")
    public String access_token;

    @SerializedName("token_type")
    public String token_type;

    @SerializedName("refresh_token")
    public String refresh_token;

    @SerializedName("expires_in")
    public String expires_in;
}
