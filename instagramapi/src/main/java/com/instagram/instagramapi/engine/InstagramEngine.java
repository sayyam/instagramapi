package com.instagram.instagramapi.engine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instagram.instagramapi.activities.InstagramAuthActivity;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.interfaces.InstagramAPIService;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGAPIResponse;
import com.instagram.instagramapi.objects.IGComment;
import com.instagram.instagramapi.objects.IGLike;
import com.instagram.instagramapi.objects.IGLocation;
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPostResponse;
import com.instagram.instagramapi.objects.IGRelationship;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.objects.IGTag;
import com.instagram.instagramapi.objects.IGUser;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sayyam on 3/17/16.
 * <p/>
 * Copyright (c) 2015 Sayyam
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class InstagramEngine {

    public static String TYPE = "type";
    public static String SCOPE = "scope";
    final public static int TYPE_LOGOUT = 2;
    final public static int TYPE_LOGIN = 1;
    public static String IS_LOGIN_BUTTON = "insta_login_button";

    final public static int REQUEST_CODE_LOGIN = 3111;
    final public static int REQUEST_CODE_LOGOUT = 3112;

    private String appClientID;
    private String appRedirectURL;
    private IGSession session;

    private static InstagramEngine instance = null;
    private Retrofit retrofit;
    private InstagramAPIService instagramAPIService;
    private InstagramLoginCallbackListener instagramLoginButtonCallback;

    private static Context mContext;
    private SharedPreferences sharedPref;

    private InstagramEngine() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(InstagramKitConstants.kInstagramKitBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        instagramAPIService = retrofit.create(InstagramAPIService.class);

        sharedPref = mContext.getSharedPreferences(InstagramKitConstants.kSessionKey, Context.MODE_PRIVATE);

        ApplicationInfo app;

        try {

            app = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = app.metaData;

            setAppClientID(bundle.getString(InstagramKitConstants.kInstagramAppClientIdConfigurationKey));
            setAppRedirectURL(bundle.getString(InstagramKitConstants.kInstagramAppRedirectURLConfigurationKey));

            if (appClientID != null && !appClientID.isEmpty()) {
            } else {
                throw new RuntimeException(String.format("[InstagramAPI] ERROR : Invalid Client ID. Please set a valid value for the meta-data tag  %s in AndroidManifest file.", InstagramKitConstants.kInstagramAppClientIdConfigurationKey));
            }

            if (appRedirectURL != null && !appRedirectURL.isEmpty()) {
            } else {
                throw new RuntimeException(String.format("[InstagramAPI] ERROR : Invalid Redirect URL. Please set a valid value for the meta-data tag  %s in AndroidManifest file.", InstagramKitConstants.kInstagramAppRedirectURLConfigurationKey));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static InstagramEngine getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new InstagramEngine();
        }
        return instance;
    }


    public static InstagramEngine getInstance(Context context, IGSession session) {
        if (instance == null) {
            mContext = context;
            instance = new InstagramEngine();
            instance.setSession(session);
        }
        return instance;
    }

    public String getAppRedirectURL() {
        return appRedirectURL;
    }

    private void setAppRedirectURL(String appRedirectURL) {
        this.appRedirectURL = appRedirectURL;
    }

    public String getAppClientID() {
        return appClientID;
    }

    private void setAppClientID(String appClientID) {
        this.appClientID = appClientID;
    }

    public IGSession getSession() {

        if (null != session) {
            return session;
        } else {

            String stringSession = readFromSharedPreferences(InstagramKitConstants.kSessionKey);

            if (null != stringSession) {
                session = new Gson().fromJson(stringSession, IGSession.class);
                if (null != session && null != session.getAccessToken() && !session.getAccessToken().isEmpty())
                    return session;
            }

            throw new RuntimeException("Invalid session, Please get your application authorized by user.");
        }
    }


    public void setSession(IGSession _session) {
        session = _session;
        if (null == session) {
            removeFromSharedPreferences(InstagramKitConstants.kSessionKey);
        } else {
            saveOnSharedPreferences(InstagramKitConstants.kSessionKey, new Gson().toJson(session));
        }

    }

    public void logout(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, InstagramAuthActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra(TYPE, TYPE_LOGOUT);
        activity.startActivityForResult(intent, requestCode);

        setSession(null);
    }


    public String authorizationURL() {
        return authorizationURLForScope(InstagramKitLoginScope.BASIC);
    }


    public String authorizationURLForScope(String... scope) {

        Map<String, String> parameters = authorizationParametersWithScope(scope);

        String authRequestURL = Utils.constructURL(InstagramKitConstants.kInstagramKitAuthorizationURL, parameters);

        return authRequestURL;
    }

    private void saveOnSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String readFromSharedPreferences(String key) {
        return sharedPref.getString(key, null);
    }

    private void removeFromSharedPreferences(String key) {
        if (sharedPref.contains(InstagramKitConstants.kSessionKey)) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(key);
            editor.commit();
        }
    }

    //InstagramKitLoginScope
    Map<String, String> authorizationParametersWithScope(String... scope) {

        String scopeString = stringsForScope(scope);
        Map<String, String> parameters = new HashMap<>();

        parameters.put("client_id", appClientID);
        parameters.put("redirect_uri", appRedirectURL);
        parameters.put("response_type", "token");
        parameters.put("scope", scopeString);

        return parameters;
    }

    public String stringsForScope(String... scope) {

        String delimiter = "";

        StringBuilder builder = new StringBuilder();
        for (String s : scope) {
            builder.append(delimiter);
            builder.append(s);
            delimiter = " ";
        }

        System.out.println("Concatenated:" + builder.toString());

        return builder.toString();

    }


    HashMap<String, String> parametersFromCount(int count, String maxId, String paginationKey) {

        HashMap<String, String> params = new HashMap<>();

        if (count > 0) {
            params.put(InstagramKitConstants.kCount, count + "");
        }

        if (null != maxId && !maxId.isEmpty()) {
            params.put(paginationKey, maxId);

        }
        return params;
    }


    //-------USER-------

    /**
     * Get basic information about a user.
     *
     * @param userId   Id of a User object.
     * @param callback Provides a fully populated IGUser object.
     */
    public void requestURL(InstagramAPIResponseCallback<IGUser> callback, String userId) {

        Call<IGAPIResponse> call = instagramAPIService.getUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, IGUser.class));
    }

    /**
     * Get basic information about a user.
     *
     * @param userId   Id of a User object.
     * @param callback Provides a fully populated IGUser object.
     */
    public void getUserDetails(InstagramAPIResponseCallback<IGUser> callback, String userId) {

        Call<IGAPIResponse> call = instagramAPIService.getUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, IGUser.class));
    }

    /**
     * Get the most recent media published by a user.
     *
     * @param userId   Id of a User object.
     * @param callback Provides an array of Media objects and IGPageInfo object.
     */
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String userId) {

        getMediaForUser(callback, userId, 0, null);
    }

    /**
     * Get the most recent media published by a user.
     *
     * @param userId   Id of a IGUser object.
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param callback Provides an array of Media objects and IGPageInfo object.
     */
    //synced
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String userId, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.getMediaForUser(userId, getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }


    /**
     * Search for a user by name.
     *
     * @param name     username string as search query.
     * @param callback Provides an array of IGUser objects and IGPageInfo object.
     */
    //synced
    public void searchUser(InstagramAPIResponseCallback<ArrayList<IGUser>> callback, String name) {

        Call<IGAPIResponse> call = instagramAPIService.searchUser(name, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }


    //-------SELF-------

    /**
     * Get basic information about the authenticated user.
     *
     * @param callback Provides an IGUser object.
     */
    public void getUserDetails(InstagramAPIResponseCallback<IGUser> callback) {

        Call<IGAPIResponse> call = instagramAPIService.getUser(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, IGUser.class));
    }

    /**
     * Get the most recent media published by the authenticated user.
     *
     * @param callback Provides an array of Media objects and IGPageInfo object.
     */
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback) {
        getMediaForUser(callback, 0, null);
    }

    /**
     * Get the most recent media published by the authenticated user.
     *
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.getMediaForUser(getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }

    /**
     * See the list of media liked by the authenticated user.
     * Private media is returned as long as the authenticated user has permission to view that media.
     * Liked media lists are only available for the currently authenticated user.
     *
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getUserLikedMedia(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback) {

        getUserLikedMedia(callback, 0, null);
    }

    /**
     * See the list of media liked by the authenticated user.
     * Private media is returned as long as the authenticated user has permission to view that media.
     * Liked media lists are only available for the currently authenticated user.
     *
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getUserLikedMedia(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.getUserLikedMedia(getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }


    //-------RELATIONSHIP-------

    /**
     * Get the list of users this user follows.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void getUsersIFollow(InstagramAPIResponseCallback<ArrayList<IGUser>> callback) {

        Call<IGAPIResponse> call = instagramAPIService.getFollows(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    /**
     * Get information about a relationship to another user. Relationships are expressed using the following terms in the response:
     * -outgoing_status: Your relationship to the user. Can be 'follows', 'requested', 'none'.
     * -incoming_status: A user's relationship to you. Can be 'followed_by', 'requested_by', 'blocked_by_you', 'none'.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void getRelationshipStatusOfUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {

        Call<IGAPIResponse> call = instagramAPIService.getRelationshipStatusOfUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGRelationship>() {
        }.getType()));
    }

    /**
     * Modify the relationship between the current user and the target user. You need to include an action parameter to specify the relationship action you want to perform. Valid actions are: 'follow', 'unfollow' 'approve' or 'ignore'.
     * Relationships are expressed using the following terms in the response:
     * -outgoing_status: Your relationship to the user. Can be 'follows', 'requested', 'none'.
     * -incoming_status: A user's relationship to you. Can be 'followed_by', 'requested_by', 'blocked_by_you', 'none'.
     *
     * @param callback Arraylist<InstagramUser>
     * @param action   Action to perform on a relationship follow, unfollow, block, unblock, approve, ignore
     * @param userId   Id of the User object to perform action on.
     */
    private void updateRelationShip(InstagramAPIResponseCallback<IGRelationship> callback, String action, String userId) {

        Call<IGAPIResponse> call = instagramAPIService.updateRelationship(userId, action, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGRelationship>() {
        }.getType()));
    }

    //    -------RELATIONSHIP WRAPPERS-------

    /**
     * Get the list of users userId follows.
     *
     * @param userId   Id of the User object.
     * @param callback Provides an array of User objects as IGUser and IGPageInfo object.
     */
    public void getUsersFollowedByUser(InstagramAPIResponseCallback<ArrayList<IGUser>> callback, String userId) {

        Call<IGAPIResponse> call = instagramAPIService.getUsersFollowedByUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    /**
     * Get the list of users this user is followed by.
     *
     * @param callback Provides an array of User objects as IGUser and IGPageInfo object.
     */
    public void getFollowedBy(InstagramAPIResponseCallback<ArrayList<IGUser>> callback) {

        Call<IGAPIResponse> call = instagramAPIService.getFollowedBy(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    /**
     * Get the list of users userId is followed by.
     *
     * @param userId   Id of the IGUser object.
     * @param callback Provides an array of User objects as IGUser and IGPageInfo.
     */
    public void getFollowersOfUser(InstagramAPIResponseCallback<ArrayList<IGUser>> callback, String userId) {

        Call<IGAPIResponse> call = instagramAPIService.getFollowersOfUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    /**
     * List the users who have requested this user's permission to follow.
     *
     * @param callback Provides an array of User objects as IGUser and IGPageInfo.
     */
    public void getFollowRequests(InstagramAPIResponseCallback<ArrayList<IGUser>> callback) {

        Call<IGAPIResponse> call = instagramAPIService.getFollowRequests(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    /**
     * Modify the relationship between the current user and the target user.
     * Follow a user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeRelationships during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param userId   Id of the User object.
     * @param callback Provides the server response as IGRelationship.
     */
    public void followUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionFollow, userId);
    }

    /**
     * Modify the relationship between the current user and the target user.
     * Unfollow a user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeRelationships during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param userId   Id of the User object.
     * @param callback Provides the server response as IGRelationship.
     */
    public void unFollowUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionUnfollow, userId);
    }

    /**
     * Modify the relationship between the current user and the target user.
     * Block a user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeRelationships during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param userId   Id of the User object.
     * @param callback Provides the server response as IGRelationship.
     */
    public void blockUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionBlock, userId);
    }

    /**
     * Modify the relationship between the current user and the target user.
     * Unblock a user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeRelationships during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param userId   Id of the User object.
     * @param callback Provides the server response as IGRelationship.
     */

    public void unblockUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionUnblock, userId);
    }

    /**
     * Modify the relationship between the current user and the target user.
     * Approve a user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeRelationships during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param userId   Id of the User object.
     * @param callback Provides the server response as IGRelationship.
     */
    public void approveUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionApprove, userId);
    }

    /**
     * Modify the relationship between the current user and the target user.
     * Ignore a user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeRelationships during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param userId   Id of the User object.
     * @param callback Provides the server response as IGRelationship.
     */
    public void ignoreUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionIgnore, userId);
    }


    //-------MEDIA-------


    /**
     * Get information about a Media object.
     *
     * @param callback Provides a fully populated IGMedia object.
     * @param mediaId  Id of a IGMedia object.
     */
    public void getMedia(InstagramAPIResponseCallback<IGMedia> callback, String mediaId) {

        Call<IGAPIResponse> call = instagramAPIService.getMedia(mediaId, getSession().getAccessToken());

        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGMedia>() {
        }.getType()));
    }

    /**
     * This endpoint returns the same response as GET /media/media-id.
     * A media object's shortcode can be found in its shortlink URL.
     * An example shortlink is http://instagram.com/p/tsxp1hhQTG/. Its corresponding shortcode is tsxp1hhQTG.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void getMediaByShortCode(InstagramAPIResponseCallback<IGMedia> callback, String shortCode) {

        Call<IGAPIResponse> call = instagramAPIService.getMediaByShortCode(shortCode, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGMedia>() {
        }.getType()));
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, IGLocation location) {
        getMediaAtLocation(callback, location, 0, null);
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, IGLocation location, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.getMediaAtLocation(location.getLatitude(), location.getLongitude(), getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }


    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param distance Distance in metres to from location - max 5000 (5km), default is 1000 (1km) in other methods
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, Float distance, IGLocation location) {
        getMediaAtLocation(callback, distance, location, 0, null);
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param distance Distance in metres to from location - max 5000 (5km), default is 1000 (1km) in other methods
     * @param callback Provides an array of IGMedia objects and IGPageInfo object.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, Float distance, IGLocation location, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.getMediaAtLocation(distance, location.getLatitude(), location.getLongitude(), getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }


//    -------COMMENT-------

    /**
     * Get a list of recent comments on a media object.
     *
     * @param callback Provides an array of IGComment objects.
     * @param mediaId  Id of the Media object.
     */
    public void getCommentsOnMedia(InstagramAPIResponseCallback<ArrayList<IGComment>> callback, String mediaId) {

        Call<IGAPIResponse> call = instagramAPIService.getCommentsOnMedia(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGComment>>() {
        }.getType()));
    }

    /**
     * Create a comment on a media object with the following rules:
     * - The total length of the comment cannot exceed 300 characters.
     * - The comment cannot contain more than 4 hashtags.
     * - The comment cannot contain more than 1 URL.
     * - The comment cannot consist of all capital letters.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeComments during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param commentText The comment text.
     * @param mediaId     Id of the Media object.
     * @param callback    Invoked on successfully creating comment.
     */
    public void postCommentOnMedia(InstagramAPIResponseCallback<IGPostResponse> callback, String mediaId, String commentText) {

        Call<IGAPIResponse> call = instagramAPIService.postCommentOnMedia(mediaId, commentText, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }

    /**
     * Remove a comment either on the authenticated user's media object
     * or authored by the authenticated user.
     * <p/>
     * REQUIREMENTS : InstagramKitLoginScopeComments during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param commentId Id of the Comment object.
     * @param mediaId   Id of the Media object.
     * @param callback  Invoked on successfully deleting comment.
     */
    //TODO: Handle exception for REQUIREMENTS
    public void removeComment(InstagramAPIResponseCallback<IGPostResponse> callback, String commentId, String mediaId) {

        Call<IGAPIResponse> call = instagramAPIService.removeComment(commentId, mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }

    //    -------LIKES-------

    /**
     * Get a list of users who have liked this media.
     *
     * @param callback Provides an array of IGLike(subset of user)  objects.
     * @param mediaId  Id of the Media object.
     */
    public void getLikesOnMedia(InstagramAPIResponseCallback<ArrayList<IGLike>> callback, String mediaId) {

        Call<IGAPIResponse> call = instagramAPIService.getLikesOnMedia(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGLike>>() {
        }.getType()));
    }

    /**
     * Set a like on this media by the currently authenticated user.
     * REQUIREMENTS : InstagramKitLoginScopeLikes during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param callback Invoked on successfully liking a Media.
     * @param mediaId  Id of the Media object.
     */
    public void likeMedia(InstagramAPIResponseCallback<IGPostResponse> callback, String mediaId) {

        Call<IGAPIResponse> call = instagramAPIService.postMediaLike(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }

    /**
     * Remove a like on this media by the currently authenticated user.
     * REQUIREMENTS : InstagramKitLoginScopeLikes during authentication.
     * <p/>
     * To request access to this endpoint, please complete this form -
     * https://help.instagram.com/contact/185819881608116
     *
     * @param callback Invoked on successfully un-liking a Media.
     * @param mediaId  Id of the Media object.
     */
    public void unlikeMedia(InstagramAPIResponseCallback<IGPostResponse> callback, String mediaId) {

        Call<IGAPIResponse> call = instagramAPIService.deleteMediaLikes(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }


//    -------TAG-------

    /**
     * Get information about a tag object.
     *
     * @param callback Provides a IGTag object.
     * @param name     Name of a Tag object.
     */
    public void getTagDetails(InstagramAPIResponseCallback<IGTag> callback, String name) {

        Call<IGAPIResponse> call = instagramAPIService.getTagDetails(name, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGTag>() {
        }.getType()));
    }

    /**
     * Get a list of recently tagged media.
     *
     * @param name     A valid tag name without a leading #. (eg. snowy, nofilter)
     * @param callback Provides an array of IGMedia objects this user has added and IGPageInfo info.
     */
    public void getMediaWithTagName(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String name) {

        getMediaWithTagName(callback, name, 0, null);
    }

    /**
     * Get a list of recently tagged media.
     *
     * @param tag      Name of a Tag object.
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param callback Provides an array of IGMedia objects and IGPageInfo info.
     */
    public void getMediaWithTagName(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String tag, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.getMediaWithTagName(tag, getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }

    /**
     * Search for tags by name.
     *
     * @param name     A valid tag name without a leading #. (eg. snowy, nofilter)
     * @param callback Provides an array of IGTag objects and IGPageInfo info.
     */
    public void searchTagsWithName(InstagramAPIResponseCallback<ArrayList<IGTag>> callback, String name) {

        searchTagsWithName(callback, name, 0, null);
    }

    /**
     * Search for tags by name.
     *
     * @param name     A valid tag name without a leading #. (eg. snowy, nofilter)
     * @param count    Count of objects to fetch.
     * @param maxId    The nextMaxId from the previously obtained IGPage object.
     * @param callback Provides an array of IGTag objects and IGPageInfo info.
     */
    public void searchTagsWithName(InstagramAPIResponseCallback<ArrayList<IGTag>> callback, String name, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<IGAPIResponse> call = instagramAPIService.searchTagsWithName(name, getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGTag>>() {
        }.getType()));
    }

    //    -------LOCATION RAW-------

    /**
     * Search for a location by geographic coordinate.
     *
     * @param callback Provides an array of IGLocation objects.
     * @param location Geographic Location coordinates.
     */
    //synced
    public void searchLocationsAtLocation(InstagramAPIResponseCallback<ArrayList<IGLocation>> callback, IGLocation location) {

        Call<IGAPIResponse> call = instagramAPIService.searchLocation(location.getLatitude(), location.getLongitude(), getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGLocation>>() {
        }.getType()));
    }

    /**
     * Search for a location by geographic coordinate.
     *
     * @param callback         Provides an array of IGLocation objects.
     * @param location         Geographic Location coordinates.
     * @param distanceInMeters Default is 1000, max distance is 5000.
     */
    //synced
    public void searchLocationsAtLocation(InstagramAPIResponseCallback<ArrayList<IGLocation>> callback, IGLocation location, int distanceInMeters) {

        Call<IGAPIResponse> call = instagramAPIService.searchLocation(location.getLatitude(), location.getLongitude(), distanceInMeters, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGLocation>>() {
        }.getType()));
    }

    /**
     * Get information about a Location.
     *
     * @param callback   Provides a IGLocation object.
     * @param locationId Id of a IGLocation object.
     */
    //synced getLocationWithId
    public void getLocation(InstagramAPIResponseCallback<IGLocation> callback, String locationId) {

        Call<IGAPIResponse> call = instagramAPIService.getLocation(locationId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGLocation>() {
        }.getType()));
    }


    /**
     * Get a list of recent IGMedia objects from a given location.
     *
     * @param callback   Provides an array of IGMedia objects and IGPageInfo info
     * @param locationId Id of location you want the recent media from.
     */

    public void getRecentMediaFromLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String locationId) {

        Call<IGAPIResponse> call = instagramAPIService.getRecentMediaFromLocation(locationId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }


    //    -------LOCATION WRAPPERS-------


    //=======================================================================================================================


    /**
     * This method is used internal for handling login responses.
     *
     * @param instagramLoginButtonCallback
     */
    public void setInstagramLoginButtonCallback(InstagramLoginCallbackListener instagramLoginButtonCallback) {
        this.instagramLoginButtonCallback = instagramLoginButtonCallback;
    }

    /**
     * This method is used internal for handling login responses.
     */
    public InstagramLoginCallbackListener getInstagramLoginButtonCallback() {
        return instagramLoginButtonCallback;
    }


}
