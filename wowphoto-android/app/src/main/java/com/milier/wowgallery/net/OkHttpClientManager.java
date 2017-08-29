package com.milier.wowgallery.net;

import android.content.Context;

import com.milier.wowgallery.config.NetworkConfig;
import com.milier.wowgallery.utils.MilierLog;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by guofe on 2016/1/19.
 */
public class OkHttpClientManager {
    /**
     * Picasso全局下载器
     */
    private static com.squareup.okhttp.OkHttpClient picassoDownloader;
    /**
     * http
     */
    private static OkHttpClient okHttpClient;
    /**
     * https
     */
    private static OkHttpClient sslOkHttpClient;

    public static void init(Context context) {
        // Picasso下载器初始化
        // TODO: 2016/12/16 如果不用picasso，需要删除
        picassoDownloader = new com.squareup.okhttp.OkHttpClient();

        // 缓存大小
        int bitmapCacheSize = 20 * 1024 * 1024;
        com.squareup.okhttp.Cache bitmapCache = new com.squareup.okhttp.Cache(context.getCacheDir(), bitmapCacheSize);
        picassoDownloader.setCache(bitmapCache);

        // 初始化http
        int cacheSize = 5 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        okhttp3.OkHttpClient.Builder okHttpClientBuilder = new okhttp3.OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new RequestInterceptor(context))
                .retryOnConnectionFailure(true)
//                .retryOnConnectionFailure(false) //请求失败不重试
                .readTimeout(30, TimeUnit.SECONDS);

        okHttpClient = okHttpClientBuilder.build();

        try {
            InputStream is = context.getAssets().open(NetworkConfig.cerFilename);
            setCertificates(is);
        } catch (IOException e) {
            MilierLog.t(OkHttpClientManager.class.getSimpleName(), e);
        }
    }

    public static com.squareup.okhttp.OkHttpClient getPicassoDownloader() {
        return picassoDownloader;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static OkHttpClient getSslOkHttpClient() {
        return sslOkHttpClient;
    }

    private static void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    MilierLog.t(OkHttpClientManager.class.getSimpleName(), e);
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );

            OkHttpClient.Builder builder = okHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory());

            //  https 注释掉
//            if (MilierConfigs.DEBUG) {
            builder.hostnameVerifier(new NullHostNameVerifier());
//            }
            // http 需要拿掉注释
            //            if (MilierConfigs.DEBUG) {
//            builder.hostnameVerifier(new NullHostNameVerifier());
//            }
            sslOkHttpClient = builder.build();

        } catch (Exception e) {
            MilierLog.t(OkHttpClientManager.class.getSimpleName(), e);
        }

    }
}
