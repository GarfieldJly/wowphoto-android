/*
package com.milier.wowgallery.model;

import com.milier.wowgallery.api.WelfareService;
import com.milier.wowgallery.bean.WelfareBean;
import com.milier.wowgallery.net.RetrofitFactory;

import java.util.List;

import rx.Single;



public class FragmentBaseModelImpl implements FragmentBaseModel {

    WelfareService mImageService;

    public FragmentBaseModelImpl() {
        this.mImageService = RetrofitFactory.createRestService(WelfareService.class);
    }

    @Override
    public Single<List<WelfareBean.ResultsBean>> getAtlasBeanList(int pageSize, int page) {
        return RxUtil.wrapRestCall(mImageService.getAtlasList(type, pageSize,page));
    }
}
*/
