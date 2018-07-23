
Instagram API
============
A wrapper on instagram API for android.

[ ![Download](https://api.bintray.com/packages/sayyam/maven/instagramapi/images/download.svg) ](https://bintray.com/sayyam/maven/instagramapi/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-instagramapi-green.svg?style=true)](https://android-arsenal.com/details/1/3381)

Download
--------
#### Gradle:
```groovy
maven { url 'https://dl.bintray.com/sayyam/maven' }

compile 'com.github.sayyam:instagramapi:0.1.1'
```
#### Maven:
```xml
<dependency>
  <groupId>com.github.sayyam</groupId>
  <artifactId>instagramapi</artifactId>
  <version>0.1.1</version>
  <type>pom</type>
</dependency>
```

Usage
--------

#### Include Application's Client Id and Redirect Uri:
Add following meta tags in ```<application>``` tag of your ```AndroidManifest```.

```xml
  <meta-data
    android:name="com.instagram.instagramapi.InstagramAppClientId"
    android:value="PUT-YOUR-CLIENT-ID-HERE" />

  <meta-data
    android:name="com.instagram.instagramapi.InstagramAppRedirectURL"
    android:value="PUT-YOUR-REDIRECT-URI-HERE" />
```
You can get above credentials from [Instagram Developrs Portal](https://www.instagram.com/developer/ "Instagram Developrs Portal").

#### Initiate the authorization process:
You can start the ```InstagramEngine``` in two different ways:
1. Intent
2. InstagramLoginButton

#### 1- Initiate the authorization process via ```Intent```:

##### Create an intent
```java

  String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.COMMENTS};
  
  Intent intent = new Intent(SampleActivity.this, InstagramAuthActivity.class);
  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
          Intent.FLAG_ACTIVITY_SINGLE_TOP);

  intent.putExtra(InstagramEngine.TYPE, InstagramEngine.TYPE_LOGIN);
  //add scopes if you want to have more than basic access
  intent.putExtra(InstagramEngine.SCOPE, scopes);
  
  startActivityForResult(intent, 0);
```
##### Handle Login Result

```java
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
      }

  }
```

#### 2- Initiate the login process via ```InstagramLoginButton```:

##### Add ```InstagramLoginButton``` in your layout
```xml
  <com.instagram.instagramapi.widgets.InstagramLoginButton
      android:id="@+id/instagramLoginButton"
      android:layout_width="200dp"
      android:layout_height="55dp"
      android:text="Login Button" />
```
#####Initialize ```InstagramLoginButton``` in your ```Activity```
```java
  String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.COMMENTS};
    
 InstagramLoginButton instagramLoginButton = (InstagramLoginButton) findViewById(R.id.instagramLoginButton);
 instagramLoginButton.setInstagramLoginCallback(instagramLoginCallbackListener);
 //if you dont specify scopes, you will have basic access.
 instagramLoginButton.setScopes(scopes);

```
##### Handle Login callback
```java
  InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
      @Override
      public void onSuccess(IGSession session) {

          Toast.makeText(SampleActivity.this, "Wow!!! User trusts you :) " + session.getAccessToken(),
                  Toast.LENGTH_LONG).show();

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
```

#### Fetch User details:

##### Get user details via ```getUserDetails()```
```java
InstagramEngine.getInstance(SampleActivity.this).getUserDetails(instagramUserResponseCallback);
```
##### Handle Response in callback
```java
  InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
      @Override
      public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {

          Toast.makeText(SampleActivity.this, "Username: " + responseObject.getUsername(),
                  Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(InstagramException exception) {
          Log.v("SampleActivity", "Exception:" + exception.getMessage());
      }
  };
```

#### Logout user:

```java
InstagramEngine.getInstance(SampleActivity.this).logout();
```
#### Exception Handling:
In case of exceptions like insufficient scope or invalid parameters you will get InstagramException in onFailure() of your callback.
```java
InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
  ...
  @Override
  public void onFailure(InstagramException exception) {
      Log.v("Exception", "Exception:" + exception.getMessage());
      }
        
  }
```


License
--------

  Copyright 2016 Sayyam.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and limitation
  under the License.
