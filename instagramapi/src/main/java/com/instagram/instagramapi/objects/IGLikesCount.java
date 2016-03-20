package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGLikesCount implements Serializable {
    @SerializedName("count")
    @Expose
    private Integer count;

    /**
     * @return The count
     */
    public Integer getLikesCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setLikesCount(Integer count) {
        this.count = count;
    }

}