package com.milier.wowgallery.presenter;

import com.milier.wowgallery.utils.googleplay.Purchase;

/**
 * Created by jly on 2017/1/4.
 */
public interface PayOrderPresenter {
    void getRSAKey();

    void downOrder(String goodId);

    void checkOrderCallBack(String productId, Purchase purchase);

    void payCallBack(String productId, Purchase purchase);

    void getUserBean();

    void getGoods();
}
