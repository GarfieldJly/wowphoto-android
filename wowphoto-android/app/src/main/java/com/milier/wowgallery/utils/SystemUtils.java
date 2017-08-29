/*
package com.milier.wowgallery.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

*/
/**
 * Created by wang on 2016/8/22 0022.
 *//*

public class SystemUtils {
    public static String DEVICE_NAME;
    public static String IMEL;
    public static String IMSL;
    public static String ANDROID_VERSION;
    public static String VERSION_NAME;
    public static String ICCID;
    public static String CHANEL_ID;

//    */
/**
//     * 存入用户忽略更新版本
//     *
//     * @param context
//     * @param appCode
//     *//*

//    public static void putIgnoreUpdateVersion(Context context, int appCode) {
//        SPUtils.putInt(context, "ignoreCode", appCode);
//    }

//    */
/**
//     * 是否是用户忽略更新的版本
//     *
//     * @param context
//     * @param appCode
//     * @return
//     *//*

//    public static boolean getIgnoreUpdateVersion(Context context, int appCode) {
//        Log.e("version",appCode+" old:"+SPUtils.getInt(context, "ignoreCode"));
//        return (SPUtils.getInt(context, "ignoreCode") == appCode);
//
//    }


    public SystemUtils(Context context) {
        DEVICE_NAME = getDeviceName(context);
        IMEL = getSystemIMEL(context);
        IMSL = getSystemIMSL(context);
        ANDROID_VERSION = String.valueOf(getAndroidVersion());
        VERSION_NAME = getVersionName(context);
        ICCID = getIccId(context);
        CHANEL_ID = getChannelId(context);
    }

    public static String getDeviceInfo(Context context) {

        DeviceInfoBean deviceInfoBean = new DeviceInfoBean(DEVICE_NAME, IMEL, IMSL
                , ANDROID_VERSION, VERSION_NAME, ICCID, CHANEL_ID, null);
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<DeviceInfoBean> adapter = moshi.adapter(DeviceInfoBean.class);
        return adapter.toJson(deviceInfoBean);
    }

    */
/**
     * 获取系统IMEL
     *
     * @param context
     * @return
     *//*

    public static String getSystemIMEL(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) (context.getSystemService(context.TELEPHONY_SERVICE));
        return TelephonyMgr.getDeviceId();
    }

    */
/**
     * 获取系统IMSL
     *
     * @param context
     * @return
     *//*

    public static String getSystemIMSL(Context context) {

        TelephonyManager mTelephonyMgr = (TelephonyManager) (context.getSystemService(context.TELEPHONY_SERVICE));

        return mTelephonyMgr.getSubscriberId();
    }

    */
/**
     * Android sdk 版本
     *
     * @return
     *//*

    public static int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    */
/**
     * 设备名称
     *
     * @param context
     * @return
     *//*

    public static String getDeviceName(Context context) {
        Build bd = new Build();
        String model = bd.MODEL;
        return model;
    }

    */
/**
     * iccId
     * @param context
     * @return
     *//*

    public static String getIccId(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) (context.getSystemService(context.TELEPHONY_SERVICE));
        return mTelephonyMgr.getSimSerialNumber();
    }

    */
/**
     * qu dao xin xi
     * @param context
     * @return
     *//*

    public static String getChannelId(Context context) {
        ApplicationInfo info = null;
        String forceVersion = "google";
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            forceVersion = info.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return forceVersion;
    }

    */
/*app version*//*

    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;

        return version;
    }

    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionCode;

    }


}
*/
