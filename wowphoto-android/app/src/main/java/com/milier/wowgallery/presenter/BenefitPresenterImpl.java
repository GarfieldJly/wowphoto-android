/*
package com.milier.wowgallery.presenter;


import android.content.Context;

import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.utils.RxBus;
import com.milier.wowgallery.view.PictureView;

import rx.Subscription;
import rx.functions.Action1;

*/
/**
 * Created by wang on 2016/8/22 0022.
 *//*

public class BenefitPresenterImpl extends BasePresenterImpl implements BenefitPresenter {
    private final static String TAG = BenefitPresenterImpl.class.getSimpleName();

    private PictureView mPictureView;

    private BenefitModel mBenefitModel;

    private Context context;

    private Subscription stopRefreshSubscription;

    public BenefitPresenterImpl(PictureView fragmentBaseView, Context context) {
        this.mPictureView = fragmentBaseView;
        this.context = context;
        mBenefitModel = new BenefitModelImpl();
    }

    @Override
    public void resume() {
        // Activity 准备工作
        stopRefreshSubscription = RxBus.getDefault().toObservable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(Constants.STOP_REFRESH)) {
                    mPictureView.dismissProgress();
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
        mPictureView.dismissProgress();
    }

    @Override
    public void getImageList() {
//        Subscription subscribe = mBenefitModel.getPicNames().subscribe(new Action1<List<ImageBean>>() {
//            @Override
//            public void call(List<ImageBean> imageBeanList) {
//                MilierLog.i("size","appraiseInfoBean size is :"+imageBeanList.size());
//                if(imageBeanList.size() == 0 ){
//                    MilierToast.toast("没有更多了");
//                }else{
//                    mPictureView.getImages(imageBeanList,false);
//                }
//                mPictureView.dismissProgress();
//            }
//        }, new DefaultErrorHandler());
//
//        addToOnDestroyUnsubscribeQueue(subscribe);
    }
}
*/
