//package com.milier.wowgallery.asynctask;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.text.TextUtils;
//import android.widget.ImageView;
//
//import com.alibaba.sdk.android.oss.ClientException;
//import com.milier.wowgallery.R;
//import com.milier.wowgallery.bean.LocalPictureBean;
//import com.milier.wowgallery.common.MyApplication;
//import com.milier.wowgallery.config.Constants;
//import com.milier.wowgallery.utils.BitmapUtils;
//import com.milier.wowgallery.utils.MilierLog;
//import com.milier.wowgallery.utils.OssUtils;
//import com.squareup.picasso.Picasso;
//
//
///**
// * Created by zx on 2016/9/24 0024.
// */
//public class ListPictureCircleAsync extends AsyncTask<String, Void, String> {
//    private final static String TAG = ListPictureCircleAsync.class.getSimpleName();
//    private Context context;
//
//    private ImageView imageview;
//
//    private String pictureName;
//
//    private String fileName;
//
//    private int width,height;
//
//    public ListPictureCircleAsync(Context context, ImageView imageView, int width, int height) {
//        this.context = context;
//        this.imageview = imageView;
//        this.width = width;
//        this.height = height;
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        String path = null;
//        this.pictureName = params[0];
//        try {
//            // String imageParam = "@"+height+"h_"+width+"w_1e_1c-0ci";
//            path = OssUtils.getInstance().presignConstrainedObjectURL(Constants.OSS_BUCKET, pictureName + "@4096-1ci", 600000 * 60);
//
//            MilierLog.e("List", "circle get path ===" + path);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        if (!TextUtils.isEmpty(path)) {
//            path = BitmapUtils.replacePath(path);
//            LocalPictureBean localPictureBean = new LocalPictureBean(pictureName, path,width,height);
//            MyApplication.getHeadMap().put(pictureName, localPictureBean);
//        }
//        return path;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        if (!TextUtils.isEmpty(s)) {
////            MilierLog.i(TAG,"头像地址是 : "+s+";;width is :"+width+";;;height is :"+height);
//
////            Picasso.with(context).load(s)
////                    .transform(new CircleTransform())
////                    .placeholder(R.drawable.default_loading)
////                    .error(R.drawable.default_loading)
////                    .resize(width, height)
////                    .into(imageview);
//
//            Picasso.with(context)
//                    .load(s)
//                    .placeholder(R.drawable.icon_header)
//                    .error(R.drawable.icon_header)
//                    .resize(width, height)
////                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
////                    .transform(new CircleTransform())
//                    .into(imageview);
//        }
//
//    }
//}