package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGRelationship {

    @SerializedName("outgoing_status")
    @Expose
    String outgoingStatus;

    @SerializedName("incoming_status")
    @Expose
    String incomoingStatus;

    public String getOutgoingStatus() {
        return outgoingStatus;
    }

    public void setOutgoingStatus(String outgoingStatus) {
        this.outgoingStatus = outgoingStatus;
    }

    public String getIncomoingStatus() {
        return incomoingStatus;
    }

    public void setIncomoingStatus(String incomoingStatus) {
        this.incomoingStatus = incomoingStatus;
    }
}
