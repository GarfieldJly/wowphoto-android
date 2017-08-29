package com.milier.wowgallery.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

public class PhoneInfoUtils {

    /**
     * 先获取手机IMSI
     *
     * @param context
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getIMSI(Context context) {
        String imsi = null;
        try {
            TelephonyManager mTelephonyManager = null;
            mTelephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            imsi = mTelephonyManager.getSubscriberId();
            if (TextUtils.isEmpty(imsi)) {
                Class<? extends TelephonyManager> tmClass = mTelephonyManager.getClass();

                Method getImsiMethod = tmClass.getMethod("getSubscriberIdGemini", Integer.TYPE);

                if (null != getImsiMethod) {
                    // 先取SIM2
                    imsi = (String) getImsiMethod.invoke(mTelephonyManager, 1);
                    if (null == imsi) {
                        imsi = (String) getImsiMethod.invoke(mTelephonyManager, 0);
                    }
                }
            }
        } catch (Exception e) {
//			QYLog.e("getIMSI method1 exce : " + e);
        } catch (Error e) {

        }

        if (TextUtils.isEmpty(imsi)) {
            // 高通平台
            try {
                Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
                Object obj = context.getSystemService("phone_msim");
                Method md = clazz.getMethod("getSubscriberId", int.class);
                imsi = (String) md.invoke(obj, 1);
                if (TextUtils.isEmpty(imsi)) {
                    imsi = (String) md.invoke(obj, 0);
                }
            } catch (Exception e) {
//				QYLog.e("getIMSI method2 exce : " + e);
            }
        }

        return imsi;
    }

}
