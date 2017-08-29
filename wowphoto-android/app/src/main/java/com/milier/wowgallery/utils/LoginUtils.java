/*
package com.milier.wowgallery.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.milier.wowgallery.config.Constants;
import com.zhexinit.wowphoto.server.api.bean.UserBean;

*/
/**
 * 存储用户信息
 *//*

public class LoginUtils {
    private final static String TAG = LoginUtils.class.getSimpleName();

    */
/**
     * 判断是否登陆
     *
     * @param context
     * @return
     *//*

    public static boolean isAppHasSignIn(Context context) {
        return SPUtils.getBoolean(context, Constants.SIGN_IN);
    }

    */
/**
     * 设置登录信息
     *//*

    public static void setSignIn(Context context, Long userId, String portraitUrl,String name, int vipExpireDay) {
        */
/**
         * 用户信息
         *//*

        SPUtils.putLong(context, Constants.USER_ID, userId);
        SPUtils.putString(context, Constants.PORTRAIT_URL, portraitUrl);
        SPUtils.putString(context, Constants.NAME, name);
        SPUtils.putInt(context, Constants.VIP_EXPIRE_DAY, vipExpireDay);
        */
/**
         * 登陆状态
         *//*

        SPUtils.putBoolean(context, Constants.SIGN_IN, true);
    }

    */
/**
     * 设置登录用户名
     *
     * @param context
     * @param name
     *//*

    public static void setUserName(Context context, String name) {
        SPUtils.putString(context, Constants.NAME, name);
    }

    */
/**
     * 设置Vip天数
     *
     * @param context
     * @param days
     *//*

    public static void setVipDay(Context context, int days) {
        SPUtils.putInt(context, Constants.VIP_EXPIRE_DAY, days);
    }

    */
/**
     * 设置 用户头像地址
     *
     * @param context
     * @param portraitUrl
     *//*

    public static void setUserPortraitUrl(Context context, String portraitUrl) {
        SPUtils.putString(context, Constants.PORTRAIT_URL, portraitUrl);
    }

    */
/**
     * 退出登录状态
     *
     * @param context
     *//*

    public static void setSignOut(Context context) {
        SPUtils.putBoolean(context, Constants.SIGN_IN, false);
    }

    */
/**
     * 退出登录
     *
     * @param context
     *//*

    public static void LoginOut(Context context,final boolean autoFlag) {

        if (GoogleManager.getInstance(context) != null) {
            if (GoogleManager.getInstance(context).isConnected()) {
                Auth.GoogleSignInApi.signOut(GoogleManager.getInstance(context)).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(autoFlag){
                            MilierLog.i(TAG,"status message : "+status.getStatusMessage());
                            Toaster.toast(status.getStatusMessage());
                        }
                    }
                });
            }
        }
        setSignOut(context);
        OssUtils.disableUploadOSS();//设置为无效的token
    }

    public static UserBean getUserInfo(Context context) {

        UserBean userInfo = new UserBean();
        userInfo.userId = SPUtils.getLongPreferences(context, Constants.USER_ID);
        userInfo.portraitUrl = SPUtils.getString(context, Constants.PORTRAIT_URL);
        userInfo.name = SPUtils.getString(context, Constants.NAME);
        userInfo.vipExpireDay = SPUtils.getInt(context, Constants.VIP_EXPIRE_DAY);

        return userInfo;
    }

    public static String getUserPortraitUrl(Context context) {
        return SPUtils.getString(context, Constants.PORTRAIT_URL);
    }

    public static boolean isPostActiveInfo(Context context) {
        return SPUtils.getBoolean(context, Constants.IS_ACTIVATE);
    }

    public static void setIsPostActiveInfo(Context context, boolean isActive) {
        SPUtils.putBoolean(context, Constants.IS_ACTIVATE, isActive);
    }
}
*/
