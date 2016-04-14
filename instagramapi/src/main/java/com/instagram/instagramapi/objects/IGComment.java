package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGComment implements Serializable{
    @SerializedName("created_time")
    @Expose
    private Long createdTime;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("from")
    @Expose
    private IGUser from;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * @return The createdTime
     */
    public Long getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime The created_time
     */
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The from
     */
    public IGUser getFrom() {
        return from;
    }

    /**
     * @param from The from
     */
    public void setFrom(IGUser from) {
        this.from = from;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
