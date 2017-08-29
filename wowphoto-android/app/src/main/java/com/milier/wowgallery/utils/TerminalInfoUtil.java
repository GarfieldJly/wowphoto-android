package com.milier.wowgallery.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class TerminalInfoUtil {

    public static String getCountry(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * 获取手机ICCID
     *
     * @return
     */
    public static String getIccid(Context context) {
        String ret = "";
        try {
            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            ret = telMgr.getSimSerialNumber();
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 获取手机设备号
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        String ret = "";
        try {
            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            ret = telMgr.getDeviceId();
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        String mac = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            if (null == info) {
                return "";
            }
            mac = info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }
}
