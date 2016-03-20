package com.instagram.instagramapi.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/18/16.
 */
public class IGUserCounts implements Serializable {

    @SerializedName("media")
    Integer mediaCount;
    @SerializedName("follows")
    Integer followsCount;
    @SerializedName("followed_by")
    Integer followedByCount;

    protected Integer getMediaCount() {
        return mediaCount;
    }

    protected void setMediaCount(Integer mediaCount) {
        this.mediaCount = mediaCount;
    }

    protected Integer getFollowsCount() {
        return followsCount;
    }

    protected void setFollowsCount(Integer followsCount) {
        this.followsCount = followsCount;
    }

    protected Integer getFollowedByCount() {
        return followedByCount;
    }

    protected void setFollowedByCount(Integer followedByCount) {
        this.followedByCount = followedByCount;
    }
}
