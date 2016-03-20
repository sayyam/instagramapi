package com.instagram.instagramapi.objects;

import android.location.Location;

/**
 * Created by Sayyam on 3/17/16.
 */
public class InstagramLocation {

    Location coordinates;
    String name;

    public Location getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Location coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
