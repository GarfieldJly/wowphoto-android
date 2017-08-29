package com.milier.wowgallery.ui.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.milier.wowgallery.R;
import com.milier.wowgallery.bean.LoginOutBean;
import com.milier.wowgallery.common.MyApplication;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.ui.adapter.MyFragmentPagerAdapter;
import com.milier.wowgallery.ui.fragment.HotestFragment;
import com.milier.wowgallery.ui.fragment.NewestFragment;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.RxBus;
import com.milier.wowgallery.utils.SystemBarTintHelper;
import com.milier.wowgallery.utils.Toaster;
import com.milier.wowgallery.view.MainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainView {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.menu_header_image)
    ImageView mMenuHeaderImage;
    @BindView(R.id.menu_my_account)
    LinearLayout mMenuMyAccount;
    @BindView(R.id.menu_feedback)
    LinearLayout mMenuFeedback;
    @BindView(R.id.cursor_iv)
    ImageView mCursorIv;
    @BindView(R.id.new_tv)
    TextView mNewTv;
    @BindView(R.id.hot_tv)
    TextView mHotTv;
    @BindView(R.id.navigation_iv)
    ImageView mNavigationIv;
    @BindView(R.id.title_layout)
    public RelativeLayout mTitleLayout;
    @BindView(R.id.login_tv)
    TextView mLoginTv;
    @BindView(R.id.account_iv)
    ImageView mAccountIv;
    @BindView(R.id.fav_iv)
    ImageView mFavIv;
    @BindView(R.id.feedback_iv)
    ImageView mFeedbackIv;
    @BindView(R.id.toolbar_center_iv)
    ImageView mToolbarCenterIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.toolbar_title)
    ImageView mToolbarTitle;
    @BindView(R.id.menu_my_info)
    LinearLayout mMenuMyInfo;
    @BindView(R.id.menu_my_fav)
    LinearLayout mMenuMyFav;
    @BindView(R.id.tab_ll)
    LinearLayout mTabLl;
//    @BindView(R.id.adViewContainer)
//    RelativeLayout mAdViewContainer;
    @BindView(R.id.benefit_btn)
    ImageView mBenefitBtn;
    @BindView(R.id.benefit_layout)
    RelativeLayout mBenefitLayout;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    private List<Integer> titleList = new ArrayList<>();
    private int currIndex, bmpW, offset;
    private TextView[] mTvs;

    private Integer[] mNoVipIcons;
    private Integer[] mVipIcons;
    private Subscription loginInSubscribe, loginOutSubscribe;
    //    private boolean isVip;
    private Dialog mLogOutDialog,mLoginDialog;
//    private MainPresenter mMainPresenter;
    public boolean mIsShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //AppsFlyer 初始化
//        AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APPSFLYER_ID);

        ButterKnife.bind(this);

        // 设置显示Toolbar
//        setSupportActionBar(mToolbar);
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
//        ActionBarDrawerToggle mActionBarDrawerToggle =
//                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
//        mActionBarDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // 状态栏与Toolbar同色
        new SystemBarTintHelper().onCreate(this, true, R.color.statusbar_color);

        initData();
        initEvent();

        getUserBean();

        if(Constants.EnterTime == -1){
            Constants.EnterTime = System.currentTimeMillis();
        }
        Constants.LOOK__ALBUM_COUNT = 0;
    }

    protected void showLogOutDialog() {
        mLogOutDialog = new Dialog(this, R.style.customDialogStyle);
        View view = LinearLayout.inflate(this, R.layout.dialog_logout, null);
        mLogOutDialog.setContentView(view);
        mLogOutDialog.setCanceledOnTouchOutside(true); //强制修改，点空白区域不会消失
        final TextView confirmTv = (TextView) view.findViewById(R.id.confirm_tv);
        TextView cancelTv = (TextView) view.findViewById(R.id.cancel_tv);

       /* confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.loginOut();
                if (mLogOutDialog.isShowing()) {
                    mLogOutDialog.dismiss();
                }
            }
        });*/
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogOutDialog.isShowing()) {
                    mLogOutDialog.dismiss();
                }
            }
        });
        mLogOutDialog.show();
    }

    @Override
    public void onBackPressed() {
        MilierLog.e("返回","onBackPressed*****");

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initData() {

        fragments.add(new NewestFragment());
        fragments.add(new HotestFragment());

        titleList.add(R.string.newest);
        titleList.add(R.string.hotest);

        mTvs = new TextView[2];
        mTvs[0] = mNewTv;
        mTvs[1] = mHotTv;
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.navigation_tiao_no).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 2 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0); //将图像向右移动
        mCursorIv.setImageMatrix(matrix);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titleList, this);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置滑动时渐变的阴影颜色
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.navigation_bg));

        mNoVipIcons = new Integer[]{R.drawable.icon_account_no, R.drawable.icon_fav_no, R.drawable.icon_feedback_no, R.drawable.icon_toolbar_center_no};
        mVipIcons = new Integer[]{R.drawable.icon_account, R.drawable.icon_fav, R.drawable.icon_feedback, R.drawable.icon_toolbar_center};
        boolean flag = false;
