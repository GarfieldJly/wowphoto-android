/*
package com.milier.wowgallery.presenter;


import android.content.Context;

import com.milier.wowgallery.common.DefaultErrorHandler;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.model.PictureModel;
import com.milier.wowgallery.model.PictureModelImpl;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.MilierToast;
import com.milier.wowgallery.utils.RxBus;
import com.milier.wowgallery.view.PictureView;
import com.zhexinit.wowphoto.server.api.bean.ImageBean;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

*/
/**
 * Created by wang on 2016/8/22 0022.
 *//*

public class PicturePresenterImpl extends BasePresenterImpl implements PicturePresenter {
    private final static String TAG = PicturePresenterImpl.class.getSimpleName();

    private PictureView mPictureView;

    private PictureModel mPictureModel;

    private Context context;

    private Subscription stopRefreshSubscription;



    public PicturePresenterImpl(PictureView fragmentBaseView, Context context) {
        this.mPictureView = fragmentBaseView;
        this.context = context;
        mPictureModel = new PictureModelImpl();
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
    public void getImageList(long atlasId) {
        Subscription subscribe = mPictureModel.getPicNames(atlasId).subscribe(new Action1<List<ImageBean>>() {
            @Override
            public void call(List<ImageBean> imageBeanList) {
                MilierLog.i("size","appraiseInfoBean size is :"+imageBeanList.size());
                if(imageBeanList.size() == 0 ){
                    MilierToast.toast("没有更多了");
                }else{
                    mPictureView.getImages(imageBeanList,false);
                }
                mPictureView.dismissProgress();
            }
        }, new DefaultErrorHandler());

        addToOnDestroyUnsubscribeQueue(subscribe);
    }


    @Override
    public void getImageListAfterAd(long atlasId) {
        Subscription subscribe = mPictureModel.adCallBack(atlasId).subscribe(new Action1<List<ImageBean>>() {
            @Override
            public void call(List<ImageBean> imageBeanList) {
                MilierLog.i("size","appraiseInfoBean size is :"+imageBeanList.size());
                if(imageBeanList.size() == 0 ){
                    MilierToast.toast("没有更多了");
                }else{
                    mPictureView.getImages(imageBeanList,true);
                }
                mPictureView.dismissProgress();
            }
        }, new DefaultErrorHandler());

        addToOnDestroyUnsubscribeQueue(subscribe);
    }
}
*/
