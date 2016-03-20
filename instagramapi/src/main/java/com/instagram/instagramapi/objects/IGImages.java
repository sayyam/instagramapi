package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGImages implements Serializable {
    @SerializedName("low_resolution")
    @Expose
    private IGImage lowResolution;
    @SerializedName("thumbnail")
    @Expose
    private IGImage thumbnail;
    @SerializedName("standard_resolution")
    @Expose
    private IGImage standardResolution;

    /**
     * @return The lowResolution
     */
    public IGImage getLowResolution() {
        return lowResolution;
    }

    /**
     * @param lowResolution The low_resolution
     */
    public void setLowResolution(IGImage lowResolution) {
        this.lowResolution = lowResolution;
    }

    /**
     * @return The thumbnail
     */
    public IGImage getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail The thumbnail
     */
    public void setThumbnail(IGImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return The standardResolution
     */
    public IGImage getStandardResolution() {
        return standardResolution;
    }

    /**
     * @param standardResolution The standard_resolution
     */
    public void setStandardResolution(IGImage standardResolution) {
        this.standardResolution = standardResolution;
    }

}