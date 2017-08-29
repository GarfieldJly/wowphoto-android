/*
package com.milier.wowgallery.model;

import android.util.Log;

import com.milier.wowgallery.common.DefaultErrorHandler;
import com.milier.wowgallery.common.RxUtil;
import com.milier.wowgallery.net.RetrofitFactory;
import com.milier.wowgallery.utils.MilierLog;
import com.zhexinit.wowphoto.server.api.bean.UserBean;
import com.zhexinit.wowphoto.server.api.service.ImageService;
import com.zhexinit.wowphoto.server.api.service.UserLoginService;
import com.zhexinit.wowphoto.server.api.service.UserService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


*/
/**
 * Created by jly on 2016/8/26.
 *//*

public class LoginModelImpl implements LoginModel {

    private final static String TAG = LoginModelImpl.class.getSimpleName();

    private UserLoginService userLoginService;
    private UserService userService;
    private ImageService imageService;

    private Subscription googleLoginSubscribe;

    public LoginModelImpl() {
        userLoginService = RetrofitFactory.createRestService(UserLoginService.class);
        userService = RetrofitFactory.createRestService(UserService.class);
        imageService = RetrofitFactory.createRestService(ImageService.class);
    }

    @Override
    public void googleLogin(String googleToken, final LoginOverListener loginOverListener) {
        googleLoginSubscribe = userLoginService.loginWithGoogle(googleToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        loginOverListener.successLogin("Google");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         Log.i("info","asdsadsa google");
                        Log.i("Throwable","Throwable is :"+throwable.toString());
                        loginOverListener.failLogin();
                    }
                });
    }

    @Override
    public void getUserInfo(final LoginOverListener loginOverListener) {

        RxUtil.wrapRestCall(userService.getUserInfo()).subscribe(new Action1<UserBean>() {
            @Override
            public void call(UserBean userBean) {
                loginOverListener.returnUserBean(userBean);
                MilierLog.i(TAG,"userBean name is : "+userBean.getName()+";;"+userBean.getPortraitUrl()+";;"+userBean.getPortraitUrl());
            }
        }, new DefaultErrorHandler());
    }

    @Override
    public void getStoreAtlasIds(final LoginOverListener loginOverListener) {
        RxUtil.wrapRestCall(imageService.getStoreAtlasIds()).subscribe(new Action1<List<Long>>() {
            @Override
            public void call(List<Long> ids) {
                //得到收藏的ids
                MilierLog.i("Login","ids is : "+ids);
                loginOverListener.getStoreAtlasIds(ids);
            }
        }, new DefaultErrorHandler());
    }

    @Override
    public void onDestroy() {
        if (googleLoginSubscribe != null) {
            googleLoginSubscribe.unsubscribe();
        }

    }

    public interface LoginOverListener {

        void successLogin(String loginType);

        void failLogin();

        void returnUserBean(UserBean userBean);

        void getStoreAtlasIds(List<Long> ids);

    }

}
*/
