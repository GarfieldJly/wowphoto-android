package com.milier.wowgallery.exception;

/**
 * Created by konie on 16-8-20.
 */
public class CommonException extends Exception {

    private int code;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
        setVip(code);
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CommonException code: " + code + " " + super.toString();
    }

    private void setVip(int code){
        if(code == 109){
//            MilierToast.showToast(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.vip_finish));
           /* if(LoginUtils.isAppHasSignIn(MyApplication.getInstance())){
                UserBean userBean = LoginUtils.getUserInfo(MyApplication.getInstance());
                if(userBean != null){
                    LoginUtils.setVipDay(MyApplication.getInstance(),0);
                    userBean.setVipExpireDay(0);
                    RxBus.getDefault().post(userBean);
                }
            }
            RxBus.getDefault().post( Constants.SIGN_IN);*/
        }

    }
}