package com.instagram.instagramapi.interfaces;

import com.instagram.instagramapi.objects.IGAPIResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Sayyam on 3/17/16.
 */
public interface InstagramAPIService {

    //----USER----

    /**
     * @param accessToken
     * @return Returns Logged in user profile.
     */
    @GET("users/self")
    Call<IGAPIResponse> getUser(@Query("access_token") String accessToken);

    @GET("users/{user_id}")
    Call<IGAPIResponse> getUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET("users/self/media/recent")
    Call<IGAPIResponse> getMediaForUser(@Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("users/{user_id}/media/recent")
    Call<IGAPIResponse> getMediaForUser(@Path("user_id") String userId, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("users/self/media/liked")
    Call<IGAPIResponse> getUserLikedMedia(@Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("users/search")
    Call<IGAPIResponse> searchUser(@Query("q") String query, @Query("access_token") String accessToken);


    //----RELATIONSHIP----

    @GET("users/self/follows")
    Call<IGAPIResponse> getFollows(@Query("access_token") String accessToken);

    @GET("users/{user_id}/follows")
    Call<IGAPIResponse> getUsersFollowedByUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET("users/self/followed-by")
    Call<IGAPIResponse> getFollowedBy(@Query("access_token") String accessToken);

    @GET("users/{user_id}/followed-by")
    Call<IGAPIResponse> getFollowersOfUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET("users/self/requested-by")
    Call<IGAPIResponse> getFollowRequests(@Query("access_token") String accessToken);

    @GET("users/{user_id}/relationship")
    Call<IGAPIResponse> getRelationshipStatusOfUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @FormUrlEncoded
    @POST("users/{user_id}/relationship")
    Call<IGAPIResponse> updateRelationship(@Path("user_id") String userId, @Field("action") String action, @Query("access_token") String accessToken);


    //----MEDIA----

    @GET("media/{media_id}")
    Call<IGAPIResponse> getMedia(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @GET("media/shortcode/{short_code}")
    Call<IGAPIResponse> getMediaByShortCode(@Path("short_code") String shortCode, @Query("access_token") String accessToken);

    @Deprecated
    @GET("media/popular")
    Call<IGAPIResponse> getPopularMedia(@Query("access_token") String accessToken);

    @GET("media/search")
    Call<IGAPIResponse> getMediaAtLocation(@Query("lat") Double latitude, @Query("lng") Double longitude, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("media/search")
    Call<IGAPIResponse> getMediaAtLocation(@Query("distance") Float distance, @Query("lat") Double latitude, @Query("lng") Double longitude, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

//----COMMENT----

    @GET("media/{media_id}/comments")
    Call<IGAPIResponse> getCommentsOnMedia(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @FormUrlEncoded
    @POST("media/{media_id}/comments")
    Call<IGAPIResponse> postCommentOnMedia(@Path("media_id") String mediaId, @Field("text") String commentText, @Query("access_token") String accessToken);

    @DELETE("media/{media_id}/comments/{comment_id}")
    Call<IGAPIResponse> removeComment(@Path("comment_id") String commentId, @Path("media_id") String mediaId, @Query("access_token") String accessToken);

    //----LIKES----

    @GET("media/{media_id}/likes")
    Call<IGAPIResponse> getLikesOnMedia(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @POST("media/{media_id}/likes")
    Call<IGAPIResponse> postMediaLike(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @DELETE("media/{media_id}/likes")
    Call<IGAPIResponse> deleteMediaLikes(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    //----TAGS----

    @GET("tags/{tag_name}")
    Call<IGAPIResponse> getTagDetails(@Path("tag_name") String tagName, @Query("access_token") String accessToken);

    @GET("tags/{tag_name}/media/recent")
    Call<IGAPIResponse> getMediaWithTagName(@Path("tag_name") String tagName, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("tags/search")
    Call<IGAPIResponse> searchTagsWithName(@Query("q") String tagName, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    //-------LOCATION-------

    @GET("locations/{location_id}")
    Call<IGAPIResponse> getLocation(@Path("location_id") String tagName, @Query("access_token") String accessToken);

    @GET("locations/{location_id}/media/recent")
    Call<IGAPIResponse> getRecentMediaFromLocation(@Path("location_id") String tagName, @Query("access_token") String accessToken);

    @GET("locations/search")
    Call<IGAPIResponse> searchLocation(@Query("lat") Double latitude, @Query("lng") Double longitude, @Query("access_token") String accessToken);

    @GET("locations/search")
    Call<IGAPIResponse> searchLocation(@Query("lat") Double latitude, @Query("lng") Double longitude, @Query("distance") Integer distance, @Query("access_token") String accessToken);

}
