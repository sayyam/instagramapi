package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGLocation {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("id")
    @Expose
    private String id;

    public IGLocation() {
    }

    public IGLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
