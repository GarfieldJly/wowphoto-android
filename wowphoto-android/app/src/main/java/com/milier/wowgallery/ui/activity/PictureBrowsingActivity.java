/*
package com.milier.wowgallery.ui.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.appsflyer.AppsFlyerLib;
import com.jakewharton.rxbinding.view.RxView;
import com.milier.wowgallery.R;
import com.milier.wowgallery.asynctask.ListBigPictureAsync;
import com.milier.wowgallery.bean.LocalPictureBean;
import com.milier.wowgallery.common.MyApplication;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.presenter.PicturePresenter;
import com.milier.wowgallery.utils.BitmapUtils;
import com.milier.wowgallery.utils.DialogTools;
import com.milier.wowgallery.utils.LoginUtils;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.MilierToast;
import com.milier.wowgallery.utils.OssUtils;
import com.milier.wowgallery.utils.RxBus;
import com.milier.wowgallery.utils.SPUtils;
import com.milier.wowgallery.utils.SystemBarTintHelper;
import com.milier.wowgallery.utils.Toaster;
import com.milier.wowgallery.view.PictureView;
import com.milier.wowgallery.weight.ImageViewPager;
import com.milier.wowgallery.weight.PhotoView;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.Orientation;
import com.vungle.publisher.VunglePub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

*
 * 查看大图



public class PictureBrowsingActivity extends BaseActivity implements View.OnClickListener, PictureView {
    private final static String TAG = PictureBrowsingActivity.class.getSimpleName();
    private final static String ATLASID = "ATLASID";
    private final static String TOTAL = "TOTAL";
    private final static String NAME = "NAME";

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.currentTv)
    TextView mCurrentTv;
    @BindView(R.id.totalTv)
    TextView mTotalTv;
    @BindView(R.id.save_iv)
    ImageView mSaveIv;
    @BindView(R.id.vp)
    ImageViewPager mVp;
    @BindView(R.id.title_rl)
    public RelativeLayout mTitleRl;
    @BindView(R.id.nickname_tv)
    TextView mNicknameTv;
    @BindView(R.id.adViewContainer)
    RelativeLayout mAdViewContainer;

    List<ImageView> noVipListView;
    List<PhotoView> vipListView;
    List<ImageBean> listUrl;

    private long atlasId;
    private int current, titleHeight, total;
    private String mName;
    private PicturePresenter mPicturePresenter;
    private Dialog mVipDialog;
    private boolean isLogin, isShow;
    private UserBean mUserBean;
    private Map<String, LocalPictureBean> picMap;
    private Subscription urlSubscribe;
    // 获取 VunglePub 实例
    final VunglePub vunglePub = VunglePub.getInstance();

    final UnityAdsListener unityAdsListener = new UnityAdsListener();
    private boolean isUnityPrepare = true;
    private boolean mIsAd;

    public static void launch(Context context, long atlasId, int total, String name) {
        Intent intent = new Intent(context, PictureBrowsingActivity.class);
        intent.putExtra(ATLASID, atlasId);
        intent.putExtra(TOTAL, total);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = LoginUtils.isAppHasSignIn(this);
        mUserBean = LoginUtils.getUserInfo(this);
        MilierLog.e(TAG, "登录 ,isLogin : " + isLogin);
        if (isLogin && mUserBean != null && mUserBean.getVipExpireDay() > 0) {
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_picturebrowse);
        } else {
            setContentView(R.layout.activity_picturebrowse_no);
            // 状态栏与Toolbar同色
            new SystemBarTintHelper().onCreate(this, true, R.color.statusbar_color);
            boolean isShowBenefit = SPUtils.getBoolean(this,Constants.IS_SHOW_BENEFIT);
            if(!isShowBenefit){
                SPUtils.putBoolean(this,Constants.IS_SHOW_BENEFIT,true);
            }
        }
        ButterKnife.bind(this);

        if (getIntent() != null) {
            atlasId = getIntent().getLongExtra(ATLASID, 0);
            total = getIntent().getIntExtra(TOTAL, 0);
            mName = getIntent().getStringExtra(NAME);
        }

        initData();
        initEvent();

        if(Constants.LOOK_ALBUM_TIME == -1){
            Constants.LOOK_ALBUM_TIME = System.currentTimeMillis();
        }

    }

    private void initData() {
        vipListView = new ArrayList<PhotoView>();
        noVipListView = new ArrayList<ImageView>();
        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_vip_image,null);
            vipListView.add((PhotoView) view);
        }
        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_no_vip_image,null);
            noVipListView.add((ImageView) view);
        }
        if(!TextUtils.isEmpty(mName)){
            mNicknameTv.setText(mName);
        }

//        mVp.setAdapter(new MyVipPageAdapter());
//        mVp.setCurrentItem(0, false);

        mPicturePresenter = new PicturePresenterImpl(this, this);
        mPicturePresenter.getImageList(atlasId);

        if (isLogin && mUserBean != null && mUserBean.getVipExpireDay() > 0) {
            mSaveIv.setVisibility(View.VISIBLE);
        } else {
            mSaveIv.setVisibility(View.GONE);
        }

        isShow = true;
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTitleRl.measure(width, height);
        titleHeight = mTitleRl.getMeasuredHeight();
        mTotalTv.setText("/" + total);
        picMap = MyApplication.getPicMap();
        getUrl();

        // 初始化 Publisher SDK
        vunglePub.init(this, Constants.Vungle_App_ID);
        vunglePub.setEventListeners(vungleListener);

        UnityAds.setListener(unityAdsListener);
        UnityAds.initialize(PictureBrowsingActivity.this, Constants.UNITY_AD_ID, unityAdsListener,false);
        isUnityPrepare =  true;
    }


    private final EventListener vungleListener = new EventListener(){

        @Deprecated
        @Override
        public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {
            // 此方法已弃用，将被删除。请勿使用。
            // 请使用 onAdEnd。
        }

        @Override
        public void onAdStart() {
            // 在播放广告前调用
        }

        @Override
        public void onAdEnd(boolean wasSuccessfulView, boolean wasCallToActionClicked) {
            // 当用户离开广告，控制转回至您的应用程序时调用
            // 如果 wasSuccessfulView 为 true，表示用户观看了广告，应获得奖励
            //（如果是奖励广告）。
// 如果 wasCallToActionClicked 为 true，表示用户点击了广告中的
            // 行动号召按钮。
            if(wasSuccessfulView){
                mPicturePresenter.getImageListAfterAd(atlasId);
            }

        }

        @Override
        public void onAdPlayableChanged(boolean isAdPlayable) {
            // 当可播放状态发生变化时调用。如果 isAdPlayable 为 true，您现在可以
            // 播放广告。
            // 如果为 false，您还不能播放广告。
        }

        @Override
        public void onAdUnavailable(String reason) {
            // 当调用 VunglePub.playAd() 时调用，但没有可以播放的广告
        }

    };

    @Override
    public void getImages(List<ImageBean> urls,boolean isAd) {
        listUrl = urls;
        MilierLog.i(TAG, "urls  is : " + urls);
        MilierLog.i(TAG, "urls size is : " + urls.size());

        mIsAd = isAd;
        if (isLogin && mUserBean != null && mUserBean.getVipExpireDay() > 0) {
            mVp.setAdapter(new MyVipPageAdapter());
        }else{

            mVp.setAdapter(new MyNoVipPageAdapter());
            if(isAd){
                mVp.setCurrentItem(1);
            }
        }
    }

    class MyVipPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return listUrl.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public Object instantiateItem(View v, int position) {
            int index = position % 4;
            ((ImageViewPager) v).addView(vipListView.get(index));
            PhotoView view = vipListView.get(index);
            if (view instanceof PhotoView) {
                try {
                    view.enable();
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    int density = Math.round(MyApplication.getInstance().density);
                    int picHeight = MyApplication.getInstance().screenHeight - 75 * density;

                    if (picMap.get(listUrl.get(position).image_name) != null) {
                        DialogTools.closeWaittingDialog();
                        Picasso.with(PictureBrowsingActivity.this)
                                .load(picMap.get(listUrl.get(position).image_name).getPicUrl())
                                .placeholder(R.drawable.icon_default_big_picture)
                                .error(R.drawable.icon_default_big_picture)
                                .resize(MyApplication.getInstance().screenWidth, MyApplication.getInstance().screenHeight)
                                .config(Bitmap.Config.RGB_565)
                                .centerCrop()
                                .into(view);

                        MilierLog.i("3333", picMap.get(listUrl.get(position).image_name).getPicUrl());
//                            MilierLog.i("4444",listUrl.get(position).url);
                        listUrl.get(position).url = picMap.get(listUrl.get(position).image_name).getPicUrl();
                    } else {
                        new ListBigPictureAsync(PictureBrowsingActivity.this, view, MyApplication.getInstance().screenWidth, MyApplication.getInstance().screenHeight, position,listUrl.get(position).rotate).execute(listUrl.get(position).image_name);
                    }

                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    Process.killProcess(Process.myPid()); // 获取PID
                    System.exit(0);
                }


                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (LoginUtils.isAppHasSignIn(PictureBrowsingActivity.this)) {
                            UserBean userBean = LoginUtils.getUserInfo(PictureBrowsingActivity.this);
                            if(userBean.getVipExpireDay() > 0){
                                if (isShow) {
                                    hide();
                                    translateHide();
                                } else {
                                    show();
                                    translateShow();
                                }
                                isShow = !isShow;
                            }
                        }
                    }
                });
            }
            return vipListView.get(index);
        }

        @Override
        public void destroyItem(View v, int position, Object arg2) {
            int index = position % 4;
            ((ImageViewPager) v).removeView(vipListView.get(index));
        }
    }


    class MyNoVipPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return listUrl.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public Object instantiateItem(View v, int position) {
            int index = position % 4;
            ((ImageViewPager) v).addView(noVipListView.get(index));
            ImageView view = noVipListView.get(index);
            if (view instanceof ImageView) {
                try {
                    int density = Math.round(MyApplication.getInstance().density);
                    int picHeight = MyApplication.getInstance().screenHeight - 75 * density;

                    if (position == (listUrl.size() - 1) && listUrl.size() > 1 && listUrl.size() == 2 && !mIsAd) {
                        new ListBigPictureAsync(PictureBrowsingActivity.this, view, MyApplication.getInstance().screenWidth, picHeight, position,listUrl.get(position).rotate).execute(listUrl.get(position).image_name, Constants.BLUR);
                    } else {
                        if (picMap.get(listUrl.get(position).image_name) != null) {
                            DialogTools.closeWaittingDialog();
                            Picasso.with(PictureBrowsingActivity.this)
                                    .load(picMap.get(listUrl.get(position).image_name).getPicUrl())
                                    .placeholder(R.drawable.icon_default_big_picture)
                                    .error(R.drawable.icon_default_big_picture)
                                    .resize(MyApplication.getInstance().screenWidth, picHeight)
                                    .config(Bitmap.Config.RGB_565)
                                    .into(view);
                        } else {

                            new ListBigPictureAsync(PictureBrowsingActivity.this, view, MyApplication.getInstance().screenWidth, picHeight, position,listUrl.get(position).rotate).execute(listUrl.get(position).image_name);
                        }
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    Process.killProcess(Process.myPid()); // 获取PID
                    System.exit(0);
                }

            }
            return noVipListView.get(index);
        }

        @Override
        public void destroyItem(View v, int position, Object arg2) {
            int index = position % 4;
            ((ImageViewPager) v).removeView(noVipListView.get(index));
        }
    }

    private void getUrl() {
        DialogTools.showWaittingDialog(this);
        urlSubscribe = RxBus.getDefault().toObservable(LocalPictureBean.class).subscribe(new Action1<LocalPictureBean>() {
            @Override
            public void call(LocalPictureBean localPictureBean) {
                if (localPictureBean != null) {
                    listUrl.get(localPictureBean.getOrder()).url = localPictureBean.getPicUrl();
                }
//                DialogTools.closeWaittingDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogTools.closeWaittingDialog();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (urlSubscribe != null) {
            urlSubscribe.unsubscribe();
            urlSubscribe = null;
        }
        vunglePub.removeEventListeners(vungleListener);

        if (Constants.LOOK_ALBUM_TIME != -1) {
            int duration = (int) (System.currentTimeMillis() - Constants.LOOK_ALBUM_TIME);
            // AppsFlyer的统计
            Map<String, Object> eventValue = new HashMap<String, Object>();
            eventValue.put("time",duration);
            eventValue.put("album_id",atlasId);
//            AppsFlyerLib.getInstance().trackEvent(MyApplication.getInstance(),Constants.LOOK_PHOTO_ALBUM_TIME ,eventValue);
            AppsFlyerLib.getInstance().trackEvent(MyApplication.getInstance(),atlasId+"" ,eventValue);

            Constants.LOOK_ALBUM_TIME = -1;
        }
    }

    private void initEvent() {
        mBackIv.setOnClickListener(this);
//        mSaveIv.setOnClickListener(this);
        RxView.clicks(mSaveIv).throttleFirst(3, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                MobclickAgent.onEvent(PictureBrowsingActivity.this, Constants.image_download_click);
                String objectName = listUrl.get(current).image_name;
                String format = listUrl.get(current).format;
                String url = listUrl.get(current).url;
                MilierLog.i("1111111", "url = " + url);
                saveBitmap(url, objectName, format, mSaveHandler);

            }
        });

        mVp.addOnPageChangeListener(new ImageViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                MilierLog.i(TAG,"arg0 is :"+arg0);
                current = arg0;
                mCurrentTv.setText(arg0 + 1 + "");
                if (arg0 == listUrl.size() - 1 && listUrl.size() > 1 && listUrl.size() == 2 && !mIsAd) {
                    if (LoginUtils.isAppHasSignIn(PictureBrowsingActivity.this)) {
                        if (LoginUtils.getUserInfo(PictureBrowsingActivity.this).getVipExpireDay() <= 0 ) {
                            showVipDialog();
                        }
                    } else {
                        showVipDialog();
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

*
     * 成为 VIP 的Dialog


    protected void showVipDialog() {
        mVipDialog = new Dialog(this, R.style.customDialogStyle);
        View view = LinearLayout.inflate(this, R.layout.dialog_vip, null);
        mVipDialog.setContentView(view);
        mVipDialog.setCanceledOnTouchOutside(false);

        TextView cancelTv = (TextView) view.findViewById(R.id.watch_ad_tv);
        TextView confirmTv = (TextView) view.findViewById(R.id.confirm_tv);
        cancelTv.setOnClickListener(this);
        confirmTv.setOnClickListener(this);
        mVipDialog.show();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_iv:
//                Toaster.toast(getString(R.string.begin_save) + Environment.getExternalStorageDirectory().getPath() + File.separatorChar + getString(R.string.app_name));
//                MobclickAgent.onEvent(PictureBrowsingActivity.this, Constants.image_download_click);
//                String objectName = listUrl.get(current).image_name;
//                String format = listUrl.get(current).format;
//                String url = listUrl.get(current).url;
//                MilierLog.i("1111111", "url = " + url);
//                saveBitmap(url, objectName, format, mSaveHandler);

//                saveImage(objectName, format,mSaveHandler);
                break;
            case R.id.watch_ad_tv:
                boolean isPrepare = false;
                if(UnityAds.isReady()){
                    UnityAds.show(PictureBrowsingActivity.this);
                    isPrepare = true;
                }else{
                    if(vunglePub.isAdPlayable()){
//                    vunglePub.playAd();
                        playAdOptions();
                        isPrepare = true;
                    }
                }

                if(!isPrepare ){
                    MilierToast.toast(getString(R.string.ad_preparation));
                }

                if (mVipDialog.isShowing()) {
                    mVipDialog.dismiss();
                }
                break;
            case R.id.confirm_tv:
                if (mVipDialog.isShowing()) {
                    mVipDialog.dismiss();
                }
                Intent intent = new Intent(PictureBrowsingActivity.this, AccountActivity.class);
                intent.putExtra(ATLASID,atlasId);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void saveBitmap(final String url, final String objectName, final String formatType, final Handler handler) {
        Schedulers.newThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                try {
                    //同步加载一张图片,注意只能在子线程中调用并且Bitmap不会被缓存到内存里
                    Bitmap bitmap = Picasso.with(PictureBrowsingActivity.this).load(url).get();
                    Log.e("lllllllll", bitmap + "");
                    if (bitmap != null) {
                        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                        int quality = 100;
                        File rootFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separatorChar + getString(R.string.app_name));
                        if (!rootFile.exists()) {
                            rootFile.mkdir();
                        }

                        Log.i("info", "info Exists:" + rootFile.exists() + "   " + rootFile.getAbsolutePath() + "  " + File.pathSeparatorChar);
                        File targetFile = new File(rootFile, System.currentTimeMillis() + "." + formatType);
                        OutputStream stream = new FileOutputStream(targetFile);
                        boolean flag = bitmap.compress(format, quality, stream);
                        if (flag) {
                            MilierLog.i("22222", "保存成功*******");
                            MediaStore.Images.Media.insertImage(getContentResolver(),
                                    targetFile.getAbsolutePath(), targetFile.getName(), null);

                            // 最后通知图库更新
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + targetFile.getAbsolutePath())));

                            handler.sendEmptyMessage(0);
                        } else {
                            saveImage(objectName, formatType, handler);
                        }
                    } else {
                        saveImage(objectName, formatType, handler);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }catch (OutOfMemoryError e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }catch (Throwable throwable){

                }

            }
        });
    }


    public void saveImage(final String objectName, final String format, final Handler handler) {

        GetObjectRequest get = new GetObjectRequest(Constants.OSS_BUCKET, objectName);
        OSS oss = OssUtils.getReadOSS();
//        get.setxOssProcess("image/resize,m_fixed,w_1080,h_1920");
        oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();
                BitmapUtils.saveFromInternet(PictureBrowsingActivity.this, inputStream, format, handler);
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                MilierToast.showToast(MyApplication.getInstance(), "下载失败");
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        });

    }

    private void show() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTitleRl, View.TRANSLATION_Y, -titleHeight, 0);
        animator.setDuration(500);
        animator.start();
    }

    private void hide() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTitleRl, View.TRANSLATION_Y, 0, -titleHeight);
        animator.setDuration(500);
        animator.start();
    }

    private void translateShow() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mSaveIv, "alpha", 1f);
        anim.setDuration(500);// 动画持续时间
        anim.start();

//        mSaveIv.animate().alpha(0f);
    }

    private void translateHide() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mSaveIv, "alpha", 0f);
        anim.setDuration(500);// 动画持续时间
        anim.start();
//        mSaveIv.animate().alpha(1f);
    }

    Handler mSaveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + getString(R.string.app_name);
                Toaster.toast(getString(R.string.already_save) + path);
            } else if (msg.what == 1) {
                Toaster.toast(getString(R.string.save_failed));
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = LoginUtils.isAppHasSignIn(this);
        mUserBean = LoginUtils.getUserInfo(this);
        if (isLogin && mUserBean != null && mUserBean.getVipExpireDay() > 0) {
            //vip
            initGoogleAd(mAdViewContainer,false);
        } else {
            //非 vip
            initGoogleAd(mAdViewContainer,true);
        }
        vunglePub.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vunglePub.onPause();
    }

    private void playAdOptions() {
        // create a new AdConfig object
        final AdConfig overrideConfig = new AdConfig();
        overrideConfig.setIncentivized(true);

        // set any configuration options you like.
        overrideConfig.setOrientation(Orientation.matchVideo);
        overrideConfig.setSoundEnabled(true);
        overrideConfig.setBackButtonImmediatelyEnabled(false);
        overrideConfig.setPlacement("StoreFront");
        overrideConfig.setIncentivizedCancelDialogBodyText(getString(R.string.ad_dialog_body));
        //overrideConfig.setExtra1("LaunchedFromNotification");

        // the overrideConfig object will only affect this ad play.
        vunglePub.playAd(overrideConfig);
    }


    private class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(final String zoneId) {
//            if(isUnityPrepare){
//                UnityAds.show(PictureBrowsingActivity.this, zoneId);
//                isUnityPrepare = false;
//            }else{
//
//            }
            MilierLog.i(TAG,"ad 准备好");


        }

        @Override
        public void onUnityAdsStart(String zoneId) {
        }

        @Override
        public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {
            MilierLog.i(TAG,"UnityAds result is :"+result);
            if(result == UnityAds.FinishState.COMPLETED){
                mPicturePresenter.getImageListAfterAd(atlasId);
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

        }

    }

}
*/
