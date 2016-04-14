package com.instagram.instagramapi.interfaces;

/**
 * Created by Sayyam on 3/17/16.
 */

import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.objects.IGSession;

/**
 * A callback class for the Instagram SDK.
 */
public interface InstagramLogoutCallbackListener {
    /**
     * Called when the dialog completes without error.
     *
     * @param result Result from the dialog
     */
    public void onSuccess(IGSession result);

    /**
     * Called when the dialog is canceled.
     */
    public void onCancel();

    /**
     * Called when the dialog finishes with an error.
     *
     * @param error The error that occurred
     */
    public void onError(InstagramException error);
}
