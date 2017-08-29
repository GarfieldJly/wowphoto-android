/*
package com.milier.wowgallery.model;

import com.milier.wowgallery.common.DefaultErrorHandler;
import com.milier.wowgallery.common.RxUtil;
import com.milier.wowgallery.net.RetrofitFactory;
import com.milier.wowgallery.utils.MilierLog;
import com.zhexinit.wowphoto.server.api.bean.UserBean;
import com.zhexinit.wowphoto.server.api.service.UserLoginService;
import com.zhexinit.wowphoto.server.api.service.UserService;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

*/
/**
 * Created by jly on 2016/12/30.
 *//*

public class MainModelmpl implements MainModel {
    private final static String TAG = MainModel.class.getSimpleName();
    @Override
    public void logOut(final LoginListener loginOutListener) {
        UserLoginService restService = RetrofitFactory.createRestService(UserLoginService.class);
        restService.loginOut().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                loginOutListener.loginOut();
            }
        }, new DefaultErrorHandler());
    }

    @Override
    public void getUserInfo(final LoginListener loginListener) {
        UserService userService = RetrofitFactory.createRestService(UserService.class);
        RxUtil.wrapRestCall(userService.getUserInfo()).subscribe(new Action1<UserBean>() {
            @Override
            public void call(UserBean userBean) {
                loginListener.returnUserBean(userBean);
                MilierLog.i(TAG,"userBean name is : "+userBean.getName()+";;"+userBean.getPortraitUrl()+";;"+userBean.getPortraitUrl());
            }
        }, new DefaultErrorHandler());
    }

    public interface LoginListener {
        void loginOut();
        void returnUserBean(UserBean userBean);
    }
}
*/
