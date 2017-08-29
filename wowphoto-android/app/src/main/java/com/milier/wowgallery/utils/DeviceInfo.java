package com.milier.wowgallery.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by xubo on 16/1/13.
 */
public class DeviceInfo {

    /**
     * 屏幕信息
     */
    private static String SCREEN_INFO = "S:";

    /**
     * 手机mac地址
     */
    private static String MAC_ADDRESS = "M:";

    /**
     * 当前国家
     */
    private static String COUNTRY = "C:";

    public static void setDevicesInfo(Context context) {
        com.milier.wowgallery.bean.DeviceInfo deviceInfo = new com.milier.wowgallery.bean.DeviceInfo();
        deviceInfo.setAndroidVersion(Build.VERSION.SDK_INT + "");
        deviceInfo.setAppVersion(getVersion(context));
        deviceInfo.setDeviceName(Build.MODEL);
        deviceInfo.setIccId(TerminalInfoUtil.getIccid(context));
        deviceInfo.setIMEI(TerminalInfoUtil.getImei(context));
        deviceInfo.setIMSI(PhoneInfoUtils.getIMSI(context));
        deviceInfo.setOthers(SCREEN_INFO + DensityUtil.getScreenWidth(context) + "*" + DensityUtil.getScreenHeight(context) +
                "," + MAC_ADDRESS + TerminalInfoUtil.getLocalMacAddress(context) + "," + COUNTRY + TerminalInfoUtil.getCountry(context));
        deviceInfo.setChannelId(ChannelUtil.getChannel(context, "milier"));

    }

    private static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
