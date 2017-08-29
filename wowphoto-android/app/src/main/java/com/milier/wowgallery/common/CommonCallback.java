package com.milier.wowgallery.common;

/**
 * 通用回调接口
 * Created by guofe on 2015/9/11.
 */
public abstract class CommonCallback<T> {

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable t);

    public void whatever(){};
}