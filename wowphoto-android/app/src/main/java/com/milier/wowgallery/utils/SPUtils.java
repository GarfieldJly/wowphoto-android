package com.milier.wowgallery.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *  SharedPreferences 工具类
 */
public class SPUtils {
    public static final String PREFERENCES_NAME = "WOWPHOTO";

    /**
     * put long preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putLong(Context context, String key,
                               long value) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key
     * @return
     */
    public static long getLongPreferences(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, -1);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value==null?"":value);
        editor.apply();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key
     * @return 默认为false
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    public static void putHashMap(Context context,HashMap<String, Object> map) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Set<String> mapSet = map.keySet(); // 获取所有的key值 为set的集合
        Iterator<String> itor = mapSet.iterator();// 获取key的Iterator便利
        while (itor.hasNext()) {// 存在下一个值
            String key = itor.next();// 当前key值
            editor.putString(key, map.get(key)==null?"":""+map.get(key));
            editor.apply();
        }

    }

}
