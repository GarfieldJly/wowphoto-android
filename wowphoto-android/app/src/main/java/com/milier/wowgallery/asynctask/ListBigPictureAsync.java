/*
package com.milier.wowgallery.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientException;
import com.milier.wowgallery.R;
import com.milier.wowgallery.bean.LocalPictureBean;
import com.milier.wowgallery.common.MyApplication;
import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.utils.BitmapUtils;
import com.milier.wowgallery.utils.DialogTools;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.RxBus;
import com.squareup.picasso.Picasso;

*/
/**
 * Created by zx on 2016/9/24 0024.
 *//*

public class ListBigPictureAsync extends AsyncTask<String, Void, String> {
    private final static String TAG = ListBigPictureAsync.class.getSimpleName();
    private Context context;

    private ImageView imageview;

    private String pictureName;

    private int position;

    private int width,height,rotate; //是否旋转,0:否1:是

    public ListBigPictureAsync(Context context, ImageView imageView, int width, int height,int position,int rotate) {
        this.context = context;
        this.imageview = imageView;
        this.width = width;
        this.height = height;
        this.position = position;
        this.rotate = rotate;
    }

    @Override
    protected String doInBackground(String... params) {
        String path = null;
        this.pictureName = params[0];
        try {
            if(params.length > 1 && !TextUtils.isEmpty(params[1]) && TextUtils.equals(Constants.BLUR,params[1])){
                String imageParam = "";
                if(rotate == 1){
//                    imageParam = pictureName + "@1pr_40-20bl_90r";
                    imageParam = pictureName + "@"+width+"h_"+height+"w_1pr_40-20bl_90r";
                }else{
                    imageParam = pictureName + "@"+height+"h_"+width+"w_1pr_40-20bl";
                }
                path = OssUtils.getInstance().presignConstrainedObjectURL(Constants.OSS_BUCKET,imageParam, 600000 * 60);
            }else{
//                path = OssUtils.getInstance().presignConstrainedObjectURL(Constants.OSS_BUCKET, pictureName + "@1pr", 600000 * 60);
                String imageParam = "";
//                String imageParam = "@"+width+"w_1e_1c";
                if(rotate == 1){
                    imageParam = pictureName+"@"+width+"h_"+height+"w_1e_1c_90r";
                }else{
                    imageParam = pictureName+"@"+height+"h_"+width+"w_1e_1c";
                }

                path = OssUtils.getInstance().presignConstrainedObjectURL(Constants.OSS_BUCKET, imageParam, 60 * 60 * 1000 * 168);
            }

            MilierLog.i("List", "get path ===" + path);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(path) ) {
            path = BitmapUtils.replacePath(path);
            if(params.length == 1){
                LocalPictureBean localPictureBean = new LocalPictureBean(pictureName, path,width,height);
                MyApplication.getPicMap().put(pictureName, localPictureBean);
            }
        }
        return path;
    }

    @Override
    protected void onPostExecute(String s) {
        if (!TextUtils.isEmpty(s)) {
//            MilierLog.i(TAG,"图片地址是 : "+s);

            Picasso.with(context)
                    .load(s)
                    .placeholder(R.drawable.icon_default_big_picture)
                    .error(R.drawable.icon_default_big_picture)
                    .resize(width, height)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .centerCrop()
                    .into(imageview);

            RxBus.getDefault().post(new LocalPictureBean(s, position));

            DialogTools.closeWaittingDialog();
//            Picasso.with(MainActivity.this).load(userBean.getPortraitUrl()).transform(new CircleTransform()).into(mMenuHeaderImage);
        }

    }
}*/
