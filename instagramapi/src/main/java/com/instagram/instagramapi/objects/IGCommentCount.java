package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGCommentCount implements Serializable {


    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private ArrayList<IGComment> comments = new ArrayList<>();


    /**
     * @return The count
     */
    public Integer getCommentCount() {
        return count;
    }

    /**
     * @return Arraylist of Comments
     */
    public void setCommentCount(Integer count) {
        this.count = count;
    }

    public ArrayList<IGComment> getComments() {
        return comments;
    }

}
