package com.milier.wowgallery.common;

/**
 * 通用请求接口
 * Created by guofe on 2015/9/21.
 */
public interface CommonCall<T> {

    /**
     * 同步执行
     * @return
     */
    T execute() throws Throwable;

    /**
     * 异步回调
     * @param callback
     */
    void enqueue(CommonCallback<T> callback);

    /**
     * 取消请求
     */
    void cancel();

}
