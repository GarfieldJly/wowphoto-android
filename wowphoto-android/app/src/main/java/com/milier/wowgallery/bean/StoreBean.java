package com.milier.wowgallery.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jly on 2017/1/4.
 */
public class StoreBean implements Serializable {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public StoreBean(List<Long> ids) {
        this.ids = ids;
    }

    public StoreBean() {
    }
}
