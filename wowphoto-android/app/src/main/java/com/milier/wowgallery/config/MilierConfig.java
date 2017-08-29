package com.milier.wowgallery.config;

/**
 * Created by xubo on 2016/12/16.
 */

public class MilierConfig {

    //手动配置客户端debug模式
    private final static boolean CONFIG_DEBUG = true;

    //配置当前应用的模式
//    public final static boolean DEBUG = CONFIG_DEBUG || BuildConfig.DEBUG;
    public final static boolean DEBUG = true;

    /**
     * 登录url
     */
    public final static String LOGIN_URL = /*NetworkConfig.getApiBasePath()+*/"login";

    /**
     * 注销url
     */
    public final static String LOGOUT_URL = "logout";

    /**
     * 用于TOKEN加密的AES密钥
     */
    public final static String AES_KEY_ENCRYPT_TOKEN = "Kocakin@20161001";

    /**
     * 渠道名
     */
    private static String CHANNEL_NAME = "Google";

    /**
     * build文件中的versionCode
     */
    private static int appVersionCode;

    /**
     * build文件中的versionName
     */
    private static String appVersionName;

    public static String getChannelName() {
        return CHANNEL_NAME;
    }

    public static int getAppVersionCode() {
        return appVersionCode;
    }

    public static String getAppVersionName() {
        return appVersionName;
    }

//    public static void install(Context context) {
//        try {
//            appVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//            appVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        List<DeviceInfo> deviceInfoList = DbUtil.getDeviceInfoHelper().queryAll();
//        if (deviceInfoList != null && deviceInfoList.size() > 0) {
//            DeviceInfo deviceInfo = deviceInfoList.get(0);
//            CHANNEL_NAME = deviceInfo.getChannelId();
//        }
//    }

}
