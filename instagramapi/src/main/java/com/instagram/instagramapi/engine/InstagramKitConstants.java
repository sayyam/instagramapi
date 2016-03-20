package com.instagram.instagramapi.engine;

/**
 * Created by Sayyam on 3/17/16.
 */
public class InstagramKitConstants {

    public static String kInstagramKitBaseURLConfigurationKey = "InstagramKitBaseUrl";
    public static String kInstagramKitAuthorizationURLConfigurationKey = "InstagramKitAuthorizationUrl";
    public static String kInstagramKitBaseURL = "https://api.instagram.com/v1/";
    public static String kInstagramKitAuthorizationURL = "https://api.instagram.com/oauth/authorize/";

    public static String kInstagramAppClientIdConfigurationKey = "com.instagram.instagramapi.InstagramAppClientId";
    public static String kInstagramAppRedirectURLConfigurationKey = "com.instagram.instagramapi.InstagramAppRedirectURL";

    public static String InstagramKitUserAuthenticationChangedNotification = "com.instagramkit.token.change";
    public static String InstagramKitErrorDomain = "com.instagramkit";
    public static String InstagramKitKeychainStore = "com.instagramkit.secure";

    public static String kKeyClientID = "client_id";
    public static String kKeyAccessToken = "access_token";
    public static String kKeychainTokenKey = "token";
    public static String kSessionKey = "session";

    public static String kPagination = "pagination";
    public static String kPaginationKeyMaxId = "max_id";
    public static String kPaginationKeyMaxLikeId = "max_like_id";
    public static String kPaginationKeyMaxTagId = "max_tag_id";
    public static String kPaginationKeyCursor = "cursor";

    public static String kRelationshipActionKey = "action";
    public static String kRelationshipActionFollow = "follow";
    public static String kRelationshipActionUnfollow = "unfollow";
    public static String kRelationshipActionBlock = "block";
    public static String kRelationshipActionUnblock = "unblock";
    public static String kRelationshipActionApprove = "approve";
    public static String kRelationshipActionIgnore = "ignore";

    protected static final String kID = "id";
    protected static final String kCount = "count";
    protected static final String kURL = "url";
    protected static final String kHeight = "height";
    protected static final String kWidth = "width";
    protected static final String kData = "data";

    protected static final String kThumbnail = "thumbnail";
    protected static final String kLowResolution = "low_resolution";
    protected static final String kStandardResolution = "standard_resolution";

    public static final String kMediaTypeImage = "image";
    public static final String kMediaTypeVideo = "video";

    protected static final String kUser = "user";
    protected static final String kUserHasLiked = "user_has_liked";
    protected static final String kCreatedDate = "created_time";
    protected static final String kLink = "link";
    protected static final String kCaption = "caption";
    protected static final String kLikes = "likes";
    protected static final String kComments = "comments";
    protected static final String kFilter = "filter";
    protected static final String kTags = "tags";
    protected static final String kImages = "images";
    protected static final String kVideos = "videos";
    protected static final String kLocation = "location";
    protected static final String kType = "type";

    protected static final String kCreator = "from";
    protected static final String kText = "text";

    protected static final String kUsername = "username";
    protected static final String kFullName = "full_name";
    protected static final String kFirstName = "first_name";
    protected static final String kLastName = "last_name";
    protected static final String kProfilePictureURL = "profile_picture";
    protected static final String kBio = "bio";
    protected static final String kWebsite = "website";

    protected static final String kCounts = "counts";
    protected static final String kCountMedia = "media";
    protected static final String kCountFollows = "follows";
    protected static final String kCountFollowedBy = "followed_by";

    protected static final String kUsersInPhoto = "users_in_photo";
    protected static final String kPosition = "position";
    protected static final String kX = "x";
    protected static final String kY = "y";

    protected static final String kTagMediaCount = "media_count";
    protected static final String kTagName = "name";

    protected static final String kLocationLatitude = "latitude";
    protected static final String kLocationLongitude = "longitude";
    protected static final String kLocationName = "name";

    protected static final String kNextURL = "next_url";
    protected static final String kNextMaxId = "next_max_id";
    protected static final String kNextMaxLikeId = "next_max_like_id";
    protected static final String kNextMaxTagId = "next_max_tag_id";
    protected static final String kNextCursor = "next_cursor";

    protected static final String kMaxId = "max_id";
    protected static final String kMaxLikeId = "max_like_id";
    protected static final String kMaxTagId = "max_tag_id";
    protected static final String kCursor = "cursor";

    String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


}