/*
        if(flag == true){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mViewPager.setLayoutParams(params);
            initGoogleAd(mAdViewContainer,false);
        }else{
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.bottomMargin = (int) (50* MyApplication.getInstance().density);
            mViewPager.setLayoutParams(params);
            initGoogleAd(mAdViewContainer,true);
        }*/
        initUserBean(flag);
    }

    private void initUserBean(boolean flag) {

        mNavigationIv.setImageResource(flag ? R.drawable.navigation_left : R.drawable.navigation_left_no);
//        mMainPresenter = new MainPresenterImpl(this, this);
        mAccountIv.setImageResource(flag ? mVipIcons[0] : mNoVipIcons[0]);
        mFavIv.setImageResource(flag ? mVipIcons[1] : mNoVipIcons[1]);
        mFeedbackIv.setImageResource(flag ? mVipIcons[2] : mNoVipIcons[2]);
        mToolbarTitle.setImageResource(flag ? R.drawable.icon_title : R.drawable.icon_title_no);
        mToolbarCenterIv.setImageResource(flag ? mVipIcons[3] : mNoVipIcons[3]);
        mNameTv.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        mLoginTv.setText(isLogin ? getResources().getString(R.string.login_out) : getResources().getString(R.string.login));
        mLoginTv.setBackgroundResource(isLogin ? R.drawable.navdrawer_title_tv_select_bg : R.drawable.navdrawer_title_tv_select_no_bg);
        mLoginTv.setSelected(isLogin);
        mCursorIv.setImageResource(flag ? R.drawable.navigation_tiao : R.drawable.navigation_tiao_no);
        mNewTv.setTextColor(flag ? getResources().getColor(R.color.yellow) : getResources().getColor(R.color.pink));
//        if (LoginUtils.isAppHasSignIn(this)) {
//            mMainPresenter.getUserBean();
//        }
    }


    private void initEvent() {
        mMenuHeaderImage.setOnClickListener(this);
        mMenuMyAccount.setOnClickListener(this);

        mMenuFeedback.setOnClickListener(this);
        mNavigationIv.setOnClickListener(this);
        mMenuMyFav.setOnClickListener(this);
        mLoginTv.setOnClickListener(this);
        mNewTv.setOnClickListener(new MyOnClickListener(0));
        mHotTv.setOnClickListener(new MyOnClickListener(1));
        mBenefitBtn.setOnClickListener(this);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                boolean flag = false;

//                if(flag){
//                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    mViewPager.setLayoutParams(params);
//                    initGoogleAd(mAdViewContainer,false);
//                }else{
//                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    params.bottomMargin = (int) (50* MyApplication.getInstance().density);
//                    mViewPager.setLayoutParams(params);
//                    initGoogleAd(mAdViewContainer,true);
//                }
                mNavigationIv.setImageResource(flag ? R.drawable.navigation_left : R.drawable.navigation_left_no);
                mNavigationIv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.toolbar_left_open));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mNavigationIv.setImageResource(R.drawable.toolbar_left);
                mNavigationIv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.toolbar_left_close));
            }

            //当抽屉滑动状态改变的时候被调用
            // 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
            //   * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE/
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_header_image:
//            case R.id.login_tv:
//                MobclickAgent.onEvent(MainActivity.this, Constants.slider_menu_click);
//                if (LoginUtils.isAppHasSignIn(MainActivity.this)) {
//                    showLogOutDialog();
//                } else {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                }
//                break;
//            case R.id.menu_my_account:
//                MobclickAgent.onEvent(MainActivity.this, Constants.slider_menu_click);
//                startActivity(new Intent(MainActivity.this, AccountActivity.class));
//                break;
//            case R.id.menu_my_fav:
//                MobclickAgent.onEvent(MainActivity.this, Constants.slider_menu_click);
//                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
//                break;
//            case R.id.menu_feedback:
//                MobclickAgent.onEvent(MainActivity.this, Constants.slider_menu_click);
//                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
//                break;
            case R.id.navigation_iv:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
