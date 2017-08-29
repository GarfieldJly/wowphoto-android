package com.milier.wowgallery.utils;

import android.util.Log;

import com.milier.wowgallery.config.MilierConfig;

/**
 * Created by xubo on 2016/12/16.
 */

public class MilierLog {
    public static void d(String tag, String content) {
        if (!MilierConfig.DEBUG) return;
        Log.d(tag, content);
    }

    public static void w(String tag, String content) {
        if (!MilierConfig.DEBUG) return;
        Log.w(tag, content);
    }

    public static void e(String tag, String content) {
        if (!MilierConfig.DEBUG) return;
        Log.e(tag, content);
    }

    public static void i(String tag, String content) {
        if (!MilierConfig.DEBUG) return;
        Log.i(tag, content);
    }

    public static void t(String tag, Throwable t) {
        if (!MilierConfig.DEBUG) return;

        if (t == null) {
            throw new NullPointerException("Throwable t with tag " + tag + " == null");
        }

        String message = Log.getStackTraceString(t);
        Log.e(tag, message);
    }

}
