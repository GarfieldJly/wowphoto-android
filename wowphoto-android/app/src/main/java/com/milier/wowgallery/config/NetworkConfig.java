package com.milier.wowgallery.config;

/**
 * Created by xubo on 16/8/12.
 */
public class NetworkConfig {

//     public static String ip = "192.168.30.196:8443"; //测试，域名
//      public static String ip = "47.88.191.81:9443"; //测试
        public static String ip = "47.88.136.40:9843"; //正式服务器地址
    /**
     * http
     */
    public static String apiHost = "http://" + ip;

    /**
     * https
     */
    public static String apiHttpsHost = "https://" + ip;

    /**
     * 服务器路径
     */
    public static String apiPath = "";

    /**
     * 证书名字
     */
    public static final String cerFilename = "mycerts.cer";

    public static String getApiBasePath() {
        return apiHost + apiPath + "/";
    }

    public static String getApiHttpsBasePath() {
        return apiHttpsHost + apiPath + "/";
    }

}
