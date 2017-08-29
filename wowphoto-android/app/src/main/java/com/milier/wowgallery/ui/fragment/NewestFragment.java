package com.milier.wowgallery.ui.fragment;


import android.animation.ObjectAnimator;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.milier.wowgallery.R;
import com.milier.wowgallery.api.WelfareService;
import com.milier.wowgallery.bean.WelfareBean;
import com.milier.wowgallery.broadcast.MyNetWorkBroadCast;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.net.RetrofitFactory;
import com.milier.wowgallery.ui.activity.MainActivity;
import com.milier.wowgallery.ui.adapter.PictureAdapter;
import com.milier.wowgallery.utils.DialogTools;
import com.milier.wowgallery.view.FragmentBaseView;
import com.milier.wowgallery.weight.refresh.RefreshLoadMoreLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by jly on 2016/12/19.
 */
public class NewestFragment extends Fragment implements RefreshLoadMoreLayout.CallBack, FragmentBaseView {
    private static final String TAG = "NewestFragment";

    View rootView;
    @BindView(R.id.receler_view)
    RecyclerView mRecelerView;
    @BindView(R.id.refresh_view)
    RefreshLoadMoreLayout mRefreshView;
    @BindView(R.id.empty_iv)
    ImageView mEmptyIv;
    @BindView(R.id.empty_tv)
    TextView mEmptyTv;

    private ArrayList<WelfareBean.ResultsBean> mDatas = new ArrayList<>();
    private ArrayList<WelfareBean> mTempDatas = new ArrayList<>();
    private PictureAdapter mPictureAdapter;
    private boolean isLoadingMore;
    private int page = 1;
    private int disy, titleHeight;
    private boolean isShow = true;
    private RelativeLayout mTitleLayout;
    //    private Subscription loginInSubscribe, loginOutSubscribe, storeSubscribe;
    private MainActivity mainActivity;
    protected String loadType;
    private MyNetWorkBroadCast myReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_newest, container, false);
        ButterKnife.bind(this, rootView);

        initData();
        initEvent();
        getUserBean();
        return rootView;
    }

    private void initData() {

        loadType = Constants.LOAD_REFRESH;
//        fragmentBasePresenter = new FragmentBasePresenterImpl(this, this.getContext());
//        fragmentBasePresenter.resume();
//        fragmentBasePresenter.loadDatas(ImageType.NEW, Constants.PAGE_SIZE, page);

        mRefreshView.init(new RefreshLoadMoreLayout.Config(this).canRefresh(true).canLoadMore(true).autoLoadMore());
        mRefreshView.startAutoRefresh(); //自动刷新

//        mRecelerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecelerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mPictureAdapter = new PictureAdapter(getContext(), mDatas, false);
//        if (LoginUtils.isAppHasSignIn(getContext())) {
//            UserBean userBean = LoginUtils.getUserInfo(getContext());
//            if (userBean != null) {
//                if (userBean.getVipExpireDay() > 0) {
//                    mPictureAdapter.setLogin(true);
//                }
//            }
//        }
        mRecelerView.setAdapter(mPictureAdapter);

        mainActivity = (MainActivity) getActivity();
        mTitleLayout = (RelativeLayout) mainActivity.findViewById(R.id.title_layout);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTitleLayout.measure(width, height);
        titleHeight = mTitleLayout.getMeasuredHeight();

        myReceiver = new MyNetWorkBroadCast(mRefreshView);
        getContext().registerReceiver(myReceiver, new IntentFilter(Constants.NET_UNCONNECTED));


//        getWelfareData(10,1);






    }

    private void getWelfareData(int pageSize, int currentPage) {
        Retrofit retrofit = RetrofitFactory.init("http://gank.io/api/data/");
        WelfareService welfareService = retrofit.create(WelfareService.class);
        Call<WelfareBean> observable = welfareService.getWelfareBeanList(pageSize, currentPage);

        observable.enqueue(new Callback<WelfareBean>() {
            @Override
            public void onResponse(Call<WelfareBean> call, Response<WelfareBean> response) {
                WelfareBean welfareBean = response.body();
                Log.e(TAG, "response is : " + welfareBean.isError());
                if (welfareBean != null) {
                    Log.i(TAG, "SIZE is : " + welfareBean.getResults().size());
                    if (loadType.equals(Constants.LOAD_REFRESH)) {
                        mPictureAdapter.addHeadData(welfareBean.getResults());
                        if (welfareBean.getResults().size() < Constants.PAGE_SIZE) {
                            mRefreshView.setCanLoadMore(false);
                        } else {
                            mRefreshView.setCanLoadMore(true);
                        }
                        mRefreshView.stopRefresh();
                    } else {
                        mPictureAdapter.addFootData(welfareBean.getResults());
                    }

//                    mPictureAdapter.addHeadData(welfareBean.getResults());
                }
            }

            @Override
            public void onFailure(Call<WelfareBean> call, Throwable t) {
                Log.e(TAG, "请求失败");
            }
        });
    }

    private void initEvent() {

        mRecelerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dy > 0 时为向上滚动
                // dy < 0 时为向下滚动

                disy = dy;

                int firstVisibleItem = ((StaggeredGridLayoutManager) mRecelerView.getLayoutManager()).findFirstVisibleItemPositions(null)[0];
                if (firstVisibleItem == 0) {
                    if (!isShow) {
                        isShow = true;
                        showTitle();
                    }
                } else {
                    if (disy > 15 && isShow) {
                        isShow = false;
                        hideTitle();
                        disy = 0;
                    }
                    if (disy < -15 && !isShow) {
                        isShow = true;
                        showTitle();
                        disy = 0;
                    }
                }

                if ((isShow && dy > 0) || (!isShow && dy < 0)) {
                    disy += dy;
                }
            }
        });

