/*
package com.milier.wowgallery.presenter;

import android.content.Context;

import com.milier.wowgallery.R;
import com.milier.wowgallery.common.DefaultErrorHandler;
import com.milier.wowgallery.model.FeedbackModel;
import com.milier.wowgallery.model.FeedbackModelImpl;
import com.milier.wowgallery.ui.activity.FeedbackActivity;
import com.milier.wowgallery.utils.DialogTools;
import com.milier.wowgallery.utils.Toaster;
import com.milier.wowgallery.view.FeedbackView;

import rx.functions.Action1;

*/
/**
 * Created by zx on 2016/9/11 0011.
 *//*

public class FeedbackPresenterImpl extends BasePresenterImpl implements FeedbackPresenter {

    private final FeedbackModel feedbackModel;
    private final Context context;
    private FeedbackView mFeedbackView;

    public FeedbackPresenterImpl(Context context,FeedbackView feedbackView) {
        this.context = context;
        this.feedbackModel = new FeedbackModelImpl();
        this.mFeedbackView = feedbackView;
    }

    @Override
    public void sendSuggest(String suggest) {
        DialogTools.showWaittingDialog(context);
        addToOnDestroyUnsubscribeQueue(feedbackModel.sendSuggent(suggest).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toaster.toast(context.getString(R.string.suggest_send_success));
                ((FeedbackActivity) mFeedbackView).finish();
                DialogTools.dismissDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
//                Toaster.toast(context.getString(R.string.operateFail));
                DialogTools.dismissDialog();
                new DefaultErrorHandler().call(throwable);
            }
        }));
    }
}
*/
