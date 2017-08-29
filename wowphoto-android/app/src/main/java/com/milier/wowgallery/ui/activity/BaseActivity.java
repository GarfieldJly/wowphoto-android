package com.milier.wowgallery.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.appsflyer.AppsFlyerLib;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.view.BaseView;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xubo on 2016/12/17.
 */

public class BaseActivity extends AppCompatActivity  implements BaseView{
    private static final String TAG = "BaseActivity";
    private final static String APP_ID = "ca-app-pub-3159252643064769~4545744135";
    private final static String AD_UNIT_ID = "ca-app-pub-3159252643064769/6022477336";
    private final static String FB_AD_ID = "406133119753288_406135586419708";

    private AdView mFBAdView;
    private com.google.android.gms.ads.AdView mGPAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppsFlyerLib.getInstance().sendDeepLinkData(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AppEventsLogger.deactivateApp(this);
    }

    public  void initGoogleAd(final ViewGroup viewGroup, final boolean isShow){
        if(!isShow){
            viewGroup.setVisibility(View.GONE);
            return;
        }
        viewGroup.setVisibility(View.VISIBLE);
        if(mGPAdView != null && mGPAdView.isShown())
            return;

        MobileAds.initialize(this, APP_ID);
        AdRequest adRequest = new AdRequest.Builder().build();
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("3F87908F69EDCC45743BD59D32BB0C3C")        // All emulators
//                .build();
        mGPAdView = new com.google.android.gms.ads.AdView(this);
        mGPAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        mGPAdView.setAdUnitId(AD_UNIT_ID);
        mGPAdView.loadAd(adRequest);
        viewGroup.addView(mGPAdView);
        mGPAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                MilierLog.i(TAG,"onGoogleAdloaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                initFacebookAd(viewGroup,isShow);
            }

            @Override
            public void onAdOpened() {}

            @Override
            public void onAdLeftApplication() {}

            @Override
            public void onAdClosed() {}
        });

    }

    public void initFacebookAd(ViewGroup viewGroup, boolean isShow) {
        if(!isShow){
            viewGroup.setVisibility(View.GONE);
            return;
        }

        viewGroup.setVisibility(View.VISIBLE);
        if(mFBAdView != null && mFBAdView.isShown())
            return;
        mFBAdView = new AdView(this, FB_AD_ID, AdSize.BANNER_320_50);

        viewGroup.addView(mFBAdView);
        mFBAdView.setAdListener(new com.facebook.ads.AdListener(){
            @Override
            public void onError(Ad ad, AdError adError) {
                MilierLog.i(TAG,"Facebook onError");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                MilierLog.i(TAG,"Facebook Ad loaded!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                MilierLog.i(TAG,"Facebook Ad clicked!!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        mFBAdView.loadAd();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFBAdView != null) {
            mFBAdView.destroy();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }
}
