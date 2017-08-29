package com.milier.wowgallery.presenter;

import android.util.Log;


import java.util.LinkedList;
import java.util.Queue;

import rx.Subscription;

/**
 * Created by konie on 16-8-20.
 */
public class BasePresenterImpl implements BasePresenter {

    /**
     * The queue for unsubscribe Rx subscriptions when presenter to be destroy
     */
    private Queue<Subscription> subscriptionQueue;

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    @Override
    public void resume() {
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    @Override
    public void pause() {
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    @Override
    public void destroy() {
//        if (subscriptionQueue != null) {
//            RxUtil.unsubscribeIfNotNull(subscriptionQueue);
//            subscriptionQueue = null;
//        }
    }

    /**
     * Add Rx subscription to the queue for unsubscribe it when presenter to be destroy
     *
     * @param subscriptions
     */
    protected void addToOnDestroyUnsubscribeQueue(Subscription... subscriptions) {
        if (subscriptions != null) {
            if (subscriptionQueue == null) {
                subscriptionQueue = new LinkedList<>();
            }

            for (Subscription subscription : subscriptions) {
                Log.i("info ", "addToOnDestroyUnsubscribeQueue");
                subscriptionQueue.add(subscription);
            }
        }
    }

}
