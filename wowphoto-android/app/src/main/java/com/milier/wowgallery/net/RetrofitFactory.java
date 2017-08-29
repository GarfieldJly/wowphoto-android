package com.milier.wowgallery.net;

import android.content.Context;

import com.milier.wowgallery.config.NetworkConfig;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit service工厂类
 * Created by guofe on 2015/9/11.
 */
public class RetrofitFactory {

    /**
     * 用来生成Restful Service的Retrofit单例
     */
    private static Retrofit retrofitInstance;

    /**
     * https的Retrofit单例
     */
    private static Retrofit httpsRetrofitInstance;

    public static void init(Context context) {
        // 初始化Retrofit
        Moshi moshi = MoshiFactor.create();

        retrofitInstance = new Retrofit.Builder()
                .baseUrl(NetworkConfig.getApiBasePath())
                .client(OkHttpClientManager.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        httpsRetrofitInstance = new Retrofit.Builder()
                .baseUrl(NetworkConfig.getApiHttpsBasePath())
                .client(OkHttpClientManager.getSslOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    /**
     * 用来生成Restful Service的Retrofit单例
     */

    public static  Retrofit init(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取AppService的单例
     *
     * @return
     */
    public static <T> T createRestService(Class<T> service) {
        return httpsRetrofitInstance.create(service);  // https
//        return retrofitInstance.create(service);  // http
    }

    /**
     * 获取https AppService的单例
     * @return
     */
    public static <T> T createHttpsRestService(Class<T> service) {
        return httpsRetrofitInstance.create(service);
    }
}