//        mEmptyIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentBasePresenter.loadDatas(ImageType.NEW, Constants.PAGE_SIZE, 1);
//            }
//        });
//
//        mEmptyTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentBasePresenter.loadDatas(ImageType.NEW, Constants.PAGE_SIZE, 1);
//            }
//        });

    }


    private void showTitle() {
        mainActivity.mIsShow = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTitleLayout, View.TRANSLATION_Y, -titleHeight, 0);
        animator.setDuration(500);
        animator.start();
    }

    private void hideTitle() {
        mainActivity.mIsShow = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTitleLayout, View.TRANSLATION_Y, 0, -titleHeight);
        animator.setDuration(500);
        animator.start();
    }


    /**
     * 返回recycleView的头部
     */
    public void returnTop() {
        if (mRecelerView != null) {
            mRecelerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
//        fragmentBasePresenter.loadDatas(ImageType.NEW, Constants.PAGE_SIZE, page++);
        getWelfareData(Constants.PAGE_SIZE, page++);
        loadType = Constants.LOAD_REFRESH;
    }

    @Override
    public void onLoadMore() {
//        fragmentBasePresenter.loadDatas(ImageType.NEW, Constants.PAGE_SIZE, page++);
        getWelfareData(Constants.PAGE_SIZE, page++);
        loadType = Constants.LOAD_MORE;

    }

    @Override
    public void setRefreshState(boolean refreshState) {
        this.isLoadingMore = refreshState;
        if (!refreshState) {
            mRefreshView.stopLoadMore();
        }
//        if (!refreshState && page != 1) {
//            mRefreshView.setCanRefresh(false);
//        }
    }

    @Override
    public void showProgress() {
        DialogTools.showWaittingDialog(this.getContext());
    }

    @Override
    public void dismissProgress() {
        DialogTools.closeWaittingDialog();
        mRefreshView.stopLoadMore();

        if (mTempDatas != null && mTempDatas.size() >= Constants.PAGE_SIZE) {
            mRefreshView.stopRefresh();
        } else {
            mRefreshView.stopRefreshNoMoreData(true);
        }


        setEmptyView();
    }

//    @Override
//    public void setData(List<WelfareBean> atlasBean) {
//
//        /** 只有当数据获取成功时， page才+1 才可以上拉*/
////        if (!mRefreshView.isCanLoadMore() && page == 1) {
////            mRefreshView.setCanLoadMore(true);
////        }
////        if (mRefreshView.isCanRefresh()) {
////            mRefreshView.stopRefresh();
////        }
////
////        mPictureAdapter.setData(atlasBean);
////
////        if (mRefreshView.isCanLoadMore()) {
////            mRefreshView.stopLoadMore();
////        }
////        page++;
//
//        /**
//         * 只有刚进入页面时候才能下拉刷新，或者是数据获取失败可以下拉刷新
//         */
////        mRefreshView.setCanRefresh(false);
//
//
//        /**
//         * 下拉刷新
//         */
//        if (loadType.equals(Constants.LOAD_REFRESH)) {
//            mPictureAdapter.addHeadData(atlasBean);
//            if (atlasBean.size() < Constants.PAGE_SIZE) {
//                mRefreshView.setCanLoadMore(false);
//            } else {
//                mRefreshView.setCanLoadMore(true);
//            }
//        }
//        /**
//         * 加载更多
//         */
//        if (loadType.equals(Constants.LOAD_MORE)) {
//
//            mPictureAdapter.addFootData(atlasBean);
//        }
//
//        mTempDatas.clear();
//        mTempDatas = (ArrayList<AtlasBean>) atlasBean;
//
//        setEmptyView();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(myReceiver);

    }

    private void getUserBean() {
//        loginInSubscribe = RxBus.getDefault().toObservable(String.class).subscribe(new Action1<String>() {
//            @Override
//            public void call(String str) {
//                if (TextUtils.equals(str, Constants.SIGN_IN)) {
//                    UserBean userBean = LoginUtils.getUserInfo(getContext());
//                    if (userBean != null) {
//                        if (userBean.getVipExpireDay() > 0) {
//                            mPictureAdapter.setLogin(true);
//                            mPictureAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//
//            }
//        });


//        storeSubscribe = RxBus.getDefault().toObservable(StoreBean.class).subscribe(new Action1<StoreBean>() {
//            @Override
//            public void call(StoreBean storeBean) {
//                if (storeBean != null && storeBean.getIds().size() > 0) {
//                    for (int i = 0; i < storeBean.getIds().size(); i++) {
//                        for (int j = 0; j < mPictureAdapter.getDatas().size(); j++) {
//                            if (storeBean.getIds().get(i) == mPictureAdapter.getDatas().get(j).getId()) {
//                                mPictureAdapter.getDatas().get(j).setIsStored(1);
//
//                            }
//                        }
//                    }
//                    mPictureAdapter.notifyDataSetChanged();
//                }
//
//            }
//        });
    }

    public void setEmptyView() {
        if (mPictureAdapter.getAllData().size() == 0 || mPictureAdapter.getAllData() == null) {
            mEmptyIv.setVisibility(View.VISIBLE);
            mEmptyTv.setVisibility(View.VISIBLE);
        } else {
            mEmptyIv.setVisibility(View.GONE);
            mEmptyTv.setVisibility(View.GONE);
        }
    }

}
