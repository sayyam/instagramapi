package com.instagram.instagramapi.interfaces;

import com.instagram.instagramapi.objects.InstagramAPIResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
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
    Call<InstagramAPIResponse> getUser(@Query("access_token") String accessToken);

    @GET("users/{user_id}")
    Call<InstagramAPIResponse> getUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET("users/self/media/recent")
    Call<InstagramAPIResponse> getMediaForUser(@Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("users/{user_id}/media/recent")
    Call<InstagramAPIResponse> getMediaForUser(@Path("user_id") String userId, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("users/self/media/liked")
    Call<InstagramAPIResponse> getUserLikedMedia(@Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("users/search")
    Call<InstagramAPIResponse> searchUser(@Query("q") String query, @Query("access_token") String accessToken);

    //----RELATIONSHIP----

    @GET("users/self/follows")
    Call<InstagramAPIResponse> getFollows(@Query("access_token") String accessToken);

    @GET("users/{user_id}/follows")
    Call<InstagramAPIResponse> getUsersFollowedByUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET("users/self/followed-by")
    Call<InstagramAPIResponse> getFollowedBy(@Query("access_token") String accessToken);

    @GET("users/{user_id}/followed-by")
    Call<InstagramAPIResponse> getFollowersOfUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET("users/self/requested-by")
    Call<InstagramAPIResponse> getFollowRequests(@Query("access_token") String accessToken);

    @GET("users/{user_id}/relationship")
    Call<InstagramAPIResponse> getRelationshipStatusOfUser(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @POST("users/{user_id}/relationship")
    Call<InstagramAPIResponse> updateRelationship(@Field("first_name") String first, @Path("user_id") String userId, @Query("access_token") String accessToken);


    //----MEDIA----

    @GET("media/{media_id}")
    Call<InstagramAPIResponse> getMedia(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @GET("media/shortcode/{short_code}")
    Call<InstagramAPIResponse> getMediaByShortCode(@Path("short_code") String shortCode, @Query("access_token") String accessToken);

    @Deprecated
    @GET("media/popular")
    Call<InstagramAPIResponse> getPopularMedia(@Query("access_token") String accessToken);

    @GET("media/search")
    Call<InstagramAPIResponse> getMediaAtLocation(@Query("lat") Double latitude, @Query("lng") Double longitude, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("media/search")
    Call<InstagramAPIResponse> getMediaAtLocation(@Query("distance") Float distance, @Query("lat") Double latitude, @Query("lng") Double longitude, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

//----COMMENT----

    @GET("media/{media_id}/comments")
    Call<InstagramAPIResponse> getCommentsOnMedia(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @POST("media/{media_id}/comments")
    Call<InstagramAPIResponse> postCommentOnMedia(@Path("text") String commentText, @Query("media_id") String mediaId, @Query("access_token") String accessToken);

    @DELETE("media/{media_id}/comments/{comment_id}")
    Call<InstagramAPIResponse> removeComment(@Path("comment_id") String commentId, @Path("media_id") String mediaId, @Query("access_token") String accessToken);

    //----LIKES----

    @GET("media/{media_id}/likes")
    Call<InstagramAPIResponse> getLikesOnMedia(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @POST("media/{media_id}/likes")
    Call<InstagramAPIResponse> postMediaLike(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    @DELETE("media/{media_id}/likes")
    Call<InstagramAPIResponse> deleteMediaLikes(@Path("media_id") String mediaId, @Query("access_token") String accessToken);

    //----TAGS----

    @GET("tags/{tag_name}")
    Call<InstagramAPIResponse> getTagDetails(@Path("tag_name") String tagName, @Query("access_token") String accessToken);

    @GET("tags/{tag_name}/media/recent")
    Call<InstagramAPIResponse> getMediaWithTagName(@Path("tag_name") String tagName, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    @GET("tags/search")
    Call<InstagramAPIResponse> searchTagsWithName(@Query("q") String tagName, @Query("access_token") String accessToken, @QueryMap HashMap<String, String> page);

    //-------LOCATION-------

    @GET("locations/{location_id}")
    Call<InstagramAPIResponse> getLocation(@Path("location_id") String tagName, @Query("access_token") String accessToken);

    @GET("locations/{location_id}/media/recent")
    Call<InstagramAPIResponse> getRecentMediaFromLocation(@Path("location_id") String tagName, @Query("access_token") String accessToken);

    @GET("locations/search")
    Call<InstagramAPIResponse> searchLocation(@Query("lat") Double latitude, @Query("lng") Double longitude, @Query("access_token") String accessToken);

    @GET("locations/search")
    Call<InstagramAPIResponse> searchLocation(@Query("lat") Double latitude, @Query("lng") Double longitude,@Query("distance") Integer distance, @Query("access_token") String accessToken);

}
