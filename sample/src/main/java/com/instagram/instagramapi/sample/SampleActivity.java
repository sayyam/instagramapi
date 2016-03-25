package com.instagram.instagramapi.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.instagram.instagramapi.activities.InstagramAuthActivity;
import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.engine.InstagramKitConstants;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGComment;
import com.instagram.instagramapi.objects.IGLike;
import com.instagram.instagramapi.objects.IGLocation;
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGPostResponse;
import com.instagram.instagramapi.objects.IGRelationship;
import com.instagram.instagramapi.objects.IGTag;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.objects.IGUser;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.widgets.InstagramLoginButton;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    InstagramLoginButton instagramLoginButton;
    Button loginButton;

    String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.COMMENTS, InstagramKitLoginScope.LIKES, InstagramKitLoginScope.RELATIONSHIP, InstagramKitLoginScope.PUBLIC_ACCESS, InstagramKitLoginScope.FOLLOWER_LIST};
    //String[] scopes = {InstagramKitLoginScope.BASIC};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //Implement via InstagramLoginButton
        instagramLoginButton = (InstagramLoginButton) findViewById(R.id.instagramLoginButton);
        instagramLoginButton.setOnClickListener(instagramOnClickListener);
        instagramLoginButton.setInstagramLoginCallback(instagramLoginCallbackListener);
        instagramLoginButton.setScopes(scopes);


        //Implement via intent
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginOnClickListener);

    }

    InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
        @Override
        public void onSuccess(IGSession session) {

            Toast.makeText(SampleActivity.this, "Wow!!! User trusts you :) " + session.getAccessToken(),
                    Toast.LENGTH_LONG).show();
            InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback);

        }

        @Override
        public void onCancel() {
            Toast.makeText(SampleActivity.this, "Oh Crap!!! Canceled.",
                    Toast.LENGTH_LONG).show();

        }

        @Override
        public void onError(InstagramException error) {
            Toast.makeText(SampleActivity.this, "User does not trust you :(\n " + error.getMessage(),
                    Toast.LENGTH_LONG).show();

        }
    };

    View.OnClickListener instagramOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SampleActivity.this, InstagramAuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);

            intent.putExtra("type", 1);
            intent.putExtra("scopes", scopes);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:

                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();

                    if (bundle.containsKey(InstagramKitConstants.kSessionKey)) {

                        IGSession session = (IGSession) bundle.getSerializable(InstagramKitConstants.kSessionKey);

                        Toast.makeText(SampleActivity.this, "Woohooo!!! User trusts you :) " + session.getAccessToken(),
                                Toast.LENGTH_LONG).show();

                    }
                }
                break;
            default:
                break;
        }

    }

    public void genericClickListener(View v) {

        IGLocation location = new IGLocation(51.508530, -0.076132);

        switch (v.getId()) {
            case R.id.userDetail:
                InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback, "3043977032");
                break;
            case R.id.selfUserDetail:
                InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback);
                break;
            case R.id.mediaForSelfUser:
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(instagramMediaResponseCallback);
                break;
            case R.id.mediaForUser:
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(instagramMediaResponseCallback, "3043977032");
                break;
            case R.id.userLikedMedia:
                InstagramEngine.getInstance(SampleActivity.this).getUserLikedMedia(instagramMediaResponseCallback);
                break;
            case R.id.searchUser:
                InstagramEngine.getInstance(SampleActivity.this).searchUser(instagramUsersListResponseCallback, "sayyam");
                break;
            case R.id.usersIFollow:
                InstagramEngine.getInstance(SampleActivity.this).getUsersIFollow(instagramUsersListResponseCallback);
                break;
            case R.id.getMediaForUser:
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, null);
                break;
            case R.id.getUserLikedMedia:
                InstagramEngine.getInstance(SampleActivity.this).getUserLikedMedia(likedMediaApiResponseCallback);
                break;
            case R.id.getRelationshipStatusOfUser:
                InstagramEngine.getInstance(SampleActivity.this).getRelationshipStatusOfUser(usersRelationshipApiResponseCallback, "");
                break;
            case R.id.getUsersFollowedByUser:
                InstagramEngine.getInstance(SampleActivity.this).getUsersFollowedByUser(usersFollowedByApiResponseCallback, "3043977032");
                break;
            case R.id.getFollowedBy:
                InstagramEngine.getInstance(SampleActivity.this).getFollowedBy(usersFollowedByApiResponseCallback);
                break;
            case R.id.getFollowersOfUser:
                InstagramEngine.getInstance(SampleActivity.this).getFollowersOfUser(followersApiResponseCallback, "3043977032");
                break;
            case R.id.getFollowRequests:
                InstagramEngine.getInstance(SampleActivity.this).getFollowRequests(followRequestsApiResponseCallback);
                break;
            case R.id.followUser:
                InstagramEngine.getInstance(SampleActivity.this).followUser(followUserApiResponseCallback, "");
                break;
            case R.id.unFollowUser://test this api exception
                InstagramEngine.getInstance(SampleActivity.this).unFollowUser(unFollowUserApiResponseCallback, "userId");
                break;
            case R.id.blockUser:
                InstagramEngine.getInstance(SampleActivity.this).blockUser(blockUserApiResponseCallback, "userId");
                break;
            case R.id.unblockUser:
                InstagramEngine.getInstance(SampleActivity.this).unblockUser(unBlockUserApiResponseCallback, "userId");
                break;
            case R.id.approveUser:
                InstagramEngine.getInstance(SampleActivity.this).approveUser(approveUserApiResponseCallback, "userId");
                break;
            case R.id.ignoreUser:
                InstagramEngine.getInstance(SampleActivity.this).ignoreUser(ignoreUserApiResponseCallback, "userId");
                break;
            case R.id.getMedia:
                InstagramEngine.getInstance(SampleActivity.this).getMedia(mediaApiResponseCallback, "mediaId");
                break;
            case R.id.getMediaByShortCode:
                InstagramEngine.getInstance(SampleActivity.this).getMediaByShortCode(mediaApiResponseCallback, "shortcode");
                break;
            case R.id.getMediaAtLocation:
                InstagramEngine.getInstance(SampleActivity.this).getMediaAtLocation(mediaAtLocationApiResponseCallback, location);
                break;
            case R.id.getMediaAtLocationWithRadius:
                InstagramEngine.getInstance(SampleActivity.this).getMediaAtLocation(mediaAtLocationApiResponseCallback, 100000f, location);
                break;
            case R.id.getCommentsOnMedia:
                InstagramEngine.getInstance(SampleActivity.this).getCommentsOnMedia(mediaCommentsApiResponseCallback, "mediaId");
                break;
            case R.id.postCommentOnMedia:
                InstagramEngine.getInstance(SampleActivity.this).postCommentOnMedia(postCommentApiResponseCallback, "This is a test comment", "mediaId");
                break;
            case R.id.removeComment:
                InstagramEngine.getInstance(SampleActivity.this).removeComment(deleteCommentApiResponseCallback, "commentId", "mediaId");
                break;
            case R.id.getLikesOnMedia:
                InstagramEngine.getInstance(SampleActivity.this).getLikesOnMedia(mediaLikesApiResponseCallback, "mediaId");
                break;
            case R.id.likeMedia:
                InstagramEngine.getInstance(SampleActivity.this).likeMedia(likeApiResponseCallback, "mediaId");
                break;
            case R.id.unlikeMedia:
                InstagramEngine.getInstance(SampleActivity.this).unlikeMedia(unLikeApiResponseCallback, "mediaId");
                break;
            case R.id.getTagDetails:
                InstagramEngine.getInstance(SampleActivity.this).getTagDetails(tagDetailsApiResponseCallback, "name");
                break;
            case R.id.getMediaWithTagName:
                InstagramEngine.getInstance(SampleActivity.this).getMediaWithTagName(mediaWithTagApiResponseCallback, "tagName");
                break;
            case R.id.searchTagsWithName:
                InstagramEngine.getInstance(SampleActivity.this).searchTagsWithName(searchTagsWithNameApiResponseCallback, "tagName");
                break;
            case R.id.searchLocationsAtLocation:
                InstagramEngine.getInstance(SampleActivity.this).searchLocationsAtLocation(searchLocationsAtApiResponseCallback, location);
                break;
            case R.id.getLocation:
                InstagramEngine.getInstance(SampleActivity.this).getLocation(locationApiResponseCallback, "locationId");
                break;
            case R.id.getRecentMediaFromLocation:
                InstagramEngine.getInstance(SampleActivity.this).getRecentMediaFromLocation(mediaAtLocationApiResponseCallback, "locationId");
                break;
            case R.id.logout:
                InstagramEngine.getInstance(SampleActivity.this).logout();
        }

    }


    InstagramAPIResponseCallback<IGLocation> locationApiResponseCallback = new InstagramAPIResponseCallback<IGLocation>() {
        @Override
        public void onResponse(IGLocation response, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Location : " + response);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
    InstagramAPIResponseCallback<ArrayList<IGLocation>> searchLocationsAtApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGLocation>>() {
        @Override
        public void onResponse(ArrayList<IGLocation> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + responseArray);
            Log.v("SampleActivity", "Media: " + responseArray.size());
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGTag>> searchTagsWithNameApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGTag>>() {
        @Override
        public void onResponse(ArrayList<IGTag> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + responseArray);
            Log.v("SampleActivity", "Media: " + responseArray.size());
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGMedia>> mediaWithTagApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Media: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGMedia media : responseArray) {

                    Toast.makeText(SampleActivity.this, "Media Caption: " + media.getCaption().getText(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + media.getCaption().getText());
                    Log.v("SampleActivity", "Media Type: " + media.getType());
                    if (media.getType().equals(InstagramKitConstants.kMediaTypeImage)) {
                        Log.v("SampleActivity", "Media Photo: " + media.getImages().getStandardResolution().getUrl() + "\n");
                    }
                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGTag> tagDetailsApiResponseCallback = new InstagramAPIResponseCallback<IGTag>() {
        @Override
        public void onResponse(IGTag response, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + response);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGPostResponse> unLikeApiResponseCallback = new InstagramAPIResponseCallback<IGPostResponse>() {
        @Override
        public void onResponse(IGPostResponse response, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + response);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
    InstagramAPIResponseCallback<IGPostResponse> likeApiResponseCallback = new InstagramAPIResponseCallback<IGPostResponse>() {
        @Override
        public void onResponse(IGPostResponse response, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + response);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGLike>> mediaLikesApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGLike>>() {
        @Override
        public void onResponse(ArrayList<IGLike> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Media: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGLike like : responseArray) {

                    Toast.makeText(SampleActivity.this, "Media Caption: " + like.getUsername(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + like.getUsername());

                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGPostResponse> deleteCommentApiResponseCallback = new InstagramAPIResponseCallback<IGPostResponse>() {
        @Override
        public void onResponse(IGPostResponse response, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + response);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
    InstagramAPIResponseCallback<IGPostResponse> postCommentApiResponseCallback = new InstagramAPIResponseCallback<IGPostResponse>() {
        @Override
        public void onResponse(IGPostResponse response, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + response);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGComment>> mediaCommentsApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGComment>>() {
        @Override
        public void onResponse(ArrayList<IGComment> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Media: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGComment media : responseArray) {

                    Toast.makeText(SampleActivity.this, "Media Caption: " + media.getText(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + media.getText());

                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGMedia>> mediaAtLocationApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Media: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGMedia media : responseArray) {

                    Toast.makeText(SampleActivity.this, "Media Caption: " + media.getCaption().getText(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + media.getCaption().getText());
                    Log.v("SampleActivity", "Media Type: " + media.getType());
                    if (media.getType().equals(InstagramKitConstants.kMediaTypeImage)) {
                        Log.v("SampleActivity", "Media Photo: " + media.getImages().getStandardResolution().getUrl() + "\n");
                    }
                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGMedia> mediaApiResponseCallback = new InstagramAPIResponseCallback<IGMedia>() {
        @Override
        public void onResponse(IGMedia responseMedia, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + responseMedia.getType());
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGRelationship> ignoreUserApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
    InstagramAPIResponseCallback<IGRelationship> approveUserApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGRelationship> blockUserApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
    InstagramAPIResponseCallback<IGRelationship> unBlockUserApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
    InstagramAPIResponseCallback<IGRelationship> unFollowUserApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGRelationship> followUserApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGUser>> followRequestsApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Users I Follow: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGUser user : responseArray) {

                    Toast.makeText(SampleActivity.this, "User: " + user.getUsername(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "User: " + user.getUsername());

                }
            }
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGUser>> followersApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Users I Follow: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGUser user : responseArray) {

                    Toast.makeText(SampleActivity.this, "User: " + user.getUsername(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "User: " + user.getUsername());

                }
            }
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGUser>> usersFollowedByApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Users I Follow: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGUser user : responseArray) {

                    Toast.makeText(SampleActivity.this, "User: " + user.getUsername(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "User: " + user.getUsername());

                }
            }
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getErrorReason());
        }
    };

    InstagramAPIResponseCallback<IGRelationship> usersRelationshipApiResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship relationship, IGPagInfo pageInfo) {

            Toast.makeText(SampleActivity.this, "Status: " + relationship.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();
            Log.v("SampleActivity", "Status: " + relationship.getIncomoingStatus());

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGUser>> usersIFollowApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Users I Follow: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGUser user : responseArray) {

                    Toast.makeText(SampleActivity.this, "User: " + user.getUsername(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "User: " + user.getUsername());

                }
            }
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };


    InstagramAPIResponseCallback<ArrayList<IGMedia>> likedMediaApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Liked Media: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGMedia media : responseArray) {

                    Toast.makeText(SampleActivity.this, "Media Caption: " + media.getCaption().getText(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + media.getCaption().getText());
                    Log.v("SampleActivity", "Media Type: " + media.getType());
                    if (media.getType().equals(InstagramKitConstants.kMediaTypeImage)) {
                        Log.v("SampleActivity", "Media Photo: " + media.getImages().getStandardResolution().getUrl() + "\n");
                    }
                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(SampleActivity.this).getUserLikedMedia(likedMediaApiResponseCallback, 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGMedia>> mediaListApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Media: " + responseArray.size());

            if (responseArray.size() > 0) {

                for (IGMedia media : responseArray) {

                    Toast.makeText(SampleActivity.this, "Media Caption: " + media.getCaption().getText(),
                            Toast.LENGTH_LONG).show();
                    Log.v("SampleActivity", "Media Caption: " + media.getCaption().getText());
                    Log.v("SampleActivity", "Media Type: " + media.getType());
                    if (media.getType().equals(InstagramKitConstants.kMediaTypeImage)) {
                        Log.v("SampleActivity", "Media Photo: " + media.getImages().getStandardResolution().getUrl() + "\n");
                    }
                }
            }

            if (null != pageInfo && null != pageInfo.getNextMaxId() && !pageInfo.getNextMaxId().isEmpty()) {
                InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, pageInfo.getNextMaxId());
            }

        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGUser>> followedUsersApiResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseArray, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Users:" + responseArray.size());

            Toast.makeText(SampleActivity.this, "Users: " + responseArray.size(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
        @Override
        public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "User:" + responseObject.getUsername());

            Toast.makeText(SampleActivity.this, "Username: " + responseObject.getUsername(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGMedia>> instagramMediaResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseObject, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Id:" + responseObject.size());

            Toast.makeText(SampleActivity.this, "Id: " + responseObject.size(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGUser>> instagramUsersListResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseObject, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "Id:" + responseObject.size());

            Toast.makeText(SampleActivity.this, "Id: " + responseObject.size(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };


    InstagramAPIResponseCallback<IGRelationship> instagramRelationshipResponseCallback = new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship responseObject, IGPagInfo pageInfo) {
            Log.v("SampleActivity", "getIncomoingStatus:" + responseObject.getIncomoingStatus());
            Log.v("SampleActivity", "getOutgoingStatus:" + responseObject.getOutgoingStatus());

            Toast.makeText(SampleActivity.this, "Username: " + responseObject.getIncomoingStatus(),
                    Toast.LENGTH_LONG).show();


        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };
}
