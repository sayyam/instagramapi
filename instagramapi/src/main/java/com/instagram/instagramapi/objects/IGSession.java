package com.instagram.instagramapi.objects;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/18/16.
 */
public class IGSession implements Serializable {

    private String accessToken;

    public IGSession() {
    }

    public IGSession(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
