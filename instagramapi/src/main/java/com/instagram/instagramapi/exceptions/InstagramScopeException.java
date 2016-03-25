package com.instagram.instagramapi.exceptions;

/**
 * Created by Sayyam on 3/18/16.
 */
public class InstagramScopeException extends Exception {

    String error;
    String errorReason;

    public InstagramScopeException() {
    }

    public InstagramScopeException(String detailMessage) {
        super(detailMessage);
    }

    public InstagramScopeException(String detailMessage, String error, String errorReason) {
        super(detailMessage);
        this.error = error;
        this.errorReason = errorReason;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
