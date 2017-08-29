/*
package com.milier.wowgallery.presenter;

import android.content.Context;

import com.milier.wowgallery.R;
import com.milier.wowgallery.model.PayOrderModel;
import com.milier.wowgallery.model.PayOrderModelImpl;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.googleplay.IabHelper;
import com.milier.wowgallery.utils.googleplay.Purchase;
import com.milier.wowgallery.view.PayOrderView;
import com.zhexinit.wowphoto.server.api.bean.GoodsBean;
import com.zhexinit.wowphoto.server.api.bean.UserBean;

import java.util.List;

*/
/**
 * Created by jly on 2017/1/4.
 *//*

public class PayOrderPresenterImpl implements PayOrderPresenter{
    private final static String TAG = PayOrderPresenterImpl.class.getSimpleName();

    private final PayOrderModel mPayOrderModel;
    private final PayOrderModelImpl.PayOrderListener mPayOrderListener;
    private PayOrderView mPayOrderView;
    private Context mContext;
    private IabHelper mHelper;

    public PayOrderPresenterImpl(final PayOrderView payOrderView,final Context context) {
        mPayOrderModel = new PayOrderModelImpl();
        this.mPayOrderView = payOrderView;
        this.mContext = context;
        mPayOrderListener = new PayOrderModelImpl.PayOrderListener() {
            @Override
            public void getKey(String key) {
//                mPayOrderModel.getKey(this);
//                getRSAKey(key);
                mPayOrderView.initGooglePlayWithKey(key);
            }

            @Override
            public void failInitGooglePlay() {
                mPayOrderView.showInitGooglePlayMessage(mContext.getString(R.string.init_googleplay_fail));
            }

            @Override
            public void failCheckOrderCallBack() {
                mPayOrderView.failCheckOrderCallBack(mContext.getString(R.string.check_googleplay_fail));
//                mPayOrderView.submitUnconsumeGooglePlayOrder(purchase);

            }

            @Override
            public void failPay() {
                mPayOrderView.failPay(mContext.getString(R.string.pay_fail));
            }

            @Override
            public void failGetOrderId() {
                mPayOrderView.failGetOrderId(mContext.getString(R.string.fail_getOrderId));
            }

            @Override
            public void downOrder(String goodId,String orderId) {
                mPayOrderView.sendGooglePlayRequest(goodId,orderId);
            }

            @Override
            public void checkOrderCallBack(Purchase purchase,String orderId) {
                MilierLog.i(TAG,"checkOrderCallBack is :"+orderId);
                mPayOrderView.submitUnconsumeGooglePlayOrder(purchase);
//                mPayOrderView.paySuccess(purchase,orderId);
            }

            @Override
            public void payCallBakc(Purchase purchase,String goodId) {
                MilierLog.i(TAG,"支付结果: payCallBakc is :"+goodId);
                mPayOrderView.paySuccess(purchase,goodId);
            }

            @Override
            public void returnUserBean(UserBean userBean) {
                mPayOrderView.notifyUserBen(userBean);
            }

            @Override
            public void getGoods(List<GoodsBean> goodsBeens) {
                mPayOrderView.getGoods(goodsBeens);
            }
        };
    }

    @Override
    public void getRSAKey() {
        mPayOrderModel.getKey(mPayOrderListener);
    }

    @Override
    public void downOrder(String goodId) {
        mPayOrderModel.downOrder(goodId,mPayOrderListener);
    }

    @Override
    public void checkOrderCallBack(String productId, Purchase purchase) {
        mPayOrderModel.checkOrderCallBack(productId,purchase,mPayOrderListener);
    }

    @Override
    public void payCallBack(String productId, Purchase purchase) {
        mPayOrderModel.payCallBack(productId,purchase,mPayOrderListener);
    }

    @Override
    public void getUserBean() {
        mPayOrderModel.getUserInfo(mPayOrderListener);
    }

    @Override
    public void getGoods() {
        mPayOrderModel.getGoods(mPayOrderListener);
    }

}
*/
