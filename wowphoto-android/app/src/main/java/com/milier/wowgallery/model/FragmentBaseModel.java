package com.milier.wowgallery.model;


import com.milier.wowgallery.bean.WelfareBean;

import java.util.List;

import rx.Single;

public interface FragmentBaseModel {
    /**
     * 根据类型Type 获取首页展示条目
     */
    Single<List<WelfareBean.ResultsBean>> getAtlasBeanList( int pageSize, int page);

}
