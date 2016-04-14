package com.instagram.instagramapi.objects;

/**
 * Created by Sayyam on 3/19/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class IGMedia implements Serializable{
    @SerializedName("tags")
    @Expose
    private List<Object> tags = new ArrayList<Object>();
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("comments")
    @Expose
    private IGCommentCount comments;
    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("created_time")
    @Expose
    private Long createdTime;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("likes")
    @Expose
    private IGLikesCount likes;
    @SerializedName("images")
    @Expose
    private IGImages images;
    @SerializedName("users_in_photo")
    @Expose
    private List<IGUserInPhoto> usersInPhoto = new ArrayList<IGUserInPhoto>();
    @SerializedName("caption")
    @Expose
    private IGCaption caption;
    @SerializedName("user_has_liked")
    @Expose
    private Boolean userHasLiked;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private IGUser user;
    @SerializedName("location")
    @Expose
    private IGLocation location;
    @SerializedName("videos")
    @Expose
    private IGVideos videos;


    /**
     *
     * @return
     * The tags
     */
    public List<Object> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The comments
     */
    public IGCommentCount getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(IGCommentCount comments) {
        this.comments = comments;
    }

    /**
     *
     * @return
     * The filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     *
     * @param filter
     * The filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     *
     * @return
     * The createdTime
     */
    public Long getCreatedTime() {
        return createdTime;
    }

    /**
     *
     * @param createdTime
     * The created_time
     */
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The likes
     */
    public IGLikesCount getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     * The likes
     */
    public void setLikes(IGLikesCount likes) {
        this.likes = likes;
    }

    /**
     *
     * @return
     * The images
     */
    public IGImages getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(IGImages images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The usersInPhoto
     */
    public List<IGUserInPhoto> getUsersInPhoto() {
        return usersInPhoto;
    }

    /**
     *
     * @param usersInPhoto
     * The users_in_photo
     */
    public void setUsersInPhoto(List<IGUserInPhoto> usersInPhoto) {
        this.usersInPhoto = usersInPhoto;
    }

    /**
     *
     * @return
     * The caption
     */
    public IGCaption getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     * The caption
     */
    public void setCaption(IGCaption caption) {
        this.caption = caption;
    }

    /**
     *
     * @return
     * The userHasLiked
     */
    public Boolean getUserHasLiked() {
        return userHasLiked;
    }

    /**
     *
     * @param userHasLiked
     * The user_has_liked
     */
    public void setUserHasLiked(Boolean userHasLiked) {
        this.userHasLiked = userHasLiked;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The user
     */
    public IGUser getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(IGUser user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The location
     */
    public IGLocation getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(IGLocation location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The videos
     */
    public IGVideos getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     * The videos
     */
    public void setVideos(IGVideos videos) {
        this.videos = videos;
    }


}
