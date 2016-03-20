package com.instagram.instagramapi.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/19/16.
 */
public class IGMeta implements Serializable {

    @SerializedName("error_type")
    @Expose
    String errorType;
    @SerializedName("code")
    @Expose
    Integer code;
    @SerializedName("errorMessage")
    @Expose
    String error_message;

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
