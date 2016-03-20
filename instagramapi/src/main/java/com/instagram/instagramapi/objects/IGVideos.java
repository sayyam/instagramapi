package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/20/16.
 */
public class IGVideos implements Serializable{


    @SerializedName("low_bandwidth")
    @Expose
    private IGVideoResolution lowBandwidth;
    @SerializedName("standard_resolution")
    @Expose
    private IGVideoResolution standardResolution;
    @SerializedName("low_resolution")
    @Expose
    private IGVideoResolution lowResolution;

    /**
     * @return The lowBandwidth
     */
    public IGVideoResolution getLowBandwidth() {
        return lowBandwidth;
    }

    /**
     * @param lowBandwidth The low_bandwidth
     */
    public void setLowBandwidth(IGVideoResolution lowBandwidth) {
        this.lowBandwidth = lowBandwidth;
    }

    /**
     * @return The standardResolution
     */
    public IGVideoResolution getStandardResolution() {
        return standardResolution;
    }

    /**
     * @param standardResolution The standard_resolution
     */
    public void setStandardResolution(IGVideoResolution standardResolution) {
        this.standardResolution = standardResolution;
    }

    /**
     * @return The lowResolution
     */
    public IGVideoResolution getLowResolution() {
        return lowResolution;
    }

    /**
     * @param lowResolution The low_resolution
     */
    public void setLowResolution(IGVideoResolution lowResolution) {
        this.lowResolution = lowResolution;
    }

}