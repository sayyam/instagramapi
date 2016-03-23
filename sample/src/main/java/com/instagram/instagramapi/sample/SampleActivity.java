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
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGRelationship;
import com.instagram.instagramapi.objects.InstagramException;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.objects.IGUser;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.widgets.InstagramLoginButton;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    InstagramLoginButton instagramLoginButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //Implement via InstagramLoginButton
        instagramLoginButton = (InstagramLoginButton) findViewById(R.id.instagramLoginButton);
        instagramLoginButton.setOnClickListener(instagramOnClickListener);
        instagramLoginButton.setInstagramLoginCallback(instagramLoginCallbackListener);

        //Implement via intent
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginOnClickListener);

    }

    InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
        @Override
        public void onSuccess(IGSession session) {

            Toast.makeText(SampleActivity.this, "Wow!!! User trusts you :) " + session.getAccessToken(),
                    Toast.LENGTH_LONG).show();
//          InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback,"3043977032");
            InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback);
//          InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(instagramMediaResponseCallback);

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

            String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.COMMENTS, InstagramKitLoginScope.LIKES, InstagramKitLoginScope.RELATIONSHIP, InstagramKitLoginScope.PUBLIC_ACCESS, InstagramKitLoginScope.FOLLOWER_LIST};

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

//                      InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback,"3043977032");
//                      InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback);
//                      InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(instagramMediaResponseCallback);
//-->Test it            InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(instagramMediaResponseCallback, "3043977032");
//                      InstagramEngine.getInstance(SampleActivity.this).getUserLikedMedia(instagramMediaResponseCallback);
//                      InstagramEngine.getInstance(SampleActivity.this).searchUser(instagramUsersListResponseCallback, "sayyam");
//-->Test it            InstagramEngine.getInstance(SampleActivity.this).getUsersIFollow(instagramUsersListResponseCallback);
//                      InstagramEngine.getInstance(SampleActivity.this).getRelationship(instagramRelationshipResponseCallback,"3043977032");
//                      InstagramEngine.getInstance(SampleActivity.this).getUsersIFollow(followedUsersApiResponseCallback);
//                      InstagramEngine.getInstance(SampleActivity.this).getMediaForUser(mediaListApiResponseCallback, 5, null);
//                        InstagramEngine.getInstance(SampleActivity.this).getUserLikedMedia(likedMediaApiResponseCallback);
//                        InstagramEngine.getInstance(SampleActivity.this).getUsersIFollow(usersIFollowApiResponseCallback);
                        InstagramEngine.getInstance(SampleActivity.this).getRelationshipStatusOfUser(usersRelationshipApiResponseCallback, "");
//                      InstagramEngine.getInstance(SampleActivity.this).getUsersFollowedByUser
//                      InstagramEngine.getInstance(SampleActivity.this).
//                      InstagramEngine.getInstance(SampleActivity.this).
//                      InstagramEngine.getInstance(SampleActivity.this).
//                      InstagramEngine.getInstance(SampleActivity.this).
//                      InstagramEngine.getInstance(SampleActivity.this).
//                      InstagramEngine.getInstance(SampleActivity.this).
//                      InstagramEngine.getInstance(SampleActivity.this).

                    }
                }
                break;
            default:
                break;
        }

    }


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
