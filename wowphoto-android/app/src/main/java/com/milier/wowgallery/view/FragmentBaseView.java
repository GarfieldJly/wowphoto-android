package com.milier.wowgallery.view;

/**
 * Created by zx on 2016/8/22 0022.
 */
public interface FragmentBaseView extends BaseView {
    @Override
    void showProgress();

    @Override
    void dismissProgress();

//    void setData(List<AtlasBean> atlasBean);

//    void setListData(List<String> data);

    void setRefreshState(boolean refreshState);

}
