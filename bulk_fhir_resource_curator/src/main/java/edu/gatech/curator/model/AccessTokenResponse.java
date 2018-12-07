package edu.gatech.curator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {
    private String tokenType;
    private int expiresIn;
    private String accessToken;

    public AccessTokenResponse() { }

    public AccessTokenResponse(String tokenType, int expiresIn, String accessToken) {
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    @JsonProperty("expires_in")
    public int getExpiresIn() {
        return expiresIn;
    }

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }
}
