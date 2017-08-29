package com.milier.wowgallery.net;

import com.milier.wowgallery.common.converter.BigDecimalAdapter;
import com.milier.wowgallery.common.converter.DateJsonAdapter;
import com.milier.wowgallery.common.converter.VoidJsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by konie on 16-8-22.
 */
public class MoshiFactor {

    public static Moshi create() {
        Moshi moshi = new Moshi.Builder().add(new DateJsonAdapter())
                .add(new VoidJsonAdapter())
                .add(new BigDecimalAdapter())
                .build();

        return moshi;
    }
}
