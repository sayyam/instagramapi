package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sayyam on 3/20/16.
 */
public class IGPagInfo {

    @SerializedName("next_url")
    @Expose
    String nextURL;

    @SerializedName("next_min_id")
    @Expose
    String nextMinId;

    @SerializedName("next_max_id")
    @Expose
    String nextMaxId;

    @SerializedName("type")
    @Expose
    Class type;

    public String getNextURL() {
        return nextURL;
    }

    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }

    public String getNextMaxId() {
        return nextMaxId;
    }

    public void setNextMaxId(String nextMaxId) {
        this.nextMaxId = nextMaxId;
    }

    public String getNextMinId() {
        return nextMinId;
    }

    public void setNextMinId(String nextMinId) {
        this.nextMinId = nextMinId;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}
