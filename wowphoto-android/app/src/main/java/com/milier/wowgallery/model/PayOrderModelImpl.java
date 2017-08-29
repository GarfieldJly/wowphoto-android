//package com.milier.wowgallery.model;
//
//import com.milier.wowgallery.common.DefaultErrorHandler;
//import com.milier.wowgallery.common.RxUtil;
//import com.milier.wowgallery.net.RetrofitFactory;
//import com.milier.wowgallery.utils.MilierLog;
//import com.milier.wowgallery.utils.googleplay.Purchase;
//import com.zhexinit.wowphoto.server.api.bean.GoodsBean;
//import com.zhexinit.wowphoto.server.api.bean.PayPlatform;
//import com.zhexinit.wowphoto.server.api.bean.UserBean;
//import com.zhexinit.wowphoto.server.api.service.OrderService;
//import com.zhexinit.wowphoto.server.api.service.UserService;
//
//import java.util.List;
//
//import rx.functions.Action1;
//
///**
// * Created by jly on 2017/1/4.
// */
//public class PayOrderModelImpl implements PayOrderModel{
//    private static final String TAG = PayOrderModelImpl.class.getSimpleName();
//    private OrderService mOrderService;
//    private UserService userService;
//
//    public PayOrderModelImpl() {
//        mOrderService = RetrofitFactory.createRestService(OrderService.class);
//        userService = RetrofitFactory.createRestService(UserService.class);
//    }
//
//    @Override
//    public void getKey(final PayOrderListener loginOverListener) {
//
//        RxUtil.wrapRestCall(mOrderService.getRSAKey()).subscribe(new Action1<String>() {
//            @Override
//            public void call(String key) {
//                loginOverListener.getKey(key);
//                MilierLog.i(TAG, "key is :" + key);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                loginOverListener.failInitGooglePlay();
//            }
//        });
//    }
//
//    @Override
//    public void downOrder(final String goodsId, final PayOrderListener loginOverListener) {
//        RxUtil.wrapRestCall(mOrderService.downOrder(goodsId, PayPlatform.GOOGLEPAY)).subscribe(new Action1<String>() {
//            @Override
//            public void call(String orderId) {
//                loginOverListener.downOrder(goodsId, orderId);
//                MilierLog.i(TAG, "orderId is :" + orderId);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                loginOverListener.failGetOrderId();
//            }
//        });
//    }
//
//    @Override
//    public void checkOrderCallBack(String productId, final Purchase purchase,final PayOrderListener loginOverListener) {
//
//        RxUtil.wrapRestCall(mOrderService.checkOrderCallBack(productId,purchase.getToken(),purchase.getOrderId())).subscribe(new Action1<String>() {
//            @Override
//            public void call(String orderId) {
//                loginOverListener.checkOrderCallBack(purchase,orderId);
//                MilierLog.i(TAG, "checkOrderCallBack,,,orderId is :" + orderId);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                loginOverListener.failCheckOrderCallBack();
//                MilierLog.i(TAG, "checkOrderCallBack,,fail" );
//            }
//        });
//    }
//
//    @Override
//    public void payCallBack(String productId, final Purchase purchase, final PayOrderListener loginOverListener) {
//        RxUtil.wrapRestCall(mOrderService.payCallBack(productId,purchase.getToken(),purchase.getOrderId())).subscribe(new Action1<String>() {
//            @Override
//            public void call(String result) {
//                loginOverListener.payCallBakc(purchase,result);
//                MilierLog.i(TAG, "payCallBakc,,,result is :" + result);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                loginOverListener.failPay();
//                MilierLog.i(TAG, "payCallBakc,,,请求失败" );
//            }
//        });
//    }
//
//    @Override
//    public void getUserInfo(final PayOrderListener payOrderListener) {
//        RxUtil.wrapRestCall(userService.getUserInfo()).subscribe(new Action1<UserBean>() {
//            @Override
//            public void call(UserBean userBean) {
//                payOrderListener.returnUserBean(userBean);
//                MilierLog.i(TAG,"userBean name is : "+userBean.getName()+";;"+userBean.getPortraitUrl()+";;"+userBean.getPortraitUrl());
//            }
//        }, new DefaultErrorHandler());
//    }
//
//    @Override
//    public void getGoods(final PayOrderListener payOrderListener) {
//        RxUtil.wrapRestCall(mOrderService.getGoods()).subscribe(new Action1<List<GoodsBean>>() {
//            @Override
//            public void call(List<GoodsBean> goodsBeens) {
//                payOrderListener.getGoods(goodsBeens);
//
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                MilierLog.i(TAG, "get_goods_failed" );
//            }
//        });
//    }
//
//    public interface PayOrderListener {
//
//        void getKey(String key);
//
//        void failInitGooglePlay();
//
//        void failCheckOrderCallBack();
//
//        void failPay();
//
//        void failGetOrderId();
//
//        void downOrder(String goodId,String orderId);
//
//        void checkOrderCallBack(Purchase purchase,String orderId);
//
//        void payCallBakc(Purchase purchase,String goodId);
//
//        void returnUserBean(UserBean userBean);
//
//        void getGoods(List<GoodsBean> goodsBeens);
//    }
//}
