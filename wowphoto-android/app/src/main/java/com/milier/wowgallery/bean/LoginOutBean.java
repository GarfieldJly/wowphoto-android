package com.milier.wowgallery.bean;

import java.io.Serializable;

/**
 * Created by jly on 2017/1/6.
 */
public class LoginOutBean implements Serializable {
    private boolean autoLogout;

    private String msg;

    public boolean isAutoLogout() {
        return autoLogout;
    }

    public void setAutoLogout(boolean autoLogout) {
        this.autoLogout = autoLogout;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginOutBean() {
    }

    public LoginOutBean(boolean autoLogout, String msg) {
        this.autoLogout = autoLogout;
        this.msg = msg;
    }
}
