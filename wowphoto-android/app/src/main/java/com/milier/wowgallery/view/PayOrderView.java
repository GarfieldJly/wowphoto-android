package com.milier.wowgallery.view;

import com.milier.wowgallery.utils.googleplay.Purchase;

/**
 * Created by xubo on 15/12/16.
 */
public interface PayOrderView  {

//    /**
//     * 支付请求
//     */
//    String EVENT_PAY = "pay";
//
//    /**
//     * 初始化Googleplay
//     */
//    String EVENT_INIT_GOOGLE_PLAY = "init_google_play";
//
//    /**
//     * 发送GooglePlay支付结果
//     */
//    String EVENT_SEND_GOOGLE_PLAY_ACTIVITY_RESULT = "send_google_play_result";
//
//    /**
//     * 在线支付失败，未扣款
//     */
//    int PAY_RESULT_CODE_ONLINE_PAY_FAIL = 1;
//
//    /**
//     * 在线支付成功
//     */
//    int PAY_RESULT_CODE_ONLINE_PAY_SUCCESS = 2;
//
//    /**
//     * 获取支付信息失败
//     */
//    int PAY_RESULT_CODE_GET_PAY_INFO_FAIL = 5;
//
//    /**
//     * 购买成功
//     */
//    int PAY_RESULT_CODE_BUY_SUCCESS = 6;
//
//    /**
//     * 发送购买结果
//     * @param result
//     */
//    void sendPayResult(int result);
//
//    /**
//     * 设置GooglePlay选项显示
//     * @param state
//     */
//    void setGooglePlayState(boolean state);
//
//    /**
//     * 获取Googleplay 状态
//     * @return
//     */
//    boolean googlePlayIsRunning();
//
//    /**
//     * 获取Activity
//     *
//     * @return
//     */
//    Activity getActivity();

    void initGooglePlayWithKey(String googlePlayKey);


    void sendGooglePlayRequest(String goodId,String billNo);

    void showInitGooglePlayMessage(String message);

    void failCheckOrderCallBack(String msg);

    void submitUnconsumeGooglePlayOrder(Purchase purchase);

    void failPay(String msg);

    void failGetOrderId(String msg);

    void paySuccess(Purchase purchase,String goodId);

}
