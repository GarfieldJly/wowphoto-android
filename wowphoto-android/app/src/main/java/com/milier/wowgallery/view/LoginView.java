package com.milier.wowgallery.view;

/**
 * Created by jly on 2016/8/26.
 */
public interface LoginView {

    void showProgress();

    void dismissProgress();

    void showLoginMessage(String loginInfo);
}
