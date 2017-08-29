package com.milier.wowgallery.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.milier.wowgallery.config.Constants;
import com.milier.wowgallery.config.MilierConfig;
import com.milier.wowgallery.utils.MilierLog;
import com.milier.wowgallery.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * api请求的拦截器
 * Created by guofe on 2015/12/25.
 */
public class RequestInterceptor implements Interceptor {

    private final Context context;

    public RequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        // TODO: 2016/12/16 修改无网络拦截位置
        if (!NetWorkUtils.isNetWorkUseful(context)) {
           context.sendBroadcast(new Intent(Constants.NET_UNCONNECTED));
        }

        /** 设备信息*/
        // TODO: 2016/12/16 等服务器接口
//       if (SystemUtils.getDeviceInfo(context) != null) {
//           builder.header(Constants.HEADER_DEVIECE_INFO, SystemUtils.getDeviceInfo(context));
//        }

        if (TokenManager.getToken(context) != null) {
            builder.header(Constants.HEADER_TOKEN_NAME, TokenManager.getToken(context));
        }
        Request request = builder.build();

        long t1 = System.nanoTime();
        MilierLog.i("API", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        if (response.isSuccessful()) {
            // 检查是否是登录请求
            checkLogin(response);
            checkLogout(response);

            String requestUrl = response.request().method() + " " + response.request().url().toString();
            int responseCode = response.code();
            String errorMessage = responseCode + " on " + requestUrl;
            MilierLog.e("API_ERROR", errorMessage);
        }

        long t2 = System.nanoTime();
        MilierLog.i("API", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

    /**
     * 检查是否是登录请求,并存储token
     *
     * @param response
     */
    private void checkLogin(Response response) {
        String token = null;
        String path = response.request().url().uri().getPath();
        if (path.indexOf(MilierConfig.LOGIN_URL) >= 0) {
            token = response.header(Constants.HEADER_TOKEN_NAME, null);
            if (token != null) {
                TokenManager.setToken(context, token);
            }
        }
        Log.i("checkLogin", "token " + token);
    }

    /**
     * 检查是否是注销请求，并清除TOKEN
     * @param response
     */
    private void checkLogout(Response response) {
        if (response.request().url().uri().getPath().indexOf(MilierConfig.LOGOUT_URL) >= 0) {
            Log.i("syso", "removeToken");
            TokenManager.removeToken(context);
        }
    }
}

