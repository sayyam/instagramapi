package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGMedia {

    @SerializedName("data")
    @Expose
    ArrayList<InstagramMedia> media;

    public ArrayList<InstagramMedia> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<InstagramMedia> media) {
        this.media = media;
    }
}
