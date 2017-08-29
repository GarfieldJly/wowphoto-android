package com.milier.wowgallery.common;

import com.milier.wowgallery.R;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.exception.AuthorizeException;
import com.milier.wowgallery.exception.CommonException;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.MilierToast;
import com.milier.wowgallery.utils.RxBus;

import java.io.IOException;

import rx.functions.Action1;

/**
 * Created by konie on 16-8-20.
 */
public class DefaultErrorHandler implements Action1<Throwable> {

    private static final String ERROR_TAG = "URIEL_ERROR";

    @Override
    public void call(Throwable throwable) {
//        Log.i(ERROR_TAG, ((CommonException) throwable).getCode() + " ");
//        if (throwable instanceof HttpException){
//            if (((HttpException) throwable).code() == 401){
//                Log.e(ERROR_TAG, "call: "+"aaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
//                MilierToast.showToast(MyApplication.getInstance(),MyApplication.getInstance().getString(R.string.no_login));
//
//                UserLoginService userLoginService = RetrofitFactory.createRestService(UserLoginService.class);
//                userLoginService.loginOut().subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        LoginUtils.LoginOut(MyApplication.getInstance(),false);
//                        RxBus.getDefault().post(Constants.SIGN_OUT);
//                    }
//                }, new DefaultErrorHandler());
//            }
//        }

        RxBus.getDefault().post(Constants.STOP_REFRESH);
        // TODO: 2016/12/17 失败处理需要优化
        if (throwable instanceof IOException) {
            MilierToast.showToast(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.operateFail));
        }
        if (throwable instanceof AuthorizeException) {
            //TODO: 提示登录
            return;
        } else if (throwable instanceof CommonException) {
            CommonException ce = (CommonException) throwable;
            MilierLog.e(ERROR_TAG,"ce is :"+ce.toString());
            switch (ce.getCode()) {
                case 9999:
                    //TODO: 强制升级
                    break;
                case 104:
//                    MilierToast.showToast(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.alreadyUsed));
                    break;
                case 201:
//                    MilierToast.showToast(MyApplication.getInstance(),MyApplication.getInstance().getString(R.string.empty));
                    break;
                case 404:
                    MilierToast.showToast(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.operateFail));
                    break;
                case 500:
                    MilierToast.showToast(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.operateFail));
                    break;
                case 109:
                    MilierToast.showToast(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.vip_finish));
//                    if(LoginUtils.isAppHasSignIn(MyApplication.getInstance())){
//                        UserBean userBean = LoginUtils.getUserInfo(MyApplication.getInstance());
//                        if(userBean != null){
//                            LoginUtils.setVipDay(MyApplication.getInstance(),0);
//                        }
//                    }
                    break;

            }
            return;
        }

        MilierLog.t(ERROR_TAG, throwable);
//        Toaster.debugToast(AndroidApplication.getInstance(), ErrorMessageFactory.create(throwable));
    }

}