//                    mNavigationIv.setImageResource(R.drawable.toolbar_left);
//                    mNavigationIv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.toolbar_left_close));
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
//                    mNavigationIv.setImageResource(R.drawable.navigation_left);
//                    mNavigationIv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.toolbar_left_open));
                }
                break;
//            case R.id.benefit_btn:
//                if(LoginUtils.isAppHasSignIn(this)){
//                    BenefitActivity.launch(MainActivity.this);
//                    SPUtils.putBoolean(this, Constants.IS_SHOW_BENEFIT_FLAG, true);
//                }else{
//                    showLoginDialog();
//                }
//                break;
        }
    }


    protected void showLoginDialog() {
        mLoginDialog = new Dialog(this, R.style.customDialogStyle);
        View view = LinearLayout.inflate(this, R.layout.dialog_login, null);
        mLoginDialog.setContentView(view);
        mLoginDialog.setCanceledOnTouchOutside(false); //强制修改，点空白区域不会消失
        final TextView confirmTv = (TextView) view.findViewById(R.id.confirm_tv);
        TextView cancelTv = (TextView) view.findViewById(R.id.cancel_tv);

//        confirmTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                if (mLoginDialog.isShowing()) {
//                    mLoginDialog.dismiss();
//                }
//            }
//        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginDialog.isShowing()) {
                    mLoginDialog.dismiss();
                }
            }
        });
        mLoginDialog.show();
    }

//    @Override
//    public void notifyUserBean(UserBean userBean) {
//        LoginUtils.setSignIn(this, userBean.userId, userBean.portraitUrl, userBean.name, userBean.vipExpireDay);
//        if (!TextUtils.isEmpty(userBean.getPortraitUrl())) {
//            Picasso.with(MainActivity.this).load(userBean.getPortraitUrl()).transform(new CircleTransform()).into(mMenuHeaderImage);
//        }
//        if (!TextUtils.isEmpty(userBean.getName())) {
//            mNameTv.setText(userBean.getName());
//        }
//    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;

        @Override
        public void onPageSelected(int arg0) {

            Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);
            currIndex = arg0;
            resetText();
            MilierLog.i(TAG, "offset is :" + offset);
            MilierLog.i(TAG, "bmpW is :" + bmpW);

            boolean flag = false;

            mTvs[currIndex].setTextColor(flag ? getResources().getColor(R.color.yellow) : getResources().getColor(R.color.pink));

            animation.setFillAfter(true);// True
            animation.setDuration(200);
            mCursorIv.startAnimation(animation);

            // 页面切换的话，tablayout回到原位
            MilierLog.i(TAG, "scrollY is :" + mTitleLayout.getScrollY());
