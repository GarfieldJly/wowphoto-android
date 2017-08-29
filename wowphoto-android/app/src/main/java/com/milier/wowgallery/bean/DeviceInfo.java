package com.milier.wowgallery.bean;

import java.io.Serializable;

/**
 * Created by jly on 2017/1/6.
 */
public class DeviceInfo implements Serializable{
    private Long id;
    private String deviceName;
    private String IMEI;
    private String IMSI;
    private String androidVersion;
    private String appVersion;
    private String iccId;
    private String channelId;
    private String others;

    public DeviceInfo() {
    }

    public DeviceInfo(Long id) {
        this.id = id;
    }

    public DeviceInfo(Long id, String deviceName, String IMEI, String IMSI, String androidVersion, String appVersion, String iccId, String channelId, String others) {
        this.id = id;
        this.deviceName = deviceName;
        this.IMEI = IMEI;
        this.IMSI = IMSI;
        this.androidVersion = androidVersion;
        this.appVersion = appVersion;
        this.iccId = iccId;
        this.channelId = channelId;
        this.others = others;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

}
