/*
package com.milier.wowgallery.presenter;

import android.content.Context;

import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.view.FavoriteView;
import com.zhexinit.wowphoto.server.api.bean.AtlasBean;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

*/
/**
 * Created by zx on 2016/8/22 0022.
 *//*

public class FavoritePresenterImpl extends BasePresenterImpl implements FavoritePresenter {

    private FavoriteView mFavoriteView;
    private Context context;
    private FavoriteModel mFavoriteModel;

    public FavoritePresenterImpl(FavoriteView favoriteView, Context context) {
        this.mFavoriteView = favoriteView;
        this.context = context;
        this.mFavoriteModel = new FavoriteModelImpl();
    }

    @Override
    public void getStoreAtlas(int pageSize, final int page) {
        Subscription subscribe = mFavoriteModel.getStoreAtlas(pageSize,page).subscribe(new Action1<List<AtlasBean>>() {
            @Override
            public void call(List<AtlasBean> appraiseInfoBean) {
                MilierLog.i("size", "appraiseInfoBean size is :" + appraiseInfoBean.size());
//                if (appraiseInfoBean.size() == 0 && page != 1) {
//                    MilierToast.toast("没有更多了");
//                } else {
//                    mFavoriteView.setData(appraiseInfoBean);
//                }
                mFavoriteView.setData(appraiseInfoBean);
                mFavoriteView.dismissProgress();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mFavoriteView.dismissProgress();
            }
        });

        addToOnDestroyUnsubscribeQueue(subscribe);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
*/
