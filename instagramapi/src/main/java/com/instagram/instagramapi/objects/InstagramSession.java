package com.instagram.instagramapi.objects;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/18/16.
 */
public class InstagramSession implements Serializable {

    private String accessToken;

    public InstagramSession() {
    }

    public InstagramSession(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
