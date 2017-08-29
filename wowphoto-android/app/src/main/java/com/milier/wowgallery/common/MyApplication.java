package com.milier.wowgallery.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.milier.wowgallery.bean.LocalPictureBean;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.utils.ChannelUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by xubo on 2016/12/16.
 */

public class MyApplication extends Application {
    public final static String TAG = "wowgallery";

    private static MyApplication instance;
    public int screenWidth,screenHeight;
    public float density;

    private Timer timer;

    public MyApplication() {
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private static Map<String, LocalPictureBean> picMap;

    private static Map<String, LocalPictureBean> headMap;

    public static Map<String, LocalPictureBean> getPicMap() {
        return picMap;
    }

    public static Map<String, LocalPictureBean> getHeadMap() {
        return headMap;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //数据库初始化
//        DbCore.init(this);
        //获取机型信息
//        DeviceInfo.setDevicesInfo(this);

        //网络
//        OkHttpClientManager.init(this);
        // 服务
//        RetrofitFactory.init(this);


//        Logger.init(TAG);

//        GoogleManager.init(this);

//        new SystemUtils(this);


        init();

        picMap = new HashMap<>();

        headMap = new HashMap<>();
    }

    private void init(){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;  // 屏幕宽度（像素）
        screenHeight = metric.heightPixels;  // 屏幕高度（像素）
        density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）

//        if (!LoginUtils.isPostActiveInfo(this)) {
//            startService(new Intent(MyApplication.this, ActivateService.class));
//        }
        //友盟 init
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, Constants.UMENG_KEY, ChannelUtil.getChannel(this), MobclickAgent.EScenarioType.E_UM_NORMAL));


//        String IMEI = new SystemUtils(this).getSystemIMEL(this);
//        AppsFlyerLib.getInstance().setImeiData(IMEI);
//        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        AppsFlyerLib.getInstance().setAndroidIdData(android_id);
//
//        //AppsFlyer 初始化
//        AppsFlyerLib.getInstance().startTracking(this, Constants.APPSFLYER_ID);


    }



}
