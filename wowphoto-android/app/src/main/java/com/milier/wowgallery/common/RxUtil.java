/*
package com.milier.wowgallery.common;

import com.milier.wowgallery.R;
import com.milier.wowgallery.bean.LoginOutBean;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.exception.CallCanceledException;
import com.milier.wowgallery.exception.CommonException;
import com.milier.wowgallery.net.RetrofitFactory;
import com.milier.wowgallery.utils.LoginUtils;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.MilierToast;
import com.milier.wowgallery.utils.RxBus;

import java.util.Collection;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

*/
/**
 * RxJava相关工具类
 * Created by guofe on 2016/1/22.
 *//*

public class RxUtil {
    private static final String TAG = "RxUtil";
    */
/**
     * 剥离ResponseBean
     *
     * @param call
     * @param <T>
     * @return
     *//*

    public static <T> Single<T> wrapRestCall(final Observable<ResponseBean<T>> call) {
        return call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<ResponseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final ResponseBean<T> response) {
                        MilierLog.e(TAG, "call response: "+response.code );
                        if (response.code == 0) {
                            return Observable.just(response.result);
                        } else {
                            return Observable.error(new CommonException(response.code, response.message));
                        }
                    }
                }, new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(final Throwable throwable) {
                        if (throwable instanceof CallCanceledException) {
                            return Observable.never();
                        } else if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            if (httpException.code() == 403  || httpException.code() == 404) {
                                MilierToast.toast(MyApplication.getInstance().getString(R.string.operateFail));
                            }
                            if (httpException.code()  == 401){
                                MilierToast.showToast(MyApplication.getInstance(),MyApplication.getInstance().getString(R.string.no_login));

                                UserLoginService userLoginService = RetrofitFactory.createRestService(UserLoginService.class);
                                userLoginService.loginOut().subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
                                    @Override
                                    public void call(Void aVoid) {
                                        LoginUtils.LoginOut(MyApplication.getInstance(),false);
                                        RxBus.getDefault().post(new LoginOutBean(false, Constants.SIGN_OUT));
                                    }
                                }, new DefaultErrorHandler());
                            }
                        }

                        return Observable.error(throwable);
                    }
                }, new Func0<Observable<? extends T>>() {

                    @Override
                    public Observable<? extends T> call() {
                        return Observable.empty();
                    }
                }).toSingle();
    }

    */
/**
     * 取消订阅
     *
     * @param subscriptions
     *//*

    public static void unsubscribeIfNotNull(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }

    */
/**
     * 取消订阅
     *
     * @param subscriptions
     *//*

    public static void unsubscribeIfNotNull(Collection<Subscription> subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null) {
                subscription.unsubscribe();
            }
        }
    }
}
*/
