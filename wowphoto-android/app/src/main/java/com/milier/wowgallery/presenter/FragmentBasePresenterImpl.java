/*
package com.milier.wowgallery.presenter;


import android.content.Context;

import com.milier.wowgallery.common.DefaultErrorHandler;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.model.FragmentBaseModel;
import com.milier.wowgallery.model.FragmentBaseModelImpl;
import com.milier.wowgallery.utils.RxBus;
import com.milier.wowgallery.view.FragmentBaseView;
import com.zhexinit.wowphoto.server.api.bean.AtlasBean;
import com.zhexinit.wowphoto.server.api.bean.ImageType;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

*/
/**
 * Created by wang on 2016/8/22 0022.
 *//*

public class FragmentBasePresenterImpl extends BasePresenterImpl implements FragmentBasePresenter {
    private final static String TAG = FragmentBasePresenterImpl.class.getSimpleName();

    private FragmentBaseView fragmentBaseView;

    private FragmentBaseModel fragmentBaseModel;

    private Context context;

    private Subscription stopRefreshSubscription;

    public FragmentBasePresenterImpl(FragmentBaseView fragmentBaseView, Context context) {
        this.fragmentBaseView = fragmentBaseView;
        this.context = context;
        fragmentBaseModel = new FragmentBaseModelImpl();
    }

    @Override
    public void resume() {
        // Activity 准备工作
        stopRefreshSubscription = RxBus.getDefault().toObservable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(Constants.STOP_REFRESH)) {
                    fragmentBaseView.dismissProgress();
                }
            }
        });

        addToOnDestroyUnsubscribeQueue(stopRefreshSubscription);

    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void destroy() {
        super.destroy();
        fragmentBaseView.dismissProgress();
    }

    @Override
    public void loadDatas(ImageType imageType,  int pageSize,final int page) {
//        fragmentBaseView.showProgress();
        Subscription subscribe = fragmentBaseModel.getAtlasBeanList(imageType, pageSize,page).subscribe(new Action1<List<AtlasBean>>() {
            @Override
            public void call(List<AtlasBean> appraiseInfoBean) {
                if(appraiseInfoBean == null || appraiseInfoBean.size() == 0){
                    return;
                }
                fragmentBaseView.setData(appraiseInfoBean);
                fragmentBaseView.dismissProgress();
            }
        }, new DefaultErrorHandler());

        addToOnDestroyUnsubscribeQueue(subscribe);

    }
}
*/
