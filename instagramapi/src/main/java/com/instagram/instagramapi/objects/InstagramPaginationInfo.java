package com.instagram.instagramapi.objects;

/**
 * Created by Sayyam on 3/17/16.
 */
public class InstagramPaginationInfo {

    String nextURL;
    String nextMaxId;
    Class type;

    public String getNextURL() {
        return nextURL;
    }

    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }

    public String getNextMaxId() {
        return nextMaxId;
    }

    public void setNextMaxId(String nextMaxId) {
        this.nextMaxId = nextMaxId;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}
