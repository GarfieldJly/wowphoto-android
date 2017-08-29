package com.milier.wowgallery.ui.activity;

import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.milier.wowgallery.R;
import com.milier.wowgallery.utils.MilierToast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.accounts.AccountManager.ERROR_CODE_NETWORK_ERROR;
import static com.google.android.gms.ads.doubleclick.PublisherAdRequest.ERROR_CODE_INTERNAL_ERROR;
import static com.google.android.gms.ads.search.SearchAdRequest.ERROR_CODE_INVALID_REQUEST;
import static com.google.android.gms.ads.search.SearchAdRequest.ERROR_CODE_NO_FILL;

/**
 * Created by xubo on 2016/12/20.
 */

public class TestActivity extends BaseActivity {
    private final static String APP_ID = "ca-app-pub-3159252643064769~4545744135";

    @BindView(R.id.google_adView)
    AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        MobileAds.initialize(this, APP_ID);
//        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("3F87908F69EDCC45743BD59D32BB0C3C")        // All emulators
//                .addTestDevice("34ed66841c31a8a29129ce8817019db9")  // An example device ID
                .build();

        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                MilierToast.toast("onAdloaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                if(errorCode == ERROR_CODE_INTERNAL_ERROR ){
                    MilierToast.toast("内部出现问题。例如，从广告服务器中收到无效响应");
                }else if(errorCode == ERROR_CODE_INVALID_REQUEST){
                    MilierToast.toast("广告请求无效。例如，广告单元 ID 不正确");
                }else if(errorCode == ERROR_CODE_NETWORK_ERROR){
                    MilierToast.toast("广告请求因网络连接而未成功");
                }else if(errorCode == ERROR_CODE_NO_FILL){
                    MilierToast.toast("广告请求已成功，但因缺少广告库存而未返回广告");
                }

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                MilierToast.toast("onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                MilierToast.toast("onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                MilierToast.toast("onAdClosed");
            }
        });

    }



}
