/*
package com.milier.wowgallery.model;

import com.milier.wowgallery.common.RxUtil;
import com.milier.wowgallery.net.RetrofitFactory;
import com.zhexinit.wowphoto.server.api.bean.ImageBean;
import com.zhexinit.wowphoto.server.api.service.ImageService;
import com.zhexinit.wowphoto.server.api.service.UserService;

import java.util.List;

import rx.Single;

*/
/**
 * Created by jly on 2016/12/28.
 *//*

public class PictureModelImpl implements PictureModel {


    ImageService mImageService;
    UserService mUserService;

    public PictureModelImpl() {
        this.mImageService = RetrofitFactory.createRestService(ImageService.class);
        mUserService = RetrofitFactory.createRestService(UserService.class);
    }

    @Override
    public Single<List<ImageBean>> getPicNames(long atlasId) {
        return RxUtil.wrapRestCall(mImageService.getImageList(atlasId));
    }

    @Override
    public Single<List<ImageBean>> adCallBack(long atlasId) {
        return RxUtil.wrapRestCall(mUserService.adCallBack(atlasId));
    }
}
*/
