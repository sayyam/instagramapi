package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sayyam on 3/20/16.
 */
public class IGPositionInPhoto {

    @SerializedName("y")
    @Expose
    private Float y;
    @SerializedName("x")
    @Expose
    private Float x;

    /**
     *
     * @return
     * The y
     */
    public Float getY() {
        return y;
    }

    /**
     *
     * @param y
     * The y
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     *
     * @return
     * The x
     */
    public Float getX() {
        return x;
    }

    /**
     *
     * @param x
     * The x
     */
    public void setX(Float x) {
        this.x = x;
    }

}