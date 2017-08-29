package com.milier.wowgallery.presenter;

/**
 * Created by zx on 2016/8/22 0022.
 */
public interface PicturePresenter extends BasePresenter {
    /**
     * 获取图片名称
     */
    void getImageList(long atlasId);

    /**
     *  看完广告后，获取相册
     * @param atlasId
     */
    void getImageListAfterAd(long atlasId);

}
