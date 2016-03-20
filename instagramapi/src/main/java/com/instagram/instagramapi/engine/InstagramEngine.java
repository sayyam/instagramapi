package com.instagram.instagramapi.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.interfaces.InstagramAPIService;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGComment;
import com.instagram.instagramapi.objects.IGLike;
import com.instagram.instagramapi.objects.IGLocation;
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPostResponse;
import com.instagram.instagramapi.objects.IGRelationship;
import com.instagram.instagramapi.objects.IGTag;
import com.instagram.instagramapi.objects.InstagramAPIResponse;
import com.instagram.instagramapi.objects.InstagramMedia;
import com.instagram.instagramapi.objects.InstagramSession;
import com.instagram.instagramapi.objects.IGUser;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    private String appClientID;
    private String appRedirectURL;
    private InstagramSession session;
    //@property (nonatomic, strong, nonnull) AFHTTPSessionManager *httpManager;


    private static InstagramEngine instance = null;
    private Retrofit retrofit;
    private InstagramAPIService instagramAPIService;
    private InstagramLoginCallbackListener instagramLoginButtonCallback;

    private static Context mContext;

    private InstagramEngine() {

        retrofit = new Retrofit.Builder()
                .baseUrl(InstagramKitConstants.kInstagramKitBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        instagramAPIService = retrofit.create(InstagramAPIService.class);

        ApplicationInfo app = null;

        try {

            app = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = app.metaData;

            appClientID = bundle.getString(InstagramKitConstants.kInstagramAppClientIdConfigurationKey);
            appRedirectURL = bundle.getString(InstagramKitConstants.kInstagramAppRedirectURLConfigurationKey);


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


    public void setSession(InstagramSession _session) {
        session = _session;
    }


    public String authorizationURL() {
        return authorizationURLForScope(InstagramKitLoginScope.BASIC);
    }


    public String authorizationURLForScope(String... scope) {

        Map<String, String> parameters = authorizationParametersWithScope(scope);

        String authRequestURL = Utils.constructURL(InstagramKitConstants.kInstagramKitAuthorizationURL, parameters);

        return authRequestURL;

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
     * Get currently logged in user profile.
     *
     * @param callback Provides an array of Media objects and Pagination info.
     */
    public void getUserDetails(InstagramAPIResponseCallback<IGUser> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getUser(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, IGUser.class));
    }

    /**
     * Get information about a user. This endpoint requires the public_content scope if the user-id is not the owner of the access_token.
     *
     * @param callback Provides user object of given id.
     * @param userId   Id of user you want to get profile of.
     */
    public void getUserDetails(InstagramAPIResponseCallback<IGUser> callback, String userId) {

        Call<InstagramAPIResponse> call = instagramAPIService.getUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, IGUser.class));
    }

    /**
     * Get the most recent media published by a user.
     * This endpoint requires the public_content scope if the user-id is not the owner of the access_token.
     *
     * @param callback Media list
     */
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<InstagramMedia>> callback) {
        getMediaForUser(callback, 0, null);
    }

    /**
     * Get the most recent media published by a user.
     * This endpoint requires the public_content scope if the user-id is not the owner of the access_token.
     *
     * @param callback Media list
     */
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<InstagramMedia>> callback, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.getMediaForUser(getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<InstagramMedia>>() {
        }.getType()));
    }

    /**
     * Get the most recent media published by a user.This API requires the public_content scope if the user_id is not the owner of the access_token.
     *
     * @param callback Arraylist<InstagramMedia>
     */
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<InstagramMedia>> callback, String userId) {

        getMediaForUser(callback, userId, 0, null);
    }

    /**
     * Get the most recent media published by a user.This API requires the public_content scope if the user_id is not the owner of the access_token.
     *
     * @param callback Arraylist<InstagramMedia>
     */
    //synced
    public void getMediaForUser(InstagramAPIResponseCallback<ArrayList<InstagramMedia>> callback, String userId, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.getMediaForUser(userId, getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<InstagramMedia>>() {
        }.getType()));
    }


    /**
     * Get the list of recent media liked by the owner of the access_token.
     *
     * @param callback Arraylist<InstagramMedia>
     */
    public void getUserLikedMedia(InstagramAPIResponseCallback<ArrayList<InstagramMedia>> callback) {

        getUserLikedMedia(callback, 0, null);
    }

    /**
     * Get the list of recent media liked by the owner of the access_token.
     *
     * @param callback Arraylist<InstagramMedia>
     */
    public void getUserLikedMedia(InstagramAPIResponseCallback<ArrayList<InstagramMedia>> callback, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.getUserLikedMedia(getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<InstagramMedia>>() {
        }.getType()));
    }

    /**
     * Get a list of users matching the query.
     *
     * @param callback Arraylist<InstagramUser>
     * @param query    term to search
     */
    //synced
    public void searchUser(InstagramAPIResponseCallback<ArrayList<IGUser>> callback, String query) {

        Call<InstagramAPIResponse> call = instagramAPIService.searchUser(query, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    //-------RELATIONSHIP-------

    /**
     * Get the list of users this user follows.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void getUsersIFollow(InstagramAPIResponseCallback<ArrayList<IGUser>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getFollows(getSession().getAccessToken());
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

        Call<InstagramAPIResponse> call = instagramAPIService.getRelationshipStatusOfUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGRelationship>() {
        }.getType()));
    }

    /**
     * Modify the relationship between the current user and the target user. You need to include an action parameter to specify the relationship action you want to perform. Valid actions are: 'follow', 'unfollow' 'approve' or 'ignore'. Relationships are expressed using the following terms in the response:
     * -outgoing_status: Your relationship to the user. Can be 'follows', 'requested', 'none'.
     * -incoming_status: A user's relationship to you. Can be 'followed_by', 'requested_by', 'blocked_by_you', 'none'.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void updateRelationShip(InstagramAPIResponseCallback<IGRelationship> callback, String action, String userId) {

        Call<InstagramAPIResponse> call = instagramAPIService.updateRelationship(userId, action, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGRelationship>() {
        }.getType()));
    }

    //    -------RELATIONSHIP WRAPPERS-------

    public void getUsersFollowedByUser(InstagramAPIResponseCallback<ArrayList<IGUser>> callback, String userId) {

        Call<InstagramAPIResponse> call = instagramAPIService.getUsersFollowedByUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    public void getFollowedBy(InstagramAPIResponseCallback<ArrayList<IGUser>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getFollowedBy(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    public void getFollowersOfUser(InstagramAPIResponseCallback<ArrayList<IGUser>> callback, String userId) {

        Call<InstagramAPIResponse> call = instagramAPIService.getFollowersOfUser(userId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    /**
     * List the users who have requested this user's permission to follow.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void getFollowRequests(InstagramAPIResponseCallback<ArrayList<IGUser>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getFollowRequests(getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGUser>>() {
        }.getType()));
    }

    public void followUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionFollow, userId);
    }

    public void unFollowUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionUnfollow, userId);
    }

    public void blockUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionBlock, userId);
    }

    public void unblockUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionUnblock, userId);
    }

    public void approveUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionApprove, userId);
    }

    public void ignoreUser(InstagramAPIResponseCallback<IGRelationship> callback, String userId) {
        updateRelationShip(callback, InstagramKitConstants.kRelationshipActionIgnore, userId);
    }


    //-------MEDIA-------

    /**
     * Get information about a Media object.
     *
     * @param mediaId  Id of a Media object.
     * @param callback callback.
     */
    public void getMedia(String mediaId, InstagramAPIResponseCallback<InstagramMedia> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getMedia(mediaId, getSession().getAccessToken());

        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<InstagramMedia>() {
        }.getType()));
    }

    /**
     * This endpoint returns the same response as GET /media/media-id.
     * A media object's shortcode can be found in its shortlink URL.
     * An example shortlink is http://instagram.com/p/tsxp1hhQTG/. Its corresponding shortcode is tsxp1hhQTG.
     *
     * @param callback Arraylist<InstagramUser>
     */
    public void getMediaByShortCode(InstagramAPIResponseCallback<InstagramMedia> callback, String shortCode) {

        Call<InstagramAPIResponse> call = instagramAPIService.getMediaByShortCode(shortCode, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<InstagramMedia>() {
        }.getType()));
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param callback Provides an array of Media objects and Pagination info.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, IGLocation location) {
        getMediaAtLocation(callback, location, 0, null);
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param callback Provides an array of Media objects and Pagination info.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, IGLocation location, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.getMediaAtLocation(location.getLatitude(), location.getLongitude(), getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param callback Provides an array of Media objects and Pagination info.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, Float distance, IGLocation location) {
        getMediaAtLocation(callback, distance, location, 0, null);
    }

    /**
     * Search for media in a given area. The default time span is set to 5 days.
     * Can return mix of image and video types.
     *
     * @param location Geographic Location coordinates.
     * @param callback Provides an array of Media objects and Pagination info.
     */
    public void getMediaAtLocation(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, Float distance, IGLocation location, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.getMediaAtLocation(distance, location.getLatitude(), location.getLongitude(), getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }



//    -------COMMENT-------

    /**
     * Get a list of recent comments on a media object. The public_content permission scope is required to get comments for a media that does not belong to the owner of the access_token.
     *
     * @param mediaId  Id of media you want to get comments of.
     * @param callback
     */
    public void getCommentsOnMedia(String mediaId, InstagramAPIResponseCallback<ArrayList<IGComment>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getCommentsOnMedia(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGComment>>() {
        }.getType()));
    }

    /**
     * Create a comment on a media object with the following rules:
     * -The total length of the comment cannot exceed 300 characters.
     * -The comment cannot contain more than 4 hashtags.
     * -The comment cannot contain more than 1 URL.
     * -The comment cannot consist of all capital letters.
     * The public_content permission scope is required to create comments on a media that does not belong to the owner of the access_token.
     *
     * @param commentText Comment text you want to post on media.
     * @param mediaId     Id of media to post comment on.
     * @param callback
     */
    public void postCommentOnMedia(String commentText, String mediaId, InstagramAPIResponseCallback<IGPostResponse> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.postCommentOnMedia(commentText, mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }

    /**
     * Remove a comment either on the authenticated user's media object or authored by the authenticated user.
     *
     * @param commentId Id of comment to delete.
     * @param mediaId   Id of media to remove comment from.
     * @param callback
     */
    public void removeComment(String commentId, String mediaId, InstagramAPIResponseCallback<IGPostResponse> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.removeComment(commentId, mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }

    //    -------LIKES-------

    /**
     * Get a list of users who have liked this media.
     *
     * @param mediaId  Id of media you want to get comments of.
     * @param callback
     */
    public void getLikesOnMedia(String mediaId, InstagramAPIResponseCallback<ArrayList<IGLike>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getLikesOnMedia(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGLike>>() {
        }.getType()));
    }

    /**
     * Set a like on this media by the currently authenticated user.
     * The public_content permission scope is required to create likes on a media that does not belong to the owner of the access_token.
     *
     * @param mediaId  Id of media to post comment on.
     * @param callback
     */
    public void likeMedia(String mediaId, InstagramAPIResponseCallback<IGPostResponse> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.postMediaLike(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }

    /**
     * Remove a like on this media by the currently authenticated user. The public_content permission scope is required to delete likes on a media that does not belong to the owner of the access_token.
     *
     * @param mediaId  Id of media to remove likes from.
     * @param callback
     */
    public void unlikeMedia(String mediaId, InstagramAPIResponseCallback<IGPostResponse> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.deleteMediaLikes(mediaId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGPostResponse>() {
        }.getType()));
    }


//    -------TAG-------

    /**
     * Get information about a tag object.
     *
     * @param tagName  Id of media you want to get comments of.
     * @param callback
     */
    public void getTagDetails(String tagName, InstagramAPIResponseCallback<IGTag> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getTagDetails(tagName, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGTag>() {
        }.getType()));
    }

    /**
     * Get a list of recently tagged media.
     *
     * @param tagName  tag string for which you want recent media.
     * @param callback
     */
    public void getMediaWithTagName(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String tagName) {

        getMediaWithTagName(callback, tagName, 0, null);
    }

    /**
     * Get a list of recently tagged media.
     *
     * @param tagName  tag string for which you want recent media.
     * @param callback
     */
    public void getMediaWithTagName(InstagramAPIResponseCallback<ArrayList<IGMedia>> callback, String tagName, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.getMediaWithTagName(tagName, getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }

    /**
     * Search for tags by name.
     *
     * @param tagName  string you want to get related tags.
     * @param callback
     */
    public void searchTagsWithName(InstagramAPIResponseCallback<ArrayList<IGTag>> callback, String tagName) {

        searchTagsWithName(callback, tagName, 0, null);
    }

    /**
     * Search for tags by name.
     *
     * @param callback
     * @param tagName  string you want to get related tags.
     * @param count    number of items you want to fetch
     * @param maxId
     */
    public void searchTagsWithName(InstagramAPIResponseCallback<ArrayList<IGTag>> callback, String tagName, int count, String maxId) {

        HashMap<String, String> pageParameters = parametersFromCount(count, maxId, InstagramKitConstants.kPaginationKeyMaxId);

        Call<InstagramAPIResponse> call = instagramAPIService.searchTagsWithName(tagName, getSession().getAccessToken(), pageParameters);
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGTag>>() {
        }.getType()));
    }

    //    -------LOCATION RAW-------

    /**
     * Get information about a location.
     *
     * @param locationId Id of location you want to get information of.
     * @param callback
     */
    //synced getLocationWithId
    public void getLocation(String locationId, InstagramAPIResponseCallback<IGLocation> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getLocation(locationId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<IGLocation>() {
        }.getType()));
    }


    /**
     * Get a list of recent media objects from a given location.
     *
     * @param locationId Id of location you want the recent media from.
     * @param callback
     */
    public void getRecentMediaFromLocation(String locationId, InstagramAPIResponseCallback<ArrayList<IGMedia>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.getRecentMediaFromLocation(locationId, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGMedia>>() {
        }.getType()));
    }


    /**
     * Search for a location by geographic coordinate.
     *
     * @param location geographic coordinate you want to searach locations around.
     * @param callback
     */
    //synced
    public void searchLocation(IGLocation location, InstagramAPIResponseCallback<ArrayList<IGLocation>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.searchLocation(location.getLatitude(), location.getLongitude(), getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGLocation>>() {
        }.getType()));
    }

    /**
     * Search for a location by geographic coordinate.
     *
     * @param location geographic coordinate you want to searach locations around.
     * @param callback
     */
    //synced
    public void searchLocation(IGLocation location, int distanceInMeters, InstagramAPIResponseCallback<ArrayList<IGLocation>> callback) {

        Call<InstagramAPIResponse> call = instagramAPIService.searchLocation(location.getLatitude(), location.getLongitude(), distanceInMeters, getSession().getAccessToken());
        call.enqueue(new InstagramAPIResponseManager<>(callback, new TypeToken<ArrayList<IGLocation>>() {
        }.getType()));
    }


    //    -------LOCATION WRAPPERS-------


    //=======================================================================================================================


    private Map<String, String> mapWithAccessTokenAndParameters(Map<String, String> params) {

        if (null != session) {
            params.put(InstagramKitConstants.kKeyAccessToken, session.getAccessToken());
        } else {
            params.put(InstagramKitConstants.kKeyClientID, appClientID);
        }

        return params;
    }

    public void getPaginatedPath(String path, Map<String, String> parameters, Class modelClass, Callback callback) {

    }

    public String getAppRedirectURL() {
        return appRedirectURL;
    }

    public void setAppRedirectURL(String appRedirectURL) {
        this.appRedirectURL = appRedirectURL;
    }

    public String getAppClientID() {
        return appClientID;
    }

    public void setAppClientID(String appClientID) {
        this.appClientID = appClientID;
    }

    public InstagramSession getSession() {
        return session;
    }

    public InstagramLoginCallbackListener getInstagramLoginButtonCallback() {
        return instagramLoginButtonCallback;
    }

    public void setInstagramLoginButtonCallback(InstagramLoginCallbackListener instagramLoginButtonCallback) {
        this.instagramLoginButtonCallback = instagramLoginButtonCallback;
    }

    Callback<InstagramAPIResponse> instagramMediaCallback = new Callback<InstagramAPIResponse>() {
        @Override
        public void onResponse(Call<InstagramAPIResponse> call, Response<InstagramAPIResponse> response) {
            Log.v("SampleActivity", "onResponse:" + response.body().toString());
        }

        @Override
        public void onFailure(Call<InstagramAPIResponse> call, Throwable t) {
            Log.v("SampleActivity", "onFailure:");
        }
    };
}
