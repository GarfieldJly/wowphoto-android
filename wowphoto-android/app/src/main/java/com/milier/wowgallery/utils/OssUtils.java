//package com.milier.wowgallery.utils;
//
//import android.util.Log;
//
//import com.alibaba.sdk.android.oss.ClientConfiguration;
//import com.alibaba.sdk.android.oss.OSS;
//import com.alibaba.sdk.android.oss.OSSClient;
//import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
//import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
//import com.milier.wowgallery.common.MyApplication;
//import com.milier.wowgallery.config.Constants;
//
//
//public class OssUtils {
//
//    //OSS用于读取图片
//    private static OSS readOss = null;
//    //OSS用于上传图片
//    private static OSS uploadOss = null;
//    //uploadOss同步锁
//    private static Object lock = new Object();
//
//    /**
//     * 获取查看图片权限的OSS
//     *
//     * @return
//     */
//    public static OSS getReadOSS() {
//        if (readOss != null) {
//            return readOss;
//        }
//        //使用自己的获取STSToken的类
//        Log.e("oss null","null getReadOSS");
//        OSSCredentialProvider credentialProvider = new STSGetter();
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        readOss = new OSSClient(MyApplication.getInstance(), Constants.OSS_ENDPOINT, credentialProvider, conf);
//        return readOss;
//    }
//
//    public static OSS getInstance() {
//        if (readOss != null) {
//            return readOss;
//        } else {
//            Log.e("oss null","null");
//            return getReadOSS();
//        }
//    }
//
//    /**
//     * 获取上传图片权限的OSS
//     * 需要异步调用
//     *
//     * @return
//     */
//    public static UploadOssBean getUploadOSS() {
//        UploadOssBean uploadOssBean = new UploadOssBean();
//        STSTokenBean stsTokenBean = UploadSTSGetter.getSTSTokenBean();
//        if (stsTokenBean == null) {
//            return null;
//        }
//        synchronized (lock) {
//            if (uploadOss != null) {
//                uploadOss.updateCredentialProvider(getToken(stsTokenBean));
//            } else {
//                //使用自己的获取STSToken的类
//                ClientConfiguration conf = new ClientConfiguration();
//                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//                conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//                uploadOss = new OSSClient(MyApplication.getInstance(), Constants.OSS_ENDPOINT, getToken(stsTokenBean), conf);
//            }
//            uploadOssBean.setObjectName(stsTokenBean.getObjectName());
//            uploadOssBean.setOss(uploadOss);
//        }
//        return uploadOssBean;
//    }
//
//    public static UploadOssBean getUploadOSS(STSTokenBean stsTokenBean) {
//        UploadOssBean uploadOssBean = new UploadOssBean();
//        synchronized (lock) {
//            if (uploadOss != null) {
//                uploadOss.updateCredentialProvider(getToken(stsTokenBean));
//            } else {
//                //使用自己的获取STSToken的类
//                ClientConfiguration conf = new ClientConfiguration();
//                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//                conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//                uploadOss = new OSSClient(MyApplication.getInstance(), Constants.OSS_ENDPOINT, getToken(stsTokenBean), conf);
//            }
//            uploadOssBean.setObjectName(stsTokenBean.getObjectName());
//            uploadOssBean.setOss(uploadOss);
//        }
//        return uploadOssBean;
//    }
//
//    /**
//     * 更新到无效的OSS
//     */
//    public static void disableUploadOSS() {
//        MilierLog.i("OssUtils", "====disableUploadOSS");
//        synchronized (lock) {
//            if (uploadOss != null) {
//                uploadOss.updateCredentialProvider(getDisableToken());
//            }
//        }
//    }
//
//    private static OSSStsTokenCredentialProvider getToken(STSTokenBean stsTokenBean) {
//        return new OSSStsTokenCredentialProvider(stsTokenBean.getAccessKeyId(), stsTokenBean.getAccessKeySecret(), stsTokenBean.getSecurityToken());
//    }
//
//    private static OSSStsTokenCredentialProvider getDisableToken() {
//        return new OSSStsTokenCredentialProvider("disable", "disable", "disable");
//    }
//
//}
