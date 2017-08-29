package com.milier.wowgallery.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.milier.wowgallery.R;
import com.milier.wowgallery.bean.LocalPictureBean;
import com.milier.wowgallery.bean.WelfareBean;
import com.milier.wowgallery.common.MyApplication;
import com.milier.wowgallery.ui.activity.PictureActivity;
import com.milier.wowgallery.utils.DensityUtils;
import com.milier.wowgallery.utils.SlideInLeftAnimation;
import com.milier.wowgallery.utils.Toaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jly on 2016/12/19.
 */
public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = PictureAdapter.class.getSimpleName();

    private ArrayList<WelfareBean.ResultsBean> mDatas;
    private Context mContext;
    private SlideInLeftAnimation mSlideInLeftAnimation;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private boolean mIsFav;
    private Dialog mLoginDialog, mFavDialog, mUnLikeDialog;
    private final Map<String, LocalPictureBean> picMap;

    private final Map<String, LocalPictureBean> headMap;

    public PictureAdapter(Context context, ArrayList<WelfareBean.ResultsBean> datas, boolean isFav) {
        mContext = context;
        mDatas = datas;
        mSlideInLeftAnimation = new SlideInLeftAnimation();
        this.mIsFav = isFav;
        picMap = MyApplication.getPicMap();
        headMap = MyApplication.getHeadMap();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final WelfareBean.ResultsBean atlasBean = mDatas.get(position);
        final PictureViewHolder pictureViewHolder = (PictureViewHolder) holder;

//        pictureViewHolder.mFavIv.setImageResource(isLogin ? R.drawable.icon_fav : R.drawable.icon_fav_no);
//        if (isLogin) {
//            if (atlasBean.getIsStored() == 0) {//未收藏
//                pictureViewHolder.mFavIv.setImageResource(R.drawable.icon_unlike);
//            } else {//已收藏
//                pictureViewHolder.mFavIv.setImageResource(R.drawable.icon_fav);
//            }
//        } else {
//            if (atlasBean.getIsStored() == 0) {//未收藏
//                pictureViewHolder.mFavIv.setImageResource(R.drawable.icon_unlike_no);
//            } else {
//                pictureViewHolder.mFavIv.setImageResource(R.drawable.icon_fav_no);
//            }
//        }

        int width = MyApplication.getInstance().screenWidth;
//        pictureViewHolder.picIv.setMaxWidth(width);
//        pictureViewHolder.picIv.setMaxHeight(width);

        /**
         * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
         * 如果这样使用，则每个item的左右边距是不一样的，
         * 这样item不能复用，所以下拉刷新成功后显示会闪一下
         * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
         */
        if (position % 2 == 0) {
            DensityUtils.setViewMargin(holder.itemView, false, 12, 6, 12, 0);
        } else {
            DensityUtils.setViewMargin(holder.itemView, false, 6, 12, 12, 0);
        }

        pictureViewHolder.picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PictureActivity.launchActivity(mContext,atlasBean.getUrl());
            }
        });

//        pictureViewHolder.mCountTv.setText(atlasBean.getCount() + "");
//        pictureViewHolder.mFavTv.setText(atlasBean.getLikeCount() + "");
        if(!TextUtils.isEmpty(mDatas.get(position).getWho())){
            pictureViewHolder.mNameTv.setText(mDatas.get(position).getWho());
        }else{
            pictureViewHolder.mNameTv.setText("");
        }

        //设置封面
//        if(!TextUtils.isEmpty(atlasBean.getCover())){
//            if (picMap.get(atlasBean.getCover()) != null)  {
//                Picasso.with(mContext)
//                        .load(mDatas.get(position).getUrl())
//                        .placeholder(R.drawable.icon_default_picture)
//                        .error(R.drawable.icon_default_picture)
////                        .resize(width, width)
////                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        .config(Bitmap.Config.RGB_565)
//                        .into(((PictureViewHolder) holder).picIv);


        Glide.with(mContext).load(mDatas.get(position).getUrl())
                .asBitmap()
                .placeholder(R.drawable.icon_default_picture)
                .error(R.drawable.icon_default_picture)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(((PictureViewHolder) holder).picIv);

