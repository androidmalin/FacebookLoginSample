package com.wish.go.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.orhanobut.logger.Logger;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.init("malin_MainActivity");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
    }

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private void initView() {

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");


        callbackManager = CallbackManager.Factory.create();


        // Other app specific specialization
        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(final LoginResult loginResult) {
//                // App code
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                      Logger.d("onSuccess:loginResult:"+loginResult.toString());
//                    }
//                });
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Logger.d("onCancel");
//                    }
//                });
//            }
//
//            @Override
//            public void onError(final FacebookException exception) {
//                // App code
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Logger.d("onError:"+exception.getMessage());
//                    }
//                });
//            }
//        });


        /**
         * userid:1641191829466182
         * ApplicationId:1737411589822464
         * Permissions:[]
         * Permissions:[user_friends, public_profile]
         */
        //TODO:在授权界面的回调在这里。
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        // App code
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO：授权成功
                                Logger.d("onSuccess:" + loginResult.toString());

                                Logger.d(
                                                "userid:" + loginResult.getAccessToken().getUserId() + "\n" +
                                                "Token:" + loginResult.getAccessToken().getToken() + "\n" +
                                                "ApplicationId:" + loginResult.getAccessToken().getApplicationId() + "\n" +
                                                "Permissions:" + loginResult.getAccessToken().getDeclinedPermissions() + "\n" +
                                                "Permissions:" + loginResult.getAccessToken().getPermissions()
                                );
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TODO：授权取消
                                Logger.d("onCancel:");
                            }
                        });
                    }

                    @Override
                    public void onError(final FacebookException exception) {
                        // App code
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logger.d("onError:" + exception.getMessage());
                            }
                        });
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }


    private void showToast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logMessage(final String str) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.d(str);
            }
        });
    }
}
