package com.milier.wowgallery.ui.adapter;

import java.io.Serializable;

/**
 * Created by jly on 2017/7/4.
 */

public class ImageTestBean implements Serializable {

    /**
     * count : 10438
     * fcount : 0
     * galleryclass : 3
     * id : 995
     * img : /ext/161027/c3f11f510ab9bc302739140cef619be6.jpg
     * rcount : 0
     * size : 11
     * time : 1477575845000
     * title : OL白领美女秘书大胆肉丝美腿高跟迷人养眼写真
     */

    private int count;
    private int fcount;
    private int galleryclass;
    private int id;
    private String img;
    private int rcount;
    private int size;
    private long time;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
