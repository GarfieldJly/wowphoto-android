package com.milier.wowgallery.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.milier.wowgallery.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jly on 2016/12/21.
 */
public class BitmapUtils {

//    public static void saveFromInternet(Context context, InputStream inputStream,String path) {
//        File rootFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separatorChar + context.getString(R.string.app_name));
//        if (!rootFile.exists()) {
//            rootFile.mkdir();
//        }
//
//        Log.i("info", "info Exists:" + rootFile.exists() + "   " + rootFile.getAbsolutePath() + "  " + File.pathSeparatorChar);
//        String type = getPicType(path);
//        File targetFile = new File(rootFile, System.currentTimeMillis() + type);
//
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inputStream.read(buffer)) != -1) {
//                // 处理下载的数据
//                fileOutputStream.write(buffer, 0, len);
//            }
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    targetFile.getAbsolutePath(), targetFile.getName(), null);
//
//            // 最后通知图库更新
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + targetFile.getAbsolutePath())));
//            Log.i("info", "保存成功");
//            inputStream.close();
//            fileOutputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void saveFromInternet(Context context, InputStream inputStream, String format, Handler handler) {
        File rootFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separatorChar + context.getString(R.string.app_name));
        if (!rootFile.exists()) {
            rootFile.mkdir();
        }

        Log.i("info", "info Exists:" + rootFile.exists() + "   " + rootFile.getAbsolutePath() + "  " + File.pathSeparatorChar);
        File targetFile = new File(rootFile, System.currentTimeMillis() + "." + format);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                // 处理下载的数据
                fileOutputStream.write(buffer, 0, len);
            }
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    targetFile.getAbsolutePath(), targetFile.getName(), null);

            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + targetFile.getAbsolutePath())));
//            MilierToast.toast(context.getString(R.string.alreadySave) + rootFile.getAbsolutePath());
//            Toaster.toast(context.getString(R.string.alreadySave) + rootFile.getAbsolutePath());
            Log.i("info", "保存成功");
            handler.sendEmptyMessage(0);
            inputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getPicType(String picPath) {
        return picPath.substring(picPath.lastIndexOf(".") + 1);
    }

    public static String replacePath(String oldPath) {
        //正式的
        return oldPath.replaceFirst("wowphoto.oss-ap-southeast-1.aliyuncs.com", "pic.hishendeng.com");
        //测试的
//        return oldPath.replaceFirst("oss", "img");
    }

}
