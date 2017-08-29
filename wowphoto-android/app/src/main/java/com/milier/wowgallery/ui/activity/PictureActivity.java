package com.milier.wowgallery.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.milier.wowgallery.R;
import com.milier.wowgallery.utils.MilierToast;
import com.milier.wowgallery.weight.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jly on 2017/8/29.
 */

public class PictureActivity extends Activity {
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.save_iv)
    ImageView mSaveIv;

    private String mImageUrl;

    public static void launchActivity(Context context, String imgUrl) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra("imageUrl", imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mImageUrl = getIntent().getStringExtra("imageUrl");
        }
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        init();
        initEvent();
    }

    private void init() {
        mPhotoView.enable();
        mPhotoView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 子线程获得图片路径
                final String imagePath = getImagePath(mImageUrl);
                // 主线程更新
                PictureActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imagePath != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                            if (bitmap != null) {
                                mPhotoView.setImageBitmap(bitmap);
//                                saveImageToGallery(ViewBigImageActivity.this, bitmap);
//                                ToastUtil.showToast("已保存至"+ Environment.getExternalStorageDirectory().getAbsolutePath()+"/云阅相册");
//                                            Toast.makeText(ViewBigImageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    private void initEvent() {
        mSaveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MilierToast.toast("开始下载图片");
                final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 子线程获得图片路径
                        final String imagePath = getImagePath(mImageUrl);
                        // 主线程更新
                        PictureActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (imagePath != null) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                                    if (bitmap != null) {
                                        saveImageToGallery(PictureActivity.this, bitmap);
                                        MilierToast.toast("已保存至" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/wowphoto");
//                                            Toast.makeText(ViewBigImageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(this)
                .load(imgUrl)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    @OnClick(R.id.back_iv)
    public void onClick() {
        finish();
    }


    /**
     * 保存图片至相册
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "wowphoto");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));
    }
}
