package com.milier.wowgallery.weight.refresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by jly on 2017/6/9.
 */


/**
 * @author qinbaowei
 * @createtime 2015/10/10 09:52
 * @description must be only one child view
 */
public class RefreshLoadMoreLayout extends ViewGroup {

    private HeaderLayout mHeaderLayout;
    private boolean mCanRefresh;

    private FooterLayout mFooterLayout;
    private boolean mCanLoadMore;

    private boolean mMultiTask;

    private CallBack mCallBack;

    /**
     * 上一次的y轴坐标
     */
    private float mPreviousYPos;
    /**
     * 当前y轴坐标
     */
    private float mNowYPos;
    /**
     * y轴移动的距离
     */
    private float mYDistance;

    /**
     * down事件时的y坐标
     */
    private float mDownYPos;
    /**
     * 第一次触发move事件的时候，判断移动的距离（避免误触）
     */
    private boolean mFirstMove;
    /**
     * 第一次触发move事件的时候，y轴移动的距离
     */
    private float mJudgeYDistance;
    /**
     * 误差距离
     */
    private int mTouchSlop;


    public RefreshLoadMoreLayout(Context context) {
        super(context);
        initViews(null);
    }

    public RefreshLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    private String getString(int stringResId) {
        return getContext().getResources().getString(stringResId);
    }

    private Drawable getDrawable(int drawableResId) {
        return getContext().getResources().getDrawable(drawableResId);
    }

    private int getColor(int colorResId) {
        return getContext().getResources().getColor(colorResId);
    }

    private float getDimen(int dimenResId) {
        return getContext().getResources().getDimension(dimenResId);
    }