//            }else{
//                new ListPictureAsync(mContext, ((PictureViewHolder) holder).picIv, width, width).execute(atlasBean.getCover());
//            }
//        }

//        MilierLog.i(TAG,"atlasBean.getCover() === "+atlasBean.getCover());
//        //设置头像
//        if(atlasBean.getPubUser() != null && !TextUtils.isEmpty(atlasBean.getPubUser().getProfile())){
//            if (headMap.get(atlasBean.getPubUser().getProfile()) != null)  {
//                Picasso.with(mContext)
//                        .load(mDatas.get(position).getUrl())
//                        .placeholder(R.drawable.icon_header)
//                        .error(R.drawable.icon_header)
//                        .resize(32, 32)
////                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        .transform(new CircleTransform())
//                        .into(((PictureViewHolder) holder).mHeaderImage);
//            }else{
//                new ListPictureCircleAsync(mContext, ((PictureViewHolder) holder).mHeaderImage, 32, 32).execute(atlasBean.getPubUser().getProfile());
//            }
//        }else{
//            ((PictureViewHolder) holder).mHeaderImage.setImageResource(R.drawable.icon_header);
//        }
//        //收藏
//        pictureViewHolder.mFavIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImageView iv = (ImageView) view;
//                changeSave(atlasBean, iv, pictureViewHolder.mFavTv);
//            }
//        });
    }


