package com.instagram.instagramapi.objects;

/**
 * Created by Sayyam on 3/17/16.
 */
public class InstagramComment {

    long createdDate;
    IGUser user;
    String text;

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public IGUser getUser() {
        return user;
    }

    public void setUser(IGUser user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