    private void initViews(AttributeSet attributeSet) {
        setClickable(true);//make event deliver

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof HeaderLayout || child instanceof FooterLayout) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        child.getMeasuredHeightAndState());
            } else {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        getContentMeasuredHeightState());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed,
                            int l,
                            int t,
                            int r,
                            int b) {//height of child must be smaller than parent
        int childCount = getChildCount();
        View vHeader = null;
        View vFooter = null;
        View vContent = null;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof HeaderLayout) {
                vHeader = child;
                vHeader.layout(0, 0, vHeader.getMeasuredWidth(), vHeader.getMeasuredHeight());
            } else if (child instanceof FooterLayout) {
                vFooter = child;
                int y = getMeasuredHeight() - vFooter.getMeasuredHeight();
                vFooter.layout(0, y, vFooter.getMeasuredWidth(), vFooter.getMeasuredHeight() + y);
            } else {
                vContent = child;
            }
        }
        int y = (null == vHeader ? 0 : vHeader.getMeasuredHeight()) - (null == vFooter ? 0 : vFooter
                .getMeasuredHeight());
        vContent.layout(0, y, vContent.getMeasuredWidth(), getMeasuredHeight() + y);
    }

    /**
     * @param height
     * @return force
     */
    private float externForce(int height, int factorHeight) {
        float s1 = (float) height / factorHeight;
        if (s1 >= 1.0f) {
            s1 = 0.4f;
        } else if (s1 >= 0.6 && s1 < 1.0) {
            s1 = 0.6f;
        } else if (s1 >= 0.3 && s1 < 0.6) {
            s1 = 0.8f;
        } else {
            s1 = 1.0f;
        }
        return s1;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreviousYPos = ev.getRawY();
                mDownYPos = ev.getRawY();
                mFirstMove = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mNowYPos = ev.getRawY();
                mYDistance = mNowYPos - mPreviousYPos;
                mPreviousYPos = mNowYPos;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mFirstMove && (mJudgeYDistance = Math.abs(mNowYPos - mDownYPos)) < mTouchSlop) {
                    mFirstMove = false;
                    return false;
                }
                mFirstMove = false;
                if (mYDistance == 0f) {
                    return false;
                }
                if (isPullDown(MotionEvent.ACTION_MOVE, mYDistance)) {
                    return true;
                } else if (isPullUp(MotionEvent.ACTION_MOVE, mYDistance)) {
                    return true;
                }
                break;
            default:
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mYDistance == 0f) {
                    break;
                }
                if (isHeaderActive()) {
                    mYDistance *= externForce(mHeaderLayout.getHeaderHeight(),
                            mHeaderLayout.getHeaderContentHeight());
                } else if (isFooterActive()) {
                    mYDistance *= externForce(mFooterLayout.getFooterHeight(),
                            mFooterLayout.getFooterContentHeight());
                }
                if (isPullDown(MotionEvent.ACTION_MOVE, mYDistance)) {
                    mHeaderLayout.setHeaderHeight((int) (mHeaderLayout.getHeaderHeight() + mYDistance));
                    updatePullDownStatus(MotionEvent.ACTION_MOVE);
                } else if (isPullUp(MotionEvent.ACTION_MOVE, mYDistance)) {
                    mFooterLayout.setFooterHeight((int) (mFooterLayout.getFooterHeight() - mYDistance));
                    updatePullUpStatus(MotionEvent.ACTION_MOVE);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isPullDown(MotionEvent.ACTION_UP, 0)) {
                    updatePullDownStatus(MotionEvent.ACTION_UP);
                } else if (isPullUp(MotionEvent.ACTION_UP, 0)) {
                    updatePullUpStatus(MotionEvent.ACTION_UP);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private boolean isHeaderActive() {
        return isCanRefresh() && HeaderLayout.Status.NORMAL != mHeaderLayout.getStatus() && mHeaderLayout
                .getHeaderHeight() > 0;
    }

    private boolean isHeaderAutoMove() {
        if (!isCanRefresh()) {
            return false;
        }
        if (HeaderLayout.Status.BACK_REFRESH == mHeaderLayout.getStatus()) {
            return true;
        }
        if (HeaderLayout.Status.BACK_NORMAL == mHeaderLayout.getStatus()) {
            return true;
        }
        if (HeaderLayout.Status.AUTO_REFRESH == mHeaderLayout.getStatus()) {
            return true;
        }
        return false;
    }

    /**
     * @return isContentToTop() == isContentToBottom() ? isFooterActive() : isFooterAutoMove();
     */
    private boolean isLoadMoreActive() {
        return isContentToTop() == isContentToBottom() ? isFooterActive() : isFooterAutoMove();
    }

    private boolean isPullDown(int action, float fDisYPos) {
        if (!isCanRefresh()) {
            return false;
        }
        if (isHeaderAutoMove()) {
            return false;
        }
        if (isLoadMoreActive()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (fDisYPos > 0) {//move down
                    if (isContentToTop()) {//pull up
                        return true;
                    }
                } else if (fDisYPos < 0) {//move up
                    if (isHeaderActive()) {//back after pull down
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
                if (isHeaderActive()) {
                    return true;
                }
            default:
                break;
        }
        return false;
    }


    private boolean isFooterAutoMove() {
        if (!isCanLoadMore()) {
            return false;
        }
        if (FooterLayout.Status.BACK_LOAD == mFooterLayout.getStatus()) {
            return true;
        }
        if (FooterLayout.Status.BACK_NORMAL == mFooterLayout.getStatus()) {
            return true;
        }
        return false;
    }

    private boolean isFooterActive() {
        return isCanLoadMore() && FooterLayout.Status.NORMAL != mFooterLayout.getStatus() && mFooterLayout
                .getFooterHeight() > 0;
    }

    /**
     * @return isContentToTop() == isContentToBottom() ? isHeaderActive() : isHeaderAutoMove();
     */
    private boolean isPullDownActive() {
        return isContentToTop() == isContentToBottom() ? isHeaderActive() : isHeaderAutoMove();
    }

    private boolean isPullUp(int action, float fDisYPos) {
        if (!isCanLoadMore()) {
            return false;
        }
        if (isFooterAutoMove()) {
            return false;
        }
        if (isPullDownActive()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (fDisYPos > 0) {//move down
                    if (isFooterActive()) {//back after pull up
                        return true;
                    }
                } else if (fDisYPos < 0) {//move up
                    if (isContentToBottom()) {//pull up
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
                if (isFooterActive()) {
                    return true;
                }
            default:
                break;
        }
        return false;
    }

    private void updatePullDownStatus(int action) {

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (HeaderLayout.Status.REFRESH == mHeaderLayout.getStatus()) {//change height not change status when refreshing
                    return;
                }
                if (mHeaderLayout.getHeaderHeight() >= mHeaderLayout.getHeaderContentHeight()) {//can release to refresh
                    mHeaderLayout.setStatus(HeaderLayout.Status.CAN_RELEASE);
                } else {//only pull down
                    mHeaderLayout.setStatus(HeaderLayout.Status.PULL_DOWN);
                }
                break;
            case MotionEvent.ACTION_UP://change status when move,check status on up
                if (HeaderLayout.Status.CAN_RELEASE == mHeaderLayout.getStatus()) {
                    mHeaderLayout.setStatus(HeaderLayout.Status.REFRESH);
                } else if (HeaderLayout.Status.PULL_DOWN == mHeaderLayout.getStatus()) {
                    mHeaderLayout.setStatus(HeaderLayout.Status.BACK_NORMAL);
                } else if (HeaderLayout.Status.REFRESH == mHeaderLayout.getStatus()) {
                    if (mHeaderLayout.getHeaderHeight() > mHeaderLayout.getHeaderContentHeight()) {
                        mHeaderLayout.setStatus(HeaderLayout.Status.BACK_REFRESH);
                    }
                }
                break;
            default:
                break;
        }

    }

    private void updatePullUpStatus(int action) {

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (FooterLayout.Status.LOAD == mFooterLayout.getStatus()) {
                    return;
                }
                if (mFooterLayout.getFooterHeight() >= mFooterLayout.getFooterContentHeight()) {
                    mFooterLayout.setStatus(FooterLayout.Status.CAN_RELEASE);
                } else {
                    mFooterLayout.setStatus(FooterLayout.Status.PULL_UP);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (FooterLayout.Status.CAN_RELEASE == mFooterLayout.getStatus()) {
                    mFooterLayout.setStatus(FooterLayout.Status.LOAD);
                } else if (FooterLayout.Status.PULL_UP == mFooterLayout.getStatus()) {
                    mFooterLayout.setStatus(FooterLayout.Status.BACK_NORMAL);
                } else if (FooterLayout.Status.LOAD == mFooterLayout.getStatus()) {
                    if (mFooterLayout.getFooterHeight() > mFooterLayout.getFooterContentHeight()) {
                        mFooterLayout.setStatus(FooterLayout.Status.BACK_LOAD);
                    }
                }
                break;
            default:
                break;
        }

    }

    private int getContentMeasuredHeightState() {
        if (getContentView() instanceof ScrollView || getContentView() instanceof RecyclerView || getContentView() instanceof AbsListView) {
            return MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        } else if (getContentView() instanceof View) {
            return MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        }
        return 0;
    }


    public boolean isContentToTop() {
        return RefreshLoadMoreUtil.isContentToTop(getContentView());
    }


    public boolean isContentToBottom() {
        return RefreshLoadMoreUtil.isContentToBottom(getContentView());
    }

    public void init(Config config) {
        setCallBack(config.mCallBack);
        setRefreshLayout();
        setCanRefresh(config.mCanRefresh);
        setIsShowLastRefreshTime(config.mShowLastRefreshTime);
        setHeaderKeyLastRefreshTime(config.mKeyLastRefreshTime);
        setHeaderDateFormat(config.mHeaderDateFormat);
        setLoadMoreLayout();
        setCanLoadMore(config.mCanLoadMore);
        setSupportAutoLoadMore(config.mAutoLoadMore);
        setMultiTask(config.mMultiTask);
    }

    public static class Config {
        public CallBack mCallBack;
        public boolean mCanRefresh = true;
        public boolean mShowLastRefreshTime = false;
        public String mKeyLastRefreshTime = "";
        public String mHeaderDateFormat = "yyyy-MM-dd";
        public boolean mCanLoadMore = true;
        public boolean mAutoLoadMore = false;
        public boolean mMultiTask = false;

        public Config(CallBack callBack) {
            this.mCallBack = callBack;
        }

        /**
         * @param b 是否支持下拉刷新
         */
        public Config canRefresh(boolean b) {
            mCanRefresh = b;
            return this;
        }

        /**
         * @param currActivityClass 当前页面activity的类名（作为key保存时间）
         * @param dateFormat        显示上次刷新时间的格式
         */
        public Config showLastRefreshTime(Class currActivityClass, String dateFormat) {
            mShowLastRefreshTime = true;
            mKeyLastRefreshTime = currActivityClass.getSimpleName();
            mHeaderDateFormat = dateFormat;
            return this;
        }

        public Config showLastRefreshTime(Class currActivityClass) {
            return showLastRefreshTime(currActivityClass, "");
        }

        /**
         * @param b 是否支持上拉加载更多
         */
        public Config canLoadMore(boolean b) {
            mCanLoadMore = b;
            return this;
        }

        /**
         * 自动上拉加载更多（默认不自动加载更多）
         */
        public Config autoLoadMore() {
            mAutoLoadMore = true;
            return this;
        }

        /**
         * 刷新和加载更多可同时进行（默认不能同时进行）
         */
        public Config multiTask() {
            mMultiTask = true;
            return this;
        }
    }

    private void setHeaderDateFormat(String dateFormat) {
        mHeaderLayout.setDateFormat(dateFormat);
    }

    private void setHeaderKeyLastRefreshTime(String key) {
        mHeaderLayout.setKeyLastUpdateTime(key);
    }

    private void setIsShowLastRefreshTime(boolean b) {
        mHeaderLayout.setIsShowLastRefreshTime(b);
    }

    public void setCanRefresh(boolean canRefresh) {
        mCanRefresh = canRefresh;
    }

    private void setRefreshLayout() {
        mHeaderLayout = new HeaderLayout(getContext());
        mHeaderLayout.setCallBack(getCallBack());
        mHeaderLayout.setHeaderHeight(0);
        addView(mHeaderLayout, 0);//header should be the first view

    }

    public boolean isCanRefresh() {
        if (mMultiTask) {
            return mCanRefresh;
        } else {
            return mCanRefresh && null != mFooterLayout && !mFooterLayout.isLoadingMore();
        }
    }

    public boolean isCanLoadMore() {
        if (mMultiTask) {
            return mCanLoadMore;
        } else {
            return mCanLoadMore && null != mHeaderLayout && !mHeaderLayout.isRefreshing();
        }
    }

    public void setCanLoadMore(boolean canLoadMore) {
        mCanLoadMore = canLoadMore;
    }

    private void setLoadMoreLayout() {
        mFooterLayout = new FooterLayout(getContext());
        mFooterLayout.setCallBack(getCallBack());
        mFooterLayout.setFooterHeight(0);
        addView(mFooterLayout);
    }

    public void startAutoRefresh() {
        startAutoRefresh(500);
    }

    public void startAutoRefresh(final long delay) {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isCanRefresh()) {
                            return;
                        }
                        if (HeaderLayout.Status.NORMAL != mHeaderLayout.getStatus()) {
                            return;
                        }
                        mHeaderLayout.setStatus(HeaderLayout.Status.AUTO_REFRESH);
                    }
                }, delay);
            }
        });

    }

    public void stopRefresh() {
        stopRefresh(true, false, 0);
    }

    public void stopRefresh(long delay) {
        stopRefresh(true, false, delay);
    }

    public void stopRefresh(boolean canRefresh) {
        stopRefresh(canRefresh, false, 0);
    }

    public void stopRefreshNoMoreData(boolean noMoreData) {
        stopRefresh(true, noMoreData, 0);
    }

    public void stopRefreshNoMoreData(boolean noMoreData, long delay) {
        stopRefresh(true, noMoreData, delay);
    }

    public void stopRefresh(boolean canRefresh, boolean noMoreData) {
        stopRefresh(canRefresh, noMoreData, 0);
    }

    /**
     * @param canRefresh 是否禁用刷新
     * @param noMoreData 是否没有更多数据了（第一页就小于你设置的pagesize的时候需要）
     * @param delay      延迟时间
     */
    public void stopRefresh(final boolean canRefresh, final boolean noMoreData, long delay) {
        if (!isCanRefresh()) {
            return;
        }
        if (HeaderLayout.Status.BACK_NORMAL == mHeaderLayout.getStatus()) {
            return;
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mHeaderLayout.setStatus(HeaderLayout.Status.BACK_NORMAL);
                mFooterLayout.setNoMoreData(noMoreData);
                setCanRefresh(canRefresh);
            }
        }, delay);

    }

    /**
     * 依然可以上拉加载更多
     */
    public void stopLoadMore() {
        stopLoadMore(true, false);
    }

    /**
     * @see #stopLoadMoreNoMoreData(boolean)
     */
    @Deprecated
    public void stopLoadMoreNoData(boolean noMoreData) {
        stopLoadMoreNoMoreData(noMoreData);
    }

    public void stopLoadMoreNoMoreData(boolean noMoreData) {
        stopLoadMore(true, noMoreData);
    }

    /**
     * 如果 canLoadMore=false,则上拉加载更多功能不能使用
     *
     * @param canLoadMore
     */
    public void stopLoadMore(boolean canLoadMore) {
        stopLoadMore(canLoadMore, false);
    }

    private void stopLoadMore(boolean canLoadMore, boolean noMoreData) {
        if (!isCanLoadMore()) {
            return;
        }
        if (FooterLayout.Status.BACK_NORMAL == mFooterLayout.getStatus()) {
            return;
        }
        mFooterLayout.setStatus(FooterLayout.Status.BACK_NORMAL);
        mFooterLayout.setNoMoreData(noMoreData);
        setCanLoadMore(canLoadMore);
    }

    public CallBack getCallBack() {
        return mCallBack;
    }

    private void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    private void setSupportAutoLoadMore(final boolean b) {
        final View cv = getContentView();
        if (cv instanceof RecyclerView) {
            ((RecyclerView) cv).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (b) {
                        if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                            if (isCanLoadMore() && RefreshLoadMoreUtil.isContentToBottom(cv)) {
                                mFooterLayout.setStatus(FooterLayout.Status.LOAD);
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 && isCanLoadMore() && RefreshLoadMoreUtil.isContentToBottom(cv) || dy < 0 && isCanRefresh() && RefreshLoadMoreUtil
                            .isContentToTop(cv)) {
                        //滑动到顶部或者底部，自动调用了RefreshLoadMoreLayout的requestDisallowInterceptTouchEvent(true)
                        //所以我们需要延迟设置为false
                        //还不清楚是哪里自动调用了requestDisallowInterceptTouchEvent(true)
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                requestDisallowInterceptTouchEvent(false);
                            }
                        }, 10);
                    }
                }
            });
        } else if (cv instanceof AbsListView) {
            ((AbsListView) cv).setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (SCROLL_STATE_IDLE == scrollState) {
                        if (isCanLoadMore() && RefreshLoadMoreUtil.isContentToBottom(cv)) {
                            if (b) {
                                mFooterLayout.setStatus(FooterLayout.Status.LOAD);
                            }
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view,
                                     int firstVisibleItem,
                                     int visibleItemCount,
                                     int totalItemCount) {
                    if (isCanLoadMore() && RefreshLoadMoreUtil.isContentToBottom(cv) || isCanRefresh() && RefreshLoadMoreUtil
                            .isContentToTop(cv)) {
                        requestDisallowInterceptTouchEvent(false);
                    }
                }
            });
        }
    }

    private void setMultiTask(boolean b) {
        mMultiTask = b;
    }

    public interface CallBack {
        void onRefresh();

        void onLoadMore();
    }

    public View getContentView() {
        return getChildAt(1);
    }

    public String strEvent(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return "";
        }
    }
}

