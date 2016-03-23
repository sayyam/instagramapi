package com.instagram.instagramapi.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/17/16.
 */
public class IGUser implements Serializable {


    @SerializedName("id")
    String id;
    @SerializedName("username")
    String username;
    @SerializedName("full_name")
    String fullName;
    @SerializedName("profile_picture")
    String profilePictureURL;
    @SerializedName("bio")
    String bio;
    @SerializedName("website")
    String website;
    @SerializedName("counts")
    IGUserCounts counts;
    String accessToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getMediaCount() {
        return counts.getMediaCount();
    }

    public void setFollowsCount(Integer followsCount) {
        counts.setFollowsCount(followsCount);
    }

    public Integer getFollowsCount() {
        return counts.getFollowsCount();
    }

    public void setFollowedByCount(Integer followedByCount) {
        counts.setFollowedByCount(followedByCount);
    }

    public void setMediaCount(Integer mediaCount) {
        counts.setMediaCount(mediaCount);
    }

    public Integer getFollowedByCount() {
        return counts.getFollowedByCount();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
