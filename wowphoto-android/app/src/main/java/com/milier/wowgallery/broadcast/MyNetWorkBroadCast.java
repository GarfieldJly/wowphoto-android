package com.milier.wowgallery.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.milier.wowgallery.R;
import com.milier.wowgallery.utils.NetWorkUtils;
import com.milier.wowgallery.utils.Toaster;
import com.milier.wowgallery.weight.refresh.RefreshLoadMoreLayout;


public class MyNetWorkBroadCast extends BroadcastReceiver {

    private RefreshLoadMoreLayout refreshLoadMoreLayout;

    public MyNetWorkBroadCast(RefreshLoadMoreLayout refreshLoadMoreLayout) {
        this.refreshLoadMoreLayout = refreshLoadMoreLayout;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (refreshLoadMoreLayout != null) {
            if(!NetWorkUtils.isBackground(context)){
                Toaster.showToast(context, context.getString(R.string.network_unConnect));
            }
            refreshLoadMoreLayout.stopLoadMore();
            refreshLoadMoreLayout.stopRefresh();
        }
    }
}