//    public void changeSave(AtlasBean atlasBean, ImageView iv, TextView tv) {
//        if (LoginUtils.isAppHasSignIn(mContext)) {
//            UserBean userBean = LoginUtils.getUserInfo(mContext);
//            if (userBean.vipExpireDay == 0) {
//                showVipDialog();
//            } else {
//                if (atlasBean.getIsStored() == 0) {//未收藏
//                    MobclickAgent.onEvent(mContext, Constants.like_btn_click);
//                    ItemActionModel.pictureAction(atlasBean.getId(), ImageType.STORE);
//                    iv.setImageResource(R.drawable.icon_fav);
//                    atlasBean.setLikeCount(atlasBean.getLikeCount() + 1);
//                    tv.setText(atlasBean.getLikeCount() + "");
//                    atlasBean.setIsStored(1);
//                } else {//收藏
//                    showUnlikeDialog(atlasBean, iv, tv);
//                }
//            }
//        } else {
//            showLoginDialog();
//        }
//    }

    protected void showLoginDialog() {
        mLoginDialog = new Dialog(mContext, R.style.customDialogStyle);
        View view = LinearLayout.inflate(mContext, R.layout.dialog_login, null);
        mLoginDialog.setContentView(view);
        mLoginDialog.setCanceledOnTouchOutside(true); //强制修改，点空白区域不会消失
        final TextView confirmTv = (TextView) view.findViewById(R.id.confirm_tv);
        TextView cancelTv = (TextView) view.findViewById(R.id.cancel_tv);

//        confirmTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, LoginActivity.class));
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

    /**
     * 取消收藏Dialog
     */
//    protected void showUnlikeDialog(final AtlasBean atlasBean, final ImageView iv, final TextView tv) {
//        mUnLikeDialog = new Dialog(mContext, R.style.customDialogStyle);
//        View view = LinearLayout.inflate(mContext, R.layout.dialog_cancel_fav, null);
//        mUnLikeDialog.setContentView(view);
//        mUnLikeDialog.setCanceledOnTouchOutside(true); //强制修改，点空白区域不会消失
//        final TextView confirmTv = (TextView) view.findViewById(R.id.confirm_tv);
//        TextView cancelTv = (TextView) view.findViewById(R.id.cancel_tv);
//
//        confirmTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ItemActionModel.pictureAction(atlasBean.getId(), ImageType.UNSTORE);
//                iv.setImageResource(R.drawable.icon_unlike);
//                atlasBean.setLikeCount(atlasBean.getLikeCount() - 1);
//                if (atlasBean.getLikeCount() >= 0) {
//                    tv.setText(atlasBean.getLikeCount() + "");
//                }
//                atlasBean.setIsStored(0);
//
//                if (mUnLikeDialog.isShowing()) {
//                    mUnLikeDialog.dismiss();
//                }
//
//                if (mIsFav) {
//                    int position = mDatas.indexOf(atlasBean);
//                    mDatas.remove(position);
//                    notifyItemRemoved(position);
//                }
//
//            }
//        });
//        cancelTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mUnLikeDialog.isShowing()) {
//                    mUnLikeDialog.dismiss();
//                }
//            }
//        });
//        mUnLikeDialog.show();
//    }

    /**
     * 成为 VIP 的Dialog
     */
    protected void showVipDialog() {
        mFavDialog = new Dialog(mContext, R.style.customDialogStyle);
        View view = LinearLayout.inflate(mContext, R.layout.dialog_fav, null);
        mFavDialog.setContentView(view);
        mFavDialog.setCanceledOnTouchOutside(false);

        TextView cancelTv = (TextView) view.findViewById(R.id.cancel_tv);
        TextView confirmTv = (TextView) view.findViewById(R.id.confirm_tv);
//        confirmTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, AccountActivity.class));
//                if (mFavDialog.isShowing()) {
//                    mFavDialog.dismiss();
//                }
//            }
//        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFavDialog.isShowing()) {
                    mFavDialog.dismiss();
                }
            }
        });
        mFavDialog.show();
    }

    public void setData(List<WelfareBean.ResultsBean> dd) {
        mDatas.addAll(dd);
        notifyDataSetChanged();
    }

    public ArrayList<WelfareBean.ResultsBean> getDatas() {
        return mDatas;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 返回数据源
     */
    public List<WelfareBean.ResultsBean> getAllData() {
        return mDatas;
    }

    /**
     * 下拉刷新
     */
    public void addHeadData(List<WelfareBean.ResultsBean> headData) {

        if (headData == null) {
            return;
        }
        if (headData.size() == 0) {
            mDatas.clear();
            mDatas.addAll(headData);
            notifyDataSetChanged();
            return;
        }

        mDatas.addAll(headData);
        notifyDataSetChanged();
    }

    /**
     * 上拉加载下一页
     */
    public void addFootData(List<WelfareBean.ResultsBean> footData) {
        if (footData != null && footData.size() > 0) {

            /**数据去重，相同数据不加*/
            for (WelfareBean.ResultsBean oldBean : mDatas) {
                for (int i = 0; i < footData.size(); i++) {
                    /*if (oldBean.getId().equals(footData.get(i).getId())) {
                        footData.remove(footData.get(i));
                    }*/
                }
            }

            /**数据去重后重新判断数量*/
            if (footData.size() > 0) {
                mDatas.addAll(footData);
                notifyDataSetChanged();
            } else {
                Toaster.showToast(mContext, mContext.getString(R.string.now_new));
            }

        } else {
            Toaster.showToast(mContext, mContext.getString(R.string.now_new));
        }
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }


//    @Override
//    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        addAnimation(holder);
//    }

//    private void addAnimation(RecyclerView.ViewHolder holder) {
//        for (Animator anim : mSlideInLeftAnimation.getAnimators(holder.itemView)) {
//            startAnim(anim, holder.getLayoutPosition());
//        }
//    }

//    protected void startAnim(Animator anim, int index) {
//        anim.setDuration(mDuration).start();
//        anim.setInterpolator(mInterpolator);
//    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic_iv)
        ImageView picIv;
        @BindView(R.id.count_tv)
        TextView mCountTv;
        @BindView(R.id.header_image)
        ImageView mHeaderImage;
        @BindView(R.id.name_tv)
        TextView mNameTv;
        @BindView(R.id.fav_iv)
        ImageView mFavIv;
        @BindView(R.id.fav_tv)
        TextView mFavTv;

        public PictureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
