/*
package com.milier.wowgallery.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.milier.wowgallery.R;

*/
/**
 * Created by xubo on 16/7/4.
 *//*

public class GoogleManager {

    private static GoogleApiClient mGoogleApiClient;

    public static GoogleApiClient init(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // TODO: 2016/12/19 需要换正式的key，包括json文件和String里的id
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        return mGoogleApiClient;
    }

    public static GoogleApiClient getInstance(final Context context) {
        if (mGoogleApiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.server_client_id))
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(((AppCompatActivity) context), new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            //链接失败
                            mGoogleApiClient.disconnect();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            return mGoogleApiClient;
        }

        return mGoogleApiClient;
    }

    public static GoogleApiClient getInstance() {
        return mGoogleApiClient;
    }

}
*/
