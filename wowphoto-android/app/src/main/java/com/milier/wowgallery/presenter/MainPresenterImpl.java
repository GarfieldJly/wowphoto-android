//package com.milier.wowgallery.presenter;
//
//import android.content.Context;
//
//import com.milier.wowgallery.bean.LoginOutBean;
//import com.milier.wowgallery.config.Constants;
//import com.milier.wowgallery.model.MainModel;
//import com.milier.wowgallery.model.MainModelmpl;
//import com.milier.wowgallery.utils.LoginUtils;
//import com.milier.wowgallery.utils.MilierLog;
//import com.milier.wowgallery.utils.RxBus;
//import com.milier.wowgallery.view.MainView;
//import com.zhexinit.wowphoto.server.api.bean.UserBean;
//
///**
// * Created by jly on 2016/12/30.
// */
//public class MainPresenterImpl implements MainPresenter {
//    private final String TAG = MainPresenterImpl.class.getSimpleName();
//
//    private MainModel mMainModel;
//    private MainView mMainView;
//    private MainModelmpl.LoginListener mLoginListener;
//    private Context mContext;
//
//    public MainPresenterImpl(Context context,MainView mainView) {
//        mMainModel = new MainModelmpl();
//        this.mContext = context;
//        this.mMainView = mainView;
//
//        mLoginListener = new MainModelmpl.LoginListener(){
//
//            @Override
//            public void loginOut() {
//                LoginUtils.LoginOut(mContext,true);
//                RxBus.getDefault().post(new LoginOutBean(true, Constants.SIGN_OUT));
//            }
//
//            @Override
//            public void returnUserBean(UserBean userBean) {
//                MilierLog.i(TAG,"userBean 头像:"+userBean.getPortraitUrl());
//                mMainView.notifyUserBean(userBean);
//            }
//        };
//    }
//
//    @Override
//    public void loginOut() {
//        mMainModel.logOut(mLoginListener);
//    }
//
//    @Override
//    public void getUserBean() {
//        mMainModel.getUserInfo(mLoginListener);
//    }
//}