//            mTitleLayout.scrollBy(0, -mTitleLayout.getScrollY());

            if (!mIsShow) {
                showTitle();
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }

    public void resetText() {
        for (int i = 0; i < mTvs.length; i++) {
            mTvs[i].setTextColor(getResources().getColor(R.color.text_color));
        }
    }

    private void showTitle() {
        mIsShow = true;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTitleLayout, View.TRANSLATION_Y, -mTitleLayout.getMeasuredHeight(), 0);
        animator.setDuration(500);
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginInSubscribe != null) {
            loginInSubscribe.unsubscribe();
        }
        if (loginOutSubscribe != null) {
            loginOutSubscribe.unsubscribe();
        }

        MilierLog.i(TAG,"调用onDestroy");

        if (Constants.EnterTime != -1) {
            int duration = (int) (System.currentTimeMillis() - Constants.EnterTime);
            // AppsFlyer的统计
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put("time",duration);
            AppsFlyerLib.getInstance().trackEvent(MyApplication.getInstance(), Constants.ON_LINE_TIME,eventValue);

            Constants.EnterTime = -1;
        }

        // AppsFlyer的统计
        Map<String, Object> eventLookValue = new HashMap<String, Object>();
        eventLookValue.put("count",Constants.LOOK__ALBUM_COUNT);
        AppsFlyerLib.getInstance().trackEvent(MyApplication.getInstance(), Constants.LOOK_PHOTO_ALBUM_COUNT,eventLookValue);
        Constants.LOOK__ALBUM_COUNT = 0;

    }

    public void getUserBean() {
//        loginInSubscribe = RxBus.getDefault().toObservable(UserBean.class).subscribe(new Action1<UserBean>() {
//            @Override
//            public void call(UserBean userBean) {
//                if (!TextUtils.isEmpty(userBean.getPortraitUrl())) {
//                    Picasso.with(MainActivity.this).load(userBean.getPortraitUrl()).transform(new CircleTransform()).into(mMenuHeaderImage);
//                }
//                if (!TextUtils.isEmpty(userBean.getName())) {
//                    mNameTv.setText(userBean.getName());
//                }
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                boolean flag = false;
//                if (userBean.getVipExpireDay() > 0) {
//                    flag = true;
//                }
//                if(flag == true){
//                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    mViewPager.setLayoutParams(params);
//                    initGoogleAd(mAdViewContainer,false);
//                }else{
//                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    params.bottomMargin = (int) (50* MyApplication.getInstance().density);
//                    mViewPager.setLayoutParams(params);
//                    initGoogleAd(mAdViewContainer,true);
//                }
//                initUserBean(flag);
//            }
//        });

        loginOutSubscribe = RxBus.getDefault().toObservable(LoginOutBean.class).subscribe(new Action1<LoginOutBean>() {
            @Override
            public void call(LoginOutBean loginOutBean) {
                if (TextUtils.equals(loginOutBean.getMsg(), Constants.SIGN_OUT)) {
                    if (loginOutBean.isAutoLogout()) {
                        Toaster.toast(getResources().getString(R.string.login_out));
                    }
                    mMenuHeaderImage.setImageResource(R.drawable.icon_header);
                    mNavigationIv.setImageResource(R.drawable.navigation_left_no);
                    initUserBean(false);

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.bottomMargin = (int) (50* MyApplication.getInstance().density);
                    mViewPager.setLayoutParams(params);
//                    initGoogleAd(mAdViewContainer,true);
                }

            }
        });
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    private boolean isLogin;

    @Override
    protected void onResume() {
        super.onResume();
//        mUserBean = LoginUtils.getUserInfo(this);
//        if (isLogin && mUserBean != null && mUserBean.getVipExpireDay() > 0) {
//            //vip
//            mBenefitLayout.setVisibility(View.GONE);
//
//
//
//        } else {
//            //非 vip
//            boolean isShowBenefit = SPUtils.getBoolean(this, Constants.IS_SHOW_BENEFIT);
//            boolean isShowBenefitFlag = SPUtils.getBoolean(this, Constants.IS_SHOW_BENEFIT_FLAG);
//            if (isShowBenefit && !isShowBenefitFlag) {
//                mBenefitLayout.setVisibility(View.VISIBLE);
//
//            } else {
//                mBenefitLayout.setVisibility(View.GONE);
//            }
//        }


    }
}
