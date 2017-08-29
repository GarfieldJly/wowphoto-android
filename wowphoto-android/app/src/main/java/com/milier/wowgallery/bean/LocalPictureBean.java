package com.milier.wowgallery.bean;

import java.io.Serializable;

/**
 * Created by jly on 2017/1/9.
 */
public class LocalPictureBean implements Serializable{

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * 图片的原始objectName
     */
    private String objectName;

    /**
     * 图片的url地址
     */
    private String picUrl;

    /**
     * 顺序
     */
    private int order;

    /**
     * 宽度
     */
    private int width;

    /**
     * 高度
     */
    private int high;

    public LocalPictureBean(String objectName, String picUrl, int order, int width, int high) {
        this.objectName = objectName;
        this.picUrl = picUrl;
        this.order = order;
        this.width = width;
        this.high = high;
    }

    public LocalPictureBean(String objectName, String picUrl, int width, int high) {
        this.objectName = objectName;
        this.picUrl = picUrl;
        this.width = width;
        this.high = high;
    }

    public LocalPictureBean(String picUrl, int order) {
        this.picUrl = picUrl;
        this.order = order;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public String getPicUrl() {
        return picUrl;
    }


    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
