package com.instagram.instagramapi.objects;

/**
 * Created by Sayyam on 3/17/16.
 */
public class InstagramModel {

    static final String kID = "id";
    static final String kCount = "count";
    static final String kURL = "url";
    static final String kHeight = "height";
    static final String kWidth = "width";
    static final String kData = "data";

    static final String kThumbnail = "thumbnail";
    static final String kLowResolution = "low_resolution";
    static final String kStandardResolution = "standard_resolution";

    static final String kMediaTypeImage = "image";
    static final String kMediaTypeVideo = "video";

    static final String kUser = "user";
    static final String kUserHasLiked = "user_has_liked";
    static final String kCreatedDate = "created_time";
    static final String kLink = "link";
    static final String kCaption = "caption";
    static final String kLikes = "likes";
    static final String kComments = "comments";
    static final String kFilter = "filter";
    static final String kTags = "tags";
    static final String kImages = "images";
    static final String kVideos = "videos";
    static final String kLocation = "location";
    static final String kType = "type";

    static final String kCreator = "from";
    static final String kText = "text";

    static final String kUsername = "username";
    static final String kFullName = "full_name";
    static final String kFirstName = "first_name";
    static final String kLastName = "last_name";
    static final String kProfilePictureURL = "profile_picture";
    static final String kBio = "bio";
    static final String kWebsite = "website";

    static final String kCounts = "counts";
    static final String kCountMedia = "media";
    static final String kCountFollows = "follows";
    static final String kCountFollowedBy = "followed_by";

    static final String kUsersInPhoto = "users_in_photo";
    static final String kPosition = "position";
    static final String kX = "x";
    static final String kY = "y";

    static final String kTagMediaCount = "media_count";
    static final String kTagName = "name";

    static final String kLocationLatitude = "latitude";
    static final String kLocationLongitude = "longitude";
    static final String kLocationName = "name";

    static final String kNextURL = "next_url";
    static final String kNextMaxId = "next_max_id";
    static final String kNextMaxLikeId = "next_max_like_id";
    static final String kNextMaxTagId = "next_max_tag_id";
    static final String kNextCursor = "next_cursor";

    static final String kMaxId = "max_id";
    static final String kMaxLikeId = "max_like_id";
    static final String kMaxTagId = "max_tag_id";
    static final String kCursor = "cursor";

    String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
