package com.milier.wowgallery.utils;

import android.content.Context;
import android.widget.Toast;

import com.milier.wowgallery.common.MyApplication;
import com.milier.wowgallery.config.MilierConfig;

/**
 * Created by xubo on 2016/12/17.
 */

public class MilierToast {
    public static Toast toast;

    public static void toast(Context context, String info) {

        if (info == null || info.isEmpty()) {
            return;
        }
        toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void debugToast(Context context, String info) {
        if (MilierConfig.DEBUG) {
            toast(context, info);
        }
    }

    public static void toastLong(Context context, String info) {
        if (info == null || info.isEmpty()) {
            return;
        }
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(String info) {
        toastLong(MyApplication.getInstance(), info);
    }

    public static void toast(String info) {
        toast(MyApplication.getInstance(), info);
    }


    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast myToast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 显示Toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        if (myToast == null) {
            myToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            myToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    myToast.show();
                }
            } else {
                oldMsg = message;
                myToast.setText(message);
                myToast.show();
            }
        }
        oneTime = twoTime;
    }
}
