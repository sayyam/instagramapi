package com.instagram.instagramapi.objects;

/**
 * Created by Sayyam on 3/17/16.
 */
public class IGUserInPhoto {

    IGUser user;
    IGPositionInPhoto position;

    public IGUser getUser() {
        return user;
    }

    public void setUser(IGUser user) {
        this.user = user;
    }

    public IGPositionInPhoto getPosition() {
        return position;
    }

    public void setPosition(IGPositionInPhoto position) {
        this.position = position;
    }
}
