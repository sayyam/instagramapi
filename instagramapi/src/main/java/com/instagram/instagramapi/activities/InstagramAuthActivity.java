package com.instagram.instagramapi.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.instagram.instagramapi.R;
import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.interfaces.InstagramAuthCallbackListener;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.engine.InstagramKitConstants;
import com.instagram.instagramapi.utils.Utils;
import com.instagram.instagramapi.widgets.InstagramWebViewClient;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Sayyam on 3/17/16.
 */
public class InstagramAuthActivity extends Activity {

    WebView instagrramAuthWebView;

    InstagramWebViewClient webViewClient;

    String authURL;
    String redirectURL;
    String[] scopes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instagram);

        instagrramAuthWebView = (WebView) findViewById(R.id.instagrramAuthWebView);

        authURL = InstagramEngine.getInstance(getApplicationContext()).authorizationURL();
        redirectURL = InstagramEngine.getInstance(getApplicationContext()).getAppRedirectURL();

        webViewClient = new InstagramWebViewClient(instagramAuthCallbackListener);

        handleIntent(getIntent());

    }

    private void handleIntent(Intent intent) {

        Bundle extras = intent.getExtras();

        if (null != extras && extras.containsKey("type")) {
            final int type = extras.getInt("type");


            switch (type) {
                case 0:
                    authURL = InstagramEngine.getInstance(getApplicationContext()).authorizationURL();
                    instagrramAuthWebView.setWebViewClient(webViewClient);
                    instagrramAuthWebView.loadUrl(authURL);

                    break;
                case 1:

                    if (extras.containsKey("scopes")) {
                        scopes = extras.getStringArray("scopes");
                        authURL = InstagramEngine.getInstance(getApplicationContext()).authorizationURLForScope(scopes);
                    }

                    InstagramEngine.getInstance(InstagramAuthActivity.this).setInstagramLoginButtonCallback(instagramLoginCallbackListener);
                    instagrramAuthWebView.setWebViewClient(webViewClient);
                    instagrramAuthWebView.loadUrl(authURL);

                    break;
                default:

                    throw new RuntimeException("You must provide type key with valid int value in intent");

            }
        } else {

            throw new RuntimeException("You must provide type key with valid int value in intent");
        }
    }


    InstagramAuthCallbackListener instagramAuthCallbackListener = new InstagramAuthCallbackListener() {
        @Override
        public boolean onRedirect(String actualRedirectURL) {
            Log.v("IntagramAuthActivity", "URL: " + redirectURL);

            Uri actualRedirectURI = Uri.parse(actualRedirectURL);
            Uri redirectURI = Uri.parse(redirectURL);

            try {

                if (actualRedirectURI.getScheme().equals(redirectURI.getScheme()) && actualRedirectURI.getHost().equals(redirectURI.getHost())) {

                    //Fragment contains the access token i.e. http://your-redirect-uri#access_token=ACCESS-TOKEN
                    String fragment = actualRedirectURI.getFragment();

                    if (null != fragment && !fragment.isEmpty()) {

                        Map<String, String> tokenHash = Utils.splitQuery(fragment);

                        if (tokenHash.size() > 0 && tokenHash.containsKey("access_token")) {
                            //TODO Save access token and return success callback

                            IGSession IGSession = new IGSession(tokenHash.get("access_token"));

                            InstagramEngine.getInstance(InstagramAuthActivity.this).setSession(IGSession);

                            InstagramEngine.getInstance(InstagramAuthActivity.this).getInstagramLoginButtonCallback().onSuccess(IGSession);
                            Log.v("IntagramAuthActivity", "Access Token: " + tokenHash.get("access_token"));

                        } else {
                            //TODO Show error dialog OR return failure callback
                            Log.v("IntagramAuthActivity", "Oh Crap....");

                            InstagramException instagramException = new InstagramException("Error: Something went wrong, please try again.");

                            InstagramEngine.getInstance(InstagramAuthActivity.this).getInstagramLoginButtonCallback().onError(instagramException);

                        }
                    } else {
                        //TODO Show error dialog OR return failure callback

                        String errorDescription = actualRedirectURI.getQueryParameter("error_description");
                        String error = actualRedirectURI.getQueryParameter("error");
                        String errorReason = actualRedirectURI.getQueryParameter("errorReason");

                        Log.v("IntagramAuthActivity", "Oh Crap...." + " Error:" + error + " Description: " + errorDescription);

                        InstagramException instagramException = new InstagramException(errorDescription, error, errorReason);

                        InstagramEngine.getInstance(InstagramAuthActivity.this).getInstagramLoginButtonCallback().onError(instagramException);

                    }
                    finish();
                    return true;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return false;
        }
    };

    InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
        @Override
        public void onSuccess(IGSession session) {

            Bundle responseSession = new Bundle();
            responseSession.putSerializable(InstagramKitConstants.kSessionKey, session);

            Intent intent = new Intent();
            intent.putExtras(responseSession);
            setResult(RESULT_OK, intent);
            finish();

        }

        @Override
        public void onCancel() {

            Bundle responseSession = new Bundle();
            responseSession.putString("message", "Something went wrong, try again.");

            Intent intent = new Intent();
            intent.putExtras(responseSession);

            setResult(RESULT_CANCELED, intent);
            finish();

        }

        @Override
        public void onError(InstagramException error) {

            Bundle responseSession = new Bundle();
            responseSession.putString("message", error.getMessage());
            responseSession.putString("error", error.getError());
            responseSession.putString("error_reason", error.getErrorReason());

            Intent intent = new Intent();
            intent.putExtras(responseSession);

            setResult(RESULT_CANCELED, intent);
            finish();


        }
    };


}
