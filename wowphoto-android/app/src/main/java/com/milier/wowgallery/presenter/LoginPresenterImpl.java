/*
package com.milier.wowgallery.presenter;

import android.app.Activity;
import android.content.Context;

import com.milier.wowgallery.R;
import com.milier.wowgallery.bean.StoreBean;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.model.LoginModel;
import com.milier.wowgallery.model.LoginModelImpl;
import com.milier.wowgallery.utils.LoginUtils;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.RxBus;
import com.milier.wowgallery.view.LoginView;
import com.zhexinit.wowphoto.server.api.bean.UserBean;

import java.util.List;

*/
/**
 * Created by jly on 2016/8/26.
 *//*

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = LoginPresenterImpl.class.getSimpleName();

    private final LoginModel mLoginModel;
    private final LoginModelImpl.LoginOverListener loginOverListener;
    private LoginView mLoginView;
    private Context context;

    public LoginPresenterImpl(LoginView loginView, final Context context) {
        this.context = context;
        this.mLoginView = loginView;
        mLoginModel = new LoginModelImpl();

        loginOverListener = new LoginModelImpl.LoginOverListener() {
            @Override
            public void successLogin(String loginType) {
                mLoginModel.getUserInfo(this);
            }

            @Override
            public void failLogin() {
                mLoginView.showLoginMessage(context.getString(R.string.login_fail));
                mLoginView.dismissProgress();
            }

            @Override
            public void returnUserBean(UserBean userBean) {
                mLoginView.dismissProgress();
                mLoginView.showLoginMessage(context.getString(R.string.login_success));
                notifyLoginMessage(userBean);
                //暂时注释了，不用请求收藏列表
//                mLoginModel.getStoreAtlasIds(this);
            }

            @Override
            public void getStoreAtlasIds(List<Long> ids) {
                notifyStoreAtlasIds(ids);
            }
        };
    }

    */
/**
     * dataEye 统计登陆方式
     *//*

//    private void statisticsLoginType(String loginType) {
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put(Constants.LOGIN_TYPE, loginType);
//        DCEvent.onEvent(Constants.LOGIN_TYPE, hashMap);
//    }


    @Override
    public void googleLogin(String idToken) {
        mLoginView.showProgress();
        mLoginModel.googleLogin(idToken, loginOverListener);
    }

    @Override
    public void onDestroy() {
        mLoginView.dismissProgress();
        mLoginModel.onDestroy();
    }

    @Override
    public void notifyStoreAtlasIds(List<Long> ids) {
        StoreBean storeBean = new StoreBean(ids);
        RxBus.getDefault().post(storeBean);
        MilierLog.i(TAG,"ids size is :"+ids);
        ((Activity) context).finish();
    }

    @Override
    public void notifyLoginMessage(UserBean userBean) {

        LoginUtils.setSignIn(context, userBean.userId, userBean.portraitUrl, userBean.name, userBean.vipExpireDay);
        MilierLog.e(TAG,"刚登录 isLogin : "+LoginUtils.isAppHasSignIn(context));
        RxBus.getDefault().post(userBean);
        RxBus.getDefault().post(Constants.SIGN_IN);
        ((Activity) context).finish();
    }

}
*/
