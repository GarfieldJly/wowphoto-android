package com.milier.wowgallery.net;

/**
 * Created by guofe on 2016/1/20.
 */

import com.milier.wowgallery.utils.MilierLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NullHostNameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        MilierLog.w("NullHostNameVerifier", "Approving certificate for " + hostname);
        return true;
    }

}