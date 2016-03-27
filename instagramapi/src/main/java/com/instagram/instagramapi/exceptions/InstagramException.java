package com.instagram.instagramapi.exceptions;

/**
 * Created by Sayyam on 3/18/16.
 */
public class InstagramException extends Exception {

    String errorType;
    String errorReason;

    public InstagramException() {
    }

    public InstagramException(String errorReason) {
        super(errorReason);
    }

    public InstagramException(String errorType,String errorReason) {
        super(errorReason);
        this.errorType = errorType;
        this.errorReason = errorReason;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
