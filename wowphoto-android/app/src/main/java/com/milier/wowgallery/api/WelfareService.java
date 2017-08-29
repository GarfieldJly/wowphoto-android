package com.milier.wowgallery.api;

import com.milier.wowgallery.bean.WelfareBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jly on 2017/8/29.
 */

public interface WelfareService {
//    @GET("Android/10/1")
//    Call<GankBean> getGankBeanList();

    @GET("福利/{pageSize}/{page}")
    Call<WelfareBean> getWelfareBeanList(@Path("pageSize")int pageSize, @Path("page") int page);
}
