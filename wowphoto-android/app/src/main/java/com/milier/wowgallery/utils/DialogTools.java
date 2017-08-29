package com.milier.wowgallery.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.milier.wowgallery.R;


public class DialogTools {
    private static ProgressDialog mWaittingDialog;

    public static void showWaittingDialog(Context context) {
        try {
            if (mWaittingDialog != null) {
                mWaittingDialog.dismiss();
                mWaittingDialog = null;
            }

            mWaittingDialog = new ProgressDialog(context, R.style.waitting_dialog);
            LayoutInflater mInflater = mWaittingDialog.getLayoutInflater();
            View mView = mInflater.inflate(R.layout.dialog_wattiing, null);
            Animation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            LinearInterpolator lir = new LinearInterpolator();
            rotateAnimation.setInterpolator(lir);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setDuration(1000);

            Animation rotateAnimation1 = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            LinearInterpolator lir1 = new LinearInterpolator();
            rotateAnimation1.setInterpolator(lir1);
            rotateAnimation1.setRepeatCount(-1);
            rotateAnimation1.setDuration(1000);

            ImageView imageView = (ImageView) mView.findViewById(R.id.loading_image);
//            if(LoginUtils.isAppHasSignIn(context)){
//                UserBean userBean = LoginUtils.getUserInfo(context);
//                if(userBean != null && userBean.getVipExpireDay() > 0){
//                    imageView.setImageResource(R.drawable.icon_loading);
//                }else{
//                    imageView.setImageResource(R.drawable.icon_loading_no);
//                }
//            }else{
//                imageView.setImageResource(R.drawable.icon_loading_no);
//            }
//            ((ImageView) mView.findViewById(R.id.loading_run_image)).setAnimation(rotateAnimation);
            ((ImageView) mView.findViewById(R.id.loading_image)).setAnimation(rotateAnimation1);

            mWaittingDialog.show();
            mWaittingDialog.setContentView(mView);
            mWaittingDialog.setOnKeyListener(onKeyListener);
//            mWaittingDialog.setCancelable(false);
            mWaittingDialog.setCanceledOnTouchOutside(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissDialog();
            }
            return false;
        }
    };

    /**
     * dismiss dialog
     */
    public static void dismissDialog() {
        if (null != mWaittingDialog && mWaittingDialog.isShowing()) {
            mWaittingDialog.dismiss();
        }
    }

    public static void closeWaittingDialog() {
        try {
            if (mWaittingDialog != null)
                mWaittingDialog.dismiss();
            mWaittingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
