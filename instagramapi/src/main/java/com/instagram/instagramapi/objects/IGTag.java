package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGTag {
    @SerializedName("media_count")
    @Expose
    private Long mediaCount;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     *
     * @return
     * The mediaCount
     */
    public Long getMediaCount() {
        return mediaCount;
    }

    /**
     *
     * @param mediaCount
     * The media_count
     */
    public void setMediaCount(Long mediaCount) {
        this.mediaCount = mediaCount;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

}