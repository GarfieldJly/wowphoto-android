package com.milier.wowgallery.common.converter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.math.BigDecimal;

/**
 * Created by jly on 2017/1/22.
 * java 中的大数据
 */
public class BigDecimalAdapter {
    @FromJson
    BigDecimal eventFromJson(String data) {
        BigDecimal bigDecimal = new BigDecimal(data);
        return bigDecimal;
    }

    @ToJson
    String eventToJson(BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }
}
